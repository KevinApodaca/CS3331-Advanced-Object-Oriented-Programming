/**
In this assignment, you are to extend your HW3 code and create the
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

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

/**
 * This will render each of the items of our list onto the panel
 */

class ItemListRenderer extends JLabel implements ListCellRenderer<Item>{
    DecimalFormat df = new DecimalFormat("###.##");
    public ItemListRenderer(){
        setOpaque(true);
    }

    /**
     * Calling all our items and getting their information to display on the screen.
     */

    @Override
    public Component getListCellRendererComponent(JList<? extends Item> list, Item item, int index, boolean isSelected, boolean cellHasFocus){

        String listItems = "<html> Name: " + item.getName() + "<br/> URL: " + item.getURL() + "<br/> Price: " + item.getCurrentPrice() + "<br/> Change: " + item.getPriceChange() + "% <br/> Date Added: " + item.getDateAdded() + " (Initial Price: " + item.getCurrentPrice() + ")";
        setText(listItems);

        if(isSelected){
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }else{
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        revalidate();
        repaint();
        return this;

    }
}
