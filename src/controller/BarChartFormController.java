package controller;

import com.mysql.jdbc.Connection;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.UILoader;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BarChartFormController {

    public AnchorPane barChartAnchorPane;
    @FXML
    private AreaChart<?, ?> barChart;

    /*- LOAD ALL CUSTOMER DETAILS INTO CHART -*/

    public void initialize() throws SQLException, ClassNotFoundException {

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("2021");

        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("select CustomerDate, Count(*) from fruitmarket.customer Group by CustomerDate");
        ResultSet rst = stm.executeQuery();
        String prevDay = "";

        while (rst.next()) {
            String date = rst.getString(1);

            int count = rst.getInt(2);
            series1.getData().add(new XYChart.Data<>(date, count));
            prevDay = date;
        }
        barChart.getData().addAll(series1);
    }

    /*- LOADING DAILY INCOME FORM -*/

    public void clickOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.SetUi("DailyIncomeForm", barChartAnchorPane);
    }

    /*- LOADING MONTHLY INCOME FORM -*/

    public void monthlyClickOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.SetUi("MonthlyIncomeForm", barChartAnchorPane);
    }

    /*- LOADING YEARLY INCOME FORM -*/

    public void yearlyOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.SetUi("YearlyIncomeForm", barChartAnchorPane);
    }

    /*- LOADING STORE DETAILS INTO PIE CHART -*/

    public void StoreItemsOnActions(ActionEvent actionEvent) {
        try {
            JasperReport compileReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/reports/store.jasper"));
            Connection connection = (Connection) DBConnection.getInstance().getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, connection);
            JasperViewer.viewReport(jasperPrint,false);

        } catch (JRException | SQLException | ClassNotFoundException ignored) {
        }
    }
}
