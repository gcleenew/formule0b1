import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;

public class Map {

    private ArrayList<Object> listObject;

    Map(String file_name) {
        try {
            Scanner fileScanner = new Scanner(new FileReader(file_name));

            while (fileScanner.hasNextLine()) {
                System.out.println(fileScanner.nextLine());
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File \"" + file_name + "\" not found.");
        }
    }

    public void addObject(Object obj) {
        listObject.add(obj);
    }
}