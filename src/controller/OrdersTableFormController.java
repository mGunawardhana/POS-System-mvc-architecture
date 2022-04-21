package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Order;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrdersTableFormController {
    public TableView<Order> OrdersTbl;
    public TableColumn orderIdCol;
    public TableColumn customerIdCol;
    public TableColumn DateCol;
    public TableColumn TimeCol;
    public TextField CustomerSearch;
    public TextField CustomerID;
    int NumberOfProducts;

    public void initialize() {
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        DateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        TimeCol.setCellValueFactory(new PropertyValueFactory<>("time"));

        try {
            loadAllCustomers();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void loadAllCustomers() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM `Order`");
        ObservableList<Order> obList = FXCollections.observableArrayList();

        while (result.next()) {
            obList.add(
                    new Order(
                            result.getString("OrderID"),
                            result.getString("CustomerID"),
                            result.getString("OrderDate"),
                            result.getString("OrderTime")

                    )
            );
        }
        OrdersTbl.setItems(obList);
        OrdersTbl.refresh();
    }

    public void save(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (CustomerSearch.getText().trim().isEmpty()) {

            loadAllCustomers();

        } else {
            ResultSet result = CrudUtil.execute("SELECT * FROM `Order` WHERE  OrderID=?", CustomerSearch.getText());
            ObservableList<Order> obList = FXCollections.observableArrayList();
            while (result.next()) {
                obList.add(
                        new Order(
                                result.getString("OrderID"),
                                result.getString("CustomerID"),
                                result.getString("OrderDate"),
                                result.getString("OrderTime")
                        ));
            }
            OrdersTbl.setItems(obList);
        }
    }

}





