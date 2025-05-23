package burgervend.utils;

import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.Scanner;

public class ResourceManager {

    // Cargar imagen desde los recursos
    public static Image loadImage(String path) {
        String fullPath = "/burgervend/imagenes/" + path;
        try (InputStream is = ResourceManager.class.getResourceAsStream(fullPath)) {
            if (is == null) {
                System.err.println("No se encontró la imagen: " + fullPath);
                return null;
            }
            System.out.println("Cargando imagen desde: " + fullPath);
            return new Image(is);
        } catch (Exception e) {
            System.err.println("Error cargando imagen: " + fullPath);
            return null;
        }
    }

    // Método para cargar otros recursos (como JSON) si es necesario
    public static String getJsonData(String filename) {
        try (InputStream is = ResourceManager.class.getResourceAsStream("/burgervend/data/" + filename)) {
            if (is == null) {
                System.err.println("No se encontró el archivo JSON: " + filename);
                return "[]";
            }
            Scanner scanner = new Scanner(is, "UTF-8").useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        } catch (Exception e) {
            System.err.println("Error cargando JSON: " + filename);
            return "[]";
        }
    }
}
