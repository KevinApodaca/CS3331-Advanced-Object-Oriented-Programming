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
package src.main.java.Pricewatcher.console;

import src.main.java.Pricewatcher.model.Item;
import src.main.java.Pricewatcher.model.PriceFinder;

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
		String testItemInitialPrice = "61.13";
		
		Item testItem = new Item(testItemName, testItemInitialPrice, testItemUrl, testDateAdded);
		List<Item> testItemList = new ArrayList<>();
		testItemList.add(testItem);
		return testItemList;
	}
	/**
	 * Method will be used to display items to console.
	 * @param itemList - the list of items.
	 */
	public static void displayItems(List<Item> itemList) {
		/* Displays the item information of each item on the terminal.*/
		for (Item item: itemList) {
			System.out.println("Name: " + item.getName());
			System.out.println("URL: " + item.getURL());
			System.out.println("Price: " + item.getCurrentPrice());
			System.out.println("Change: " + item.getPriceChange() + "%");
			System.out.println("Date Added: " + item.getDateAdded() + "($" + String.format("%.2f", item.getCurrentPrice()) + ")");
		}
	}
	/**
	 * Method will be used to find the choice the user wants. 
	 * @param reader - the scanner
	 * @return the choice.
	 */
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
	/**
	 * Method will launch the website to the default web browser.
	 * @param item - the item clicked on.
	 */
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
