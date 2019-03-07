/**
 * CS 3331 Advanced Object Oriented Programming
 * Spring 2019  
 * Homework Assignment 2
 * @author Kevin Apodaca
 * @author Imani Martin
 * @version 2.0
 * @since 3/6/2019
 *In this homework, you are to develop the second iteration of the Price
    Watcher application by providing a simple GUI using Java 2D graphics
    (see the HW0 handout). As in HW0, your application shall keep track of
    simulated prices of a single, fixed item. Use the baseline/template
    code available from the course website. The baseline code provides a
    GUI consisting of the following:

- A button to check the current price of the item (see R3 below)
- A 2D graphics area to show the details of the item (R1 and R4)
- A message bar to display various messages

Extend the baseline code to meet the following functional and
non-functional requirements.

R1. The application shall show the name of an item, its initial and
    current prices, and the percentage change of the prices (see R3
    and R4 below). Highlight the price change when the price
    drops. You may assume that the application knows a single item to
    watch over the price.

R2. The application shall provide a way to find the current price of
    the item and calculate a new price change (see R3 below to
    simulate the price of an item). You may use a button for this.

R3. The application shall include a class, say PriceFinder, to
    simulate the price of an item. Given the URL of an item, the class
    returns a "simulated" price of the item, e.g., by generating a
    random, or normally-distributed, price. The idea is to apply the
    Strategy design pattern [2] in later assignments by introducing a
    subclass that actually downloads and parses the web document of
    the given URL to find the current price.

R4. The application shall use 2-D graphics to display the details of
    the item. For this, define a custom widget class, say ItemView, as
    a subclass of JPanel and override its paint() method. A working
    template can be found in the baseline code available from the
    course website. Note that the ItemView class will be reused in HW3
    that supports a list of items; each item of the list is displayed
    in the ItemView class.

R5. Optionally, the application shall provide a way to view the Web
    page of the item. Learn how to launch a default web browser
    programmatically in Java.
 * 
*/
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class Main extends JFrame {

    /** Default dimension of the dialog. */
    private final static Dimension DEFAULT_SIZE = new Dimension(500, 400);
      
    /** Special panel to display the watched item. */
    private ItemView itemView;
    
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
		this.priceFinder = new PriceFinder();
        setSize(dim);
        
        configureUI();
        //setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        //setResizable(false);
        showMessage("Welcome!");
    }
  
    /** Callback to be invoked when the refresh button is clicked. 
     * Find the current price of the watched item and display it 
     * along with a percentage price change. */
    private void refreshButtonClicked(ActionEvent event) {
    	for (Item item : this.itemList) {
    		item.updatePrice(this.priceFinder.getNewPrice(item.getURL()));
            showMessage("New Price Updated: " + item.getCurrentPrice());
    	}
    	super.repaint();
    }
    
    /** Callback to be invoked when the view-page icon is clicked.
     * Launch a (default) web browser by supplying the URL of
     * the item. */
    private void viewPageClicked(ActionEvent event) {
        Desktop desktop = Desktop.getDesktop();
    	for (Item item : this.itemList){
            try {
                desktop.browse(new URI(item.getURL()));
                showMessage("Opening Browser...");

            } catch (IOException | URISyntaxException e) {
                showMessage("Unable to access webpage for " + item.getName() + ".");
            }

        }

    	super.repaint();
    }
        
    /** Configure UI. */
    private void configureUI() {
        setLayout(new BorderLayout());

        JPanel controlRefresh = makeRefreshControlPanel();
        controlRefresh.setBorder(BorderFactory.createEmptyBorder(10,16,0,16));
        add(controlRefresh, BorderLayout.WEST);

        JPanel controlViewPage = makeViewPageControlPanel();
        controlViewPage.setBorder(BorderFactory.createEmptyBorder(10,16,0,16));
        add(controlViewPage, BorderLayout.EAST);

        JPanel board = new JPanel();
        board.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10,5,0,16),
                BorderFactory.createLineBorder(Color.RED)));
        board.setLayout(new GridLayout(1,1));
        itemView = new ItemView(this.itemList);

        board.add(itemView);
        add(board, BorderLayout.CENTER);
        msgBar.setBorder(BorderFactory.createEmptyBorder(10,5,10,16));
        add(msgBar, BorderLayout.SOUTH);
    }
      
    /** Create a control panel consisting of a refresh button. */
    private JPanel makeRefreshControlPanel() {
    	JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
    	JButton refreshButton = new JButton("Refresh");
    	refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(this::refreshButtonClicked);
        panel.add(refreshButton);

        return panel;
    }

    /* Create control panel for View Page button */
     private JPanel makeViewPageControlPanel(){
         JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
         JButton viewPageButton = new JButton(("View Page"));
         viewPageButton.setFocusPainted(false);
         viewPageButton.addActionListener(this::viewPageClicked);
         panel.add(viewPageButton);

         return panel;
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
