import java.awt.Graphics2D;

public class SimpleTile implements ITile {
	protected Terrain terrain;
    protected int code;

	SimpleTile(Terrain terrain) {
		this.terrain = terrain;
	}

    public void draw(Graphics2D g, Vector2D camera, int i, int j) {
        g.drawImage(terrain.getTexture(), (int) camera.x+Map.TILE_SIZE*i, (int) camera.y+Map.TILE_SIZE*j, null);
    }

    public double getFriction(Vector2D position) {
    	return terrain.getFriction();
    }
}
