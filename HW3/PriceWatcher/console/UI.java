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
package PriceWatcher.console;

import PriceWatcher.model.Item;
import PriceWatcher.model.PriceFinder;

import java.awt.*;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.InputMismatchException;
/**
 * This was the first version of the software, written to work through the command line.
 */
public class UI {

	public static List<Item> createItemList () {
		// Creates the hard-coded item list used for the terminal version of price watcher
		String testItemUrl = "https://www.bestbuy.com/site/samsung-ue590-series-28-led-4k-uhd-monitor-black/5484022.p?skuId=5484022";
		String testItemName = "LED monitor";
		String testDateAdded = "3/4/19";
		double testItemInitialPrice = 61.13;
		
		Item testItem = new Item(testItemName, testItemInitialPrice, testItemUrl, testDateAdded);
		List<Item> testItemList = new ArrayList<>();
		testItemList.add(testItem);
		return testItemList;
	}
	
	public static void displayItems(List<Item> itemList) {
		/* Displays the item information of each item on the terminal.*/
		for (Item item: itemList) {
			System.out.println("Name: " + item.getName());
			System.out.println("URL: " + item.getURL());
			System.out.println("Price: " + item.getCurrentPrice());
			System.out.println("Change: " + item.getPriceChange() + "%");

			System.out.println("Date Added: " + item.getDateAdded() + "($" + String.format("%.2f", item.getOriginalPrice()) + ")");
		}
	}
	
	public static int getUserChoice(Scanner reader) {
		/* Reads user input to determine next price watcher action.*/
		int userChoice = 0;
		System.out.println("Enter 1 (to check price), 2 (to view page), or -1 to quit.");
		try {
			userChoice = reader.nextInt();
		}
		catch (InputMismatchException e) {
			// Reads the erroneous input, clearing the scanner for new input
			reader.nextLine();
		}
		return userChoice;
	}
	
	public static void launchWebsite(Item item) {
		/* Launches item web sites in user's default browser*/
		if (Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();
			try {
				desktop.browse(new URI(item.getURL()));
				System.out.println("Webpage opened in your default browser.");
			}
			catch (IOException | URISyntaxException e) {
				System.out.println("Unable to access webpage for " + item.getName() + ".");
			}
		}
		else {
			System.out.println("Unable to access webpage for " + item.getName() + ".");
		}
	}

	public static void run() {
		 /* Runs price watcher on the terminal.*/
		List<Item> itemList = createItemList();
		System.out.println("Welcome to Price Watcher!");

		displayItems(itemList);
		
		Scanner reader = new Scanner(System.in);
		int userChoice = getUserChoice(reader);

		while (userChoice != -1) {
			if (userChoice == 1) {
				for (Item item: itemList) {
					PriceFinder finder = new PriceFinder();
					item.updatePrice(finder.getNewPrice(item.getURL()));
				}
				displayItems(itemList);
				userChoice = getUserChoice(reader);
			}
			else if (userChoice == 2) {
				for (Item item: itemList) {
					launchWebsite(item);
				}
				userChoice = getUserChoice(reader);
			}
			else {
				System.out.println("Not a valid input.");
				userChoice = getUserChoice(reader);
			}
		}
		reader.close();
		System.out.println("Thank you for using this program!");
	}

}
