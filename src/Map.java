import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Map {
    public static final int TILE_SIZE = 32;

    private ArrayList<Object> listObject;
    private Tile[][] tiles;
    
    private Terrain[] terrains;

    private Vector2D camera;

    Map(String file_name) {

        listObject = new ArrayList<Object>();

        camera = new Vector2D(0, 0);
        String data = "";
        try {
            Scanner fileScanner = new Scanner(new FileReader(file_name));

            while (fileScanner.hasNextLine()) {
                data += fileScanner.nextLine() + ";";
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File \"" + file_name + "\" not found.");
        }

        String[] dataArray = data.split(";");
        
        int height = dataArray.length;
        int width = dataArray[0].length();
    
        terrains = new Terrain[3];
        terrains[0] = new Terrain("../ressources/textures/road2.png", 5);
        terrains[1] = new Terrain("../ressources/textures/grassset.png", 0.1);
        terrains[2] = new Terrain("../ressources/textures/waterset.png", 0.1);
        
        tiles = new Tile[width - 1][height - 1];


        for (int i = 0; i < width - 1; i++) {
            for(int j = 0; j < height - 1; j++) {
                //tiles[i][j] = new SimpleTile(t);
                int c1 = Character.getNumericValue(dataArray[j].charAt(i));
                int c2 = Character.getNumericValue(dataArray[j].charAt(i+1));
                int c3 = Character.getNumericValue(dataArray[j+1].charAt(i+1));
                int c4 = Character.getNumericValue(dataArray[j+1].charAt(i));
                                
                tiles[i][j] = new Tile(c1, c2, c3, c4);
                
                /*switch (dataArray[j].charAt(i)) {
                    case '.':
                        tiles[i][j] = new Tile(grass);
                        break;
                    case ' ':
                        tiles[i][j] = new Tile(road);
                        break;
                    case 'x':
                        tiles[i][j] = new Tile(rock);
                        break;
                    default:
                        tiles[i][j] = new Tile(grass);
                }*/
            }
        }
    }

    public void addObject(Object obj) {
        listObject.add(obj);
    }

    public void draw(Graphics2D g) {

        drawTiles(g);

        AffineTransform transform;

        for (Object obj : listObject) {
            if (obj.isVisible()) {

                int width = obj.getSprite().getWidth(null);
                int height = obj.getSprite().getHeight(null);

                int x = (int) (obj.getPosition().x - width/2 + camera.x);
                int y = (int) (obj.getPosition().y - height/2 + camera.y);
                double rotation = obj.getRotation();

                transform = new AffineTransform();
                transform.translate(x, y);
                transform.rotate(Math.toRadians(rotation), width/2, height/2);

                g.drawImage(obj.getSprite(), transform, null);

                // DEBUG : Draw hitbox

                if (obj.isCollidable()) {
                    for (Circle c : obj.getHitbox()) {
                        int cx = (int) (obj.getPosition().x + camera.x + c.position.x -c.ray);
                        int cy = (int) (obj.getPosition().y + camera.y + c.position.y -c.ray);
                        g.drawOval(cx, cy, (int) c.ray*2, (int) c.ray*2);
                    }
                }
            }
        }
    }

    public void drawTiles(Graphics2D g) {
        for (int i = 0; i < tiles.length; i++) {
            for(int j = 0; j < tiles[i].length; j++) {
                tiles[i][j].draw(g, camera, i, j, this);
            }
        }
    }

    public boolean testCollision(Object o) {
        for (Object obj : listObject) {
            if (obj.isCollidable() && obj != o) {
                for (Circle cObj : obj.getHitbox()) {
                    for (Circle cO : o.getHitbox()) {

                        Vector2D pO = o.getPosition().add(cO.position);
                        Vector2D pObj = obj.getPosition().add(cObj.position);
                        double d = pO.sub(pObj).norm();

                        if (d <= cO.ray + cObj.ray) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public double getFrictionAt(Vector2D position) {
        int x = (int) position.x/TILE_SIZE;
        int y = (int) position.y/TILE_SIZE;
        return tiles[x][y].getFriction(new Vector2D(position.x-TILE_SIZE*x, position.y-TILE_SIZE*y));
    }

    public void centerCamera(Object obj) {
        camera.x = Game.WINDOW_WIDTH/2 - obj.getPosition().x;
        camera.y = Game.WINDOW_HEIGHT/2 - obj.getPosition().y;
    }

    public Vector2D getScreenPosition(Object obj) {
        int x = (int) (obj.getPosition().x + camera.x);
        int y = (int) (obj.getPosition().y + camera.y);
        return new Vector2D(x, y);
    }
    
    public Terrain getTerrain(int level) {
        return terrains[level];
    }
}
