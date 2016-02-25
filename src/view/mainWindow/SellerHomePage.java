package view.mainWindow;

import model.User.LoginType;
import view.template.TopMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by Guan Huang on 11/20/2015.
 */
public class SellerHomePage extends MainWindowView {
    private JPanel panel;
    private JLabel welcomeLabel;
    private JButton addNewProductButton;
    private JPanel contentPane;
    private JButton previousButton;
    private JButton nextButton;

    public SellerHomePage() {
        super("Seller Home Page");
        loginType = LoginType.Seller;
        contentPane.setLayout(new GridLayout(4, 1));
        setUpFrame(frame, TopMenu.getInstance(loginType), panel, 550, 700);
    }

    @Override
    public void fillContentPane(JPanel panel) {
        contentPane.add(panel);
    }

    @Override
    public void repaintContentPane() {
        contentPane.revalidate();
        contentPane.repaint();
    }

    @Override
    public void clearContentPane() {
        contentPane.removeAll();
    }

    @Override
    public void paginationActionListener(ActionListener prevListener, ActionListener nextListener) {
        previousButton.addActionListener(prevListener);
        nextButton.addActionListener(nextListener);
    }

    @Override
    public void setWelcomeLabel(String username) {
        welcomeLabel.setText(welcomeLabel.getText() + username);
    }

    public void addNewProductButtonActionListener(ActionListener actionListener) {
        addNewProductButton.addActionListener(actionListener);
    }

    @Override
    protected void update(String fieldName, String value)
            throws ReflectiveOperationException {
        switch (fieldName) {
            case "welcomeLabel":
                welcomeLabel.setText(welcomeLabel.getText() + value);
                break;
        }
    }
}
