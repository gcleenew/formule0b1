import java.io.File;
import java.io.IOException;

import java.awt.Image;

import javax.imageio.ImageIO;

public class Terrain {

	protected Image texture;
	protected double friction;

	Terrain(String texturePath, double friction) {
		this.friction = friction;
		texture = ImageLoader.load(texturePath);
	}

	public double getFriction () {
		return friction;
	}

	public Image getTexture () {
		return texture;
	}
}
