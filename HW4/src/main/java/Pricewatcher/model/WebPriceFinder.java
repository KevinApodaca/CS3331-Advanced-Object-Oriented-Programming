/**
 * CS 3331 Advanced Object Oriented Programming
 * @author Kevin Apodaca, Imani Martin
 * @since 4/4/19
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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.swing.*;

public class WebPriceFinder extends PriceFinder{

    /** Given a url, this class will find the price of an item **/

    public WebPriceFinder(){

    }

/**
 * Method finds the price of the item from the web.
 * @param url - the website url of the item.
 * @return the extracted price.
 * @throws ArrayIndexOutOfBoundsException
 */
    public String getWebPrice(String url) throws ArrayIndexOutOfBoundsException{
        System.out.println("\nRunning...");
        System.out.println("url: " + url);
        Document document;
        Elements itemPrice;
        String urlHost = "";
        String priceExtracted = "";
        try {
            urlHost = getUrlHost(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Loading...");
            document = Jsoup.connect(url).get();
            System.out.println("Title: " + document.title());
            System.out.println("Host: " + urlHost);

            /* Price is parsed depending on host of site */
            if(urlHost.equals("www.amazon.com")) {
                itemPrice = document.select("span.p13n-sc-price:contains($)");
                priceExtracted = parseURL(itemPrice);
            }else if(urlHost.equals("www.bestbuy.com")){
                itemPrice = document.select("span[aria-hidden]:contains($)");
            }else if(urlHost.equals("www.ebay.com")){
                itemPrice = document.select("span.mfe-pull-left mfe-strikethrough:contains($)");
            }
            else{
                itemPrice = document.select("span:contains($)");
                priceExtracted = itemPrice.text();
            }

            System.out.println("Web Price: " + priceExtracted);
        } catch (IOException | NullPointerException e) {
            warn("Unable to retrieve price!");
            System.out.println("Price Unavailable!");
            priceExtracted = "Unavailable";
        }
        System.out.println("Search Complete!");
        return priceExtracted;
    }

    /**
     * Method checks if the URL is valid.
     * @param url - the website url 
     * @return if valid.
     */
    public boolean checkIfValid(String url){
        try{
            new URL(url).toURI();
            return true;
        }catch(Exception e){
            warn("Invalid URL entered!");
            return false;
        }
    }

    /**
     * Method will find the host of the website from the URL
     * @param url - the web url of item.
     * @return the host.
     * @throws MalformedURLException
     */
    private String getUrlHost(String url) throws MalformedURLException {
        URL hostUrl = new URL(url);
        return hostUrl.getHost();
    }

    /**
     * Method will tokenize prices in the event that there are multiple.
     * @param itemPrice - the prices of the items.
     * @return the parsed prices.
     */
    public String parseURL(Elements itemPrice){
        String[] tokens = itemPrice.text().trim().split("\\s");
        String parsedPrice;

        if(tokens.length < 1) {
            // Case 0: Nothing
            System.out.println("\nCase 1");
            parsedPrice = "N/A";
        }
        else if(tokens.length == 1)
            // Case 1: 1 element
            parsedPrice = tokens[0];
        else
            // Case 2: n+2 elements
            parsedPrice = tokens[1];

        return parsedPrice;
    }

    /**
     * Method will display a warning to the user in case something goes wrong with the JPanel.
     */
    private void warn(String msg) {
        JFrame warningWindow = new JFrame();
        warningWindow.setLocationRelativeTo(null);

        JOptionPane.showMessageDialog(warningWindow, msg, "Error Message", JOptionPane.ERROR_MESSAGE);
    }

}