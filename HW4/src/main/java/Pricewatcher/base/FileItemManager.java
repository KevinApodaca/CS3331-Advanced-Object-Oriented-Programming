package src.main.java.Pricewatcher.base;

import org.json.simple.JSONObject;
import src.main.java.Pricewatcher.model.Item;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileItemManager extends ItemManager {
    /**
     * Creates new instance of the itemView
     *
     * @param itemList
     */
    public FileItemManager(List<Item> itemList) {
        super(itemList);
    }

    public static void createJsonFile(JSONObject obj){
        System.out.println("\nCREATE FILE");
        try (FileWriter file = new FileWriter("src/main/java/Pricewatcher/saved_items.json")) {
            //File Writer creates a file in write mode at the given location
            file.write(obj.toJSONString());
            file.write("\n");

            //write function is use to write in file,
            //here we write the Json object in the file
            file.flush();

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(obj);
    }
}
