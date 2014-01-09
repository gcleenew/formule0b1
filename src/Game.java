import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

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

        Map map = new Map("../ressources/Map/Sonama2.txt");

        Game g = new Game(map);
    }

    private Map map;
    private DrawingPanel panel;

    private Vehicle car;
    private Object target;

    private int iTurn;
    private Timer turnTimer;
    
    private double time = 0;
    private JLabel timeLabel;

    public Game(Map map) {
        this.map = map;
        panel = new DrawingPanel(map);
        
        timeLabel = new JLabel("");
        panel.add(timeLabel);

        car = new Vehicle(map, new Vector2D(WINDOW_WIDTH/2, WINDOW_HEIGHT/2), "../ressources/sprites/chocobo_shadow.png");
        Circle[] hitbox = new Circle[2];
        hitbox[0] = new Circle(new Vector2D(-8, 0), 8);
        hitbox[1] = new Circle(new Vector2D(8, 0), 8);
        car.setHitbox(hitbox);
        
        target = new Object(new Vector2D(0, 0), "../ressources/sprites/target.png");
        target.setVisible(false);

        map.addObject(target);
        map.addObject(car);

        for (int i = 0; i < 20; i++) {
            int x = (int)(Math.random() * WINDOW_WIDTH);
            int y = (int)(Math.random() * WINDOW_HEIGHT);
            Object tree = new Object(new Vector2D(x, y), "../ressources/sprites/tree2.png");
            hitbox = new Circle[1];
            hitbox[0] = new Circle(new Vector2D(0, 0), 15);

            tree.setHitbox(hitbox);
            map.addObject(tree);
        }

        map.centerCamera(car);

        addMouseListener(this);
        addKeyListener(this);

        iTurn = 0;
        turnTimer = new Timer(DT, this);

        this.setTitle("Formule 0b1");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setLocationRelativeTo(null);               
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel);

        this.setVisible(true);
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
