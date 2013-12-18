import java.io.File;
import java.io.IOException;

import java.awt.Image;
import javax.imageio.ImageIO;

public class Object {

    protected Vector2D position;
    protected double rotation = 0;

    protected Image sprite;
    protected boolean visible = true;

    protected boolean collidable = false;
    protected Circle[] hitbox;

    Object(Vector2D position, String spritePath) {
        this.position = position;

        try {
            sprite = ImageIO.read(new File(spritePath));
        } catch (IOException e) {
             System.out.println("Sprite \"" + spritePath + "\" not found.");
        }
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
        double diff = rotation - this.rotation;
        this.rotation = rotation;

        if (collidable) {
            for (Circle c : hitbox) {
                double norm = c.position.norm();
                Vector2D dir = c.position.scalar(1/norm);

                double cos = Math.cos(Math.toRadians(diff));
                double sin = Math.sin(Math.toRadians(diff));

                double x = dir.x * cos - dir.y * sin;
                double y = dir.x * sin + dir.y * cos;

                //System.out.println(c.position.x + " " + c.position.y + "/" + norm + "/" + rotation + "/" + cos + " " + sin + "/" + x + " " + y);

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