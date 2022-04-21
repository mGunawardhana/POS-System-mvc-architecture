package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.item;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemDetails {
    public TextField searchItemInCode;
    public TextField UnitPrice;
    public TextField QtyOnHand;
    public TextField Description;
    public TextField PackSize;

    public TableView<item> FullTableStore;
    public TableColumn ItemTableColStore;
    public TableColumn DescriptionColStore;
    public TableColumn PackSizeColStore;
    public TableColumn UnitPriceColStore;
    public TableColumn QtyOnHandColStore;


    /*- LOAD ALL DATA INTO THE ITEM DETAILS TABLE -*/

    public void initialize() {

        ItemTableColStore.setCellValueFactory(new PropertyValueFactory<>("code"));
        DescriptionColStore.setCellValueFactory(new PropertyValueFactory<>("Discription"));
        PackSizeColStore.setCellValueFactory(new PropertyValueFactory<>("pack"));
        UnitPriceColStore.setCellValueFactory(new PropertyValueFactory<>("price"));
        QtyOnHandColStore.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

        try {
            loadAllItems();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    private void loadAllItems() throws SQLException, ClassNotFoundException {

        ResultSet result = CrudUtil.execute("SELECT * FROM Item");
        ObservableList<item> obList = FXCollections.observableArrayList();


        while (result.next()) {
            obList.add(
                    new item(
                            result.getString("ItemCode"),
                            result.getString("Description"),
                            result.getString("PackSize"),
                            result.getDouble("UnitPrice"),
                            result.getInt("QtyOnHand"),
                            result.getString("ItemDate")
                    )
            );
        }
        FullTableStore.setItems(obList);
    }

    /*- SEARCHING ITEM DETAIL  -*/

    public void save(ActionEvent actionEvent) {

        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM Item WHERE ItemCode=?", searchItemInCode.getText());
            if (result.next()) {
                Description.setText(result.getString(2));
                PackSize.setText(result.getString(3));
                UnitPrice.setText(result.getString(4));
                QtyOnHand.setText(result.getString(5));

            } else {
                loadAllItems();
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
