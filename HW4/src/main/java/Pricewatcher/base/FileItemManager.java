/**
In this assignment, you are to extend your HW3 code and create the
ultimate version of the Price Watcher application that supports
network and data persistence. Your app shall meet all the relevant
requirements from the previous homework assignments as well as the
following new ones.

R1. The application shall find the price of a watched item from the
    item's Web page. Remember that the URL of an item is provided by
    the user when the item is added to the watch list.
    
    a. It shall inform the user if the price of an item can't be found
       (e.g., malformed or non-existing URL).

    b. It shall support item pages from at least three different
       online stores.

R2. The application shall persist watched items. The items should be
    stored in an external storage to so that they can be available
    when the application is closed and launched later.

R3. You should separate network and database operations into separate
    modules (or classes) to decouple them from the rest of the code.
    Consider introducing new subclasses of the PriceFinder and
    ItemManager classes.
*/
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
     * @param itemList - the list of items.
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
