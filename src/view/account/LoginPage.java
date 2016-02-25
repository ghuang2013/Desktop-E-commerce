package view.account;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Created by michael on 11/7/2015.
 */
public class LoginPage extends AccountView {
    private JPanel panel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox accountTypeComboBox;
    private JButton signUpButton;
    private JButton loginButton;

    public LoginPage() {
        super("Login");
        setUpFrame(frame, null, panel, -1, -1);
    }

    public void resetPassword() {
        passwordField.setText("");
    }

    @Override
    public String toString() {
        if (usernameField.getText().equals("") || passwordTyped().equals("")) {
            return "";
        }
        return String.format("username=%s", usernameField.getText());
    }

    public String passwordTyped() {
        return new String(passwordField.getPassword());
    }

    public void addSubmitBtnActionListener(ActionListener actionListener) {
        loginButton.addActionListener(actionListener);
    }

    @Override
    protected void update(String fieldName, String value)
            throws ReflectiveOperationException {
        /*not needed*/
    }

    public void addSignUpBtnActionListener(ActionListener actionListener) {
        signUpButton.addActionListener(actionListener);
    }

    public void addAccountTypeActionListener(ActionListener actionListener) {
        accountTypeComboBox.addActionListener(actionListener);
    }

    public void closeWindow() {
        super.closeWindow();
        panel.setVisible(false);
    }
}
