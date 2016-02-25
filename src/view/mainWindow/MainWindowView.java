package view.mainWindow;

import model.User.LoginType;
import view.View;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Created by Guan Huang on 11/20/2015.
 */
public abstract class MainWindowView extends View{
    protected JFrame frame;
    protected LoginType loginType;

    public MainWindowView(String title){
        frame = new JFrame(title);
    }

    public JFrame getFrame(){
        return frame;
    }

    public void closeWindow(){
        frame.dispose();
        frame.setVisible(false);
    }

    public abstract void setWelcomeLabel(String username);

    public abstract void fillContentPane(JPanel panel);

    public abstract void repaintContentPane();

    public abstract void clearContentPane();

    public abstract void paginationActionListener(ActionListener prevListener, ActionListener nextListener);
}
