import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Arrays;

public class Tile {
    
    public static final int FACTOR = 2;
    
    protected int c1, c2, c3, c4;

    private Map map;

	Tile(int c1, int c2, int c3, int c4, Map map) {
		this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.c4 = c4;
        this.map = map;
	}

    public void draw(Graphics2D g, Vector2D camera, int i, int j) {
        
        int[] levels = {c1, c2, c3, c4};
        int tmp = lowestLevel();
        Arrays.sort(levels);
        
        int x = (int) camera.x+Map.TILE_SIZE*i;
        int y = (int) camera.y+Map.TILE_SIZE*j;
        
        g.drawImage(map.getTerrain(tmp).getTexture(), x, y, x + Map.TILE_SIZE, y + Map.TILE_SIZE, 0, 0, Map.TILE_SIZE/FACTOR, Map.TILE_SIZE/FACTOR, null);
        for (int e : levels) {
            if (tmp != e) {
                tmp = e;
                
                Image img = map.getTerrain(e).getTexture();
                
                int dx = 0;
                int dy = 0;
                
                switch (getCode(e)) {
                    case 1:
                        dx = 1; dy = 3;
                        break;
                    case 2:
                        dx = 0; dy = 3;
                        break;
                    case 3:
                        dx = 2; dy = 1;
                        break;
                    case 4:
                        dx = 0; dy = 2;
                        break;
                    case 5:
                        dx = 3; dy = 0;
                        break;
                    case 6:
                        dx = 0; dy = 1;
                        break;
                    case 7:
                        dx = 2; dy = 3;
                        break;
                    case 8:
                        dx = 1; dy = 2;
                        break;
                    case 9:
                        dx = 1; dy = 1;
                        break;
                    case 10:
                        dx = 3; dy = 1;
                        break;
                    case 11:
                        dx = 3; dy = 3;
                        break;
                    case 12:
                        dx = 2; dy = 0;
                        break;
                    case 13:
                        dx = 3; dy = 2;
                        break;
                    case 14:
                        dx = 2; dy = 2;
                        break;
                    case 15:
                        dx = 0; dy = 0;
                        break;
                }
                g.drawImage(img, x, y, x + Map.TILE_SIZE, y + Map.TILE_SIZE, dx * Map.TILE_SIZE/FACTOR, dy * Map.TILE_SIZE/FACTOR, (dx+1) * Map.TILE_SIZE/FACTOR, (dy +1) * Map.TILE_SIZE/FACTOR, null);
            }
        }
    }

    public double getFriction(Vector2D position) {
    	return (map.getTerrain(c1).getFriction()+map.getTerrain(c2).getFriction()+map.getTerrain(c3).getFriction()+map.getTerrain(c4).getFriction())/4;
    }
    
    public int lowestLevel() {
        return Math.min(Math.min(c1, c2), Math.min(c3, c4));
    }
    
    public int getCode(int level) {
        int code = 0;
        if (c1 == level) { code += 1; }
        if (c2 == level) { code += 2; }
        if (c3 == level) { code += 4; }
        if (c4 == level) { code += 8; }
        
        return code;
    }
}
