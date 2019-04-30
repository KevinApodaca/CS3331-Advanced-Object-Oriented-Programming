package src.main.java.Pricewatcher.model;

import src.main.java.Pricewatcher.base.Main;
import src.main.java.Pricewatcher.model.Item;
import java.io.IOException;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;


public class WebPriceFinder extends PriceFinder{

    /** Given a url, this class will find the price of an item **/

    public WebPriceFinder(){

    }

    public Double getWebPrice(String url){
        System.out.println("Running...");
        System.out.println("url: " + url);
        Document document;
        // Elements price = new Elements();
        // if(checkIfValid(url)){
            try {
                document = Jsoup.connect(url).get();
                System.out.println("Title: " + document.title());
                Elements info = document.getElementsByTag("p");
                System.out.println("Printing paras....");
                for(Element paragraph : info){
                    System.out.println(paragraph.text());
                }
            } catch (IOException | NullPointerException e) {
                System.out.println("Unable to get price");
                e.printStackTrace();
            }
            System.out.println("Price Extracted!");
            return 2.0;
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
