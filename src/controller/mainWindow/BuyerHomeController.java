package controller.mainWindow;

import controller.ProductDetailController;
import model.Model;
import model.Product;
import view.mainWindow.BuyerHomePage;
import view.template.ItemPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Guan Huang on 11/15/2015.
 */
public class BuyerHomeController extends MainWindowController {

    private final String nextStep = "Detail Page";

    public BuyerHomeController() {
        super(new BuyerHomePage());
        BuyerHomePage homePageView = (BuyerHomePage) view;
        homePageView.comboBoxActionListener(impComboBoxEvent(homePageView));
    }

    @Override
    protected ArrayList<String> selectProductsFromDatabase() {
        // return Model.selectAllDataStringFromDatabase(Product.class);
        HashMap<String, Boolean> keyValuePair = new HashMap<>();
        keyValuePair.put("sold", false);
        return Model.selectDataStringFromDatabase(Product.class, null, keyValuePair);
    }

    @Override
    protected JPanel createSubPanel(final String product) {
        ItemPanel itemPanel = new ItemPanel();
        itemPanel.setSubmitButtonLabel(nextStep);
        itemPanel.update(product);

        itemPanel.addSubmitBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProductDetailController(product);
            }
        });
        return itemPanel.getPanel();
    }

    public ActionListener impComboBoxEvent(BuyerHomePage buyerHomePage) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox comboBox = (JComboBox) e.getSource();
                String categorySelected = (String) comboBox.getSelectedItem();
                HashMap<String, Object> pair = new HashMap<>();
                pair.put("sold", false);
                if (categorySelected.equals("All")) {
                    products = Model.selectDataStringFromDatabase(Product.class, null, pair);
                } else {
                    pair.put("category", categorySelected);
                    products = Model.selectDataStringFromDatabase(Product.class, null, pair);
                }
                pageNumber = 0;
                buyerHomePage.clearContentPane();
                notifyView();
            }
        };
    }
}
