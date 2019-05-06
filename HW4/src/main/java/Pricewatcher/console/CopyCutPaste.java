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
package src.main.java.Pricewatcher.console;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;
import javax.swing.text.TextAction;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * Class will be used so that the three functions work.
 */
public class CopyCutPaste {
    private JMenu jMenu;
    private JPopupMenu popupMenu = new JPopupMenu();

    public CopyCutPaste() {
        init();
    }

    private void init() {
        jMenu = new JMenu("Edit");
        addAction(new DefaultEditorKit.CutAction(), KeyEvent.VK_X, "Cut");
        addAction(new DefaultEditorKit.CopyAction(), KeyEvent.VK_C, "Copy");
        addAction(new DefaultEditorKit.PasteAction(), KeyEvent.VK_V, "Paste");
    }
/**
 * Method will add the actins.
 * @param action - the action needed
 * @param key - the key that is pressed on the keyboard.
 * @param text - the string that will be shown.
 */
    private void addAction(TextAction action, int key, String text) {
        action.putValue(AbstractAction.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke(key, InputEvent.CTRL_DOWN_MASK));
        action.putValue(AbstractAction.NAME, text);
        jMenu.add(new JMenuItem(action));
        popupMenu.add(new JMenuItem(action));
    }
/**
 * Method will be used to create a popup 
 * @param fieldPane - the panel 
 * @param components - text components.
 */
    public void setPopup(JPanel fieldPane, JTextComponent... components) {
        if (components == null) {
            return;
        }
        for (JTextComponent tc : components) {
            tc.setComponentPopupMenu(popupMenu);
        }
    }
/**
 * Method will just get a new menu
 * @return the menu
 */
    public JMenu getMenu() {
        return jMenu;
    }
}
