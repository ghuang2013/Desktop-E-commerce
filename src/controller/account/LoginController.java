package controller.account;

import model.User;
import view.account.LoginPage;

import global.Session;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController extends AccountController {

    public LoginController() {
        super(new User(), new LoginPage());
        LoginPage loginView = (LoginPage) view;
        loginView.addSubmitBtnActionListener(impSubmitBtnEvent());
        loginView.addSignUpBtnActionListener(impSignUpBtnEvent());
        loginView.addAccountTypeActionListener(impComboBoxEvent());
    }

    public String notifyModel(String dataString) {
        LoginPage loginView = (LoginPage) view;
        user.update(dataString);
        if (user.checkCredential(loginView.passwordTyped())) {
            notifyView();
            return "Logged in successfully";
        } else {
            loginView.resetPassword();
            user = new User();
            return "Incorrect username and(or) password";
        }
    }

    @Override
    public String validation(String resultStr) {
        return super.validation(resultStr);
    }

    public ActionListener impComboBoxEvent(){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JComboBox cb = (JComboBox) actionEvent.getSource();
                String typeSelected = (String) cb.getSelectedItem();
                user.setLoginType(User.LoginType.valueOf(typeSelected));
            }
        };
    }

    public ActionListener impSignUpBtnEvent(){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Session.load(new SignUpController());
                view.closeWindow();
            }
        };
    }
}
