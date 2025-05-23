package burgervend.services;

import burgervend.models.Pedido;
import burgervend.models.ingredientes.Ingrediente;
import burgervend.utils.IngredienteTypeAdapter;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PedidoRepository {

    private static final String FILE_PATH = "pedidos.json";
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Ingrediente.class, new IngredienteTypeAdapter())
            .enableComplexMapKeySerialization() // Crucial para Map<Ingrediente, Integer>
            .setPrettyPrinting()
            .create();

    public static String guardarPedido(Pedido pedido) {
        try {
            List<Pedido> pedidos = obtenerPedidosExistentes();
            pedidos.add(pedido);
            
            try (FileWriter writer = new FileWriter(FILE_PATH)) {
                gson.toJson(pedidos, writer);
                return pedido.getId();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar pedido: " + e.getMessage());
            return null;
        }
    }

    public static List<Pedido> obtenerPedidosExistentes() {
        File file = new File(FILE_PATH);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(file)) {
            Type tipoListaPedidos = new TypeToken<List<Pedido>>(){}.getType();
            List<Pedido> pedidos = gson.fromJson(reader, tipoListaPedidos);
            return pedidos != null ? pedidos : new ArrayList<>();
        } catch (JsonSyntaxException | IOException e) {
            System.err.println("Error al leer pedidos (se retorna lista vacía): " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Opcional: Método para limpiar/reparar el archivo JSON
    public static void repararArchivoPedidos() {
        List<Pedido> pedidos = obtenerPedidosExistentes();
        guardarPedidos(pedidos); // Reescribe el archivo en formato correcto
    }

    private static void guardarPedidos(List<Pedido> pedidos) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(pedidos, writer);
        } catch (IOException e) {
            System.err.println("Error al reparar archivo: " + e.getMessage());
        }
    }
}