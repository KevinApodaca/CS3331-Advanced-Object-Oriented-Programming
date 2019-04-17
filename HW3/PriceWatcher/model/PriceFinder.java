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
package PriceWatcher.model;

import java.math.BigDecimal;
/**
 * This class just calculates a new price for our item.
 */
public class PriceFinder {
	
	public PriceFinder() {
	}
/**
 * Randomly generating a new price for our item.
 * @param url
 * @return a new price.
 */	
	public double getNewPrice (String url) {
		return roundPrice(25.0 + Math.random() * (100.0 - 25.0));

	}
/**
 * Rounding our price to two decimal places, instead of multiple decimals like the previous version of this software.
 * @param price
 * @return rounded price.
 */
	public double roundPrice (double price){
		BigDecimal bd = new BigDecimal(price);
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		return bd.doubleValue();
	}
	
}