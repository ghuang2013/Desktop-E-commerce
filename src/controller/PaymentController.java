package controller;

import controller.mainWindow.MainWindowController;
import global.Session;
import model.Product;
import model.SellerRecord;
import model.User;
import view.PaymentPage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Guan Huang on 11/30/2015.
 */
public class PaymentController extends Controller {

    private User user = Session.getCurrentLoggedInUser();
    private MainWindowController parentWindow;

    public PaymentController(MainWindowController parentWindow,
                             ArrayList<String> productDataStrings) {
        this.parentWindow = parentWindow;
        view = new PaymentPage();
        notifyView();
        PaymentPage paymentPage = (PaymentPage) view;
        paymentPage.addCheckOutButtonListener(impCheckOutEvent(productDataStrings));
    }

    private ActionListener impCheckOutEvent(ArrayList<String> productDataStrings) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!validation()) {
                    return;
                }
                user.getShoppingCart().deleteAll();
                for (String productDataString : productDataStrings) {
                    int quantityPurchased = 0;
                    String seller = "";
                    Pattern pattern = Pattern.compile("username=(.+?)&.*" +
                            "quantitySelected=([0-9]+)");
                    Matcher matcher = pattern.matcher(productDataString);
                    if (matcher.find()) {
                        seller = matcher.group(1);
                        quantityPurchased = Integer.valueOf(matcher.group(2));
                    }

                    final Product product = new Product(productDataString);
                    product.decrementQuantity(quantityPurchased);
                    final List<String> fieldTobeUpdated = new ArrayList<>();
                    fieldTobeUpdated.add("quantity");
                    if (!product.isAvailable()) {
                        product.markSoldOut();
                        fieldTobeUpdated.add("sold");
                    }
                    try {
                        product.updateDatabase(new ArrayList<>(fieldTobeUpdated));
                    } catch (ReflectiveOperationException e1) {
                        e1.printStackTrace();
                    }

                    SellerRecord.saveSoldItem(seller, user.getPrimaryKeysValue(),
                            (int) product.getPrimaryKeysValue(), quantityPurchased,
                            product.getPrice(quantityPurchased));

                    JOptionPane.showMessageDialog(null,
                            "Your transaction has been processed");
                    closeView();
                }
                parentWindow.resetContentPane();
            }
        };
    }

    //form validation
    private boolean validation() {
        return true;
    }

    public void notifyView() {
        PaymentPage paymentPage = (PaymentPage) view;
        try {
            paymentPage.update("balance", String.format(CurrencyFormatter,
                    user.getShoppingCart().getTotalPrice()));
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }
}
