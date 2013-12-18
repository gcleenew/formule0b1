import java.awt.Graphics2D;

public class SimpleTile implements ITile {
	protected Terrain terrain;

	SimpleTile(Terrain terrain) {
		this.terrain = terrain;
	}

    public void draw(Graphics2D g, Vector2D camera) {
        // TODO : dessin
    }
}