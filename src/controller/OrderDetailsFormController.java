package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.OrderDetails;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDetailsFormController {
    public TableView<OrderDetails> ODetailFormTbl;
    public TableColumn orderIdCol;
    public TableColumn ItemCodeCol;
    public TableColumn QtyCol;
    public TableColumn DiscountCol;
    public TableColumn PriceCol;
    public TextField SearchOrderID;
    public TextField itemCode;
    public TextField Discount;
    public TextField Price;
    public TextField Qty;

    public void initialize() {
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        ItemCodeCol.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        QtyCol.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        DiscountCol.setCellValueFactory(new PropertyValueFactory<>("discount"));
        PriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        try {
            loadAllCustomers();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void loadAllCustomers() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM `Order Details`");
        ObservableList<OrderDetails> obList = FXCollections.observableArrayList();

        while (result.next()) {
            obList.add(
                    new OrderDetails(
                            result.getString("OrderID"),
                            result.getString("ItemCode"),
                            result.getInt("OrderQty"),
                            result.getDouble("Discount"),
                            result.getDouble("Price")
                    )
            );
        }
        ODetailFormTbl.setItems(obList);
        ODetailFormTbl.refresh();
    }

    public void search() throws SQLException, ClassNotFoundException {

        if (SearchOrderID.getText().trim().isEmpty()) {
            try {
                loadAllCustomers();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            ResultSet result = CrudUtil.execute("SELECT * FROM `Order Details` WHERE OrderID=?", SearchOrderID.getText());
            ObservableList<OrderDetails> obList = FXCollections.observableArrayList();
            while (result.next()) {
                obList.add(
                        new OrderDetails(
                                result.getString("orderId"),
                                result.getString("itemCode"),
                                result.getInt("orderQty"),
                                result.getDouble("discount"),
                                result.getDouble("price")
                        ));
            }
            ODetailFormTbl.setItems(obList);
        }
    }
}
