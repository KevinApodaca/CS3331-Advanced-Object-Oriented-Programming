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
import PriceWatcher.model.PriceFinder;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DecimalFormat;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Creating our JFrame using java swing.
 */
@SuppressWarnings("serial")
public class Main extends JFrame {

/**
 * New instances of the items which we will check the price of.
 */
    private Item[] items = {
            new Item("LED monitor", 61.13, "https://www.bestbuy.com/site/samsung-ue590-series-28-led-4k-uhd-monitor-black/5484022.p?skuId=5484022", "3/4/19"),

            new Item("Wireless Charger", 11.04, "https://www.amazon.com/dp/B07DBX67NC/ref=br_msw_pdt-1?_encoding=UTF8&smid=A294P4X9EWVXLJ&pf_rd_m=ATVPDKIKX0DER&pf_rd_s=&pf_rd_r=R830R3XMQCGASSTMNCAC&pf_rd_t=36701&pf_rd_p=19eb5a6f-0aea-4094-a094-545fd76f6e8d&pf_rd_i=desktop", "4/15/19"),

            new Item("Persona 5 PS4", 301.99, "https://www.amazon.com/Persona-PlayStation-Take-Your-Heart-Premium/dp/B01GKHJPAC/ref=sr_1_5?keywords=persona%2B5&qid=1555473381&s=gateway&sr=8-5&th=1", "4/16/2019")
    };

    private JList itemList = new JList(items);
    private DefaultListModel model;


    /** Default dimension of the dialog box. */
    private final static Dimension DEFAULT_SIZE = new Dimension(500, 300);

    /** Special panel to display the watched item. */
    private PriceWatcher.base.ItemView itemView;

    // private List<Item> itemList;
    private PriceFinder priceFinder;

    /** Message bar to display various messages. */
    private JLabel msgBar = new JLabel(" ");

    /** Create a new dialog. */
    public Main() {
        this(DEFAULT_SIZE);
    }

/**
 * Here we createa  new dialog box with the dimensions passed and we configure all settings needed for things to be displayed properly.
 * @param dim
 */
    public Main(Dimension dim) {
        super("PRICE WATCHER");
        // new instance of the priceFinder.
        this.priceFinder = new PriceWatcher.model.PriceFinder();

        setSize(dim);
        configureUI();
        // setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setResizable(true);
        showMessage("Welcome!");
    }

    private static class MyComponentListener extends ComponentAdapter {
        private int width, height;

        public MyComponentListener(int width, int height) {
            this.width = width; this.height = height;
        }
/**
 * Here we resize our windows by getting height and width dimensinos.
 */
        public void componentResized(ComponentEvent e) {
            Component c = e.getComponent();
            if (c.getWidth() < width || c.getHeight() < height) {
                c.setSize(Math.max(width, c.getWidth()),
                        Math.max(height, c.getHeight()));
            }
        }
    }

/**
 * Callback to be invoked when the refresh button is clicked by the user. Method will then find the current price of the watched item and display it along with a percentage price change.
 * @param event
 */
    private void refreshButtonClicked(ActionEvent event) {
        for (Item item : this.items) {
            item.updatePrice(this.priceFinder.getNewPrice(item.getURL()));
            showMessage("New Price Updated!");
        }
        super.repaint();
    }
/**
 * Callback to be invoked when the view-page icon is clicked by the user. This will launch the user's default web browser and open the URL of the item chosen.
 * @param event
 */
    private void viewPageClicked(ActionEvent event) {
        Desktop desktop = Desktop.getDesktop();
        for (Item item : this.items) {
            try {
                desktop.browse(new URI(item.getURL()));
                showMessage("Opening Browser...");

            } catch (IOException | URISyntaxException e) {
                showMessage("Unable to access webpage for " + item.getName() + ".");
            }

        }

        super.repaint();
    }

    /** Configure PriceWatcher.UI. */
    private void configureUI(){
        setLayout(new BorderLayout());
        addComponentListener(new MyComponentListener(400, 300));

        /* JMenu */
        JMenuBar menuBar = buildMenuBar();

        /* Toolbar */
        JToolBar toolbar = createToolBar();

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 5, 10, 16),
                BorderFactory.createLineBorder(Color.BLACK)));
        panel.setLayout(new GridLayout(1, 1));


        setJMenuBar(menuBar);

        /* LIST */
        itemList = new JList(items);

        itemList.setCellRenderer(new ItemListRenderer());
        itemList.setFixedCellHeight(100);
        // itemList.setVisibleRowCount(-1);
        JScrollPane pane = new JScrollPane(itemList, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);


        /* Message Bar */
        msgBar.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));

        add(toolbar, BorderLayout.NORTH);
        add(pane, BorderLayout.CENTER);
        add(msgBar, BorderLayout.SOUTH);
    }
/**
 * Adding all menu options available to the user to a menu bar
 * @return the newly created menu bar
 */
    private JMenuBar buildMenuBar(){
        JMenuBar menuBar = new JMenuBar();

        JMenu mainMenu = createMainMenu();
        menuBar.add(mainMenu);

        JMenu editMenu = createEditMenu();
        menuBar.add(editMenu);

        JMenu sortMenu = createSortMenu();
        menuBar.add(sortMenu);

        return menuBar;
    }
/**
 * Here we create a toolbar that the user will interact with to do the following:
    *  Add items
    *  Remove an item
    *  Edit the information of an item.
 * @return completed toolbar
 * @see images folder for all icons used in this method.
 */
    private JToolBar createToolBar(){
        JToolBar toolbar = new JToolBar();
        toolbar.setRollover(true);

        JButton checkPriceBtn = createPriceUpdateButton();
        JButton openWebPageBtn = createViewPageButton();

        JButton addBtn = new JButton(rescaleImage(createImageIcon("add")));
        addBtn.setToolTipText("Add an additional item");

        JButton removeBtn = new JButton(rescaleImage(createImageIcon("remove.png")));   //adding remove icon png.
        removeBtn.setToolTipText("Remove from list");

        JButton editBtn = new JButton(rescaleImage(createImageIcon("edit.png")));   //adding the edit icon png
        editBtn.setToolTipText("Edit item");

        /** Adding the button options that will be available to the user. */
        toolbar.add(checkPriceBtn);
        toolbar.add(addBtn);
        toolbar.add(removeBtn);
        toolbar.add(editBtn);
        toolbar.addSeparator();
        toolbar.add(openWebPageBtn);

        return toolbar;
    }
/**
 * Here we create the main menu that will be displayed.
 * @return the main menu
 * @see images folder for the about icon.
 */
    private JMenu createMainMenu(){
        JMenu mainMenu = new JMenu("PriceWatcher");
        JMenuItem about, exit;

        mainMenu.setMnemonic(KeyEvent.VK_M);
        mainMenu.getAccessibleContext().setAccessibleDescription("Main Menu");

        about = new JMenuItem("About", rescaleImage(createImageIcon("about.png"))); //adding the about icon png.
        about.setToolTipText("About PriceWatcher");

        exit = new JMenuItem("Quit");

        mainMenu.add(about);
        mainMenu.add(exit); // closing the system when the user selects to Quit.

        return mainMenu;

    }
/**
 * This will create the Edit menu. Users will be able to see and select the options to:
    * Check the price of the item
    * Add a new item that the user wants to track the price of.
    * Remove an item from the list that will no longer be tracked.
    * Edit the information of an item that is currently being tracked.
 * @return a menu that allows user to edit
 * @see images folder for all the icons that we use
 */
    private JMenu createEditMenu(){
        JMenuItem checkPrices, addItem, removeItem, editItem;
        JMenu editMenu = new JMenu("Edit");

        checkPrices = new JMenuItem("Check Prices", KeyEvent.VK_C);
        checkPrices.setIcon(rescaleImage(createImageIcon("check.png")));
        checkPrices.setToolTipText("Check for updated prices");

        addItem = new JMenuItem("Add Item", KeyEvent.VK_A);
        addItem.setIcon(rescaleImage(createImageIcon("add.png")));
        addItem.setToolTipText("Add an additional item");

        removeItem = new JMenuItem("Remove Item", KeyEvent.VK_D);
        removeItem.setIcon(rescaleImage(createImageIcon("remove.png")));
        removeItem.setToolTipText("Remove from list");

        editItem = new JMenuItem("Edit Item");
        editItem.setIcon(rescaleImage(createImageIcon("edit.png")));
        editItem.setToolTipText("Edit item");
        /** Calling all methods that will allow buttons to perform their respective actions when clicked. */
        editMenu.add(checkPrices);
        editMenu.add(addItem);
        editMenu.add(removeItem);
        editMenu.add(editItem);


        return editMenu;
    }
/**
 * Menu will allow the user to sort their items based on the price of the item.
 * @return a menu that has been sorted.
 */
    private JMenu createSortMenu(){
        JMenuItem priceChange;
        JMenu sortMenu = new JMenu("Sort");

        priceChange = new JMenuItem("Price Change (%)");
        priceChange.setToolTipText("Sort by price change");
        sortMenu.add(priceChange);

        return sortMenu;

    }
/**
 * Creating a button that will later be used to reflect the updated price of an item. 
 * @return button to check price update.
 * @see images folder for check icon
 */
    private JButton createPriceUpdateButton() {
        ImageIcon icon = rescaleImage(createImageIcon("check.png"));    //adding the check icon

        JButton button = new JButton(icon);
        button.setFocusPainted(false);
        button.addActionListener(this::refreshButtonClicked);
        button.setToolTipText("Check for updated prices");

        return button;
    }
/**
 * Creating a button that will later be used to open a browser and see the item through its URL.
 * @return button to view the item in the browser.
 * @see images folder for open link icon.
 */
    private JButton createViewPageButton() {
        ImageIcon icon = rescaleImage(createImageIcon("openLink.png"));

        JButton button = new JButton(icon);
        button.setFocusPainted(false);
        button.addActionListener(this::viewPageClicked);    // checking that the button has been clicked to trigger another action to open browser and load URL.
        button.setToolTipText("Visit webpage");

        return button;
    }

/**
 * Here we simply resize the icons to look better in the JPanel.
 * @param icon
 * @return updated and resized icon.
 * @see iamges folder for icons and their original sizes.
 */
    private static ImageIcon rescaleImage(ImageIcon icon){

        Image rescaledImage = null;
        if (icon != null) {
            rescaledImage = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        }
        if (rescaledImage != null) {
            icon = new ImageIcon(rescaledImage);
        }
        return icon;
    }

/**
 * Here we load our images onto the JPanel. When another method needs to load an image, this function will search for the image's name and load it onto the panel. Otherwise it will
 * return an error because the file name was not in the images folder.
 * @param filename
 * @return
 * @see images folder located in PriceWatcher/images/
 */
    private ImageIcon createImageIcon(String filename) {
        URL imageUrl = getClass().getResource("../images/" + filename);
        if (imageUrl != null) {
            return new ImageIcon(imageUrl);
        }
        else {
            System.err.println("Cannot locate file: " + imageUrl);
            return null;
        }
    }
/**
 * This will load a quick message with the text that the user selected. This will last a total of 3 seconds.
 * @param msg
 */
    private void showMessage(String msg) {
        msgBar.setText(msg);
        new Thread(() -> {
            try {
                Thread.sleep(3 * 1000); // 3 seconds
            } catch (InterruptedException e) {
            }
            if (msg.equals(msgBar.getText())) {
                SwingUtilities.invokeLater(() -> msgBar.setText(" "));
            }
        }).start();
    }

/**
 * Calling our main method.
 * @param args
 */
    public static void main(String[] args) {
        new Main();
    }

}
/**
 * This will render each of the items of our list onto the panel
 */

class ItemListRenderer extends JLabel implements ListCellRenderer<Item>{
    DecimalFormat df = new DecimalFormat("###.##");
    public ItemListRenderer(){
        setOpaque(true);
    }
/**
 * Caling all our items and getting their information to display on the screen.
 */
    @Override
    public Component getListCellRendererComponent(JList<? extends Item> list, Item item, int index, boolean isSelected, boolean cellHasFocus){

        String listItems = "<html>Name: " + item.getName() + "<br/>URL: " + item.getURL() + "<br/>Price: $" + item.getCurrentPrice() + "<br/>Change: " + df.format(item.getPriceChange()) + "% <br/>Date Added: " + item.getDateAdded() + " (Initial Price: $" + item.getOriginalPrice() + ")";
        setText(listItems);


        if(isSelected){
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }else{
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        return this;

    }
}
