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

import java.math.BigDecimal;
/**
 * This class just calculates a new price for our item.
 */
public class PriceFinder {
	
	public PriceFinder() {
	}
/**
 * Randomly generating a new price for our item.
 * @param url - the URL of the item.
 * @return a new price.
 */	
	public double getNewPrice (String url) {
		return roundPrice(25.0 + Math.random() * (100.0 - 25.0));
	}
/**
 * Rounding our price to two decimal places, instead of multiple decimals like the previous version of this software.
 * @param price - the price of the item.
 * @return rounded price.
 */
	public double roundPrice (double price){
		BigDecimal bd = new BigDecimal(price);
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		return bd.doubleValue();
	}
}
