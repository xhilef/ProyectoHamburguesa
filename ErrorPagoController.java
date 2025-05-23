package burgervend.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import burgervend.utils.ScreenManager;

public class ErrorPagoController {

    @FXML
    private void onVolverClick(ActionEvent event) {
        // Vuelve a la pantalla anterior, por ejemplo, a la de pago
        ScreenManager.loadScreen("PaymentView");
    }
}
