/**
 * CS 3331 Advanced Object Oriented Programming
 * @author Kevin Apodaca, Imani Martin
 * @since 4/17/19
 * In this assignment, you are to extend your HW2 program to support
multiple items. Focus on the design of the UI. You will learn and
become familar with several Swing widgets such as JDialog, JList (or
JTable), JMenu, JPopupMenu, JMenuBar and JToolBar. Your application
shall meet all the relevant requirements from HW2 as well as the
following new ones.
R1. Provide a way to manage the list of items whose prices are to be
watched over. The user should be able to add a new item, remove an
existing item, and change an existing item, e.g., rename the item
or change its URL (see R3, R4 and R5 below).
R2. Display all watched items along with their price changes. Consider
using a JList (or JTable) for this.
R3. Use custom dialogs (subclasses of JDialog) to add and change an
item in the watch list.
R4. Improve the user interface by proving a menu and a tool bar to (a)
add a new item and (b) to check the current prices of all
items. For each menu item, provide an icon, a mnemonic and an
accelerator. For each tool bar button, use an icon and provide a
tool tip.
R5. Provide a popup menu to manipulate an indiviual item. Your popup
menu shall include menu items for:
- Checking the current price
- Viewing its webpage
- Editing it (change the name and URL; see R1 above)
- Removing it (see R1 above)
R6. Use JavaDoc to document your classes. Write a Javadoc comment for
each class/interface, field, constructor and method.
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
