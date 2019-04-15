package PriceWatcher.base;

import PriceWatcher.model.Item;
import PriceWatcher.model.PriceFinder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
        String testItemUrl = "https://www.bestbuy.com/site/samsung-ue590-series-28-led-4k-uhd-monitor-black/5484022.p?skuId=5484022";
        String testItemName = "LED monitor";
        String testDateAdded = "3/4/19";
        double testItemInitialPrice = 61.13;

        Item testItem = new Item(testItemName, testItemInitialPrice, testItemUrl, testDateAdded);
        List<Item> testItemList = new ArrayList<>();
        testItemList.add(testItem);
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

            if (item.getPriceChange() > 0) {
                setBackground(Color.RED);
            } else {
                setBackground(Color.BLUE);
            }
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
    private void configureUI() {
        setLayout(new BorderLayout());

        /* JMenu */
        JMenuBar menuBar = buildMenuBar();

        /* Toolbar */
        JToolBar toolbar = new JToolBar();
        toolbar.setRollover(true);

        JButton controlRefresh = makeRefreshControlPanel();
        controlRefresh.setToolTipText("Refresh Page");

        JButton controlViewPage = makeViewPageControlPanel();
        controlViewPage.setToolTipText("View Webpage");

        toolbar.add(controlRefresh);
        toolbar.addSeparator();
        toolbar.add(controlViewPage);

        JPanel board = new JPanel();
        board.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 5, 10, 16),
                BorderFactory.createLineBorder(Color.BLACK)));
        board.setLayout(new GridLayout(1, 1));
        itemView = new ItemView(this.itemList);

        board.add(itemView);
        setJMenuBar(menuBar);
        add(toolbar, BorderLayout.NORTH);
        add(board, BorderLayout.CENTER);
        msgBar.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        add(msgBar, BorderLayout.SOUTH);
    }

    private JMenuBar buildMenuBar(){
        JMenu mainMenu = new JMenu("Main");
        JMenu appMenu = new JMenu("App");
        JMenu sortMenu = new JMenu("Sort");

        JMenuBar menuBar = new JMenuBar();

        JMenuItem about, exit, checkPrices, priceChange;

        /* Main Menu */
        about = new JMenuItem("About");
        about.setToolTipText("About PriceWatcher");
        exit = new JMenuItem("Exit");

        /* Item Menu */
        checkPrices = new JMenuItem("Check Prices");
        checkPrices.setToolTipText("Click to get updated prices!");

        /* Sort Menu */
        priceChange = new JMenuItem("Price Change");
        priceChange.setToolTipText("Sort by price change (%)");

        mainMenu.add(about);
        mainMenu.add(exit);
        appMenu.add(checkPrices);
        sortMenu.add(priceChange);

        menuBar.add(mainMenu);
        menuBar.add(appMenu);
        menuBar.add(sortMenu);

        return menuBar;
    }

    /** Create a control panel consisting of a refresh button. */
    private JButton makeRefreshControlPanel() {
        JButton refreshButton = new JButton("Check Prices");
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(this::refreshButtonClicked);

        return refreshButton;
    }

    /* Create control panel for View Page button */
    private JButton makeViewPageControlPanel() {
        JButton viewPageButton = new JButton(("View Page"));
        viewPageButton.setFocusPainted(false);
        viewPageButton.addActionListener(this::viewPageClicked);

        return viewPageButton;
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
