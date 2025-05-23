package burgervend.services;

import burgervend.models.Hamburguesa;
import burgervend.models.Pedido;
import burgervend.utils.ResourceManager;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class GestorPedidos {
    private final List<Hamburguesa> hamburguesasBase;
    
    public GestorPedidos() {
        this.hamburguesasBase = cargarHamburguesasBase();
    }
    
    private List<Hamburguesa> cargarHamburguesasBase() {
        List<Hamburguesa> hamburguesas = new ArrayList<>();
        String json = ResourceManager.getJsonData("hamburguesas.json");
        JSONArray array = new JSONArray(json);
        
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            String nombre = obj.getString("nombre");
            double precio = obj.getDouble("precioBase");
            hamburguesas.add(new Hamburguesa(nombre, precio));
        }
        
        return hamburguesas;
    }
    
    public List<Hamburguesa> getHamburguesasBase() {
        return new ArrayList<>(hamburguesasBase);
    }
    
    public void guardarPedido(Pedido pedido) {
   
    }
}
