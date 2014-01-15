import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;

import java.io.File;
import java.io.IOException;

import java.awt.Image;

import javax.imageio.ImageIO;
 
public class MenuPanel extends JPanel {

	protected Image back;

	MenuPanel () {
        try {
            back = ImageIO.read(new File("../ressources/textures/background_txt.png"));
        } catch (IOException e) {
            System.out.println("Texture \"" + "ressources/textures/background.png" + "\" not found.");
        }
	}

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(back, 0, 0, this);

        /*g2.setColor(Color.WHITE);
        g2.drawString("Press A, B, C or D to start", 100, 700);

        g2.drawString("A - Sonama (2 players)", 200, 200);
        g2.drawString("B - Flavescence (2 players)", 200, 220);
        g2.drawString("C - Turn (2 players)", 200, 240);
        g2.drawString("D - River (1 player)", 200, 260);*/
    }

}
