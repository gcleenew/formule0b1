import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
 
public class DrawingPanel extends JPanel {

    Map map;

    DrawingPanel(Map map) {
        this.map = map;
        
        setBackground(Color.BLACK);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        map.draw(g2);
    }
}
