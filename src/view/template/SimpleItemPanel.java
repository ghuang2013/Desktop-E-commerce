package view.template;

import global.Session;
import view.View;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionListener;

/**
 * Created by Guan Huang on 11/29/2015.
 */
public class SimpleItemPanel extends View {
    private JPanel panel;
    private JLabel productName;
    private JSpinner quantitySelected;
    private JPanel imagePath;
    private JLabel sellingPrice;
    private JLabel totalPrice;
    private JButton delete;

    public void addChangeListener(ChangeListener changeListener){
        quantitySelected.addChangeListener(changeListener);
    }

    public void addDeleteActionListener(ActionListener actionListener){
        delete.addActionListener(actionListener);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        imagePath = new ImagePanel("");
    }

    @Override
    public void update(String fieldName, String value)
            throws ReflectiveOperationException {
        switch (fieldName) {
            case "totalPrice":
                totalPrice.setText(String.format("Subtotal: $%s", value));
                break;
            case "productName":
                productName.setText(value);
                break;
            case "imagePath":
                ((ImagePanel) imagePath).setImage(Session.imageRootPath + value);
                break;
            case "quantitySelected":
                quantitySelected.setValue(Integer.valueOf(value));
                break;
            default:
        }
    }

    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void closeWindow() {
        panel.setVisible(false);
    }
}
