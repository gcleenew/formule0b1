import java.io.File;
import java.io.IOException;

import java.awt.Image;

import javax.imageio.ImageIO;

public class Terrain {

	protected Image texture;
	protected double friction;

	Terrain(String texturePath, double friction) {
		this.friction = friction;
		try {
            texture = ImageIO.read(new File(texturePath));
        } catch (IOException e) {
             System.out.println("Texture \"" + texturePath + "\" not found.");
        }
	}

	public double getFriction () {
		return friction;
	}

	public Image getTexture () {
		return texture;
	}
}