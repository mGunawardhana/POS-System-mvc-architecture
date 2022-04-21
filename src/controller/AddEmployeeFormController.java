package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.cashier;
import util.CrudUtil;
import util.NotificationController;
import util.validationUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AddEmployeeFormController {

    public TableView<cashier> CashierTable;

    public TableColumn CashierNameCol;
    public TableColumn CashierIdCol;
    public TableColumn CashierNICCol;
    public TableColumn CashierContactCol;
    public TableColumn CashierSalaryCol;
    public TableColumn CashierPasswordCol;

    public TextField idTxt;
    public TextField NameTxt;
    public TextField NicTxt;
    public TextField ContactTxt;
    public TextField PwdTxt;
    public TextField SalaryTxt;
    public Button btnSaveItem;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();


    /* - SHOWING DATA VALUES IN EMPLOYEE TABLE - */

    public void initialize() {
        CashierIdCol.setCellValueFactory(new PropertyValueFactory<>("cashierId"));
        CashierNameCol.setCellValueFactory(new PropertyValueFactory<>("cashierName"));
        CashierPasswordCol.setCellValueFactory(new PropertyValueFactory<>("cashierPwd"));
        CashierContactCol.setCellValueFactory(new PropertyValueFactory<>("cashierContact"));
        CashierNICCol.setCellValueFactory(new PropertyValueFactory<>("cashierNIC"));
        CashierSalaryCol.setCellValueFactory(new PropertyValueFactory<>("cashierSalary"));

        try {
            loadAllCashiers();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        btnSaveItem.setOnMouseClicked(event -> {
            SaveBtnOnAction();
        });

        Pattern idTxtPattern = Pattern.compile("^(C00-)[0-9]{2,4}$");                        //true
        Pattern NameTxtPattern = Pattern.compile("^[A-z ]{3,20}$");                          //true
        Pattern NicTxtPattern = Pattern.compile("^([0-9]{12}|[0-9]{12}v)$");                 //true
        Pattern ContactTxtPattern = Pattern.compile("^(07(1|2|4|5|6|7|8)|091)(-)[0-9]{7}$"); //true
        Pattern PwdTxtPattern = Pattern.compile("^[0-9]{4,5}$");                             //true
        Pattern SalaryTxtPattern = Pattern.compile("^[0-9]{3,5}(.)[0]{1,2}$");               //true

        /* - MATCHING ENTERED VALUES WITH REG-X PATTERN - */

        map.put(idTxt, idTxtPattern);
        map.put(NameTxt, NameTxtPattern);
        map.put(NicTxt, NicTxtPattern);
        map.put(ContactTxt, ContactTxtPattern);
        map.put(PwdTxt, PwdTxtPattern);
        map.put(SalaryTxt, SalaryTxtPattern);
    }

    /*- LOAD ALL CASHIER DETAILS INTO CASHIER TABLE -*/

    private void loadAllCashiers() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Cashier");
        ObservableList<cashier> obList = FXCollections.observableArrayList();

        while (result.next()) {
            obList.add(
                    new cashier(
                            result.getString("CashierID"),
                            result.getString("CashierName"),
                            result.getString("CashierPsw"),
                            result.getString("CashierCNumber"),
                            result.getString("CashierNIC"),
                            result.getDouble("CashierSalary")
                    )
            );
        }
        CashierTable.setItems(obList);
        CashierTable.refresh();
    }


    /*- SEARCH ALL CASHIER DETAILS AND LOAD INTO TEXT FIELDS -*/

    public void save(ActionEvent actionEvent) {

        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM Cashier WHERE CashierID=?", idTxt.getText());

            if (result.next()) {

                NameTxt.setText(result.getString(2));
                PwdTxt.setText(result.getString(3));
                ContactTxt.setText(result.getString(4));
                NicTxt.setText(result.getString(5));
                SalaryTxt.setText(result.getString(6));


            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
                loadAllCashiers();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /*- SAVE ENTERED DETAILS INTO CASHIER DETAILS -*/

    public void SaveBtnOnAction() {
        String idTxtCheck = idTxt.getText();
        String NameTxtCheck = NameTxt.getText();
        String PwdTxtCheck = PwdTxt.getText();
        String ContactTxtCheck = ContactTxt.getText();
        String NicTxtCheck = NicTxt.getText();
        double SalaryTxtCheck = Double.parseDouble(SalaryTxt.getText());

        cashier cash = new cashier(
                idTxtCheck,
                NameTxtCheck,
                PwdTxtCheck,
                ContactTxtCheck,
                NicTxtCheck,
                SalaryTxtCheck
        );
        try {
            if (CrudUtil.execute("INSERT INTO Cashier VALUES (?,?,?,?,?,?)",
                    cash.getCashierId(), cash.getCashierName(), cash.getCashierPwd(), cash.getCashierContact(),
                    cash.getCashierNIC(), cash.getCashierSalary()

            )) {
                NotificationController.SuccessfulTableNotification("Saved ", "");
                loadAllCashiers();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        CashierTable.refresh();
        Clear();
    }

    /*- SET COLOURS INTO TEX FIELD BORDERS -*/

    public void textFields_Key_Released(KeyEvent keyEvent) {
        validationUtil.validate(map, btnSaveItem);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = validationUtil.validate(map, btnSaveItem);
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }


    /*- UPDATE PREVIOUS DETAILS IN CASHIER TABLE -*/

    public void UpdateBtnOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        cashier cash = new cashier(
                idTxt.getText(),
                NameTxt.getText(),
                NicTxt.getText(),
                ContactTxt.getText(),
                PwdTxt.getText(),
                Double.parseDouble(SalaryTxt.getText())
        );

        try {
            boolean isUpdated2 = CrudUtil.execute(
                    "UPDATE Cashier SET CashierName=? ," +
                            " CashierNIC=? ," +
                            " CashierCNumber=? ," +
                            " CashierPsw=? ," +
                            " CashierSalary=?  WHERE CashierID=?",
                    cash.getCashierName(), cash.getCashierPwd(), cash.getCashierContact(),
                    cash.getCashierNIC(), cash.getCashierSalary(), cash.getCashierId()
            );
            if (isUpdated2) {

                new Alert(Alert.AlertType.CONFIRMATION, "Update Confirmed!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }
        } catch (SQLException | ClassNotFoundException ignored) {
        }
        Clear();
    }


    /*- REMOVE CASHIER DETAILS IN TABLE -*/

    public void RemoveBtnOnAction(ActionEvent actionEvent) {
        try {
            if (CrudUtil.execute("DELETE FROM Cashier WHERE CashierID=?", idTxt.getText())) {
                loadAllCashiers();
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
                loadAllCashiers();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }

        } catch (SQLException | ClassNotFoundException ignored) {}
        CashierTable.refresh();
        Clear();
    }

    /*- CLEAR ALL TEXT-FIELDS -*/

    public void Clear() {
        idTxt.clear();
        NameTxt.clear();
        NicTxt.clear();
        ContactTxt.clear();
        SalaryTxt.clear();
        PwdTxt.clear();
    }
}
