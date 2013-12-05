public class Object {

    private Vector2D position;
    private double rotation;

    private boolean collidable = false;
    private Circle[] hitbox;

    public void draw() {

    }

    public boolean isCollidable() {

        return collidable;
    }
}