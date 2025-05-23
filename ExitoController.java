package burgervend.controllers;

import javafx.fxml.FXML;
import burgervend.utils.ScreenManager;

public class ExitoController {
    @FXML
    private void onVolverClick() {
        ScreenManager.loadScreen("Main"); // Para volver al menú principal
    }
}

