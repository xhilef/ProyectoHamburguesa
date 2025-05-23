package burgervend.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import burgervend.models.Pedido;
import burgervend.models.ingredientes.Ingrediente;
import java.util.List;

public class GsonHelper {

    // Instancia estática de Gson
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Ingrediente.class, new IngredienteTypeAdapter())
            .setPrettyPrinting()
            .create();

    public static Gson obtenerGson() {
        return gson;
    }

    // Método para convertir un objeto Pedido a JSON
    public static String serializarPedido(Pedido pedido) {
        return gson.toJson(pedido);
    }

    // Método para convertir una lista de pedidos a JSON
    public static String serializarPedidos(List<Pedido> pedidos) {
        return gson.toJson(pedidos);
    }

    // Método para convertir JSON a objeto Pedido
    public static Pedido deserializarPedido(String json) {
        return gson.fromJson(json, Pedido.class);
    }

    // Método para convertir JSON a lista de Pedidos
    public static List<Pedido> deserializarPedidos(String json) {
        return gson.fromJson(json, List.class);
    }
}
