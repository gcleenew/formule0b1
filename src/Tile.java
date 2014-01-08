import java.awt.Graphics2D;
import java.util.Arrays;

public class Tile {    
    protected int c1, c2, c3, c4;

	Tile(int c1, int c2, int c3, int c4) {
		this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.c4 = c4;
	}

    public void draw(Graphics2D g, Vector2D camera, int i, int j, Map map) {
        
        int[] levels = {c1, c2, c3, c4};
        int tmp = lowestLevel();
        Arrays.sort(levels);
        
        g.drawImage(map.getTerrain(tmp).getTexture(), (int) camera.x+Map.TILE_SIZE*i, (int) camera.y+Map.TILE_SIZE*j, null);
        for (int e : levels) {
            if (tmp != e) {
                System.out.println(e);
                tmp = e;
            }
        }
        //g.drawImage(terrain.getTexture(), (int) camera.x+Map.TILE_SIZE*i, (int) camera.y+Map.TILE_SIZE*j, null);
    }

    public double getFriction(Vector2D position) {
    	return 1; //return terrain.getFriction();
    }
    
    public int lowestLevel() {
        return Math.min(Math.min(c1, c2), Math.min(c3, c4));
    }
    
    public int getCode(int level) {
        int code = 0;
        if (c1 == level) { code += c1; }
        if (c2 == level) { code += 2*c2; }
        if (c3 == level) { code += 4*c3; }
        if (c4 == level) { code += 8*c4; }
        
        return code;
    }
}
