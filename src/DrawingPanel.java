import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;

import java.io.File;
import java.io.IOException;

import java.awt.Image;

import javax.imageio.ImageIO;

public class DrawingPanel extends JPanel {

    Map map;
    
    protected Image hud;

    DrawingPanel(Map map) {
        this.map = map;
        
        try {
            hud = ImageIO.read(new File("../ressources/textures/hud.png"));
        } catch (IOException e) {
            System.out.println("Texture \"" + "ressources/textures/hud.png" + "\" not found.");
        }
        
        setBackground(Color.BLACK);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        map.draw(g2);
        
        g2.drawImage(hud, 5, Game.panelSizeY-150, null);
    }
}
