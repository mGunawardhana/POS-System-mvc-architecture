package util;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class NotificationController {


    public static void SuccessfulTableNotification(String option, String option2) {
        Notifications notificationBuilder = Notifications.create()
                .title(option + " Successfully.!")
                .text(option2 + " Details " + option + " Successfully.")
                .graphic(new ImageView(new Image("/assets/check.png")))
                .hideAfter(Duration.seconds(6))
                .position(Pos.BASELINE_CENTER);
        notificationBuilder.darkStyle();
        notificationBuilder.show();
    }

    public static void UnSuccessfulTableNotification(String option, String option2) {
        Notifications notificationBuilder = Notifications.create()
                .title(option + " UnSuccessful.!")
                .text("Your " + option2 + " Details " + option + " incorrect.")
                .graphic(new ImageView(new Image("/assets/error.png")))
                .hideAfter(Duration.seconds(8))
                .position(Pos.BOTTOM_RIGHT);
        notificationBuilder.darkStyle();
        notificationBuilder.show();
    }


    public static void LoginSuccessfulNotification(String option) {
        Notifications notificationBuilder = Notifications.create()
                .title(option + " Login Successful.!")
                .text("You have Successfully Login " + option + " to the System.")
                .graphic(new ImageView(new Image("/assets/check.png")))
                .hideAfter(Duration.seconds(8))
                .position(Pos.BOTTOM_RIGHT);
        notificationBuilder.darkStyle();
        notificationBuilder.show();
    }

    public static void LoginUnSuccessfulNotification(String option, int attempts) {
        Notifications notificationBuilder = Notifications.create()
                .title("\t\t\t☠"+option + " ACCESS DENIED..!")
                .text("Unauthorized access detected.\nIf u entered 3 times wrong details\nyour login option is blocking temporally.")
                .graphic(new ImageView(new Image("/assets/error.png")))
                .hideAfter(Duration.seconds(8))
                .position(Pos.BOTTOM_RIGHT);
        notificationBuilder.darkStyle();
        notificationBuilder.show();
    }


}
