package view.template;

import global.Session;
import view.View;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Created by Guan Huang on 11/15/2015.
 */
public class ItemPanel extends View {
    private JPanel panel;
    private JLabel productName;
    private JButton submitButton;
    private JPanel imagePath;
    private JLabel sellingPrice;

    protected void update(String fieldName, String value) {
        switch (fieldName) {
            case "sellingPrice":
                sellingPrice.setText(String.format("$%s", value));
                break;
            case "productName":
                productName.setText(value);
                break;
            case "imagePath":
                ((ImagePanel) imagePath).setImage(Session.imageRootPath + value);
                break;
        }
    }

    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void closeWindow() {
        panel.setVisible(false);
    }

    public void addSubmitBtnActionListener(ActionListener actionListener) {
        submitButton.addActionListener(actionListener);
    }

    public void setSubmitButtonLabel(String newLabel) {
        submitButton.setText(newLabel);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        imagePath = new ImagePanel("");
    }
}
