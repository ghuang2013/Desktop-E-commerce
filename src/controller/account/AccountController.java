package controller.account;

import controller.Controller;
import controller.mainWindow.BuyerHomeController;
import controller.mainWindow.SellerHomeController;
import global.Session;
import model.User;
import view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.showMessageDialog;

/*
    Controller interface for sign up and log in face
 */
public abstract class AccountController extends Controller {
    protected User user;

    public AccountController(User model, View view) {
        this.user = model;
        this.view = view;
    }

    /*
    form validation
    login: if password is longer than 6 digits
    sign up: if password.equals(confirmedPassword)
     */
    public String validation(String formData) {
        if (formData.equals("")) {
            return "You must complete all fields";
        } else {
            return "Success";
        }
    }

    public ActionListener impSubmitBtnEvent(){
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String formData = view.toString();
                String form_validation_status = validation(formData);
                if (form_validation_status.equals("Success")) {
                    String db_status = notifyModel(formData);
                    showMessageDialog(null, db_status);
                } else {
                    showMessageDialog(null, form_validation_status);
                }
            }
        };
    }

    protected abstract String notifyModel(String dataString);

    @Override
    public void notifyView() {
        view.closeWindow();
        if (user.getLoginType().equals(User.LoginType.Seller)) {
            Session.load(new SellerHomeController());
        } else {
            Session.load(new BuyerHomeController());
        }
    }
}
