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