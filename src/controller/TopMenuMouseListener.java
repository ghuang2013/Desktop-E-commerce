package controller;

import controller.mainWindow.BuyerHomeController;
import controller.mainWindow.SellerHomeController;
import controller.mainWindow.SellerReportController;
import controller.mainWindow.ShoppingCartController;
import global.Session;
import model.User;
import model.User.LoginType;
import view.template.TopMenu;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Guan Huang on 11/20/2015.
 */
public class TopMenuMouseListener implements MouseListener {

    final User currentLoggedInUser = Session.getCurrentLoggedInUser();

    @Override
    public void mouseClicked(MouseEvent e) {
        String itemSelected = ((JMenu) e.getSource()).getText();
        itemSelected = itemSelected.replace(" ", "_");
        TopMenu.MenuItem menuItem = TopMenu.MenuItem.valueOf(itemSelected);
        Controller currentController = Session.getGlobalController();

        switch (menuItem) {
            case Home:
                currentController.closeView();
                if (currentLoggedInUser.getLoginType().equals(LoginType.Seller)) {
                    Session.load(new SellerHomeController());
                } else {
                    Session.load(new BuyerHomeController());
                }
                break;
            case Switch_to_Buyer:
                currentLoggedInUser.setLoginType(LoginType.Buyer);
                currentController.closeView();
                Session.load(new BuyerHomeController());
                break;
            case Shopping_Cart:
                currentController.closeView();
                Session.load(new ShoppingCartController());
                break;
            case Switch_to_Seller:
                currentLoggedInUser.setLoginType(LoginType.Seller);
                currentController.closeView();
                Session.load(new SellerHomeController());
                break;
            case View_Sell_Report:
                currentController.closeView();
                Session.load(new SellerReportController());
                break;
            case Log_out:
                Session.setCurrentLoggedInUser(null);
                currentController.closeView();
                break;
            default:
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
