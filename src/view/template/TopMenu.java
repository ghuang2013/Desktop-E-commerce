package view.template;

import controller.TopMenuMouseListener;

import javax.swing.*;
import java.util.ArrayList;

import static model.User.*;

/**
 * Created by Guan Huang on 11/19/2015.
 */
public class TopMenu extends JMenuBar {
    protected ArrayList<JMenu> jMenus = new ArrayList<>();

    public enum MenuItem {
        Home, Shopping_Cart, Switch_to_Seller, Switch_to_Buyer, View_Sell_Report, Log_out
    }

    public static TopMenu getInstance(LoginType type) {
        if (type.equals(LoginType.Seller)) {
            return new SellerMenu();
        } else {
            return new BuyerMenu();
        }
    }

    public String getMenuItem(MenuItem menuItem) {
        return String.valueOf(menuItem).replace("_", " ");
    }

    protected TopMenu() {
        jMenus.add(new JMenu(getMenuItem(MenuItem.Home)));
    }
}

class BuyerMenu extends TopMenu {

    public BuyerMenu() {
        super();
        jMenus.add(new JMenu(getMenuItem(MenuItem.Shopping_Cart)));
        jMenus.add(new JMenu(getMenuItem(MenuItem.Switch_to_Seller)));
        jMenus.add(new JMenu(getMenuItem(MenuItem.Log_out)));

        for (JMenu menu : jMenus) {
            this.add(menu);
            menu.addMouseListener(new TopMenuMouseListener());
        }
    }
}

class SellerMenu extends TopMenu {

    public SellerMenu() {
        super();
        jMenus.add(new JMenu(getMenuItem(MenuItem.View_Sell_Report)));
        jMenus.add(new JMenu(getMenuItem(MenuItem.Switch_to_Buyer)));
        jMenus.add(new JMenu(getMenuItem(MenuItem.Log_out)));

        for (JMenu menu : jMenus) {
            this.add(menu);
            menu.addMouseListener(new TopMenuMouseListener());
        }
    }
}