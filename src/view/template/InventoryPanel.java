package view.template;

import view.View;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Guan Huang on 11/20/2015.
 */
public class InventoryPanel extends View {
    private JTextField productName;
    private JTextField sellingPrice;
    private JTextField cost;
    private JSpinner quantity;
    private JTextArea description;
    private JComboBox category;
    private JButton update;
    private JPanel panel;
    private JButton delete;

    public void addKeyListener(KeyListener productNameListener, KeyListener sellingPriceListener,
                               KeyListener costListener, KeyListener descListener) {
        productName.addKeyListener(productNameListener);
        sellingPrice.addKeyListener(sellingPriceListener);
        cost.addKeyListener(costListener);
        description.addKeyListener(descListener);
    }

    public void addActionListener(ActionListener actionListener) {
        category.addActionListener(actionListener);
    }

    public void addChangeListener(ChangeListener changeListener) {
        quantity.addChangeListener(changeListener);
    }

    public void addDeleteListener(ActionListener actionListener) {
        delete.addActionListener(actionListener);
    }

    @Override
    protected void update(String fieldName, String value)
            throws ReflectiveOperationException {
        switch (fieldName) {
            case "quantity":
                quantity.setValue(Integer.valueOf(value));
                break;
            case "category":
                category.setSelectedItem(value);
                break;
            default:
                Field field = className.getDeclaredField(fieldName);
                Class type = field.getType();
                if (type.equals(JTextField.class) || type.equals(JTextArea.class)) {
                    Method method = type.getMethod("setText", String.class);
                    method.invoke(field.get(this), value);
                }
        }
    }

    @Override
    public void closeWindow() {
        panel.setVisible(false);
    }

    public void addSubmitBtnActionListener(ActionListener actionListener) {
        update.addActionListener(actionListener);
    }

    public JPanel getPanel() {
        return panel;
    }
}
