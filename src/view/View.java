package view;

import global.Observer;
import global.Session;
import view.template.TopMenu;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Guan Huang on 11/11/2015.
 */
public abstract class View implements Observer {
    protected Class className;
    //package private constant
    static Image favIcon;

    public final static String favIconPath = "/shoppingCart.png";

    public View() {
        className = getClass();
        //favIcon = Toolkit.getDefaultToolkit().getImage(className.getResource(favIconPath));
    }

    public void setUpFrame(JFrame frame, TopMenu menu, JPanel contentPane, int width, int height) {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //frame.setIconImage(favIcon);

        if (menu != null) {
            frame.setJMenuBar(menu);
        }
        frame.setContentPane(contentPane);
        if (width == -1 && height == -1) {
            frame.pack();
        } else {
            frame.setSize(width, height);
        }
        frame.setVisible(true);
    }

    public final void update(String dataString) {
        String[] fields = dataString.split("&");
        for (String field : fields) {
            String[] pair = field.split("=");
            try {
                update(pair[0], pair[1]);
            } catch (ReflectiveOperationException e) {
            }
        }
    }

    protected abstract void update(String fieldName, String value)
            throws ReflectiveOperationException;

    public abstract void closeWindow();
}
