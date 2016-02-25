package controller;

import view.View;

/**
 * Observable Controller
 * Observers model and view
 */
public abstract class Controller{
    protected View view;
    public static final String CurrencyFormatter = "Balance: $%.2f";

    public abstract void notifyView();

    public void closeView() {
        view.closeWindow();
    }
}
