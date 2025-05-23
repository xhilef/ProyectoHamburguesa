package burgervend.services;

import burgervend.models.ingredientes.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class GestorIngredientes {
    private List<Ingrediente> ingredientesDisponibles;

    public GestorIngredientes() {
        ingredientesDisponibles = new ArrayList<>();
        cargarIngredientes();
    }

    private void cargarIngredientes() {
        InputStream jsonStream = getClass().getResourceAsStream("/burgervend/data/ingredientes.json");
        if (jsonStream != null) {
            try (Scanner scanner = new Scanner(jsonStream).useDelimiter("\\A")) {
                String jsonStr = scanner.hasNext() ? scanner.next() : "";
                JSONArray jsonArray = new JSONArray(jsonStr);
                ingredientesDisponibles = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    String tipo = obj.getString("tipo");
                    String nombre = obj.getString("nombre");
                    double precio = obj.getDouble("precio");

                    Ingrediente ing = null;
                    switch (tipo.toLowerCase()) {
                        case "carne":
                            ing = new Carne(nombre, precio, "carne");
                            break;
                        case "queso":
                            ing = new Queso(nombre, precio, "queso");
                            break;
                        case "vegetal":
                            ing = new Vegetal(nombre, precio, "vegetal");
                            break;
                        case "salsa":
                            ing = new Salsa(nombre, precio, "salsa");
                            break;
                        default:
                            System.out.println("Tipo de ingrediente desconocido: " + tipo);
                    }

                    if (ing != null) ingredientesDisponibles.add(ing);
                }
            } catch (Exception e) {
                System.out.println("Error al leer JSON: " + e.getMessage());
            }
        }

    }

    public List<Ingrediente> getIngredientesDisponibles() {
        return new ArrayList<>(ingredientesDisponibles);
    }
}
