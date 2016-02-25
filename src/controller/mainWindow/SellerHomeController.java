package controller.mainWindow;

import controller.AddNewProductPageController;
import model.Product;
import model.StatusCode;
import view.mainWindow.SellerHomePage;
import view.template.InventoryPanel;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.text.JTextComponent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Created by Guan Huang on 11/20/2015.
 */
public class SellerHomeController extends MainWindowController {

    /**
     * keep track of the changed properties in the product class
     */
    private ArrayList<String> changedProperties = new ArrayList<>();

    public SellerHomeController() {
        super(new SellerHomePage());
        SellerHomePage sellerHomePage = (SellerHomePage) view;
        sellerHomePage.addNewProductButtonActionListener(impAddNewProductButtonEvent());
    }

    @Override
    protected ArrayList selectProductsFromDatabase() {
        return user.getInventory();
    }

    private ActionListener impAddNewProductButtonEvent() {
        return e -> new AddNewProductPageController(this);
    }

    @Override
    protected JPanel createSubPanel(String dataString) {
        InventoryPanel inventoryPanel = new InventoryPanel();
        inventoryPanel.update(dataString);

        Product product = new Product(dataString);
        inventoryPanel.addKeyListener(impKeyListener(product, "productName"),
                impKeyListener(product, "sellingPrice"),
                impKeyListener(product, "cost"),
                impKeyListener(product, "description"));

        inventoryPanel.addChangeListener(impChangeListener(product, "quantity"));
        inventoryPanel.addActionListener(impActionListener(product, "category"));
        inventoryPanel.addSubmitBtnActionListener(impInventoryActionListener(product));
        inventoryPanel.addDeleteListener(impDeleteActionListener(product));
        return inventoryPanel.getPanel();
    }

    private ActionListener impDeleteActionListener(Product product) {
        return e -> {
            product.deleteFromDatabase();
            showMessageDialog(null, "Product has been deleted");
            resetContentPane();
        };
    }

    private ActionListener impActionListener(Product product, String field) {
        return e -> {
            changedProperties.add(field);
            Object value = ((JComboBox) e.getSource()).getSelectedItem();
            try {
                product.update(field, (String) value);
            } catch (ReflectiveOperationException e1) {
                e1.printStackTrace();
            }
        };
    }

    private ChangeListener impChangeListener(Product product, String field) {
        return e -> {
            changedProperties.add(field);
            Object value = ((JSpinner) e.getSource()).getValue();
            try {
                product.update(field, String.valueOf(value));
            } catch (ReflectiveOperationException e1) {
                e1.printStackTrace();
            }
        };
    }

    private KeyListener impKeyListener(Product product, String field) {
        return new KeyListener() {
            private Object source;

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                changedProperties.add(field);
                source = e.getSource();
                try {
                    product.update(field, ((JTextComponent) source).getText());
                } catch (ReflectiveOperationException e1) {
                    e1.printStackTrace();
                }
            }
        };
    }

    private ActionListener impInventoryActionListener(Product product) {
        return e -> {
            try {
                StatusCode statusCode = product.updateDatabase(changedProperties);
                if (statusCode.equals(StatusCode.SUCCESS)) {
                    showMessageDialog(null, "Product has been updated successfully");
                    resetContentPane();
                }
            } catch (ReflectiveOperationException e1) {
                e1.printStackTrace();
            }
        };
    }
}
