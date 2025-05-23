package burgervend.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import burgervend.controllers.CustomizationController;
import burgervend.controllers.DeliveryController;
import burgervend.controllers.PaymentController;
import burgervend.controllers.ResumenController;

import burgervend.models.Hamburguesa;
import burgervend.models.Pedido;

public class ScreenManager {

    private static Stage primaryStage;

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static void loadScreen(String fxmlName) {
        loadScreen(fxmlName, (Object[]) null);
    }

    public static void loadScreen(String fxmlName, Object... params) {
        if (primaryStage == null) {
            throw new IllegalStateException("primaryStage no ha sido inicializado. Llama a setPrimaryStage() primero.");
        }

        try {
            // Capitalizar y construir nombre de archivo evitando duplicar "View"
            String capitalizedFxml = capitalize(fxmlName);
            if (!capitalizedFxml.endsWith("View")) {
                capitalizedFxml += "View";
            }
            String path = "/burgervend/views/" + capitalizedFxml + ".fxml";
            System.out.println("Intentando cargar: " + path);

            // Cargar el archivo FXML
            java.net.URL fxmlURL = ScreenManager.class.getResource(path);
            if (fxmlURL == null) {
                throw new IllegalStateException("No se encontró la vista FXML: " + path);
            }

            FXMLLoader loader = new FXMLLoader(fxmlURL);
            Parent root = loader.load();

            // Obtener el controlador de la vista cargada
            Object controller = loader.getController();

            // Asignar parámetros si aplica
            if (controller != null && params != null && params.length > 0) {
                for (Object param : params) {
                    if (controller instanceof CustomizationController && param instanceof Hamburguesa) {
                        ((CustomizationController) controller).setHamburguesa((Hamburguesa) param);
                    } else if (controller instanceof DeliveryController && param instanceof Hamburguesa) {
                        ((DeliveryController) controller).setHamburguesa((Hamburguesa) param);
                    } else if (controller instanceof ResumenController && param instanceof Pedido) {
                        ((ResumenController) controller).setPedido((Pedido) param);
                    } else if (controller instanceof PaymentController && param instanceof Pedido) {
                        ((PaymentController) controller).setPedido((Pedido) param);
                    }
                }
            }

            // Mostrar la vista en el Stage
            Scene scene = primaryStage.getScene();
            if (scene == null) {
                scene = new Scene(root);
                primaryStage.setScene(scene);
            } else {
                scene.setRoot(root);
            }

            primaryStage.sizeToScene();
            primaryStage.setMinWidth(600);
            primaryStage.setMinHeight(400);

        } catch (Exception e) {
            System.err.println("Error cargando la pantalla FXML: " + fxmlName);
            e.printStackTrace();
        }
    }

    public static void loadCustomizationScreen(Hamburguesa hamburguesa) {
        loadScreen("Customization", hamburguesa);
    }

    public static void loadDeliveryScreen(Hamburguesa hamburguesa) {
        loadScreen("Delivery", hamburguesa);
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
