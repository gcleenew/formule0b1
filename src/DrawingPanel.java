import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
 
public class DrawingPanel extends JPanel {

    Map map;

    DrawingPanel(Map map) {
        this.map = map;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        map.draw(g2);
    }
}