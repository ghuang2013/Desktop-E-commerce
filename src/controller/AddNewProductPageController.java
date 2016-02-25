package controller;

import controller.mainWindow.MainWindowController;
import model.Product;
import model.StatusCode;
import view.AddNewProductPage;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static global.Session.*;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Created by Guan Huang on 11/19/2015.
 */
public class AddNewProductPageController extends Controller {
    private File file;
    private JFileChooser chooser;
    private MainWindowController parentView;

    public AddNewProductPageController(MainWindowController parent) {
        this.parentView = parent;
        chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & PNG Images", "jpg", "png");
        chooser.setFileFilter(filter);

        view = new AddNewProductPage();
        AddNewProductPage addNewProductPage = (AddNewProductPage) view;
        addNewProductPage.chooseFileButtonActionListener(impFileButtonActionListener());
        addNewProductPage.newProductButtonActionListener(impNewProductButtonActionListener());
    }

    /**
     * change the file label
     */
    public ActionListener impFileButtonActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddNewProductPage addNewProductPage = ((AddNewProductPage) view);
                int status = chooser.showOpenDialog(addNewProductPage.getFrame());
                if (status == JFileChooser.APPROVE_OPTION) {
                    file = chooser.getSelectedFile();
                    addNewProductPage.setFileName(file.toString());
                }
            }
        };
    }

    /**
     * move the selected file to the product directory
     * save the new product to the database and display the message
     */
    public ActionListener impNewProductButtonActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (file != null) {
                    String folderURL = getClass().getResource(favIconPath).getPath().
                            replace("shoppingCart.png", "products");
                    boolean moved = file.renameTo(new File(String.format("%s\\%s.jpg",
                            folderURL, getTimeStamp())));
                }
                String status = notifyModel(view.toString());
                showMessageDialog(null, status);
            }
        };
    }

    public String notifyModel(String dataString) {
        Product product = new Product(dataString);
        StatusCode statusCode = getCurrentLoggedInUser().addNewProductToInventory(product);
        if (statusCode.equals(StatusCode.SUCCESS)) {
            parentView.resetContentPane();
            return "Your product has been added";
        }
        return "Something went wrong";
    }

    @Override
    public void notifyView() {

    }
}
