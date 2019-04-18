/**
 * CS 3331 Advanced Object Oriented Programming
 * @author Kevin Apodaca, Imani Martin
 * @since 4/17/19
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
package PriceWatcher.base;

import PriceWatcher.model.Item;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/** A special panel to display the detail of an item. */

@SuppressWarnings("serial")
public class ItemView extends JPanel {

    DecimalFormat df = new DecimalFormat("##.##");
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
 * @param itemList
 */
    public ItemView(List<Item> itemList) {
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
            g.drawString("Date Added: " + item.getDateAdded() + " ($" + df.format(item.getOriginalPrice()) + ")", x, y);
            y += 40;
        }
    }

/**
 * This method will return true if the screen coordinates (x,y) are inside of the viewPage icon. False otherwise.
 * @param x
 * @param y
 * @return
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
