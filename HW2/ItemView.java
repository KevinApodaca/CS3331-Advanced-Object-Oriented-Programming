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
    
	/** Interface to notify a click on the view page icon. */
	public interface ClickListener {
		
		/** Callback to be invoked when the view page icon is clicked. */
		void clicked();
	}
	
	/** Directory for image files: src/image in Eclipse. */
	private final static String IMAGE_DIR = "/image/";
        
	/** View-page clicking listener. */
    private ClickListener listener;
    
    /** Create a new instance. */
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
        
    /** Set the view-page click listener. */
    public void setClickListener(ClickListener listener) {
    	this.listener = listener;
    }
    
    /** Overridden here to display the details of the item. */
    @Override
	public void paint(Graphics g) {
        super.paint(g); 
        int x = 20, y = 30;
        for (Item item : this.itemList) {
        	g.drawImage(getImage("view.png"), x, y, null);
        	y += 20;
	        g.drawString("Name: " + item.getName(), x, y);
	        y += 20;
	        g.drawString("URL: " + item.getURL(), x, y);
	        y += 20;
	        g.drawString("Price: " + df.format(item.getCurrentPrice()), x, y);
	        y += 20;
	        g.drawString("Change: " + df.format(item.getPriceChange()), x, y);
	        y += 20;
	        g.drawString("Date Added: " + item.getDateAdded() + "(" + df.format(item.getOriginalPrice()) + ")", x, y);
	        y += 40;
        }
    }
    
    /** Return true if the given screen coordinate is inside the viewPage icon. */
    private boolean isViewPageClicked(int x, int y) {
    	return new Rectangle(20, 30, 30, 20).contains(x,  y);
    }
        
    /** Return the image stored in the given file. */
    public Image getImage(String file) {
        try {
        	URL url = new URL(getClass().getResource(IMAGE_DIR), file);
            return ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
