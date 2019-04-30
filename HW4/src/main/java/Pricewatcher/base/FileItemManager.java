package src.main.java.Pricewatcher.base;

import netscape.javascript.JSObject;
import org.json.JSONObject;

import src.main.java.Pricewatcher.model.Item;

import java.util.Date;
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
//        map.put("url", url);
//        map.put("date", date);
//        return new JSONObject(map);
//    }
//
//    public static Item fromJson(JSObject obj){
//        String name = obj.getString("name");
//        float currentPrice = (float) obj.getDouble("currentPrice");
//        String url = obj.getString("url");
//        Date date = (String) obj.getString("date");
//        Item item = new Item(name, currentPrice, url, date.toString());
//        // ...
//        return item;
//    }
}
