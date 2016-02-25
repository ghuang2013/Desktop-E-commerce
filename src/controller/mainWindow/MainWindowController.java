package controller.mainWindow;

import controller.Controller;
import global.Session;
import model.User;
import view.View;
import view.mainWindow.MainWindowView;
import view.template.EmptyItemPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Guan Huang on 11/21/2015.
 */
public abstract class MainWindowController extends Controller {
    protected ArrayList<String> products;
    protected int pageNumber = 0;
    protected User user = Session.getCurrentLoggedInUser();

    public MainWindowController(View view) {
        this.view = view;
        this.products = selectProductsFromDatabase();
        updateWelcomeLabel();
        ((MainWindowView) this.view).paginationActionListener(paginationActionListener(false),
                paginationActionListener(true));
        notifyView();
    }

    @Override
    public void notifyView() {
        MainWindowView homePage = (MainWindowView) view;
        if (products == null) {
            homePage.fillContentPane(new EmptyItemPanel().getPanel());
            return;
        }
        for (int i = pageNumber * 4; i < (pageNumber + 1) * 4; ++i) {
            if (i >= products.size()) {
                break;
            }
            String product = products.get(i);
            homePage.fillContentPane(createSubPanel(product));
        }
        homePage.repaintContentPane();
    }

    public void resetContentPane() {
        MainWindowView homePage = (MainWindowView) view;
        homePage.clearContentPane();
        this.products = selectProductsFromDatabase();
        notifyView();
    }

    private void updateWelcomeLabel() {
        ((MainWindowView) view).setWelcomeLabel(user.getPrimaryKeysValue());
    }

    private ActionListener paginationActionListener(boolean nextPage) {
        return new ActionListener() {
            public void setPageNumber() {
                int maxPage = products.size() / 4;
                if (nextPage) {
                    pageNumber = (pageNumber == maxPage) ? maxPage : pageNumber + 1;
                } else {
                    pageNumber = (pageNumber > 0) ? pageNumber - 1 : 0;
                }
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                setPageNumber();
                ((MainWindowView) view).clearContentPane();
                notifyView();
            }
        };
    }

    protected abstract JPanel createSubPanel(final String product);

    protected abstract ArrayList selectProductsFromDatabase();
}
