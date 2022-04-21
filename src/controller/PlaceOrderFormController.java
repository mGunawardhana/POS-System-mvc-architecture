package controller;

import com.jfoenix.controls.JFXButton;
import db.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import model.Order;
import model.OrderDetails;
import model.customer;
import model.item;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.NotificationController;
import view.TM.CartTM;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class PlaceOrderFormController {

    public TableView<CartTM> PlaceOrderTbl;
    public TableColumn TotalCol;
    public TableColumn DiscountCol;
    public TableColumn UnitPriceCol;
    public TableColumn QtyCol;
    public TableColumn ItemCodeCol;
    public TableColumn OrderIDCol;

    public ComboBox<String> ItemCodeCombo;
    public ComboBox<String> CustomerIDCombo;

    public TextField DiscountTxt;
    public TextField QtyTxt;
    public TextField DescriptionTxt;
    public TextField QtyOnHandTxt;
    public TextField UnitPriceTxt;
    public TextField CityTxt;
    public TextField AddressTxt;
    public TextField NameTxt;
    public JFXButton RemoveBtn;
    public Label OrderID;
    public Label TotalLbl;
    public Label DateLbl;
    public Label TimeLbl;
    ObservableList<CartTM> tmList = FXCollections.observableArrayList();
    int cartSelectedRowForRemove = -1;
    private Object CartTM;


    public void initialize() {

        OrderIDCol.setCellValueFactory(new PropertyValueFactory<>("OrderId"));
        ItemCodeCol.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        QtyCol.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        UnitPriceCol.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        DiscountCol.setCellValueFactory(new PropertyValueFactory<>("Discount"));
        TotalCol.setCellValueFactory(new PropertyValueFactory<>("Total"));

        setCustomerIds();
        setItemCodes();
        setOrderId();
        loadDateAndTimeForPlaceOrderForm();

        //--------------------
        CustomerIDCombo.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    setCustomerDetails(newValue);
                });

        ItemCodeCombo.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    setItemDetails(newValue);
                });
        //--------------------
        PlaceOrderTbl.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowForRemove = (int) newValue;

        });
    }

    private void loadDateAndTimeForPlaceOrderForm() {


        //Set Date
        DateLbl.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        //Set Time
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, event -> {
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

    private void setOrderId() {
        try {
            OrderID.setText(new OrderCrudController().getOrderId());
        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
        }
    }

    public void setCustomerDetails(String selectedCustomerId) {
        try {
            customer c = CustomerCrudController.getCustomer(selectedCustomerId);
            if (c != null) {

                NameTxt.setText(c.getC_Name());
                AddressTxt.setText(c.getC_address());
                CityTxt.setText(c.getC_City());
            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setItemDetails(String selectedItemCode) {
        try {
            item i = ItemCrudController.getItem(selectedItemCode);
            if (i != null) {
                DescriptionTxt.setText(i.getDiscription());
                QtyOnHandTxt.setText(String.valueOf((i.getQtyOnHand())));
                UnitPriceTxt.setText(String.valueOf(i.getPrice()));
            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setItemCodes() {
        try {
            ItemCodeCombo.setItems(FXCollections.observableArrayList(ItemCrudController.getItemCodes()));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setCustomerIds() {
        try {
            ObservableList<String> cIdObList = FXCollections.observableArrayList(
                    CustomerCrudController.getCustomerIds()
            );
            CustomerIDCombo.setItems(cIdObList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void calculateTotal() {
        double total = 0;
        for (CartTM tm : tmList
        ) {
            total += tm.getTotal();
        }
        TotalLbl.setText(String.valueOf(total));
    }

    public void RemoveOnAction(ActionEvent actionEvent) {
        if (cartSelectedRowForRemove == -1) {} else {
            tmList.remove(cartSelectedRowForRemove);
            if (tmList.isEmpty()) {}
            calculateTotal();
            PlaceOrderTbl.refresh();
        }

    }

    public void AddToCartOnAction(ActionEvent actionEvent) {
        try {
            double unitPrice = Double.parseDouble(UnitPriceTxt.getText());
            double discount = Double.parseDouble(DiscountTxt.getText());
            double qty = Double.parseDouble((QtyTxt.getText()));
            double Cost = ((unitPrice * qty) * discount / 100);
            double totalCost = (unitPrice * qty) - Cost;

            CartTM isExists = isExists(ItemCodeCombo.getValue());

            if (isExists != null) {
                for (CartTM temp : tmList
                ) {
                    if (temp.equals(isExists)) {
                        temp.setTotal(temp.getTotal() + totalCost);
                        temp.setQty((temp.getQty() + qty));
                    }
                }
            } else {

                CartTM tm = new CartTM(

                        OrderID.getText(),
                        ItemCodeCombo.getValue(),
                        DescriptionTxt.getText(),
                        qty,
                        unitPrice,
                        discount,
                        totalCost
                );

                tmList.add(tm);
                PlaceOrderTbl.setItems(tmList);
            }
            PlaceOrderTbl.refresh();
            calculateTotal();
        } catch (Exception ignored) {
        }

    }

    private CartTM isExists(String itemCode) {
        for (CartTM tm : tmList
        ) {
            if (tm.getItemCode().equals(itemCode)) {
                return tm;
            }
        }
        return null;
    }

    public void playMouseEnterAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof Button) {
            Button btn = (Button) mouseEvent.getSource();

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(100), btn);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.CORNFLOWERBLUE);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            btn.setEffect(glow);
        }

        if (mouseEvent.getSource() instanceof Button) {
            Button btn = (Button) mouseEvent.getSource();

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(100), btn);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.PURPLE);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            btn.setEffect(glow);
        }
    }

    public void playMouseExitedAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof Button) {
            Button icon = (Button) mouseEvent.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(100), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();
            icon.setEffect(null);
        }
        if (mouseEvent.getSource() instanceof AnchorPane) {
            Button btn = (Button) mouseEvent.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(100), btn);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();
            btn.setEffect(null);
        }
    }

    public void ConfirmOrderOnAction(ActionEvent actionEvent) {
        ArrayList<OrderDetails> details = new ArrayList();
        for (CartTM tm : tmList
        ) {
            details.add(
                    new OrderDetails(
                            tm.getOrderId(),
                            tm.getItemCode(),
                            tm.getQty(),
                            tm.getDiscount(),
                            tm.getTotal()
                    )
            );
            tm.getItemCode();

        }
        Order order = new Order(
                OrderID.getText(),
                CustomerIDCombo.getValue(),
                DateLbl.getText(),
                TimeLbl.getText()

        );

        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            boolean isOrderSaved = new OrderCrudController().saveOrder(order);
            if (isOrderSaved) {
                boolean isDetailsSaved = new OrderCrudController().saveOrderDetails(details);
                if (isDetailsSaved) {
                    connection.commit();
                    NotificationController.SuccessfulTableNotification("Order Place", "Order");
                } else {
                    connection.rollback();
                    NotificationController.UnSuccessfulTableNotification("Order Place", "Order");
                }
            } else {
                connection.rollback();
                NotificationController.UnSuccessfulTableNotification("Order Place", "Order");
            }
        } catch (SQLException | ClassNotFoundException ignored) {
            //System.out.println(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignored) {
            }
        }
        setOrderId();
    }

    public void ClearOrderOnAction(ActionEvent actionEvent) {
        tmList.clear();
        PlaceOrderTbl.refresh();
        TotalLbl.setText("0.00/=");
    }

    public void PrintBillOnAction(ActionEvent actionEvent) {
        try {
            JasperReport compileReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/reports/FruitMarket.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, new JRBeanCollectionDataSource(tmList));
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException ignored) {

        }


    }

    public void discountShiftOnAction(ActionEvent actionEvent) {
        QtyTxt.requestFocus();
    }
}

