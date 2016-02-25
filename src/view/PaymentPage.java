package view;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;

/**
 * Created by Guan Huang on 11/29/2015.
 */
public class PaymentPage extends View {
    private JPanel panel;
    private JLabel balance;
    private JTextArea summary;
    private JTextField creditCardNumber;
    private JComboBox creditCardType;
    private JTextField name;
    private JTextField month;
    private JTextField date;
    private JTextField year;
    private JLabel validThrough;
    private JTextField cvc;
    private JTextField email;
    private JButton checkOutButton;
    private JFrame newFrame;

    public PaymentPage() {
        newFrame = new JFrame("Payment Page");
        setUpFrame(newFrame, null, panel, 800, 700);
    }

    public void addCheckOutButtonListener(ActionListener actionListener){
        checkOutButton.addActionListener(actionListener);
    }

    @Override
    public void update(String fieldName, String value)
            throws ReflectiveOperationException {
        final Field field = getClass().getDeclaredField(fieldName);
        final Class<?> type = field.getType();

        if (type.equals(JLabel.class)) {
            ((JLabel) field.get(this)).setText(value);
        } else if (type.equals(JTextArea.class) || type.equals(JTextField.class)) {
            ((JTextComponent) field.get(this)).setText(value);
        }
    }

    @Override
    public void closeWindow() {
        newFrame.dispose();
        newFrame.setVisible(false);
    }
}
