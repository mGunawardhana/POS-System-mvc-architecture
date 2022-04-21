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

public class CustomerManagementFormController {
    public AnchorPane CustomersAp;
    public AnchorPane OrdersAp;
    public AnchorPane OrderDetailsAp;
    public AnchorPane PlaceOrderAp;
    public AnchorPane LogOutAp;
    public AnchorPane allLoaderContext;
    public AnchorPane CustomerManagementFormContext;




    /** MY ANIMATION CLIP ARTS  */

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

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(150), anchorPane);
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
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(150), anchorPane);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();
            anchorPane.setEffect(null);
        }
    }

    /** LOAD MY PAGES USING UI LOADER UTIL----------------------------------------------------------------------------*/

    public void CustomersOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.SetUi("CustomerForm", allLoaderContext);
    }

    public void OrdersOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.SetUi("OrdersTableForm", allLoaderContext);
    }

    public void OrderDetailsOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.SetUi("OrderDetailsForm", allLoaderContext);
    }

    public void PlaceOrderOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.SetUi("PlaceOrderForm", allLoaderContext);
    }

    public void ItemDetailsOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.SetUi("ItemDetailsForm", allLoaderContext);
    }

    public void LogOutOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.LogOutHandler("FirstPageUi", allLoaderContext);
    }

    /** LOAD CHOOSY DETAILS INTO TEXT FIELDS--------------------------------------------------------------------------*/
}
