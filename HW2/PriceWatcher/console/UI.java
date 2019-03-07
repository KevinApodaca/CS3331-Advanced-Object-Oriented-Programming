package PriceWatcher.console;

import PriceWatcher.model.Item;
import PriceWatcher.model.PriceFinder;

import java.util.List;
import java.util.Scanner;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.InputMismatchException;

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
