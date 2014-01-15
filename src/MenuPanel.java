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
        back = ImageLoader.load("textures/background_txt.png");
	}

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(back, 0, 0, this);
    }

}
