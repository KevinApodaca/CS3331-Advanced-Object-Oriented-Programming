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
