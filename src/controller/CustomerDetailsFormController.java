package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.OrderDetails;
import model.customer;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CustomerDetailsFormController {
    public TableView<customer> TableContextFullViewSide;
    public TableColumn customerDate;
    public TableColumn customerTime;
    public TableColumn CustomerID;
    public TableColumn customerMrMrs;
    public TableColumn CustomerName;
    public TableColumn customerAddress;
    public TableColumn customerCity;


    public TextField SearchId;
    public TextField searchMaleFemale;
    public TextField SearchCity;
    public TextField SearchName;
    public TextField SearchAddress;
    public TextField DateLoader;
    public TextField TimeLoader;

    public void initialize() {
        CustomerID.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        customerMrMrs.setCellValueFactory(new PropertyValueFactory<>("Title"));
        CustomerName.setCellValueFactory(new PropertyValueFactory<>("C_Name"));
        customerAddress.setCellValueFactory(new PropertyValueFactory<>("C_address"));
        customerCity.setCellValueFactory(new PropertyValueFactory<>("C_City"));
        customerDate.setCellValueFactory(new PropertyValueFactory<>("CustomerDate"));
        customerTime.setCellValueFactory(new PropertyValueFactory<>("CustomerTime"));

        try {
            loadAllCustomers();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    /*- LOAD ALL DETAILS INTO CUSTOMER DETAILS FORM -*/

    private void loadAllCustomers() throws SQLException, ClassNotFoundException {
        ResultSet setOfCustomer = CrudUtil.execute("SELECT * FROM Customer");
        ObservableList<customer> obList = FXCollections.observableArrayList();

        while (setOfCustomer.next()) {
            obList.add(
                    new customer(
                            setOfCustomer.getString("CustomerID"),
                            setOfCustomer.getString("CustomerTitle"),
                            setOfCustomer.getString("CustomerName"),
                            setOfCustomer.getString("CustomerAddress"),
                            setOfCustomer.getString("City"),
                            setOfCustomer.getString("Province"),
                            setOfCustomer.getString("PostCode"),
                            setOfCustomer.getString("CustomerDate"),
                            setOfCustomer.getString("CustomerTime")

                    )
            );
        }
        TableContextFullViewSide.setItems(obList);
        TableContextFullViewSide.refresh();
    }

    /*- SEARCH CUSTOMER DETAILS USING CUSTOMER SIDE TABLE -*/

    public void save() throws SQLException, ClassNotFoundException {
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM Customer WHERE CustomerID=?", SearchId.getText());
            if (result.next()) {
                searchMaleFemale.setText(result.getString(2));
                SearchName.setText(result.getString(3));
                SearchAddress.setText(result.getString(4));
                SearchCity.setText(result.getString(5));
                DateLoader.setText(result.getString(8));
                TimeLoader.setText(result.getString(9));

            } else {
                loadAllCustomers();
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}






