import java.io.File;
import java.io.IOException;

import java.awt.Image;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;

public class Object {

    protected Vector2D position;
    protected double rotation = 0;

    protected Image sprite;
    protected boolean visible = true;

    protected boolean collidable = false;
    protected Circle[] hitbox;

    Object(Vector2D position, String sprite_path) {
        this.position = position;
        this.sprite = sprite;

        try {                
            sprite = ImageIO.read(new File(sprite_path));
        } catch (IOException e) {
             System.out.println("Sprite \"" + sprite_path + "\" not found.");
        }
    }

    public void draw(Graphics2D g) {
        System.out.println("aa");
    }

    public boolean isCollidable() {

        return collidable;
    }

    public boolean isVisible() {

        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public double getRotation() {

        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;

        if (collidable) {
            for (Circle c : hitbox) {
                double norm = c.position.norm();
                Vector2D dir = c.position.scalar(1/norm);

                double cos = Math.cos(Math.toRadians(rotation));
                double sin = Math.sin(Math.toRadians(rotation));

                double x = dir.x * cos - dir.y * sin;
                double y = dir.x * sin + dir.y * cos;

                System.out.println(c.position.x + " " + c.position.y + "/" + norm + "/" + rotation + "/" + cos + " " + sin + "/" + x + " " + y);

                c.position = new Vector2D(x * norm, y * norm);
            }
        }
    }

    public Image getSprite() {

        return sprite;
    }

    public Circle[] getHitbox() {

        return hitbox;
    }

    public void setHitbox(Circle[] hitbox) {
        this.hitbox = hitbox;
        collidable = true;
    }
}