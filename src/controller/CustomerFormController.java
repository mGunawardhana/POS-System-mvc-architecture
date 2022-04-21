package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.customer;
import util.CrudUtil;
import util.NotificationController;
import util.validationUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class CustomerFormController {

    public AnchorPane CustomerAp;
    public TextField CustomerAddress;
    public TextField CustomerID;
    public TextField CustomerProvince;
    public TextField CustomerName;
    public TextField CustomerCity;
    public TextField CustomerPostalCode;
    public TextField mrMrsBtn;

    public TableView<customer> TableContextFull;
    public TableColumn id;
    public TableColumn mrMrs;
    public TableColumn Customer_Name;
    public TableColumn Address;
    public TableColumn City;
    public TableColumn Province;
    public TableColumn PostalCode;
    public Label DateLbl;
    public Label TimeLbl;
    public Button CustomerSaveButton;

    LinkedHashMap<TextField, Pattern> CustomerMap = new LinkedHashMap<>();


    public void initialize() {

        id.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        mrMrs.setCellValueFactory(new PropertyValueFactory<>("Title"));
        Customer_Name.setCellValueFactory(new PropertyValueFactory<>("C_Name"));
        Address.setCellValueFactory(new PropertyValueFactory<>("C_address"));
        City.setCellValueFactory(new PropertyValueFactory<>("C_City"));
        Province.setCellValueFactory(new PropertyValueFactory<>("C_Province"));
        PostalCode.setCellValueFactory(new PropertyValueFactory<>("C_Postal_Code"));

        CustomerSaveButton.setOnMouseClicked(event -> {
            SaveBtnOnAction();
        });

        Pattern CusIdPattern = Pattern.compile("^(C-0)[0-9]{2,4}$");
        Pattern CusMaleOrFemalePattern = Pattern.compile("^(mr|mrs)$");
        Pattern CusAddressPattern = Pattern.compile("^[A-z]{3,30}$");
        Pattern CusNamePattern = Pattern.compile("^[A-z]{3,30}$");
        Pattern CusCityPattern = Pattern.compile("^[A-z]{4,15}$");
        Pattern CusProvincePattern = Pattern.compile("^[A-z]{2,5}$");
        Pattern CusPostalCodePattern = Pattern.compile("^[0-9]{5}$");

        /* - MATCHING ENTERED VALUES WITH REG-X PATTERN - */

        CustomerMap.put(CustomerID, CusIdPattern);
        CustomerMap.put(mrMrsBtn, CusMaleOrFemalePattern);
        CustomerMap.put(CustomerName, CusNamePattern);
        CustomerMap.put(CustomerAddress, CusAddressPattern);
        CustomerMap.put(CustomerCity, CusCityPattern);
        CustomerMap.put(CustomerProvince, CusProvincePattern);
        CustomerMap.put(CustomerPostalCode, CusPostalCodePattern);


        try {
            loadAllCustomers();
            DateAndTime();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public void textFields_Key_Released_Customer(KeyEvent keyEvent) {
        validationUtil.validate(CustomerMap, CustomerSaveButton);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = validationUtil.validate(CustomerMap, CustomerSaveButton);
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }

    /*- LOAD ALL CUSTOMER DETAILS FOR TABLE -*/

    private void loadAllCustomers() throws SQLException, ClassNotFoundException {

        ResultSet result = CrudUtil.execute("SELECT * FROM Customer");
        ObservableList<customer> obList = FXCollections.observableArrayList();
        System.out.println("working");
        while (result.next()) {
            obList.add(
                    new customer(

                            result.getString("CustomerID"),
                            result.getString("CustomerTitle"),
                            result.getString("CustomerName"),
                            result.getString("CustomerAddress"),
                            result.getString("City"),
                            result.getString("Province"),
                            result.getString("PostCode"),
                            result.getString("CustomerDate"),
                            result.getString("CustomerTime")

                    )
            );
        }
        TableContextFull.setItems(obList);
        TableContextFull.refresh();
    }

    /*- CUSTOMER DETAIL SAVE INTO TABLE -*/


    public void SaveBtnOnAction() {

        customer cus = new customer(
                CustomerID.getText(),
                mrMrsBtn.getText(),
                CustomerName.getText(),
                CustomerAddress.getText(),
                CustomerCity.getText(),
                CustomerProvince.getText(),
                CustomerPostalCode.getText(),
                DateLbl.getText(),
                TimeLbl.getText()

        );

        try {
            if (CrudUtil.execute("INSERT INTO Customer VALUES (?,?,?,?,?,?,?,?,?)",
                    cus.getCustomerID(), cus.getTitle(), cus.getC_Name(),
                    cus.getC_address(), cus.getC_City(), cus.getC_Province(), cus.getC_Postal_Code(), cus.getCustomerDate(), cus.getCustomerTime()

            )) {
                clear();
                loadAllCustomers();
                NotificationController.SuccessfulTableNotification("Saved ", "");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        TableContextFull.refresh();
    }

    /*- LOAD DATE AND TIME INTO TABLE OR LABELS -*/

    private void DateAndTime() {
        DateLbl.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            System.out.println(DateLbl.getText()+"\t"+TimeLbl.getText());
            LocalTime currentTime = LocalTime.now();
            TimeLbl.setText(currentTime.getHour() + ":" +
                    currentTime.getMinute() + ":" +
                    currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

    }

    /*- UPDATES DETAILS IN TABLE -*/

    public void UpdateBtnOnAction(ActionEvent actionEvent) {

        customer c = new customer(
                CustomerID.getText(),
                mrMrsBtn.getText(),
                CustomerName.getText(),
                CustomerAddress.getText(),
                CustomerCity.getText(),
                CustomerProvince.getText(),
                CustomerPostalCode.getText(),
                DateLbl.getText(),
                TimeLbl.getText()

        );

        try {
            boolean isUpdated = CrudUtil.execute(
                    "UPDATE Customer SET CustomerTitle=? ," +
                            " CustomerName=? ," +
                            " CustomerAddress=? ," +
                            " City=? , Province=? ," +
                            " PostCode=? , CustomerDate=? , CustomerTime=? WHERE CustomerID=?", c.getTitle(), c.getC_Name(),
                    c.getC_address(), c.getC_City(), c.getC_Province(), c.getC_Postal_Code(), c.getCustomerDate(), c.getCustomerTime(), c.getCustomerID()
            );
            if (isUpdated) {
                clear();
                loadAllCustomers();
                //new Alert(Alert.AlertType.CONFIRMATION, "Update Confirmed!").show();
                NotificationController.SuccessfulTableNotification("Updated", "");
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }

        } catch (SQLException | ClassNotFoundException ignored) {}
        TableContextFull.refresh();
    }

    /*- DELETE CUSTOMER DETAILS USING CUSTOMER ID -*/

    public void DeleteBtnOnAction(ActionEvent actionEvent) {
        try {
            if (CrudUtil.execute("DELETE FROM Customer WHERE CustomerID=?", CustomerID.getText())) {
                loadAllCustomers();
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();clear();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }

        } catch (SQLException | ClassNotFoundException ignored) {
        }

        TableContextFull.refresh();
    }

    /*- LOAD CHOOSY DETAILS INTO TEXT FIELDS -*/

    public void save() {
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM Customer WHERE CustomerID=?", CustomerID.getText());
            if (result.next()) {
                mrMrsBtn.setText(result.getString(2));
                CustomerName.setText(result.getString(3));
                CustomerAddress.setText(result.getString(4));
                CustomerCity.setText(result.getString(5));
                CustomerProvince.setText(result.getString(6));
                CustomerPostalCode.setText(result.getString(7));

            } else {
                loadAllCustomers();
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }
        } catch (SQLException | ClassNotFoundException ignored) {
        }
    }

    /*- TEXT FIELD CLEARING -*/

    public void clear() {
        CustomerID.clear();
        mrMrsBtn.clear();
        CustomerName.clear();
        CustomerAddress.clear();
        CustomerCity.clear();
        CustomerProvince.clear();
        CustomerPostalCode.clear();

    }

}
