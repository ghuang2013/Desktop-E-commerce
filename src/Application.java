import controller.account.AccountController;
import controller.account.LoginController;
import global.Session;
import view.View;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.io.File;

import static global.Session.getTimeStamp;

/**
 * Created by Guan Huang on 11/7/2015.
 */
public class Application {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        AccountController controller = new LoginController();
        Session.setGlobalController(controller);
    }
}
