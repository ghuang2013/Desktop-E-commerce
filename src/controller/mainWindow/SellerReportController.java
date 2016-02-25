package controller.mainWindow;

import view.mainWindow.SellerReportPage;
import view.template.ReportItemPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ghuan_000 on 11/30/2015.
 */
public class SellerReportController extends MainWindowController {
    public SellerReportController() {
        super(new SellerReportPage());
    }

    @Override
    protected JPanel createSubPanel(String product) {
        ReportItemPanel reportItemPanel = new ReportItemPanel();
        reportItemPanel.update(product);
        return reportItemPanel.getPanel();
    }

    @Override
    protected ArrayList selectProductsFromDatabase() {
        final ArrayList<String> sellerRecordItems = user.getSellerRecordItems();
        SellerReportPage sellerReportPage = (SellerReportPage) view;

        double revenue = user.getSellerRecord().getRevenue();
        double cost = getCost(sellerRecordItems);
        double profit = revenue - cost;
        try {
            sellerReportPage.update("revenue", String.format("Revenue: $%.2f", revenue));
            sellerReportPage.update("cost", String.format("Cost: $%.2f", cost));
            sellerReportPage.update("profit", String.format("Profit: $%.2f", profit));
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }

        return sellerRecordItems;
    }

    private double getCost(ArrayList<String> sellerRecordItems) {
        double cost = 0.00;
        for (String sellerRecordItem : sellerRecordItems) {
            Pattern pattern = Pattern.compile("cost=([0-9.]+).*&quantitySelected=([0-9]+)");
            System.out.println(sellerRecordItem);
            Matcher matcher = pattern.matcher(sellerRecordItem);
            if (matcher.find()) {
                cost += Double.valueOf(matcher.group(1)) * Double.valueOf(matcher.group(2));
            }
        }
        return cost;
    }
}
