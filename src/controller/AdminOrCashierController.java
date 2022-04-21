package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import util.NotificationController;
import util.UILoader;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminOrCashierController {

    public AnchorPane OwnerUnderCashier;
    public TextField CashierUserNameTxt;
    public PasswordField CashierPwdTxt;
    public TextField OwnerTxt;
    public PasswordField PwdTxt;
    public ImageView AdminImg;
    public ImageView CashierImg;
    int attempt1 = 0;
    int attempt2 = 0;

    public void initialize() {
        AdminImg.setVisible(false);
        CashierImg.setVisible(false);
    }

    /* SECURED LOGIN FOR OWNERS OR MANAGERS PATH */

    public void AdminSideLogin(ActionEvent actionEvent) throws IOException, SQLException {

        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        String query = "SELECT * FROM Login";
        PreparedStatement stm = con.prepareStatement(query);

        String UserName = OwnerTxt.getText();
        String PassWord = PwdTxt.getText();

        OwnerTxt.setDisable(false);
        PwdTxt.setDisable(false);
        ResultSet rst = stm.executeQuery(query);
        if (rst.next()) {
            attempt1++;
            if (attempt1 <= 2) {
                if (UserName.equals(rst.getString(1)) && PassWord.equals(rst.getString(2))) {
                    NotificationController.LoginSuccessfulNotification("");
                    UILoader.AdminORCashierUI("ManagerSide", OwnerUnderCashier);
                } else {
                    NotificationController.LoginUnSuccessfulNotification("ADMIN", attempt1);
                }

            } else {
                OwnerTxt.setDisable(true);
                PwdTxt.setDisable(true);
                AdminImg.setVisible(true);
                NotificationController.LoginUnSuccessfulNotification("", 2);
            }
        }
    }

    /*- SECURED LOGIN FOR CASHIER'S PATH -*/

    public void CashierSideLogin(ActionEvent actionEvent) throws Exception {

        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException ignored) {
        }

        String query = "SELECT * FROM cashier";
        PreparedStatement stm = con.prepareStatement(query);

        String CashierID = CashierUserNameTxt.getText();
        String CashierPsw = CashierPwdTxt.getText();

        CashierUserNameTxt.setDisable(false);
        CashierPwdTxt.setDisable(false);

        ResultSet rst = stm.executeQuery(query);
        if (rst.next()) {
            attempt2++;
            if (attempt2 <= 2) {
                if (CashierID.equals(rst.getString(2)) && CashierPsw.equals(rst.getString(3))) {
                    CashierImg.setVisible(false);
                    UILoader.AdminORCashierUI("CustomerManagementForm", OwnerUnderCashier);
                    NotificationController.LoginSuccessfulNotification("");
                } else {
                    CashierImg.setVisible(false);
                    NotificationController.LoginUnSuccessfulNotification("CASHIER", attempt2);
                }
            } else {
                CashierUserNameTxt.setDisable(true);
                CashierPwdTxt.setDisable(true);
                CashierImg.setVisible(true);
                NotificationController.LoginUnSuccessfulNotification("", 2);
            }
        }
    }

    /*- SHIFT INTO NEXT TEXT FIELDS -*/

    public void OwnerShiftOnAction(ActionEvent actionEvent) {
        PwdTxt.requestFocus();
    }
    public void cashierIdOnAction(ActionEvent actionEvent) {
        CashierPwdTxt.requestFocus();
    }
}





