import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Map {
    public static final int TILE_SIZE = 16;

    private ArrayList<Object> listObject;
    private ITile[][] tiles;

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

        Terrain grass = new Terrain("../ressources/textures/grass2.png", 5);
        Terrain road = new Terrain("../ressources/textures/road.png", 0.1);
        Terrain rock = new Terrain("../ressources/textures/road2.png", 0.1);

        tiles = new ITile[width][height];

        for (int i = 0; i < tiles.length; i++) {
            for(int j = 0; j < tiles[i].length; j++) {
                //tiles[i][j] = new SimpleTile(t);
                switch (dataArray[j].charAt(i)) {
                    case '.':
                        tiles[i][j] = new SimpleTile(grass);
                        break;
                    case ' ':
                        tiles[i][j] = new SimpleTile(road);
                        break;
                    case 'x':
                        tiles[i][j] = new SimpleTile(rock);
                        break;
                    default:
                        tiles[i][j] = new SimpleTile(grass);
                }
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
                tiles[i][j].draw(g, camera, i, j);
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
}
