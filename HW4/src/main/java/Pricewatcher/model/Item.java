/**
 * CS 3331 Advanced Object Oriented Programming
 * @author Kevin Apodaca, Imani Martin
 * @since 4/17/19
 * In this assignment, you are to extend your HW3 code and create the
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
package src.main.java.Pricewatcher.model;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import src.main.java.Pricewatcher.base.FileItemManager;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Item {

	private String name;
	private String originalPrice,currentPrice,priceChange;
	private String url, dateAdded;

	/**
	 * Here we initialize our item with the following parameters.
	 * @param name - the name of the item.
	 * @param price - the price of the item.
	 * @param url - the url of the item.
	 * @param dateAdded - the date the item was added.
	 */
	public Item(String name, String price, String url, String dateAdded) {
		this.name = name;
		this.currentPrice = price;
		this.originalPrice = price;
		this.url = url;
		this.priceChange = "0.0";
		this.dateAdded = dateAdded;

		JSONObject obj = toJson();

	}
/**
 * Method will conver item data to JSON.
 * @return the new object.
 */
	public JSONObject toJson(){
		System.out.println("\nTO_JSON");

		Map<String, Object> map = new HashMap<>();
		map.put("name", name);
		map.put("price", currentPrice);
		map.put("url", url);
		map.put("date", dateAdded);

		JSONObject obj = new JSONObject(map);

		JSONArray list = new JSONArray();

		obj.put("item list", list);

		FileItemManager.createJsonFile(obj);
		return obj;
	}
/**
 * Method will read data from JSON
 * @param obj - the object 
 * @return the new item.
 * @throws IOException
 */
	public static Item fromJson(JSONObject obj) throws IOException {
		JSONParser parser = new JSONParser();
		String name = "", price = "", url = "", date = "";
		System.out.println("\nFROM_JSON");

		try{
			// convert json string to JSONObjectr
			obj = (JSONObject) parser.parse(new FileReader("src/main/java/Pricewatcher/saved_items.json"));

			// display values using keys
			// obj = (JSONObject)obj;
			System.out.println(obj);

			name = (String) obj.get("name");
			System.out.println(name);

			price = (String) obj.get("price");
			System.out.println(price);

			url = (String) obj.get("url");
			System.out.println(url);

			date = (String) obj.get("date");
			System.out.println(date);

			JSONArray arr = (JSONArray)obj.get("item");

			Iterator<String> iterator = arr.iterator();
			while (iterator.hasNext()){
				System.out.println(iterator.next());
			}

		}catch (FileNotFoundException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}catch (ParseException e){
			e.printStackTrace();
		}

		Item item = new Item(name, price, url, date);
		return item;
	}
/**
 * Method will read JSON file and parse for information.
 * @return the list
 * @throws IOException
 * @throws ParseException
 */
	public static Object readJsonFile() throws IOException, ParseException {
		FileReader reader = null;
		JSONParser parser = null;
		try {
			reader = new FileReader("src.main.java.Pricewatcher/itemWatcher.json");
			parser = new JSONParser();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return parser.parse(reader);
	}

/**
 * Here we do our setters and getters of different methods for the item class.
 *
 */
//	public void updatePrice(double newPrice) {
//		this.priceChange =  ((newPrice - this.originalPrice) / (this.originalPrice)) * 100;
//		this.currentPrice = newPrice;
//	}
/**
 * Method will show original price.
 * @return a new instance of original price.
 */
//	public double getOriginalPrice() {
//		return this.originalPrice;
//	}
	/**
	 * Method will show current price of an item.
	 * @return the current price of some item.
	 */
	public String getCurrentPrice() {
		return this.currentPrice;
	}
	/**
	 * Method will get the price change of an item.
	 * @return the change in price of an item.
	 */
	public String getPriceChange() {
		return this.priceChange;
	}
	/**
	 * Method will get the URL of an item.
	 * @return the string URL of the item.
	 */
	public String getURL() {
		return this.url;
	}
	/**
	 * Method will get the name of the item.
	 * @return the string name of a selected item.
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * Method will show the date an item was added.
	 * @return date item was added.
	 */
	public String getDateAdded() {
		return this.dateAdded;
	}
	/**
	 * Method will take in an original price for an item and set it as the price for the item.
	 * @param originalPrice - the original price of the item.
	 */
	public void setOriginalPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}
	/**
	 * Method will take in a price of the item and set it as the current price of that item.
	 * @param currentPrice - the current price of the item.
	 */
	public void setCurrentPrice(String currentPrice) {
		this.currentPrice = currentPrice;
	}
	/**
	 * Method will take in a price for an item and set it as the change of price for it.
	 * @param priceChange - the change in the price.
	 */
	public void setPriceChange(String priceChange) {
		this.priceChange = priceChange;
	}
	/**
	 * Method takes in a string URL and sets it as the URL of the item.
	 * @param url - the URL of the item.
	 */
	public void setURL(String url) {
		this.url = url;
	}
	/**
	 * Method will take in a string name of the item and set it as the name of the item.
	 * @param name - the name of the item.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Method will take in a string of the date the item was added and set it as the item's date that it was added to the app.
	 * @param dateAdded - the date the item was added.
	 */
	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}


}
