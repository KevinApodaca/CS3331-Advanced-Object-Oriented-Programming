package PriceWatcher.base;

import PriceWatcher.model.Item;
import PriceWatcher.model.PriceFinder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

@SuppressWarnings("serial")
public class Main extends JFrame {

    /** Default dimension of the dialog. */
    private final static Dimension DEFAULT_SIZE = new Dimension(1300, 300);

    /** Special panel to display the watched item. */
    private PriceWatcher.base.ItemView itemView;

    private List<Item> itemList;
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
        List<Item> testItemList = new ArrayList<>();


        Item testItem = new Item("LED monitor", 61.13, "https://www.bestbuy.com/site/samsung-ue590-series-28-led-4k-uhd-monitor-black/5484022.p?skuId=5484022", "3/4/19");

        Item testItem2 = new Item("Wireless Charger", 11.04, "https://www.amazon.com/dp/B07DBX67NC/ref=br_msw_pdt-1?_encoding=UTF8&smid=A294P4X9EWVXLJ&pf_rd_m=ATVPDKIKX0DER&pf_rd_s=&pf_rd_r=R830R3XMQCGASSTMNCAC&pf_rd_t=36701&pf_rd_p=19eb5a6f-0aea-4094-a094-545fd76f6e8d&pf_rd_i=desktop", "4/15/19");

        testItemList.add(testItem);
        testItemList.add(testItem2);

        this.itemList = testItemList;
        this.priceFinder = new PriceWatcher.model.PriceFinder();


        setSize(dim);

        configureUI();
        // setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        // setResizable(false);
        showMessage("Welcome!");
    }

    /**
     * Callback to be invoked when the refresh button is clicked. Find the current
     * price of the watched item and display it along with a percentage price
     * change.
     */
    private void refreshButtonClicked(ActionEvent event) {
        for (Item item : this.itemList) {
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
        for (Item item : this.itemList) {
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

        /* JMenu */
        JMenuBar menuBar = buildMenuBar();

        /* Toolbar */
        JToolBar toolbar = new JToolBar();
        toolbar.setRollover(true);

        JButton checkPriceBtn = createPriceUpdateButton();

        JButton openWebPageBtn = createViewPageButton();

        toolbar.add(checkPriceBtn);
        toolbar.addSeparator();
        toolbar.add(openWebPageBtn);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 5, 10, 16),
                BorderFactory.createLineBorder(Color.BLACK)));
        panel.setLayout(new GridLayout(1, 1));
        itemView = new ItemView(this.itemList);

        panel.add(itemView);

        setJMenuBar(menuBar);
        add(toolbar, BorderLayout.NORTH);

        add(panel, BorderLayout.CENTER);
        msgBar.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        add(msgBar, BorderLayout.SOUTH);
    }

    private JMenuBar buildMenuBar(){
        JMenuBar menuBar = new JMenuBar();

        JMenu mainMenu = new JMenu("Main");
        mainMenu.setMnemonic(KeyEvent.VK_M);
        mainMenu.getAccessibleContext().setAccessibleDescription("Main Menu");
        menuBar.add(mainMenu);


        JMenu appMenu = new JMenu("App");
        menuBar.add(appMenu);

        JMenu sortMenu = new JMenu("Sort");
        menuBar.add(sortMenu);

        JMenuItem about, exit, checkPrices, priceChange;

        /* Main Menu */
        about = new JMenuItem("About");
        about.setToolTipText("About PriceWatcher");
        exit = new JMenuItem("Exit");

        /* Item Menu */
        checkPrices = new JMenuItem("Check Prices", KeyEvent.VK_C);
        // checkPrices.setIcon();
        checkPrices.setToolTipText("Check for updated prices");

        /* Sort Menu */
        priceChange = new JMenuItem("Price Change");
        priceChange.setToolTipText("Sort by price change (%)");

        mainMenu.add(about);
        mainMenu.add(exit);
        appMenu.add(checkPrices);
        sortMenu.add(priceChange);

        return menuBar;
    }

    /** Create a control panel consisting of a refresh button. */
    private JButton createPriceUpdateButton() {
        ImageIcon icon = createImageIcon("checkIcon.png");
        icon = rescaleImage(icon);

        JButton button = new JButton(icon);
        button.setFocusPainted(false);
        button.addActionListener(this::refreshButtonClicked);
        button.setToolTipText("Check for updated prices");

        return button;
    }

    /* Create control panel for View Page button */
    private JButton createViewPageButton() {
        JButton button = new JButton(("View Page"));
        button.setFocusPainted(false);
        button.addActionListener(this::viewPageClicked);
        button.setToolTipText("Visit webpage");

        return button;
    }

    private ImageIcon rescaleImage(ImageIcon icon){

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
