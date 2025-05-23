package burgervend.controllers;

import burgervend.models.ingredientes.Ingrediente;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.io.InputStream;
import java.text.Normalizer;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class IngredienteCellFactory implements Callback<ListView<Ingrediente>, ListCell<Ingrediente>> {

    private final BiConsumer<Ingrediente, Integer> onAdd;
    private final Consumer<Ingrediente> onRemove;

    public IngredienteCellFactory(BiConsumer<Ingrediente, Integer> onAdd, Consumer<Ingrediente> onRemove) {
        this.onAdd = onAdd;
        this.onRemove = onRemove;
    }

    @Override
    public ListCell<Ingrediente> call(ListView<Ingrediente> listView) {
        return new ListCell<Ingrediente>() {

            private final HBox content = new HBox(10);
            private final ImageView imageView = new ImageView();
            private final CheckBox checkBox = new CheckBox();
            private final Spinner<Integer> quantitySpinner = new Spinner<>(1, 10, 1);

            {
                imageView.setFitWidth(30);
                imageView.setFitHeight(30);
                imageView.setPreserveRatio(true);

                quantitySpinner.setPrefWidth(60);
                quantitySpinner.setDisable(true); // Desactivado por defecto

                // Cambiar cantidad manualmente
                quantitySpinner.valueProperty().addListener((obs, oldVal, newVal) -> {
                    Ingrediente ing = getItem();
                    if (checkBox.isSelected() && ing != null) {
                        onAdd.accept(ing, newVal);
                    }
                });

                checkBox.setOnAction(event -> {
                    Ingrediente ing = getItem();
                    if (ing == null) return;

                    if (checkBox.isSelected()) {
                        quantitySpinner.setDisable(false);
                        onAdd.accept(ing, quantitySpinner.getValue());
                    } else {
                        quantitySpinner.setDisable(true);
                        onRemove.accept(ing);
                    }
                });

                content.getChildren().addAll(imageView, checkBox, quantitySpinner);
            }

            @Override
            protected void updateItem(Ingrediente ing, boolean empty) {
                super.updateItem(ing, empty);
                if (empty || ing == null) {
                    setGraphic(null);
                } else {
                    cargarImagen(ing);
                    checkBox.setText(ing.getNombre() + " - $" + String.format("%.2f", ing.getPrecio()));
                    checkBox.setSelected(false);
                    quantitySpinner.getValueFactory().setValue(1);
                    quantitySpinner.setDisable(true);
                    setGraphic(content);
                }
            }

            private void cargarImagen(Ingrediente ing) {
                String fileName = sanitizeFileName(ing.getNombre());
                String[] extensiones = {".png", ".jpg"};
                boolean encontrada = false;

                for (String ext : extensiones) {
                    String imagePath = "/burgervend/imagenes/ingredientes/" + fileName + ext;
                    try (InputStream stream = getClass().getResourceAsStream(imagePath)) {
                        if (stream != null) {
                            imageView.setImage(new Image(stream));
                            encontrada = true;
                            break;
                        }
                    } catch (Exception e) {
                        System.err.println("Error cargando imagen: " + e.getMessage());
                    }
                }

                if (!encontrada) {
                    imageView.setImage(null);
                    System.err.println("Imagen NO encontrada para: " + fileName);
                }
            }
        };
    }

    private String sanitizeFileName(String nombre) {
        String normalized = Normalizer.normalize(nombre.toLowerCase(), Normalizer.Form.NFD);
        return Pattern.compile("\\p{InCombiningDiacriticalMarks}+").matcher(normalized).replaceAll("");
    }

    public static IngredienteCellFactory create(BiConsumer<Ingrediente, Integer> onAdd, Consumer<Ingrediente> onRemove) {
        return new IngredienteCellFactory(onAdd, onRemove);
    }
}
