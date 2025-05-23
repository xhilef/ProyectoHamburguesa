package burgervend.controllers;

import burgervend.models.Pedido;
import burgervend.utils.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ResumenController {
    @FXML private Label lblResumen;
    @FXML private Label lblPrecioTotal;

    private Pedido pedido;

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
        lblResumen.setText(pedido.generarResumenPago());
        lblPrecioTotal.setText(String.format("Total: $%.2f", pedido.calcularTotal()));
    }

    @FXML
    private void onConfirmarClick() {
        if (pedido != null) {
            ScreenManager.loadScreen("Payment", pedido);
        }
    }

    @FXML
    private void onCancelarClick() {
        ScreenManager.loadScreen("Main");
    }
}
