package burgervend.controllers;

import burgervend.models.Pedido;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class PagoController {

    @FXML private VBox paymentMethodsContainer;
    @FXML private Label lblAmount;

    private ToggleGroup toggleGroup;

    // Este es el pedido que recibirás del flujo anterior
    private Pedido pedido;

    @FXML
    private void initialize() {
        toggleGroup = new ToggleGroup();

        RadioButton rbTarjeta = new RadioButton("Tarjeta");
        RadioButton rbEfectivo = new RadioButton("Efectivo");

        rbTarjeta.setToggleGroup(toggleGroup);
        rbEfectivo.setToggleGroup(toggleGroup);

        paymentMethodsContainer.getChildren().addAll(rbTarjeta, rbEfectivo);

        // Simulación de monto dinámico a partir del pedido
        if (pedido != null) {
            double montoAPagar = pedido.calcularTotal(); // Monto calculado desde el pedido
            lblAmount.setText(String.format("Monto a pagar: $%.2f", montoAPagar));
        } else {
            lblAmount.setText("Monto a pagar: $0.00");
        }
    }

    // Este es el método que se ejecuta cuando el usuario hace clic en "Pagar"
    @FXML
private void onPagarClick() {
    RadioButton selected = (RadioButton) toggleGroup.getSelectedToggle();
    
    if (selected == null) {
        mostrarAlerta("Error", "Selecciona un método de pago.");
        return;
    }
    
    String metodoPago = selected.getText(); // Obtener el método de pago

    try {
        if ("Tarjeta".equals(metodoPago)) {
            // Lógica para procesar el pago con tarjeta
            procesarPagoConTarjeta();
        } else if ("Efectivo".equals(metodoPago)) {
            // Lógica para procesar el pago en efectivo
            procesarPagoConEfectivo();
        } else {
            mostrarAlerta("Error", "Método de pago desconocido.");
        }
    } catch (Exception e) {
        // Capturar cualquier excepción que ocurra durante el proceso de pago
        e.printStackTrace();  // Mostrar el error en la consola
        mostrarAlerta("Error de pago", "Ocurrió un error al intentar realizar el pago. Por favor, intente nuevamente.");
    }
}

// Método para procesar pago con tarjeta
private void procesarPagoConTarjeta() {
    try {
        // Lógica simulada de procesamiento de pago con tarjeta
        System.out.println("Procesando pago con tarjeta...");
        
        // Simulamos que el pago fue exitoso
        mostrarAlerta("Pago Realizado", "El pago con tarjeta se ha procesado correctamente.");
    } catch (Exception e) {
        e.printStackTrace();
        mostrarAlerta("Error", "Hubo un error al procesar el pago con tarjeta.");
    }
}

// Método para procesar pago con efectivo
private void procesarPagoConEfectivo() {
    try {
        // Lógica simulada de procesamiento de pago en efectivo
        System.out.println("Procesando pago con efectivo...");
        
        // Simulamos que el pago fue exitoso
        mostrarAlerta("Pago Realizado", "El pago en efectivo se ha procesado correctamente.");
    } catch (Exception e) {
        e.printStackTrace();
        mostrarAlerta("Error", "Hubo un error al procesar el pago con efectivo.");
    }
}


    // Función para mostrar alertas de error
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Método para recibir el pedido de la vista anterior
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
