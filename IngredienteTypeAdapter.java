package burgervend.utils;

import com.google.gson.*;
import burgervend.models.ingredientes.*;

import java.lang.reflect.Type;

public class IngredienteTypeAdapter implements JsonSerializer<Ingrediente>, JsonDeserializer<Ingrediente> {
    private static final String TYPE_FIELD = "tipo";

    @Override
    public JsonElement serialize(Ingrediente ingrediente, Type type, JsonSerializationContext context) {
        JsonObject json = new JsonObject();
        json.addProperty("nombre", ingrediente.getNombre());
        json.addProperty("precio", ingrediente.getPrecio());
        json.addProperty(TYPE_FIELD, ingrediente.getClass().getSimpleName().toLowerCase());
        
        if (ingrediente instanceof Carne) {
            json.addProperty("tipo_carne", ((Carne) ingrediente).getTipo());
        } else if (ingrediente instanceof Queso) {
            json.addProperty("tipo_queso", ((Queso) ingrediente).getTipo());
        }
        // Añade otros tipos...
        
        return json;
    }

    @Override
    public Ingrediente deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        try {
            // Caso 1: Ingrediente en formato correcto (JSON object)
            if (json.isJsonObject()) {
                JsonObject obj = json.getAsJsonObject();
                String nombre = obj.get("nombre").getAsString();
                double precio = obj.get("precio").getAsDouble();
                String tipo = obj.get(TYPE_FIELD).getAsString();

                switch (tipo) {
                    case "carne":
                        return new Carne(nombre, precio, obj.get("tipo_carne").getAsString());
                    case "queso":
                        return new Queso(nombre, precio, obj.get("tipo_queso").getAsString());
                    // Otros casos...
                    default:
                        throw new JsonParseException("Tipo desconocido: " + tipo);
                }
            }
            // Caso 2: Ingrediente en formato incorrecto (String legacy)
            else if (json.isJsonPrimitive()) {
                String legacyString = json.getAsString();
                return parseLegacyIngrediente(legacyString);
            }
            throw new JsonParseException("Formato de ingrediente no soportado");
        } catch (Exception e) {
            throw new JsonParseException("Error deserializando ingrediente", e);
        }
    }

    private Ingrediente parseLegacyIngrediente(String legacyStr) {
        // Implementa lógica para convertir strings como "Carne de res ($30.5)" a objetos Ingrediente
        // Ejemplo simplificado:
        if (legacyStr.contains("Carne")) {
            return new Carne(legacyStr.split("\\$")[0].trim(), 
                           Double.parseDouble(legacyStr.split("\\$")[1].replace(")", "")), 
                           "res");
        }
        // Añade más casos...
        throw new JsonParseException("Formato legacy no reconocido: " + legacyStr);
    }
}