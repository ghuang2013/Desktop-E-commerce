package view.account;

import view.View;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * interact with AccountModel
 */
public abstract class AccountView extends View {
    protected JFrame frame;

    public AccountView(String title){
        frame = new JFrame(title);
    }

    public JFrame getFrame(){
        return frame;
    }

    public void closeWindow(){
        frame.dispose();
        frame.setVisible(false);
    }

    public abstract void addSubmitBtnActionListener(ActionListener actionListener);
}
