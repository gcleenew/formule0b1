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
    
    protected Image hud, hud2;

    DrawingPanel(Map map) {
        this.map = map;
        
        hud = ImageLoader.load("textures/hud.png");
        hud2 = ImageLoader.load("textures/hud2.png");
        
        setBackground(Color.BLACK);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        map.draw(g2);
        
        g2.drawImage(hud, 5, Game.panelSizeY-150, null);
        g2.drawImage(hud2, Game.panelSizeX/2-140, 0, null);
    }
}
