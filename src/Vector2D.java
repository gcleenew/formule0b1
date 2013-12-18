public class Vector2D {
    
    public double x;
    public double y;
    
    Vector2D(double x, double y) {
        
        this.x = x;
        this.y = y;
        
    }
    
    public Vector2D add(Vector2D v) {
        
        return new Vector2D(x + v.x, y + v.y);
    }
    
    public Vector2D sub(Vector2D v) {
        
        return new Vector2D(x - v.x, y - v.y);
    }
    
    public double scalarProduct(Vector2D v) {
        
        return x * v.x + y * v.y;
        
    }
    
    public Vector2D scalar(double lambda) {
     
        return new Vector2D(lambda * x, lambda * y);
    }
    
    public double norm() {

        return (double)Math.sqrt(x*x + y*y);
    }
}
