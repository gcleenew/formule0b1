import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.JPanel;

import java.awt.event.MouseListener;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

import  sun.audio.*;
import  java.io.*;

public class Game extends JFrame implements MouseListener, KeyListener, ActionListener {

    public static final int WINDOW_WIDTH = 1024;
    public static final int WINDOW_HEIGHT = 768;

    public static int panelSizeX;
    public static int panelSizeY;

    public static final double ACCELERATION_FACTOR = 50;

    public static final int DT = 25;
    public static final int DTURN = 1000;

    public static void main(String[] args) {
        System.out.println("Formule 0b1!");

        Game g = new Game();
    }

    private Map map;
    private JPanel panel;

    private Vehicle car;
    private Vehicle[] players;
    private int nbPlayers = 0;
    private int currentPlayer = 0;
    private Object target;
    private Object finish;

    private int iTurn;
    private Timer turnTimer;
    
    private int[] tick;
    private JLabel timeLabel;

    private boolean ingame = false;
    
    private boolean win = false;

    public Game() {
        
        //panel = new DrawingPanel(map);

        addMouseListener(this);
        addKeyListener(this);

        this.setTitle("Formule 0b1");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setLocationRelativeTo(null);               
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        panel = new MenuPanel();
        setContentPane(panel);

        setVisible(true);

        panelSizeX = panel.getSize().width;
        panelSizeY = panel.getSize().height;
    }

    public void launchGame(String mapPath, int nbPlayers) {
        
        this.nbPlayers = nbPlayers;
        
        ingame = true;
        map = new Map("../ressources/Map/" + mapPath);
        panel = new DrawingPanel(map);
        setContentPane(panel);

        timeLabel = new JLabel("");
        panel.add(timeLabel);

        setVisible(true); 
        //car = map.getCar();
        
        players = new Vehicle[nbPlayers];
        tick = new int[nbPlayers];
        for (int i = 0; i < nbPlayers; i++) {
            players[i] = new Vehicle(map, map.getStartPosition(), "../ressources/sprites/chocobo_shadow.png");
            Circle[] hitbox = new Circle[2];
            hitbox[0] = new Circle(new Vector2D(-20, 0), 16);
            hitbox[1] = new Circle(new Vector2D(16, 0), 16);
            players[i].setHitbox(hitbox);
            
            players[i].collidable = false;

            map.addObject(players[i]);
            
            tick[i] = 0;
        }
        car = players[0];
        car.collidable = true;
        
        finish = map.getFinish();
        
        target = new Object(new Vector2D(0, 0), "../ressources/sprites/target.png");
        target.setVisible(false);

        map.addObject(target);
        //map.addObject(car);

        map.centerCamera(car);
        iTurn = 0;
        turnTimer = new Timer(DT, this);
        
        playMusic();

    }

    public void turn() {
        if (!win) {
            if (map.testCollision(car, finish)) {
                win = true;
                timeLabel.setText("Player " + (currentPlayer + 1) + " win ! Score : " + Double.toString(tick[currentPlayer]*DT/100/10.0));
            }
            else {
                car.move((double) DT/1000);
                tick[currentPlayer] += 1;
                showTime();
                map.centerCamera(car);
            }
            refresh();
        }
    }

    public void refresh() {
        panel.repaint();
    }

    // Action events
    public void actionPerformed(ActionEvent e) {

        turn();

        iTurn++;
        if (iTurn >= DTURN/DT) {
            iTurn = 0;
            turnTimer.stop();
            
            if (!win) {
                changePlayer();
            }
        }
    }
    
    public void changePlayer() {
        players[currentPlayer].collidable = false;
        currentPlayer = (currentPlayer + 1) % nbPlayers;
        players[currentPlayer].collidable = true;
        car = players[currentPlayer];
        map.centerCamera(car);
        showTime();
    }
    
    public void showTime() {
        timeLabel.setText("Player " + (currentPlayer + 1) + " - Time : " + Double.toString(tick[currentPlayer]*DT/100/10.0));
    }

    // Keboard events
    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (ingame) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_D:
                    car.setAcceleration(new Vector2D(ACCELERATION_FACTOR, 0));
                    
                    target.setPosition(car.getNextPosition((double) DT/1000, (double) DTURN/1000));
                    target.setVisible(true);
                    break;
                case KeyEvent.VK_E:
                    car.setAcceleration(new Vector2D(ACCELERATION_FACTOR, -ACCELERATION_FACTOR));
                    
                    target.setPosition(car.getNextPosition((double) DT/1000, (double) DTURN/1000));
                    target.setVisible(true);
                    break;
                case KeyEvent.VK_Q:
                    car.setAcceleration(new Vector2D(-ACCELERATION_FACTOR, 0));
                    
                    target.setPosition(car.getNextPosition((double) DT/1000, (double) DTURN/1000));
                    target.setVisible(true);
                    break;
                case KeyEvent.VK_C:
                    car.setAcceleration(new Vector2D(ACCELERATION_FACTOR, ACCELERATION_FACTOR));
                    
                    target.setPosition(car.getNextPosition((double) DT/1000, (double) DTURN/1000));
                    target.setVisible(true);
                    break;
                case KeyEvent.VK_Z:
                    car.setAcceleration(new Vector2D(0, -ACCELERATION_FACTOR));
                    
                    target.setPosition(car.getNextPosition((double) DT/1000, (double) DTURN/1000));
                    target.setVisible(true);
                    break;
                case KeyEvent.VK_A:
                    car.setAcceleration(new Vector2D(-ACCELERATION_FACTOR, -ACCELERATION_FACTOR));
                    
                    target.setPosition(car.getNextPosition((double) DT/1000, (double) DTURN/1000));
                    target.setVisible(true);
                    break;
                case KeyEvent.VK_W:
                    car.setAcceleration(new Vector2D(-ACCELERATION_FACTOR, ACCELERATION_FACTOR));
                    
                    target.setPosition(car.getNextPosition((double) DT/1000, (double) DTURN/1000));
                    target.setVisible(true);
                    break;
                case KeyEvent.VK_S:
                    car.setAcceleration(new Vector2D(0, 0));
                    
                    target.setPosition(car.getNextPosition((double) DT/1000, (double) DTURN/1000));
                    target.setVisible(true);
                    break;
                case KeyEvent.VK_X:
                    car.setAcceleration(new Vector2D(0, ACCELERATION_FACTOR));
                    
                    target.setPosition(car.getNextPosition((double) DT/1000, (double) DTURN/1000));
                    target.setVisible(true);
                    break;
                case KeyEvent.VK_SPACE:
                    target.setVisible(false);
                    turnTimer.start();
                    break;
            }
            refresh();
        } else {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    launchGame("Sonama2.txt", 1);
                    break;
                case KeyEvent.VK_B:
                    launchGame("Flavescence.txt", 1);
                    break;
                case KeyEvent.VK_C:
                    launchGame("Turn.txt", 1);
                    break;
            }
        }
    }
    
    public void playMusic() {
        try {
            InputStream in = new FileInputStream("../ressources/Music/chocobo.wav");
            AudioStream as = new AudioStream(in);         
            AudioPlayer.player.start(as);     
                   
        } catch (IOException e) {
            System.out.println("Cannot play music");
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    // Mouse events
    public void mousePressed(MouseEvent e) {
        if (ingame) {
            int mouseX = panel.getMousePosition().x;
            int mouseY = panel.getMousePosition().y;

            Vector2D mousePosition = new Vector2D(mouseX, mouseY);
            Vector2D carPosition = map.getScreenPosition(car);

            Vector2D acceleration = mousePosition.sub(carPosition).scalar(1);

            car.setAcceleration(acceleration);

            target.setPosition(car.getNextPosition((double) DT/1000, (double) DTURN/1000));
            target.setVisible(true);
            //turnTimer.start();
            panel.repaint();
        }
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

}
