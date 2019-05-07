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
package src.main.java.Pricewatcher.base;

import src.main.java.Pricewatcher.model.Item;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;

/** A special panel to display the detail of an item. */

@SuppressWarnings("serial")
class ItemManager extends JPanel {

    private DecimalFormat df = new DecimalFormat("##.##");
    private List<Item> itemList;

    /**
     * Notifies when one of the icons on the viewPage has been clicked by the user.
     */
    public interface ClickListener {

        /**
         * Called when clicked.
         */
        void clicked();
    }

    /**
     * Directory for where the images and icons will be stored.
     */
    private final static String IMAGE_DIR = "/image/";

    /**
     * Listening for a click
     */
    private ClickListener listener;

    /**
     * Creates new instance of the itemView
     * @param itemList - the list of items.
     */
    public ItemManager(List<Item> itemList) {
        setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (isViewPageClicked(e.getX(), e.getY()) && listener != null) {
                    listener.clicked();
                }
            }
        });
        this.itemList = itemList;
    }

    /**
     * Set the view-page click listener.
     */
    public void setClickListener(ClickListener listener) {
        this.listener = listener;

    }

    /**
     * Method will repaint the graphics on the screen. Adding the information of the item, to the panel.
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);


        int x = 20, y = 20;
        for (Item item : this.itemList) {
            // g.drawImage(getImage(), x, y, 10, 10, null);
            // y += 20;

            g.drawString("Name: " + item.getName(), x, y);
            y += 20;
            g.drawString("URL: " + item.getURL(), x, y);
            y += 20;
            g.drawString("Price: $" + df.format(item.getCurrentPrice()), x, y);
            y += 20;
            g.drawString("Change: " + df.format(item.getPriceChange()) + "%", x, y);
            y += 20;
            g.drawString("Date Added: " + item.getDateAdded() + " ($" + df.format(item.getCurrentPrice()) + ")", x, y);
            y += 40;
        }
    }

    /**
     * This method will return true if the screen coordinates (x,y) are inside of the viewPage icon. False otherwise.
     * @param x - the x position
     * @param y - the y position
     * @return new Rectangle object.
     */
    private boolean isViewPageClicked(int x, int y) {
        return new Rectangle(20, 30, 30, 20).contains(x, y);
    }

    /**
     * This method will return the image that is stored.
     * @return the image
     */
    public Image getImage() {
        Image image = null;
        try {
            for (Item item : this.itemList) {
                URL url = new URL(item.getURL());
                image = ImageIO.read(url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
