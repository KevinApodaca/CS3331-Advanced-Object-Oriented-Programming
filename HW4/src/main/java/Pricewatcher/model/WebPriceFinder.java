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

    /* Finds the price of the item */
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

    /* Check if a url is valid */
    public boolean checkIfValid(String url){
        try{
            new URL(url).toURI();
            return true;
        }catch(Exception e){
            warn("Invalid URL entered!");
            return false;
        }
    }

    /* Finds the host of the website */
    private String getUrlHost(String url) throws MalformedURLException {
        URL hostUrl = new URL(url);
        return hostUrl.getHost();
    }

    /* Tokenizes prices if there are multiple prices */
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

    /** Show the given warning or error message in a modal dialog. */
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