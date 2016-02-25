package controller;

import global.Session;
import model.Product;
import model.StatusCode;
import model.User;
import view.ProductDetailPage;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Created by Guan Huang on 11/17/2015.
 */
public class ProductDetailController extends Controller {
    private String productDataString;
    private User currentLoggedInUser = Session.getCurrentLoggedInUser();
    //has to select at least one in shopping cart
    private int quantitySelected = 1;

    public ProductDetailController(String dataString) {
        view = new ProductDetailPage();
        productDataString = dataString;
        notifyView();
        ProductDetailPage productDetailPage = ((ProductDetailPage) view);
        productDetailPage.addJSliderChangeListener(impSliderChangeListener(productDetailPage));
        productDetailPage.addToCartButtonActionListener(impAddToCartEvent());
    }

    private ActionListener impAddToCartEvent() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product product = new Product(productDataString);

                StatusCode statusCode =
                        currentLoggedInUser.addProductToShoppingCart(product, quantitySelected);
                if (statusCode.equals(StatusCode.SUCCESS)) {
                    showMessageDialog(null, "This product has been added to the shopping cart");
                } else if (statusCode.equals(StatusCode.DUPLICATED_ENTRY)) {
                    showMessageDialog(null, "This product was added previously");
                } else {
                    showMessageDialog(null, "Something went wrong but I am not sure what");
                }
            }
        };
    }

    public ChangeListener impSliderChangeListener(ProductDetailPage productDetailPage) {
        return new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                quantitySelected = ((JSlider) e.getSource()).getValue();
                productDetailPage.setJSliderLabelText(quantitySelected);
            }
        };
    }

    @Override
    public void notifyView() {
        ProductDetailPage productDetailPage = ((ProductDetailPage) view);
        productDetailPage.update(productDataString);
    }
}
