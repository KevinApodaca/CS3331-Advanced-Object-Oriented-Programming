/**
 * CS 3331 Advanced Object Oriented Programming
 * @author Kevin Apodaca, Imani Martin
 * @since 4/4/19
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

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.swing.*;

public class WebPriceFinder extends PriceFinder{

    private static JProgressBar progressBar;

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
            displayProgressBar();
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

    /* Create progress bar */
    private void displayProgressBar(){
        JFrame f = new JFrame("Locating price...");
        JPanel panel = new JPanel();
        progressBar = new JProgressBar(0,100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        panel.add(progressBar);
        f.add(progressBar);

        f.setSize(200,200);
        f.setVisible(true);

        fillProgressBar();

    }

    /* Add text to progress bar */
    private static void fillProgressBar(){
        int i = 0;
        try {
            while (i <= 100) {
                // set text according to the level to which the bar is filled
                if (i > 30 && i < 70)
                    progressBar.setString("locating prices...");
                else if (i > 70)
                    progressBar.setString("almost there");
                else
                    progressBar.setString("loading items...");

                // fill the menu bar
                progressBar.setValue(i + 10);

                // delay the thread
                Thread.sleep(3000);
                i += 20;
            }
        }
        catch (Exception e) {
        }
    }
}
