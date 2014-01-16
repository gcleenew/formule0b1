import java.io.File;
import java.io.IOException;

import java.awt.Image;

import javax.imageio.ImageIO;

public class ImageLoader {
    public static Image load(String path) {
        Image img = null;
        try {
            img = ImageIO.read(new File("../ressources/" + path));
        } catch (IOException e) {
            System.out.println("Texture \"" + path + "\" not found.");
        }
        
        return img;
    }
}
