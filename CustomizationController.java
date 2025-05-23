package burgervend.controllers;

import burgervend.models.Hamburguesa;
import burgervend.models.Pedido;
import burgervend.models.ingredientes.Ingrediente;
import burgervend.services.GestorIngredientes;
import burgervend.services.MotorPersonalizacion;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javafx.scene.layout.HBox;
import java.util.List;
import burgervend.utils.ScreenManager;


public class CustomizationController {

    @FXML private ImageView hamburguesaImage;
    @FXML private ListView<Ingrediente> listIngredientes;
    @FXML private Label lblTotal;

    private final MotorPersonalizacion motor;
    private Pedido pedidoActual;
    private Hamburguesa hamburguesaBase;

    public CustomizationController() {
        this.motor = new MotorPersonalizacion(new GestorIngredientes());
    }

    public void setHamburguesa(Hamburguesa hamburguesa) {
        this.hamburguesaBase = hamburguesa;
        this.pedidoActual = new Pedido(hamburguesa, new ArrayList<>());
        cargarImagenHamburguesa();
        cargarIngredientesDisponibles();
        actualizarTotal();
    }

    private void cargarImagenHamburguesa() {
        String nombreArchivo = hamburguesaBase.getNombre().toLowerCase() + ".png";
        String path = "/burgervend/imagenes/hamburguesas/" + nombreArchivo;

        try (InputStream stream = getClass().getResourceAsStream(path)) {
            if (stream != null) {
                Image img = new Image(stream);
                hamburguesaImage.setImage(img);
            } else {
                System.err.println("No se encontró la imagen: " + path);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen: " + e.getMessage());
        }
    }

    private void cargarIngredientesDisponibles() {
        listIngredientes.setCellFactory(param -> new ListCell<Ingrediente>()   {
            private final Spinner<Integer> spinner = new Spinner<>(0, 5, 0);
            private final Label nombre = new Label();
            private final Label precio = new Label();
            private final HBox layout = new HBox(10, nombre, precio, spinner);

            {
                spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
                    Ingrediente ing = getItem();
                    if (ing != null) {
                        if (newValue > 0) {
                            pedidoActual.agregarIngrediente(ing, newValue);
                        } else {
                            pedidoActual.removerIngrediente(ing);
                        }
                        actualizarTotal();
                    }
                });
            }

            @Override
            protected void updateItem(Ingrediente item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    nombre.setText(item.getNombre());
                    precio.setText(String.format("$%.2f", item.getPrecio()));
                    spinner.getValueFactory().setValue(
                        pedidoActual.getCantidadIngrediente(item)
                    );
                    setGraphic(layout);
                }
            }
        });

        listIngredientes.getItems().setAll(motor.getOpcionesPersonalizacion());
    }

    private void actualizarTotal() {
        lblTotal.setText(String.format("Total: $%.2f", pedidoActual.calcularTotal()));
    }

    @FXML
    private void onConfirmarClick() {
        List<Ingrediente> ingredientesList = new ArrayList<>(pedidoActual.getIngredientes().keySet());

    if (motor.validarCombinacion(ingredientesList)) {
        ScreenManager.loadScreen("Resumen", pedidoActual);  // Ir a ResumenView.fxml
    } else {
        mostrarAlerta("Error", "Combinación inválida de ingredientes.");
    }
}



    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
