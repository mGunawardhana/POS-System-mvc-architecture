package util;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class validationUtil {
    public static Object validate(LinkedHashMap<TextField, Pattern> map, Button btn) {
        for (TextField key : map.keySet()) {
            Pattern pattern = map.get(key);
            if (!pattern.matcher(key.getText()).matches()){
                addError(key,btn);
                return key;
            }
            removeError(key,btn);
        }
        return true;
    }

    private static void removeError(TextField txtField,Button btn) {
        txtField.getParent().setStyle("-fx-border-color: green");
        btn.setDisable(false);
    }

    private static void addError(TextField txtField,Button btn) {
        if (txtField.getText().length() > 0) {
            txtField.getParent().setStyle("-fx-border-color: red");
        }
        btn.setDisable(true);
    }
}
