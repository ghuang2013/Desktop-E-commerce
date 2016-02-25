package view;

import global.Session;
import view.template.ImagePanel;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Guan Huang on 11/17/2015.
 */
public class ProductDetailPage extends View {
    private JPanel panel;
    private JTextPane description;
    private JLabel productName;
    private JButton addToCartButton;
    private JPanel imagePath;
    private JLabel sellingPrice;
    private JTabbedPane tabbedPane1;
    private JButton buyNowButton;
    private JLabel username;
    private JSlider quantity;
    private JLabel jsliderLabel;
    private int jslideLabelMaximal = 0;
    private JFrame newFrame;

    public ProductDetailPage() {
        newFrame = new JFrame("Product Detail Page");
        setUpFrame(newFrame, null, panel, 650, 600);
    }

    public void addToCartButtonActionListener(ActionListener actionListener) {
        addToCartButton.addActionListener(actionListener);
    }

    public void setJSliderLabelText(int value) {
        jsliderLabel.setText(String.format("%d out of %d", value, jslideLabelMaximal));
    }

    public void addJSliderChangeListener(ChangeListener changeListener) {
        quantity.addChangeListener(changeListener);
    }

    @Override
    protected void update(String fieldName, String value) throws ReflectiveOperationException {
        switch (fieldName) {
            case "imagePath":
                ((ImagePanel) imagePath).setImage(Session.imageRootPath + value);
                break;
            case "quantity":
                int maxValue = Integer.valueOf(value);
                quantity.setMaximum(maxValue);
                jsliderLabel.setText(String.format("1 out of %d", maxValue));
                jslideLabelMaximal = maxValue;
                break;
            case "sellingPrice":
                sellingPrice.setText(String.format("$%s", value));
                break;
            default:
                Field field = className.getDeclaredField(fieldName);
                Class classType = field.getType();
                if (classType.equals(JLabel.class) ||
                        field.getType().equals(JTextPane.class)) {
                    Method method = classType.getMethod("setText", String.class);
                    method.invoke(field.get(this), value);
                }
        }
    }

    public JFrame getFrame() {
        return newFrame;
    }

    @Override
    public void closeWindow() {
        newFrame.dispose();
        newFrame.setVisible(false);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        imagePath = new ImagePanel("");
    }
}