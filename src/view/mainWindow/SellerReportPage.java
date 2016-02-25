package view.mainWindow;

import model.User;
import view.template.TopMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by ghuan_000 on 11/30/2015.
 */
public class SellerReportPage extends MainWindowView{
    private JPanel panel;
    private JPanel contentPane;
    private JLabel welcomeLabel;
    private JButton addNewProductButton;
    private JButton nextButton;
    private JButton previousButton;
    private JLabel revenue;
    private JLabel cost;
    private JLabel profit;

    public SellerReportPage() {
        super("Seller Report Page");
        loginType = User.LoginType.Seller;
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
    public void update(String fieldName, String value)
            throws ReflectiveOperationException {
        Field field = className.getDeclaredField(fieldName);
        Class type = field.getType();
        if (type.equals(JLabel.class)) {
            Method method = type.getMethod("setText", String.class);
            method.invoke(field.get(this), value);
        }
    }
}
