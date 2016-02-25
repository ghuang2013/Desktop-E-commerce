package controller.account;

import model.StatusCode;
import model.User;
import view.account.SignUpPage;

/**
 * Created by Guan Huang on 11/7/2015.
 * Controller for sign up form
 * Observable: Controller
 * Observer: Model, View
 */
public class SignUpController extends AccountController {

    public SignUpController() {
        super(new User(), new SignUpPage());
        ((SignUpPage) view).addSubmitBtnActionListener(impSubmitBtnEvent());
    }

    public String validation(String resultString) {
        super.validation(resultString);
        SignUpPage signUpView = (SignUpPage) view;
        String password = signUpView.getPasswordField();
        String confirmPassword = signUpView.getConfirmPasswordField();

        if (password.trim().equals("") || confirmPassword.trim().equals(""))
            return "Passwords cannot be empty";
        else if (!password.equals(confirmPassword))
            return "Passwords don't match";
        else if (password.length() < 6)
            return "Password is less than 6 characters";
        return "Success";
    }

    public String notifyModel(String formData) {
        user = new User(formData);
        user.setActive();
        StatusCode statusCode = user.insertIntoDatabase();
        if (statusCode.equals(StatusCode.DUPLICATED_ENTRY)) {
            return "Username is already exists";
        } else if (statusCode.equals(StatusCode.SUCCESS)) {
            notifyView();
            return "You account has been created successfully";
        } else {
            return "Something went wrong";
        }
    }
}
