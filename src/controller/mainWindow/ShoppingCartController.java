package controller.mainWindow;

import controller.PaymentController;
import model.Product;
import view.mainWindow.ShoppingCartPage;
import view.template.SimpleItemPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Guan Huang on 11/28/2015.
 */
public class ShoppingCartController extends MainWindowController {

    public ShoppingCartController() {
        super(new ShoppingCartPage());
        ShoppingCartPage shoppingCartPage = (ShoppingCartPage)view;
        shoppingCartPage.addCheckOutActionListener(impCheckOutEvent());
    }

    private ActionListener impCheckOutEvent() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PaymentController(ShoppingCartController.this, products);
            }
        };
    }

    @Override
    protected JPanel createSubPanel(String product) {
        SimpleItemPanel simpleItemPanel = new SimpleItemPanel();
        simpleItemPanel.addDeleteActionListener(impDeleteButtonEvent(product));
        simpleItemPanel.update(product);
        simpleItemPanel.addChangeListener(impChangeListenerEvent(simpleItemPanel, product));
        return simpleItemPanel.getPanel();
    }

    private ChangeListener impChangeListenerEvent(SimpleItemPanel simpleItemPanel,
                                                  String productDataString) {
        return new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSpinner jSpinner = ((JSpinner) e.getSource());
                int quantitySelected = (int) jSpinner.getValue();
                Pattern pattern = Pattern.compile("sellingPrice=([0-9.]+).*quantity=([0-9]+)");
                Matcher matcher = pattern.matcher(productDataString);

                if (matcher.find()) {
                    double sellingPrice = Double.valueOf(matcher.group(1));
                    double totalPrice = sellingPrice * quantitySelected;
                    try {
                        simpleItemPanel.update("totalPrice", String.valueOf(totalPrice));
                    } catch (ReflectiveOperationException e1) {
                        e1.printStackTrace();
                    }
                    Product product = new Product(productDataString);
                    user.updateProductQuantityFromShoppingCart(product, quantitySelected);
                    resetContentPane();
                }
            }
        };
    }

    private ActionListener impDeleteButtonEvent(String productDataString) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int quantity = 0;
                Pattern pattern = Pattern.compile("quantitySelected=([0-9]+)");
                Matcher matcher = pattern.matcher(productDataString);
                if (matcher.find()) {
                    quantity = Integer.valueOf(matcher.group(1));
                }
                Product product = new Product(productDataString);
                user.deleteProductFromShoppingCart(product, quantity);
                resetContentPane();
            }
        };
    }

    @Override
    protected ArrayList<String> selectProductsFromDatabase() {
        ArrayList<String> shoppingCartProductString = user.getShoppingCartItems();
        double totalBalance = user.getShoppingCart().getTotalPrice();
        try {
            ((ShoppingCartPage) view).update("balance",
                    String.format(CurrencyFormatter, totalBalance));
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
        return shoppingCartProductString;
    }
}
