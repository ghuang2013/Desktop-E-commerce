package global;

import controller.Controller;
import model.User;

import java.util.Date;

/**
 * Created by Guan Huang on 11/12/2015.
 */
public class Session {

    private static Controller controller;
    private static User currentLoggedInUser;
    private static Date date = new Date();

    public final static String imageRootPath = "/products/";
    public final static String iconRootPath = "/";
    public final static String favIconPath = Session.iconRootPath + "shoppingCart.png";

    public static long getTimeStamp(){
        return date.getTime();
    }

    public static void setCurrentLoggedInUser(User user) {
        currentLoggedInUser = user;
    }

    public static User getCurrentLoggedInUser(){
        return currentLoggedInUser;
    }

    public static void setGlobalController(Controller otherController) {
        controller = otherController;
    }

    public static Controller getGlobalController(){
        return controller;
    }

    public static void load(Controller otherController) {
        setGlobalController(otherController);
    }

    private Session() {
    }
}
