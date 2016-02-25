package view;

import global.Session;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;

/**
 * Created by Guan Huang on 11/20/2015.
 */
public class AddNewProductPage extends View {
    private JPanel panel;
    private JButton addNewProductButton;
    private JPanel contentPanel;
    private JTextField productName;
    private JTextField sellingPrice;
    private JTextField cost;
    private JTextArea description;
    private JComboBox category;
    private JSpinner quantity;
    private JButton chooseFileButton;
    private JLabel fileName;
    private JFrame newFrame;

    public AddNewProductPage() {
        newFrame = new JFrame("Add New Product");
        setUpFrame(newFrame, null, panel, 500, 600);
    }

    public void newProductButtonActionListener(ActionListener actionListener) {
        addNewProductButton.addActionListener(actionListener);
    }

    public void chooseFileButtonActionListener(ActionListener actionListener) {
        chooseFileButton.addActionListener(actionListener);
    }

    public void setFileName(String fileName) {
        this.fileName.setText(fileName);
    }

    @Override
    protected void update(String fieldName, String value)
            throws ReflectiveOperationException {
        if(fieldName.equals("fileName")){
            fileName.setText(value);
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

    public String toString() {
        String result = "";
        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields) {
            try {
                Class fieldType = field.getType();
                if (fieldType.equals(JTextField.class)) {
                    String value = ((JTextField) field.get(this)).getText();
                    if (value.equals("")) {
                        continue;
                    }
                    result += field.getName();
                    result += "=";
                    result += value;
                    result += "&";
                } else if (fieldType.equals(JSpinner.class)) {
                    result += "quantity=" + quantity.getValue() + '&';
                } else if (fieldType.equals(JComboBox.class)) {
                    result += "category=" + category.getSelectedItem() + '&';
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        String fileNameText = fileName.getText();
        if (!fileNameText.trim().equals("")) {
            result += String.format("imagePath=%s.jpg&", Session.getTimeStamp());
        }
        String descText = description.getText();
        if (!descText.trim().equals("")) {
            result += String.format("description=%s", descText);
        }
        return result;
    }
}
