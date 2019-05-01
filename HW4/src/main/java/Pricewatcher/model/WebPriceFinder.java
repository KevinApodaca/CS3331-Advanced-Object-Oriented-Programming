package src.main.java.Pricewatcher.model;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import javax.swing.event.DocumentEvent;


public class WebPriceFinder extends PriceFinder{

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
            System.out.println("Loading...");
            document = Jsoup.connect(url).get();
            System.out.println("Title: " + document.title());
            System.out.println("Host: " + urlHost);

            /* Price is parsed depending on host of site */
            if(urlHost.equals("www.amazon.com")){
                itemPrice = document.select("span.p13n-sc-price:contains($)");
                System.out.println("itemPrice: " + itemPrice.text());
                priceExtracted = parseURL(itemPrice);

            }else{
                itemPrice = document.select("span:contains($)");
                priceExtracted = itemPrice.text();
            }

            System.out.println("Web Price: " + priceExtracted);
        } catch (IOException | NullPointerException e) {
            warn("Unable to retrieve price!");
            e.printStackTrace();
        }
        System.out.println("Price retrieved!");
        return priceExtracted;
    }

    /* Check if a url is valid */
    public boolean checkIfValid(String url){
        System.out.println("Checking if url valid");
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

}