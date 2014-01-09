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

public class Game extends JFrame implements MouseListener, KeyListener, ActionListener {

    public static final int WINDOW_WIDTH = 1024;
    public static final int WINDOW_HEIGHT = 768;
    
    public static final double ACCELERATION_FACTOR = 50;

    public static final int DT = 30;
    public static final int DTURN = 1000;

    public static void main(String[] args) {
        System.out.println("Formule 0b1!");
        //Menu menu = new Menu();
        System.out.println("done");
        //Map map = new Map("../ressources/Map/Sonama2.txt");

        Game g = new Game();
    }


    private Map map;
    private JPanel panel;

    private Vehicle car;
    private Object target;

    private int iTurn;
    private Timer turnTimer;
    
    private double time = 0;
    private JLabel timeLabel;

    private boolean ingame = false;

    public Game() {
        
        //panel = new DrawingPanel(map);
        //timeLabel = new JLabel("");
        //panel.add(timeLabel);

        addMouseListener(this);
        addKeyListener(this);

        this.setTitle("Formule 0b1");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setLocationRelativeTo(null);               
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        panel = new MenuPanel();
        setContentPane(panel);
        setVisible(true); 
        //this.setVisible(true);
    }

    public void launchGame() {
        
        ingame = true;
        map = new Map("../ressources/Map/Sonama2.txt");
        panel = new DrawingPanel(map);
        setContentPane(panel);
        setVisible(true); 
        car = map.getCar();
        
        target = new Object(new Vector2D(0, 0), "../ressources/sprites/target.png");
        target.setVisible(false);

        map.addObject(target);
        map.addObject(car);

        map.centerCamera(car);
        iTurn = 0;
        turnTimer = new Timer(DT, this);

    }

    public void turn() {
        car.move((double) DT/1000);
        time += (double) DT/1000;
        timeLabel.setText(Double.toString(time));
        //car.setAcceleration(new Vector2D(0, 0));
        map.centerCamera(car);
        refresh();
    }

    public void refresh() {
        panel.repaint();
    }

    // Action events
    public void actionPerformed(ActionEvent e) {

        turn();

        iTurn++;
        if (iTurn == DTURN/DT) {
            iTurn = 0;
            turnTimer.stop();
        }
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
                case KeyEvent.VK_SPACE:
                    launchGame();
                    break;
            }
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    // Mouse events
    public void mousePressed(MouseEvent e) {
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

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

}
