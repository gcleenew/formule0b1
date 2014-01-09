import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
 
public class MenuPanel extends JPanel {


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString("sjk", 110, 110);
    }

}
