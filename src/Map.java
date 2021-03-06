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

    private Vector2D startPosition;
    private ArrayList<Object> listFinish;

    private int mapSizeX;
    private int mapSizeY;

    Map(String file_name) {

        listObject = new ArrayList<Object>();
        listFinish = new ArrayList<Object>();

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

        String[] dataAll = data.split("=");
        String[] dataArray = dataAll[0].split(";");
        String[] dataObject = dataAll[1].split(";");

        int height = dataArray.length;
        int width = dataArray[0].length();

        mapSizeX = (width-1)*TILE_SIZE;
        mapSizeY = (height-1)*TILE_SIZE;
    
        terrains = new Terrain[3];
        terrains[0] = new Terrain("textures/road2.png", 0.1);
        terrains[1] = new Terrain("textures/grassset.png", 2);
        terrains[2] = new Terrain("textures/waterset.png", -1);
        
        tiles = new Tile[width - 1][height - 1];


        for (int i = 0; i < width - 1; i++) {
            for(int j = 0; j < height - 1; j++) {
                int c1 = Character.getNumericValue(dataArray[j].charAt(i));
                int c2 = Character.getNumericValue(dataArray[j].charAt(i+1));
                int c3 = Character.getNumericValue(dataArray[j+1].charAt(i+1));
                int c4 = Character.getNumericValue(dataArray[j+1].charAt(i));
                                
                tiles[i][j] = new Tile(c1, c2, c3, c4, this);
            }
        }

        double x;
        double y;
        Circle[] hitbox;
        
        for (int i = 1; i < dataObject.length; i++) {
            String[] dataLine = dataObject[i].split(" ");
            x = Double.parseDouble(dataLine[1]);
            y = Double.parseDouble(dataLine[2]);
            switch (dataLine[0]) {
                case "T":
                    Object tree = new Object(new Vector2D(x, y), "sprites/tree2.png");
                    hitbox = new Circle[1];
                    hitbox[0] = new Circle(new Vector2D(0, 0), 27);

                    tree.setHitbox(hitbox);
                    addObject(tree);
                    break;
                    
                case "R":
                    Object rock = new Object(new Vector2D(x, y), "sprites/rock2.png");
                    hitbox = new Circle[1];
                    hitbox[0] = new Circle(new Vector2D(0, 0), 15);

                    rock.setHitbox(hitbox);
                    addObject(rock);
                    break;

                case "L":
                    Object lily = new Object(new Vector2D(x, y), "sprites/lily.png");
                    addObject(lily);
                    break;
                    
                case "V":
                    startPosition = new Vector2D(x, y);
                    break;
                    
                case "F":
                    Object finish = new Object(new Vector2D(x, y), "sprites/Finish.png");
                    hitbox = new Circle[2];
                    hitbox[0] = new Circle(new Vector2D(0, -8), 8);
                    hitbox[1] = new Circle(new Vector2D(0, 8), 8);
                    finish.setHitbox(hitbox);
                    finish.collidable = false;
                    
                    addObject(finish);
                    listFinish.add(finish);
                    break;
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

                /*if (obj.isCollidable()) {
                    for (Circle c : obj.getHitbox()) {
                        int cx = (int) (obj.getPosition().x + camera.x + c.position.x -c.ray);
                        int cy = (int) (obj.getPosition().y + camera.y + c.position.y -c.ray);
                        g.drawOval(cx, cy, (int) c.ray*2, (int) c.ray*2);
                    }
                }*/
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
            if (obj.isCollidable() && testCollision(obj, o) && obj != o) {
                return true;
            }
        }
        return false;
    }

    public boolean testCollision(Object o, Object o2) {
        for (Circle cO2 : o2.getHitbox()) {
            for (Circle cO : o.getHitbox()) {

                Vector2D pO = o.getPosition().add(cO.position);
                Vector2D pO2 = o2.getPosition().add(cO2.position);
                double d = pO.sub(pO2).norm();

                if (d <= cO.ray + cO2.ray) {
                    return true;
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
        camera.x = Game.panelSizeX/2 - obj.getPosition().x;
        camera.y = Game.panelSizeY/2 - obj.getPosition().y;
        if (camera.x > 0) {
            camera.x = 0;
        }
        if (-camera.x + Game.panelSizeX > mapSizeX) {
            camera.x = -mapSizeX + Game.panelSizeX;
        }
        if (camera.y > 0) {
            camera.y = 0;
        }
        if (-camera.y + Game.panelSizeY > mapSizeY) {
            camera.y = -mapSizeY + Game.panelSizeY;
        }
    }

    public Vector2D getScreenPosition(Object obj) {
        int x = (int) (obj.getPosition().x + camera.x);
        int y = (int) (obj.getPosition().y + camera.y);
        return new Vector2D(x, y);
    }
    
    public Terrain getTerrain(int level) {
        return terrains[level];
    }

    public Vector2D getStartPosition() {
        return startPosition;
    }

    public int getMapSizeX() {
        return mapSizeX;
    }

    public int getMapSizeY() {
        return mapSizeY;
    }

    public ArrayList<Object> getListFinish() {
        return listFinish;
    }
}
