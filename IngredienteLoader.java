package burgervend.services;

import burgervend.models.ingredientes.*;
import com.google.gson.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class IngredienteLoader {

    public static List<Ingrediente> cargarIngredientesDesdeJson(String archivo) throws IOException {
        Gson gson = new Gson();
        List<Ingrediente> lista = new ArrayList<>();

        try (FileReader reader = new FileReader(archivo)) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            for (JsonElement elem : jsonArray) {
                JsonObject obj = elem.getAsJsonObject();
                String tipo = obj.get("tipo").getAsString();
                String nombre = obj.get("nombre").getAsString();
                double precio = obj.get("precio").getAsDouble();

                switch (tipo.toLowerCase()) {
                    case "carne": lista.add(new Carne(nombre, precio, "Carne")); break;
                    case "queso": lista.add(new Queso(nombre, precio, "Queso")); break;
                    case "vegetal": lista.add(new Vegetal(nombre, precio,  "Vegetal")); break;
                    case "salsa": lista.add(new Salsa(nombre, precio, "Salsa")); break;
                    default: throw new IllegalArgumentException("Tipo desconocido: " + tipo);
                }
            }
        }

        return lista;
    }
}

