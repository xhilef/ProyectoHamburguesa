package burgervend.controllers;

import burgervend.models.Pedido;
import burgervend.services.PagoService;
import burgervend.services.PedidoRepository;
import burgervend.utils.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;  // Importa la clase List para utilizarla correctamente.

public class PaymentController {

    @FXML private VBox paymentMethodsContainer;
    @FXML private Label lblAmount;
    @FXML private Label lblResumen;
    @FXML private TextField txtNumeroTarjeta;
    @FXML private TextField txtCvv;
    @FXML private HBox tarjetaBox;
    @FXML private HBox cvvBox;

    private final ToggleGroup toggleGroup = new ToggleGroup();
    private final PagoService pagoService = new PagoService();
    private Pedido pedido;

    @FXML
    private void initialize() {
        cargarMetodosDePago();

        if (tarjetaBox != null) tarjetaBox.setVisible(false);
        if (cvvBox != null) cvvBox.setVisible(false);

        toggleGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                String metodo = ((RadioButton) newToggle).getText().toLowerCase();
                boolean esTarjeta = metodo.contains("tarjeta");

                if (tarjetaBox != null) tarjetaBox.setVisible(esTarjeta);
                if (cvvBox != null) cvvBox.setVisible(esTarjeta);
            }
        });
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
        lblAmount.setText(String.format("Total: $%.2f", pedido.calcularTotal()));
        lblResumen.setText(pedido.generarResumen());
    }

    // Aquí se reemplaza la implementación de cargarMetodosDePago()
    private void cargarMetodosDePago() {
        paymentMethodsContainer.getChildren().clear();
        // Asumiendo que getMetodosPago() devuelve una lista de String
        List<String> metodos = pagoService.getMetodosPago();
        if (metodos.isEmpty()) {
            mostrarAlerta("Error", "No hay métodos de pago disponibles.");
            return;
        }
        
        for (String metodo : metodos) {
            RadioButton rb = new RadioButton(metodo);
            rb.setToggleGroup(toggleGroup);
            paymentMethodsContainer.getChildren().add(rb);
        }
    }

    @FXML
    private void onPagarClick() {
        RadioButton selected = (RadioButton) toggleGroup.getSelectedToggle();

        if (selected == null) {
            mostrarAlerta("Error", "Selecciona un método de pago.");
            return;
        }

        String metodoPago = selected.getText();
        boolean esTarjeta = metodoPago.toLowerCase().contains("tarjeta");

        if (pedido == null) {
            mostrarAlerta("Error", "No se ha encontrado un pedido válido.");
            return;
        }

        if (esTarjeta) {
            String numero = txtNumeroTarjeta.getText();
            String cvv = txtCvv.getText();
            if (numero.isEmpty() || cvv.isEmpty()) {
                mostrarAlerta("Error", "Por favor ingresa número de tarjeta y CVV.");
                return;
            }
        }

        if (pagoService == null) {
            mostrarAlerta("Error", "El servicio de pago no está disponible.");
            return;
        }

        boolean pagoExitoso = pagoService.procesarPago(pedido.calcularTotal(), metodoPago);

        if (pagoExitoso) {
            pedido.aprobarPago(metodoPago);

            // GUARDAR PEDIDO
            String id = PedidoRepository.guardarPedido(pedido);
            if (id != null) {
                mostrarMensajeExito(id);
            } else {
                mostrarAlerta("Error", "No se pudo guardar el pedido.");
            }

        } else {
            ScreenManager.loadScreen("ErrorPago");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarMensajeExito(String id) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Pedido Confirmado");
        alert.setHeaderText("¡Pago exitoso!");
        alert.setContentText("Tu pedido ha sido registrado con el ID #" + id);
        alert.showAndWait();

        ScreenManager.loadScreen("Main");  // Puedes cambiar a otra vista si lo prefieres
    }
}
