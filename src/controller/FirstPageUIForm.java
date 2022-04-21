package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import util.NotificationController;
import util.UILoader;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class FirstPageUIForm {
    public TextField StartUpSystemName;
    public PasswordField StartUpSystemPassword;
    public AnchorPane FirstPageAnchorPaneContext;
    public AnchorPane FPageContext;
    public Label lblHide;
    public JFXButton start;


    /*- MAIN SYSTEM LOGIN BUTTON -*/

    public void SystemStartUpOnAction(ActionEvent actionEvent) throws IOException {
        if (StartUpSystemName.getText().equals("root")) {
            if (StartUpSystemPassword.getText().equals("password")) {
                UILoader.SetUiCloseUnderTheAnchorpane("AdminOrCashier", FirstPageAnchorPaneContext);
                NotificationController.LoginSuccessfulNotification("YOUR");
            } else {
                NotificationController.LoginUnSuccessfulNotification("entered", 3);
            }
        } else {
            NotificationController.LoginSuccessfulNotification("");
        }
    }

    /*** -----------------------------------------------SHOW PASSWORD-------------------------------------------------*/

    public void showPasswordOnMousePressed(MouseEvent mouseEvent) {
        Image img = new Image("assets/show.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(30);
        view.setFitWidth(30);
        lblHide.setGraphic(view);

        StartUpSystemPassword.setPromptText(StartUpSystemPassword.getText());
        StartUpSystemPassword.setText("");
        StartUpSystemPassword.setDisable(true);
        StartUpSystemPassword.requestFocus();
    }


    /*** --------------------------------------------HIDE PASSWORD----------------------------------------------------*/

    public void hidePasswordOnMousePressed(MouseEvent mouseEvent) {
        Image img = new Image("assets/hide.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(30);
        view.setFitWidth(30);
        lblHide.setGraphic(view);

        StartUpSystemPassword.setText(StartUpSystemPassword.getPromptText());
        StartUpSystemPassword.setPromptText("");
        StartUpSystemPassword.setDisable(false);
    }


    /*- SHIFT INTO NEXT TEXT FIELD -*/
    public void shiftNextOnAction(ActionEvent actionEvent) {
        StartUpSystemPassword.requestFocus();
    }


    /*- GO TO GOOGLE  -*/

    public void goToGoogle(MouseEvent mouseEvent) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://www.amazon.com/alm/" +
                "category/ref=cg_Fr17982_1a1_w?almBrandId=QW1hem9uIEZyZXNo&node=" +
                "16318981&redirect=rsp&pf_rd_m=ATVPDKIKX0DER&pf_rd_s=alm-category-" +
                "desktop-top-2&pf_rd_r=53A3SGEBTZW5AMY5KKYS&pf_rd_t=0&pf_rd_p=5ff8d867" +
                "-9de8-43a5-8ddd-ec1fe26d4380&pf_rd_i=category"));
    }

    /*- GO TO FACE-BOOK PAGE -*/

    public void goToFacebook(MouseEvent mouseEvent) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://www.facebook.com/amazonfreshuk/"));
    }
}
