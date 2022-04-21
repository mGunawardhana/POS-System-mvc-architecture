package controller;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import util.UILoader;

import java.io.IOException;

public class ManagerSideFormController {
    public AnchorPane ManagerSideContext;
    public AnchorPane allLoaderContext;
    public AnchorPane OrdersAp;
    public AnchorPane PlaceOrderAp;
    public AnchorPane OrderDetailsAp;
    public AnchorPane LogOutAp;


    /**
     * MY ANIMATIONS & CLIP ARTS
     */

    public void playMouseEnterAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.CORNFLOWERBLUE);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);
        }

        if (mouseEvent.getSource() instanceof AnchorPane) {
            AnchorPane anchorPane = (AnchorPane) mouseEvent.getSource();

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), anchorPane);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.GOLD);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            anchorPane.setEffect(glow);
        }
    }

    public void playMouseExitedAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();
            icon.setEffect(null);
        }
        if (mouseEvent.getSource() instanceof AnchorPane) {
            AnchorPane anchorPane = (AnchorPane) mouseEvent.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), anchorPane);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();
            anchorPane.setEffect(null);
        }
    }

    public void AddItem(ActionEvent actionEvent) throws IOException {
        UILoader.SetUi("ManagerItemDetailForm", allLoaderContext);
    }

    public void LogOutOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.LogOutHandler("FirstPageUi", allLoaderContext);
    }

    public void EmployeeOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.SetUi("AddEmployeeForm", allLoaderContext);
    }

    public void CustomerDetailsOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.SetUi("CustomerDetails", allLoaderContext);
    }

    public void itemDetailsFormOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.SetUi("ItemDetailsForm", allLoaderContext);
    }

    public void OrderItemDetailOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.SetUi("OrderDetailsForm", allLoaderContext);
    }

    public void SystemReportOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.SetUi("BarChartFormController", allLoaderContext);
    }
}
