package view.template;

import view.View;

import javax.swing.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Guan Huang on 11/30/2015.
 */
public class ReportItemPanel extends View {
    private JPanel panel;
    private JLabel productName;
    private JLabel productId;
    private JLabel sellingPrice;
    private JLabel cost;
    private JLabel totalPrice;
    private JLabel buyername;
    private JLabel quantitySelected;
    private JLabel date;

    @Override
    protected void update(String fieldName, String value)
            throws ReflectiveOperationException {
        switch (fieldName) {
            case "sellingPrice":
                sellingPrice.setText(String.format("$%.2f", Double.valueOf(value)));
                break;
            case "cost":
                cost.setText(String.format("$%.2f", Double.valueOf(value)));
                break;
            case "totalPaid":
                totalPrice.setText(String.format("$%.2f", Double.valueOf(value)));
                break;
            default:
                Field field = className.getDeclaredField(fieldName);
                Class type = field.getType();
                if (type.equals(JLabel.class)) {
                    Method method = type.getMethod("setText", String.class);
                    method.invoke(field.get(this), value);
                }
                break;
        }
    }

    @Override
    public void closeWindow() {
        panel.setVisible(false);
    }

    public JPanel getPanel() {
        return panel;
    }
}
