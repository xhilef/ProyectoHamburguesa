package burgervend.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML private Button btnStart;

    @FXML
    private void initialize() {
        btnStart.setOnAction(e -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/burgervend/views/SelectionView.fxml"));
                Stage stage = (Stage) btnStart.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
