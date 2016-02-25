package view.account;

import view.View;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;

/**
 * Created by Guan Huang on 11/7/2015.
 */
public class SignUpPage extends AccountView {
    private JTextField usernameField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField addressField;
    private JTextField phoneNumberField;
    private JButton signUpButton;
    private JCheckBox iAgreeCheckBox;
    public JPanel panel;

    public SignUpPage() {
        super("Sign Up");
        setUpFrame(frame, null, panel, -1, -1);
    }

    public void addSubmitBtnActionListener(ActionListener actionListener) {
        signUpButton.addActionListener(actionListener);
    }

    public String getConfirmPasswordField() {
        return new String(confirmPasswordField.getPassword());
    }

    public String getPasswordField() {
        return new String(passwordField.getPassword());
    }

    public String toString() {
        String result = "";
        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields) {
            try {
                if (field.getType().equals(JTextField.class)) {
                    String value = ((JTextField) field.get(this)).getText();
                    if (!value.equals("")) {
                        result += field.getName().replace("Field", "");
                        result += "=";
                        result += value;
                        result += "&";
                    } else {
                        return "";
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        result += ("password=" + new String(passwordField.getPassword()));
        return result;
    }

    public void closeWindow() {
        super.closeWindow();
        panel.setVisible(false);
    }

    @Override
    protected void update(String fieldName, String value)
            throws ReflectiveOperationException {
        //no update
    }
}
