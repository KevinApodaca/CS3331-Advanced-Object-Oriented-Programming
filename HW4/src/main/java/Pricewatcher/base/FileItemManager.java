package src.main.java.Pricewatcher.base;

//import org.json.simple.JSONObject;
import src.main.java.Pricewatcher.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileItemManager extends ItemView{
    /**
     * Creates new instance of the itemView
     *
     * @param itemList
     */
    public FileItemManager(List<Item> itemList) {
        super(itemList);
    }

//    public JSONObject toJson(){
//        Map<String, Object> map = new HashMap<>();
//        map.put("name", name);
//        map.put("currentPrice", currentPrice);
//    }
}
