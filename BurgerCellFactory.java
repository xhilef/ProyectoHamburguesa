package burgervend.controllers;

import burgervend.models.Hamburguesa;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.io.InputStream;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class BurgerCellFactory implements Callback<ListView<Hamburguesa>, ListCell<Hamburguesa>> {

    @Override
    public ListCell<Hamburguesa> call(ListView<Hamburguesa> listView) {
        return new ListCell<Hamburguesa>() {
            private final HBox content = new HBox(10);
            private final ImageView imageView = new ImageView();
            private final Text text = new Text();

            {
                imageView.setFitWidth(90); 
                imageView.setFitHeight(90);
                imageView.setPreserveRatio(true);
                content.getChildren().addAll(imageView, text);
            }

            @Override
            protected void updateItem(Hamburguesa item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    try {
                        // Sanitiza el nombre de la hamburguesa para la ruta del archivo
                        String sanitizedName = sanitizeFileName(item.getNombre());
                        String imagePath = "/burgervend/imagenes/hamburguesas/" + sanitizedName + ".png";
                        System.out.println("Image path: " + imagePath);

                        // Intentar cargar la imagen desde el JAR
                        InputStream imageStream = getClass().getResourceAsStream(imagePath);
                        if (imageStream != null) {
                            imageView.setImage(new Image(imageStream));
                        } else {
                            throw new Exception("Image not found: " + imagePath);
                        }
                    } catch (Exception e) {
                        System.out.println("Error loading image: " + e.getMessage());

                        // Imagen por defecto si ocurre un error
                        String defaultPath = "/burgervend/imagenes/ui/default_burger.png";
                        InputStream defaultStream = getClass().getResourceAsStream(defaultPath);
                        if (defaultStream != null) {
                            System.out.println("Default image path: " + defaultPath);
                            imageView.setImage(new Image(defaultStream));
                        } else {
                            System.out.println("ERROR: Imagen por defecto no encontrada.");
                        }
                    }

                    // Configurar el texto con el nombre y el precio
                    text.setText(item.getNombre() + " - $" + String.format("%.2f", item.getPrecioTotal()));
                    setGraphic(content);
                }
            }
        };
    }

    // Método de fábrica para crear la instancia
    public static Callback<ListView<Hamburguesa>, ListCell<Hamburguesa>> create() {
        return new BurgerCellFactory();
    }

    // Método para quitar acentos y normalizar nombres de archivo
    private String sanitizeFileName(String nombre) {
        String normalized = Normalizer.normalize(nombre.toLowerCase(), Normalizer.Form.NFD);
        return Pattern.compile("\\p{InCombiningDiacriticalMarks}+").matcher(normalized).replaceAll("");
    }
}
