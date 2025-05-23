package burgervend.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import burgervend.models.Hamburguesa;
import burgervend.utils.ScreenManager;

public class DeliveryController {
    @FXML private Label lblOrderDetails;

    private Hamburguesa hamburguesa;

    public void setHamburguesa(Hamburguesa hamburguesa) {
        this.hamburguesa = hamburguesa;

        if (lblOrderDetails != null && hamburguesa != null) {
            mostrarDetalles();
        }
    }

    @FXML
    private void initialize() {
        if (hamburguesa != null) {
            mostrarDetalles();
        }
    }

    private void mostrarDetalles() {
        String detalles = hamburguesa.getNombre() + "\n" +
                "Ingredientes:\n";

        detalles += hamburguesa.getIngredientes().isEmpty()
                ? "- (ninguno)\n"
                : hamburguesa.getIngredientes().stream()
                    .map(i -> "- " + i.getNombre())
                    .reduce("", (a, b) -> a + b + "\n");

        detalles += "Total: $" + String.format("%.2f", hamburguesa.getPrecioTotal());

        lblOrderDetails.setText(detalles);
    }

    @FXML
    private void onVolverInicioClick() {
        ScreenManager.loadScreen("main");
    }
}
