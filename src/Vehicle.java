public class Vehicle extends Object {

    private Vector2D speed;
    private Vector2D acceleration;
    private Map map;

    Vehicle(Map map, Vector2D position, String sprite_path) {
        super(position, sprite_path);

        this.map = map;

        speed = new Vector2D(0, 0);
        acceleration = new Vector2D(0, 0);
    }

    public void move(double dt) {

        double k = map.getFrictionAt(position);

        Vector2D oldPosition = new Vector2D(position.x, position.y);
        double oldRotation = rotation;

        if (k < 0) { // Smoke On the water
            acceleration = new Vector2D(0, 0);
        }
        else {
            speed = speed.add(acceleration.sub(speed.scalar(k)).scalar(dt));
        }
        
        position = position.add(speed.scalar(dt));
        calculateRotation();
        
        if (map.testCollision(this) || position.x < 0 || position.y < 0 || position.x > map.getMapSizeX() || position.y > map.getMapSizeY()) {
            acceleration = new Vector2D(0, 0);
            speed = new Vector2D(0, 0);
            position = new Vector2D(oldPosition.x, oldPosition.y);
            setRotation(oldRotation);
        }
    }

    public Vector2D getNextPosition(double dt, double dturn) {

        Vector2D speedTmp = speed;
        Vector2D positionTmp = position;
        double k;

        for (double t = 0; t < dturn; t += dt) {
            k = map.getFrictionAt(positionTmp);
            if (k >= 0) {
                speedTmp = speedTmp.add(acceleration.sub(speedTmp.scalar(k)).scalar(dt));
            }
            
            positionTmp = positionTmp.add(speedTmp.scalar(dt));
        }

        return positionTmp;
    }

    public void calculateRotation() {
        double x = speed.x/(speed.norm());
        double y = speed.y/(speed.norm());

        double newRotation = (Math.asin(y) > 0 ) ? Math.acos(x) : -Math.acos(x);

        if (!Double.isNaN(newRotation)) {
            setRotation(Math.toDegrees(newRotation));
        }
    }

    public Vector2D getSpeed() {
        return speed;
    }

    public Vector2D getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector2D acceleration) {
        this.acceleration = acceleration;
    }
}
