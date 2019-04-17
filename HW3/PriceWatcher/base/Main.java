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

@SuppressWarnings("serial")
public class Main extends JFrame {


    private Item[] items = {
            new Item("LED monitor", 61.13, "https://www.bestbuy.com/site/samsung-ue590-series-28-led-4k-uhd-monitor-black/5484022.p?skuId=5484022", "3/4/19"),

            new Item("Wireless Charger", 11.04, "https://www.amazon.com/dp/B07DBX67NC/ref=br_msw_pdt-1?_encoding=UTF8&smid=A294P4X9EWVXLJ&pf_rd_m=ATVPDKIKX0DER&pf_rd_s=&pf_rd_r=R830R3XMQCGASSTMNCAC&pf_rd_t=36701&pf_rd_p=19eb5a6f-0aea-4094-a094-545fd76f6e8d&pf_rd_i=desktop", "4/15/19")
    };

    private JList itemList = new JList(items);
    private DefaultListModel model;


    /** Default dimension of the dialog. */
    private final static Dimension DEFAULT_SIZE = new Dimension(400, 300);

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

    /** Create a new dialog of the given screen dimension. */
    public Main(Dimension dim) {
        super("PRICE WATCHER");

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

        public void componentResized(ComponentEvent e) {
            Component c = e.getComponent();
            if (c.getWidth() < width || c.getHeight() < height) {
                c.setSize(Math.max(width, c.getWidth()),
                        Math.max(height, c.getHeight()));
            }
        }
    }

    /**
     * Callback to be invoked when the refresh button is clicked. Find the current
     * price of the watched item and display it along with a percentage price
     * change.
     */
    private void refreshButtonClicked(ActionEvent event) {
        for (Item item : this.items) {
            item.updatePrice(this.priceFinder.getNewPrice(item.getURL()));
            showMessage("New Price Updated! $" + item.getCurrentPrice());
        }
        super.repaint();
    }

    /**
     * Callback to be invoked when the view-page icon is clicked. Launch a (default)
     * web browser by supplying the URL of the item.
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
        // itemView = new ItemView(this.itemList);

        // panel.add(itemView);

        setJMenuBar(menuBar);

        /* LIST */
        itemList = new JList(items);

        itemList.setCellRenderer(new ItemListRenderer());
        itemList.setVisibleRowCount(4);
        JScrollPane pane = new JScrollPane(itemList);


        /* Message Bar */
        msgBar.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));

        add(pane, BorderLayout.CENTER);
        add(toolbar, BorderLayout.NORTH);
        // add(panel, BorderLayout.CENTER);
        add(msgBar, BorderLayout.SOUTH);
    }

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

    private JToolBar createToolBar(){
        JToolBar toolbar = new JToolBar();
        toolbar.setRollover(true);

        JButton checkPriceBtn = createPriceUpdateButton();
       // JButton openWebPageBtn = createViewPageButton();

        JButton addBtn = new JButton(rescaleImage(createImageIcon("add")));
        addBtn.setToolTipText("Add an additional item");

        JButton removeBtn = new JButton(rescaleImage(createImageIcon("remove.png")));
        removeBtn.setToolTipText("Remove from list");

        JButton editBtn = new JButton(rescaleImage(createImageIcon("edit.png")));
        editBtn.setToolTipText("Edit item");

        toolbar.add(checkPriceBtn);
        toolbar.add(addBtn);
        toolbar.add(removeBtn);
        toolbar.add(editBtn);
        toolbar.addSeparator();
        // toolbar.add(openWebPageBtn);

        return toolbar;
    }

    private JMenu createMainMenu(){
        JMenu mainMenu = new JMenu("PriceWatcher");
        JMenuItem about, exit;

        mainMenu.setMnemonic(KeyEvent.VK_M);
        mainMenu.getAccessibleContext().setAccessibleDescription("Main Menu");

        about = new JMenuItem("About", rescaleImage(createImageIcon("about.png")));
        about.setToolTipText("About PriceWatcher");

        exit = new JMenuItem("Quit");

        mainMenu.add(about);
        mainMenu.add(exit);

        return mainMenu;

    }

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

        editMenu.add(checkPrices);
        editMenu.add(addItem);
        editMenu.add(removeItem);
        editMenu.add(editItem);


        return editMenu;
    }

    private JMenu createSortMenu(){
        JMenuItem priceChange;
        JMenu sortMenu = new JMenu("Sort");

        priceChange = new JMenuItem("Price Change (%)");
        priceChange.setToolTipText("Sort by price change");



        sortMenu.add(priceChange);

        return sortMenu;

    }

    /** Create price update button. */
    private JButton createPriceUpdateButton() {
        ImageIcon icon = rescaleImage(createImageIcon("check.png"));

        JButton button = new JButton(icon);
        button.setFocusPainted(false);
        button.addActionListener(this::refreshButtonClicked);
        button.setToolTipText("Check for updated prices");

        return button;
    }

    /* Create View Page button */
    private JButton createViewPageButton() {
        ImageIcon icon = rescaleImage(createImageIcon("openLink.png"));

        JButton button = new JButton(icon);
        button.setFocusPainted(false);
        button.addActionListener(this::viewPageClicked);
        button.setToolTipText("Visit webpage");

        return button;
    }


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


    /* Create icon */
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

    /** Show briefly the given string in the message bar. */
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


    public static void main(String[] args) {
        new Main();
    }

}

class ItemListRenderer extends JLabel implements ListCellRenderer<Item>{
    DecimalFormat df = new DecimalFormat("###.##");
    public ItemListRenderer(){
        setOpaque(true);
    }

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
