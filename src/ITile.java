import java.awt.Graphics2D;

public interface ITile {
	public void draw(Graphics2D g, Vector2D camera, int i, int j);
	public double getFriction(Vector2D position);

}
