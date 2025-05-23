package burgervend;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.jar.JarFile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            // Llamada a la función para imprimir los contenidos del JAR
            printJarContents();
            
            URL fxmlUrl = getClass().getResource("/burgervend/views/MainView.fxml");
            if (fxmlUrl == null) {
                throw new IOException("¡No se encontró MainView.fxml!");
            }
            Parent root = FXMLLoader.load(fxmlUrl);
            
            Scene scene = new Scene(root, 800, 600);
            
            URL mainCss = getClass().getResource("/burgervend/css/main.css");
            if (mainCss != null) scene.getStylesheets().add(mainCss.toExternalForm());
            else System.err.println("main.css no encontrado");

            URL compCss = getClass().getClassLoader().getResource("burgervend/css/componentes.css");
            if (compCss != null) {
                scene.getStylesheets().add(compCss.toExternalForm());
            } else {
                System.err.println("components.css no encontrado");
            }

           
            primaryStage.setTitle("BurgerVend");
            primaryStage.setScene(scene);
            primaryStage.show();
            
            burgervend.utils.ScreenManager.setPrimaryStage(primaryStage);
            
        } catch (Exception e) {
            System.err.println("Error crítico: " + e.getMessage());
            e.printStackTrace();
            mostrarAlertaError(e);
        }
    }
    
    private void mostrarAlertaError(Exception e) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
            javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Error de Inicio");
        alert.setHeaderText("No se pudo iniciar la aplicación");
        alert.setContentText("Detalle: " + e.getMessage());
        alert.showAndWait();
    }

    // Función para imprimir los contenidos del JAR
    public static void printJarContents() {
    ClassLoader classLoader = App.class.getClassLoader();
    URL jarUrl = classLoader.getResource("burgervend/css/");
    
    if (jarUrl != null) {
        System.out.println("Ruta al directorio: " + jarUrl);
        try {
            // Usamos JarFile para leer los contenidos del JAR
            String jarPath = jarUrl.toString().replaceFirst("jar:file:", "").replaceAll("!.*$", "");
            JarFile jarFile = new JarFile(jarPath);
            
            // Iteramos a través de los archivos en el JAR
            jarFile.stream()
                   .filter(entry -> entry.getName().startsWith("burgervend/css/"))  // Filtramos para solo ver los archivos dentro de esa carpeta
                   .forEach(entry -> System.out.println("Archivo en el JAR: " + entry.getName()));
            
            jarFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    } else {
        System.err.println("No se encuentra la carpeta de recursos.");
    }
}
}