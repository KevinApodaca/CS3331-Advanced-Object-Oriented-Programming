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
package src.main.java.Pricewatcher.base;

import src.main.java.Pricewatcher.console.CopyCutPaste;
import src.main.java.Pricewatcher.model.Item;
import src.main.java.Pricewatcher.model.WebPriceFinder;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.*;

import static java.awt.event.ActionEvent.*;

/**
 * Creating our JFrame using java swing.
 */
@SuppressWarnings("serial")
public class Main extends JFrame {

    /**
     * New instances of the items which we will check the price of.
     */


    private List<Item> items = new ArrayList<>();

    private JList itemList;
    private DefaultListModel model;


    /** Default dimension of the dialog. */
    private final static Dimension DEFAULT_SIZE = new Dimension(500, 300);

    //private PriceFinder priceFinder;
    private WebPriceFinder webPriceFinder;

    /** Message bar to display various messages. */
    private JLabel msgBar = new JLabel(" ");

    /** Create a new dialog. */
    private Main() {
        this(DEFAULT_SIZE);
    }

    /**
     * Here we created  new dialog box with the dimensions passed and we configure all settings needed for things to be displayed properly.
     * @param dim
     */
    private Main(Dimension dim) {
        super("PRICE WATCHER");

        // this.priceFinder = new PriceFinder();
        this.webPriceFinder = new WebPriceFinder();
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        setSize(dim);
        configureUI();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setResizable(true);
        showMessage("Welcome!");
    }

    private static class MyComponentListener extends ComponentAdapter {
        private int width, height;

        MyComponentListener(int width, int height) {
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
        System.out.println("\nRefresh button clicked!");

        for (Item item : this.items) {
            webPriceFinder.getWebPrice(item.getURL());

            showMessage("New Price Updated!");
        }
        System.out.println("Refresh done!");
        super.repaint();
        super.revalidate();
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
                showMessage("Unable to access webpage for " + item.getName());
            }

        }

        super.repaint();
    }

    /* Contains test items to ensure app is working */
    private void getTestItems(List<Item> items){

        /* Test urls added to make sure web_price_finder is extracting prices */
        String testURL = "https://www.amazon.com/dp/B07DBX67NC/ref=br_msw_pdt-1?_encoding=UTF8&smid=A294P4X9EWVXLJ&pf_rd_m=ATVPDKIKX0DER&pf_rd_s=&pf_rd_r=R830R3XMQCGASSTMNCAC&pf_rd_t=36701&pf_rd_p=19eb5a6f-0aea-4094-a094-545fd76f6e8d&pf_rd_i=desktop";

        String testURL2 = "https://www.amazon.com/Persona-PlayStation-Take-Your-Heart-Premium/dp/B01GKHJPAC/ref=sr_1_5?keywords=persona%2B5&qid=1555473381&s=gateway&sr=8-5&th=1";

        items.add(new Item("Wireless Charger", webPriceFinder.getWebPrice(testURL), testURL, "4/15/19"));
        items.add(new Item("Persona 5 PS4", webPriceFinder.getWebPrice(testURL2), testURL2, "4/16/2019"));

    }

    /** Configure PriceWatcher.UI. */
    private void configureUI(){
        setLayout(new BorderLayout());
        addComponentListener(new MyComponentListener(400, 350));
        addWindowListener(new ExitListener());

        getTestItems(items);

        /* JMenu */
        JMenuBar menuBar = buildMenuBar();

        /* Toolbar */
        JToolBar toolbar = createToolBar();

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 5, 10, 16),
                BorderFactory.createLineBorder(Color.BLACK)));
        panel.setLayout(new GridLayout(1, 1,4,2));


        setJMenuBar(menuBar);

        /* LIST */
        model = new DefaultListModel();
        for (Item item : items) model.addElement(item);


        itemList = new JList(model);
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemList.setCellRenderer(new ItemListRenderer());
        itemList.setFixedCellHeight(100);
        itemList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(SwingUtilities.isRightMouseButton(e)){
                    itemList.setSelectedIndex(itemList.locationToIndex(e.getPoint()));
                    JPopupMenu menu = new JPopupMenu();
                    JMenuItem rightClickMenu = createEditMenu();
                    JMenuItem visitPage = new JMenuItem("Visit Website", rescaleImage(createImageIcon("openLink.png")));

                    visitPage.setToolTipText("Visit this item's webpage!");
                    visitPage.addActionListener(Main.this::viewPageClicked);
                    rightClickMenu.add(visitPage);

                    menu.add(rightClickMenu);
                    menu.show(itemList, e.getPoint().x,e.getPoint().y);
                }
            }
        });


        JScrollPane pane = new JScrollPane(itemList, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        pane.setBorder(BorderFactory.createEmptyBorder(10,5,10,10));


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

        JButton addBtn = new JButton(rescaleImage(createImageIcon("add.png")));
        addBtn.setToolTipText("Add an additional item");
        addBtn.addActionListener(new AddItemPopUp());

        JButton firstItemBtn = new JButton(rescaleImage(createImageIcon("firstItem.png")));
        firstItemBtn.setToolTipText("Go to first item in list");
        firstItemBtn.addActionListener(new firstItemOfList());

        JButton lastItemBtn = new JButton(rescaleImage(createImageIcon("lastItem.png")));
        lastItemBtn.setToolTipText("Go to last item in list");
        lastItemBtn.addActionListener(new lastItemOfList());

        JButton removeBtn = new JButton(rescaleImage(createImageIcon("remove.png")));
        removeBtn.setToolTipText("Remove from list");
        removeBtn.addActionListener(new removeItem());

        JButton editBtn = new JButton(rescaleImage(createImageIcon("edit.png")));
        editBtn.setToolTipText("Edit item");
        editBtn.addActionListener(new editItem());

        JButton clearBtn = new JButton(rescaleImage(createImageIcon("clear.png")));
        clearBtn.setToolTipText("Remove all items");
        clearBtn.addActionListener(new clearAllItems());

        /* Adding the button options that will be available to the user. */
        toolbar.add(checkPriceBtn);
        toolbar.add(editBtn);
        toolbar.add(addBtn);
        toolbar.add(removeBtn);
        toolbar.add(firstItemBtn);
        toolbar.add(lastItemBtn);
        toolbar.add(clearBtn);
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
        mainMenu.setMnemonic(KeyEvent.VK_M);
        JMenuItem about, exit;

        about = new JMenuItem("About", rescaleImage(createImageIcon("about.png")));
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame aboutWindow = new JFrame("About PriceWatcher App");
                setLayout(new BorderLayout());
                aboutWindow.setLocationRelativeTo(null);
                JPanel infoPanel = new JPanel();

                JLabel title = new JLabel("PriceWatcher App\t");
                JLabel authors = new JLabel("Copyright 2019 Kevin Apodaca & Imani Martin");

                infoPanel.add(title);
                infoPanel.add(authors);

                JOptionPane.showMessageDialog(aboutWindow, infoPanel);

            }
        });
        about.setToolTipText("About PriceWatcher");

        exit = new JMenuItem("Quit", KeyEvent.VK_Q);
        exit.setMnemonic(KeyEvent.VK_Q);
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ALT_MASK));
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame exitWindow = new JFrame();
                JPanel confimPanel = new JPanel();
                confimPanel.add(new Label("Are you want to go?"));

                int option = JOptionPane.showConfirmDialog(exitWindow, confimPanel, "Exit PriceWatcher", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        mainMenu.add(about);
        mainMenu.add(exit); // closing the system when the user wants Quit.

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
        CopyCutPaste convenience = new CopyCutPaste();
        JMenuItem checkPrices, addItem, removeItem, editItem, clearItem ;
        JMenu editMenu = convenience.getMenu();

        checkPrices = new JMenuItem("Check Prices", KeyEvent.VK_K);
        checkPrices.setMnemonic(KeyEvent.VK_K);
        checkPrices.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, ALT_MASK));
        checkPrices.setIcon(rescaleImage(createImageIcon("check.png")));
        checkPrices.addActionListener(this::refreshButtonClicked);
        checkPrices.setToolTipText("Check for updated prices");

        addItem = new JMenuItem("Add Item", KeyEvent.VK_A);
        addItem.setMnemonic(KeyEvent.VK_A);
        addItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ALT_MASK));
        addItem.setIcon(rescaleImage(createImageIcon("add.png")));
        addItem.setToolTipText("Add an additional item");
        addItem.addActionListener(new AddItemPopUp());


        removeItem = new JMenuItem("Remove Item", KeyEvent.VK_D);
        removeItem.setMnemonic(KeyEvent.VK_D);
        removeItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ALT_MASK));
        removeItem.setIcon(rescaleImage(createImageIcon("remove.png")));
        removeItem.setToolTipText("Remove from list");
        removeItem.addActionListener(new removeItem());

        editItem = new JMenuItem("Edit Item", KeyEvent.VK_E);
        editItem.setMnemonic(KeyEvent.VK_E);
        editItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ALT_MASK));
        editItem.setIcon(rescaleImage(createImageIcon("edit.png")));
        editItem.setToolTipText("Edit item");
        editItem.addActionListener(new editItem());

        clearItem = new JMenuItem("Clear List", KeyEvent.VK_N);
        clearItem.setMnemonic(KeyEvent.VK_N);
        clearItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ALT_MASK));
        clearItem.setIcon(rescaleImage(createImageIcon("clear.png")));
        clearItem.setToolTipText("Remove all items");
        clearItem.addActionListener(new clearAllItems());


        /** Calling all methods that will allow buttons to perform their respective actions when clicked. */
        editMenu.add(checkPrices);
        editMenu.add(addItem);
        editMenu.add(removeItem);
        editMenu.add(editItem);
        editMenu.add(clearItem);


        return editMenu;
    }

    /**
     * This will create the Sort menu. Users will be able to see and select the options to:
     * Skip to the first item of the list
     * Skip to the second item of the list
     * @return a menu that allows user to sort
     * @see images folder for all the icons that we use
     */
    private JMenu createSortMenu(){
        JMenuItem skipToFirstItem, skipToLastItem;
        JMenu sortMenu = new JMenu("Sort");

        /* Go to first item in list */
        skipToFirstItem = new JMenuItem("Go To First Item", KeyEvent.VK_F);
        skipToFirstItem.setMnemonic(KeyEvent.VK_F);
        skipToFirstItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ALT_MASK));
        skipToFirstItem.setIcon(rescaleImage(createImageIcon("firstItem.png")));
        skipToFirstItem.addActionListener(new firstItemOfList());
        skipToFirstItem.setToolTipText("Go to first item in list");


        /* Go to last item in the list */
        skipToLastItem = new JMenuItem("Go To Last Item", KeyEvent.VK_L);
        skipToLastItem.setMnemonic(KeyEvent.VK_L);
        skipToLastItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ALT_MASK));
        skipToLastItem.setIcon(rescaleImage(createImageIcon("lastItem.png")));
        skipToLastItem.addActionListener(new lastItemOfList());
        skipToLastItem.setToolTipText("Go to last item in list");


        /** Calling all methods that will allow buttons to perform their respective actions when clicked. */
        sortMenu.add(skipToFirstItem);
        sortMenu.add(skipToLastItem);

        return sortMenu;
    }

    /*
     * Creating a button that will later be used to reflect the updated price of an item.
     * @return button to check price update.
     * @see images folder for check icon
     *
     */

    private JButton createPriceUpdateButton() {
        ImageIcon icon = rescaleImage(createImageIcon("check.png"));

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
        button.addActionListener(this::viewPageClicked);
        button.setToolTipText("Visit webpage");

        return button;
    }

    /**
     * Here we simply resize the icons to look better in the JPanel.
     * @param icon
     * @return updated and resized icon.
     * @see images folder for icons and their original sizes.
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
    /* Create icon */
    private ImageIcon createImageIcon(String filename) {
        URL imageUrl = getClass().getResource("/src/main/java/Pricewatcher/images/" + filename);
        if (imageUrl != null) {
            return new ImageIcon(imageUrl);
        }
        else {
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
                Thread.sleep(2 * 1000); // 2 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (msg.equals(msgBar.getText())) {
                SwingUtilities.invokeLater(() -> msgBar.setText(" "));
            }
        }).start();
    }

    /**
     * Here is where all the action listeners can be found.
     *
     *
     **/

    private JFormattedTextField nameField;
    private JFormattedTextField urlField;
    private JFormattedTextField priceField;
    private JFormattedTextField dateField;

    private String name = "";
    private String url = "";
    private String price = "0.00";


    /*
     *  When add button is clicked, a new window is opened to enter new item information
     * @param event
     * */
    private class AddItemPopUp implements ActionListener, PropertyChangeListener {
        @Override
        public void actionPerformed(ActionEvent event){
            JFrame addItemWindow = new JFrame();
            addItemWindow.setLocationRelativeTo(null);
            addItemWindow.setBackground(new Color(50,205,50));
            JPanel fieldPane = new JPanel();

            /* Strings for labels */
            String nameStr = "Name: ";
            String urlStr = "URL: ";
            String priceStr = "Price: ";
            String dateStr = "Date: ";

            /* Labels for identifying fields */
            JLabel nameLabel = new JLabel(nameStr);
            JLabel urlLabel = new JLabel(urlStr);
            JLabel priceLabel = new JLabel(priceStr);
            JLabel dateLabel = new JLabel(dateStr);

            /* Fields for typing */
            nameField = new JFormattedTextField();
            nameField.setValue("");
            nameField.setColumns(5);
            nameField.addPropertyChangeListener("value", this);

            urlField = new JFormattedTextField();
            urlField.setValue("");
            urlField.setColumns(5);
            urlField.addPropertyChangeListener("value", this);

            priceField = new JFormattedTextField();
            priceField.setValue("$0.00");
            priceField.setColumns(5);
            priceField.setEditable(false);
            priceField.addPropertyChangeListener("value", this);

            dateField = new JFormattedTextField();
            dateField.setValue(new Date());
            Date date = (Date)dateField.getValue();
            dateField.setEditable(false);

            /* Accessibility tools */
            nameLabel.setLabelFor(nameField);
            urlLabel.setLabelFor(urlField);
            priceLabel.setLabelFor(priceField);
            dateLabel.setLabelFor(dateField);

            fieldPane.setLayout(new GridLayout(0,2,4,2));

            fieldPane.add(new JLabel("Name: "));
            fieldPane.add(nameField);

            fieldPane.add(new JLabel("URL: "));
            fieldPane.add(urlField);

            fieldPane.add(new JLabel("Price: "));
            fieldPane.add(priceField);

            fieldPane.add(new JLabel(("Date: ")));
            fieldPane.add(dateField);

            /* Copy And Paste Functions */
            CopyCutPaste convenience = new CopyCutPaste();
            convenience.setPopup(fieldPane, nameField);
            convenience.setPopup(fieldPane, urlField);

            /* Confirmation Box */
            int option = JOptionPane.showConfirmDialog(addItemWindow, fieldPane, "Add New Item", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

            if (option == JOptionPane.OK_OPTION){

                /* Append new items to the list */
                showMessage("New item Added!");
                ListSelectionModel selectionModel = itemList.getSelectionModel();
                int index = selectionModel.getMinSelectionIndex();
                if(index == -1)
                    index = 0;
                else{
                    index++;
                }

                // check if url entered is valid
                if(webPriceFinder.checkIfValid(url)){
                    String webPriceItem = webPriceFinder.getWebPrice(url);
                    // insert item at the end of the list
                    model.addElement(new Item(name, webPriceItem, url, date.toString()));
                    items.add(new Item(name, webPriceItem, url, date.toString()));
                }

                // select the new item and make it visible
                itemList.requestFocusInWindow();
                itemList.ensureIndexIsVisible(index);
            }

        }

        @Override
        public void propertyChange(PropertyChangeEvent e) {
            Object source = e.getSource();
            if (source == nameField) {
                name = ((String)nameField.getValue());
            } else if (source == urlField) {
                url = ((String)urlField.getValue());
            } else if (source == priceField) {
                price = webPriceFinder.getWebPrice(url);
            }else if (source == dateField) {
                Date date = ((Date) dateField.getValue());
            }

        }

    }

    /* Skip to first item of the list */
    private class firstItemOfList implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            int firstIndex = itemList.getFirstVisibleIndex();
            if(firstIndex == 0) {
                itemList.setSelectedIndex(firstIndex);
                itemList.ensureIndexIsVisible(firstIndex);
            }
        }
    }

    /* Skip to last item of the list */
    private class lastItemOfList implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            int lastIndex = itemList.getModel().getSize()-1;
            if(lastIndex >= 0) {
                itemList.setSelectedIndex(lastIndex);
                itemList.ensureIndexIsVisible(lastIndex);
            }

        }
    }

    /* Edit the selected item */
    private class editItem implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            JFrame editItemWindow = new JFrame("Edit New Item");
            JPanel panel = new JPanel();
            editItemWindow.addComponentListener(new MyComponentListener(400, 350));
            editItemWindow.setLocationRelativeTo(null);

            ListSelectionModel selectionModel = itemList.getSelectionModel();
            int index = selectionModel.getMinSelectionIndex();

            if(index == -1)
                return;

            /* Creating new window for editing selected item */
            panel.setLayout(new GridLayout(0,2,4,2));
            CopyCutPaste convenience = new CopyCutPaste();

            JTextField newNameField = new JTextField(5);
            JTextField newUrlField = new JTextField(5);

            panel.add(new JLabel("Please enter a new name: "));
            panel.add(newNameField);

            panel.add(new JLabel("Please enter a new URL: "));
            panel.add(newUrlField);

            convenience.setPopup(panel, newNameField);
            convenience.setPopup(panel, newUrlField);

            int option = JOptionPane.showConfirmDialog(editItemWindow, panel, "Edit Item", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                String newItemName = newNameField.getText();
                String newItemUrl = newUrlField.getText();

                for (Item item : items) {
                    if (!newItemName.isEmpty() || !newItemUrl.isEmpty()) {
                        model.remove(index);
                        model.add(index, new Item(newItemName, item.getCurrentPrice(), newItemUrl, item.getDateAdded()));

                        items.remove(index);
                        items.add(index, new Item(newItemName, item.getCurrentPrice(), newItemUrl, item.getDateAdded()));
                    }
                }

                /* When item is edited, a confirmation window will pop up */
                panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
                panel.add(new Label("Item successfully updated!"));
                JOptionPane.showMessageDialog(editItemWindow, panel);
            }

            showMessage("Item Updated!");
        }
    }

    /* Removes an item from the list */
    private class removeItem implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame deleteCurrentItemWindow = new JFrame();
            deleteCurrentItemWindow.setLocationRelativeTo(null);
            JPanel confirmPanel = new JPanel();
            confirmPanel.add(new Label("Are you sure you want to delete this item?"));

            ListSelectionModel selectionModel = itemList.getSelectionModel();
            int index = selectionModel.getMinSelectionIndex();


            /* Confirm with user that they want to remove the selected item */
            int option = JOptionPane.showConfirmDialog(deleteCurrentItemWindow, confirmPanel, "Remove Item", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (option == JOptionPane.YES_OPTION){
                if(index >= 0) {
                    model.remove(index);
                    items.remove(index);
                }

                /* Confirmation that item has been deleted */
                JOptionPane.showMessageDialog(deleteCurrentItemWindow, "Item successfully removed!");

            }
            showMessage("Item Removed!");
        }
    }

    /* Remove all items from the list */
    private class clearAllItems implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            JFrame clearItemWindow = new JFrame();
            clearItemWindow.setLocationRelativeTo(null);
            JPanel confirmPanel = new JPanel();
            confirmPanel.add(new Label("Are you sure you want to delete all items?"));


            /* Confirm with user that they want to delete all items in list */
            int option = JOptionPane.showConfirmDialog(clearItemWindow, confirmPanel, "Clear All Items", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (option == JOptionPane.YES_OPTION) {
                model.clear();
                items.clear();
                showMessage("List Cleared!");
                JOptionPane.showMessageDialog(clearItemWindow, "List successfully deleted!");
            }
        }
    }

    /* Close window when user quits */
    public class ExitListener extends WindowAdapter {
        public void windowClosing(WindowEvent event) {
            System.exit(0);
        }
    }

    /**
     * Calling our main method.
     * @param args
     */
    public static void main(String[] args) {
        new Main();
    }

}