package src.main.java.Pricewatcher.model;

import java.io.IOException;
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
    public String getWebPrice(String url){
        System.out.println("Running...");
        System.out.println("url: " + url);
        Document document;
        // Elements priceDiv = new Elements();
         Element info = null;
        // String info = "";
        try {
            document = Jsoup.connect(url).get();
            System.out.println("Title: " + document.title());

            // priceDiv = document.getElementsByClass("pb-current-price");
            // info = document.select("p span:contains($)"); // test
            info = document.select("div.pb-current-price:contains($)").first();
            //info = document.select("div.pb-current-price:contains($)").first().text();
            System.out.println("Printing Price....");
            // for(Element paragraph : info){
                // System.out.println(paragraph.text());
             System.out.println(info.text());
            //}
        } catch (IOException | NullPointerException e) {
            warn("Unable to retrieve price!");
            e.printStackTrace();
        }
        return info.text();
    }

    /* Check if a url is valid */
    public boolean checkIfValid(String url){

        System.out.println("Checking if url valid");
        try{
            new URL(url).toURI();
            return true;
        }catch(Exception e){
            warn("Invalid URL entered!");
            System.out.println("Invalid url");
            return false;
        }
    }

    /** Show the given warning or error message in a modal dialog. */
    private void warn(String msg) {
        JFrame warningWindow = new JFrame();
        warningWindow.setLocationRelativeTo(null);

        JOptionPane.showMessageDialog(warningWindow, msg, "Error Message", JOptionPane.ERROR_MESSAGE);
    }

}
