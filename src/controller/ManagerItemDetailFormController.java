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
import model.item;
import util.CrudUtil;
import util.validationUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ManagerItemDetailFormController {
    public AnchorPane ManagerItemDetailContext;
    public TextField ItemCode;
    public TextField Size;
    public TextField QtyOnHand;
    public TextField Description;
    public TextField UnitPrice;

    public TableView<item> FullTable;
    public TableColumn ItemTableCol;
    public TableColumn DescriptionCol;
    public TableColumn PackSizeCol;
    public TableColumn UnitPriceCol;
    public TableColumn QtyOnHandCol;
    public TableColumn dateCol;
    public TextField DateTxt;
    public Label dateLabel;
    public Label TimeLabel;
    public Button SaveButton;
    LinkedHashMap<TextField, Pattern> itemMap = new LinkedHashMap<>();

    /* - SHOWING DATA VALUES IN EMPLOYEE TABLE - */

    public void initialize() {

        ItemTableCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("Discription"));
        PackSizeCol.setCellValueFactory(new PropertyValueFactory<>("pack"));
        UnitPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        QtyOnHandCol.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("ItemDate"));

        dateLabel.setVisible(false);
        TimeLabel.setVisible(false);

        SaveButton.setOnMouseClicked(event -> {
            SaveBtnOnAction();
        });

        Pattern idItmPattern = Pattern.compile("^I-(0)[0-9]{2,5}$");//true
        Pattern DescriptionItmPattern = Pattern.compile("^[A-z]{2,10}$");//true
        Pattern PackItmPattern = Pattern.compile("^(small|medium|large)(/)(small|large|medium)$");//true
        Pattern UnitPriceItmPattern = Pattern.compile("^([0-9]{2,6}.[0-9]{1,2})$");//true
        Pattern QtyItmPattern = Pattern.compile("^[0-9]{1,5}$");//true

        /* - MATCHING ENTERED VALUES WITH REG-X PATTERN - */

        itemMap.put(ItemCode, idItmPattern);
        itemMap.put(Description, DescriptionItmPattern);
        itemMap.put(Size, PackItmPattern);
        itemMap.put(UnitPrice, UnitPriceItmPattern);
        itemMap.put(QtyOnHand, QtyItmPattern);


        try {
            loadDateAndTime();
            loadAllItems();
        } catch (ClassNotFoundException | SQLException ignored) {
        }

    }

    /*- SET COLOURS INTO TEX FIELD BORDERS -*/

    public void textFields_Key_Released_Item(KeyEvent keyEvent) {
        validationUtil.validate(itemMap, SaveButton);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = validationUtil.validate(itemMap, SaveButton);
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }

    /*- LOAD DATE AND TIME -*/

    private void loadDateAndTime() {
        //Set Date
        dateLabel.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        //Set Time
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            LocalTime currentTime = LocalTime.now();
            TimeLabel.setText(currentTime.getHour() + ":" +
                    currentTime.getMinute() + ":" +
                    currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

    }

    /*- LOAD ALL CASHIER DETAILS INTO CASHIER TABLE -*/

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
        FullTable.setItems(obList);
    }

    /*- SAVE ENTERED DETAILS INTO ITEM DETAILS -*/

    public void SaveBtnOnAction() {

        item newItem = new item(
                ItemCode.getText(),
                Description.getText(),
                Size.getText(),
                Double.parseDouble(UnitPrice.getText()),
                Integer.parseInt(QtyOnHand.getText()),
                dateLabel.getText()

        );

        try {
            if (CrudUtil.execute("INSERT INTO Item VALUES (?,?,?,?,?,?)",
                    newItem.getCode(), newItem.getDiscription(), newItem.getPack(), newItem.getPrice(), newItem.getQtyOnHand(), newItem.getItemDate()

            )) {
                clear();
                loadAllItems();
                new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").show();Clear();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        FullTable.refresh();
    }

    /*- REMOVE ITEM DETAILS IN TABLE -*/

    public void DeleteBtnOnAction(ActionEvent actionEvent) {
        try {
            if (CrudUtil.execute("DELETE FROM Item WHERE ItemCode=?", ItemCode.getText())) {
                loadAllItems();
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();Clear();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
        FullTable.refresh();
    }

    /*- REMOVE ITEM DETAILS IN TABLE -*/

    public void save(ActionEvent actionEvent) {

        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM Item WHERE ItemCode=?", ItemCode.getText());
            if (result.next()) {
                Description.setText(result.getString(2));
                Size.setText(result.getString(3));
                UnitPrice.setText(String.valueOf(result.getDouble(4)));
                QtyOnHand.setText(result.getString(5));
                DateTxt.setText(result.getString(6));


            } else {
                loadAllItems();
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }
        } catch (SQLException | ClassNotFoundException ignored) {
        }
    }

    public void clear() {
        ItemCode.clear();
        Size.clear();
        QtyOnHand.clear();
        Description.clear();
        UnitPrice.clear();
        DateTxt.clear();
    }
    /*- UPDATE ITEM DETAILS IN TABLE -*/

    public void UpdateButtonOnAction(ActionEvent actionEvent) {
        item ITEMS = new item(
                ItemCode.getText(),
                Description.getText(),
                Size.getText(),
                Double.parseDouble(UnitPrice.getText()),
                Integer.parseInt(QtyOnHand.getText()),
                DateTxt.getText()
        );

        try {
            boolean isUpdated2 = CrudUtil.execute(
                    "UPDATE Item SET Description=? ," +
                            " PackSize=? ," +
                            " QtyOnHand=? ," +
                            "ItemDate=? , " +
                            " UnitPrice=?  WHERE ItemCode=?", ITEMS.getDiscription(), ITEMS.getPack(), ITEMS.getQtyOnHand(), ITEMS.getItemDate(), ITEMS.getPrice(), ITEMS.getCode()
            );
            if (isUpdated2) {

                new Alert(Alert.AlertType.CONFIRMATION, "Update Confirmed!").show();Clear();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }


        } catch (SQLException | ClassNotFoundException ignored) {
        }
    }

    public void Clear() {
        ItemCode.clear();
        Size.clear();
        QtyOnHand.clear();
        Description.clear();
        UnitPrice.clear();
        DateTxt.clear();

    }
}
