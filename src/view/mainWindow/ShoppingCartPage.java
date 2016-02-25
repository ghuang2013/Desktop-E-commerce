package view.mainWindow;

import view.template.TopMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static model.User.LoginType;

/**
 * Created by michael on 11/19/2015.
 */
public class ShoppingCartPage extends MainWindowView {
    private JPanel panel;
    private JPanel contentPane;
    private JLabel welcomeLabel;
    private JButton nextButton;
    private JButton previousButton;
    private JButton checkOutButton;
    private JLabel balance;

    public ShoppingCartPage() {
        super("Shopping Cart Page");
        loginType = LoginType.Buyer;
        contentPane.setLayout(new GridLayout(2, 2));
        setUpFrame(frame, TopMenu.getInstance(loginType), panel, 800, 700);
    }

    public void addCheckOutActionListener(ActionListener actionListener){
        checkOutButton.addActionListener(actionListener);
    }

    @Override
    public void setWelcomeLabel(String username) {
        welcomeLabel.setText(welcomeLabel.getText() + username);
    }

    @Override
    public void paginationActionListener(ActionListener prevListener,
                                         ActionListener nextListener) {
        previousButton.addActionListener(prevListener);
        nextButton.addActionListener(nextListener);
    }

    @Override
    public void fillContentPane(JPanel jPanel) {
        contentPane.add(jPanel);
    }

    @Override
    public void clearContentPane() {
        contentPane.removeAll();
    }

    @Override
    public void repaintContentPane() {
        contentPane.revalidate();
        contentPane.repaint();
    }

    @Override
    public void update(String fieldName, String value)
            throws ReflectiveOperationException {
        switch (fieldName) {
            case "welcomeLabel":
                welcomeLabel.setText(welcomeLabel.getText() + value);
                break;
            case "balance":
                balance.setText(value);
                break;
            default:
        }
    }
}
