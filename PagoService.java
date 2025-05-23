package burgervend.services;

import burgervend.utils.ResourceManager;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class PagoService {
    private List<String> metodosPago;
    
    public PagoService() {
        cargarMetodosPago();
    }
    
    private void cargarMetodosPago() {
        metodosPago = new ArrayList<>();
        String json = ResourceManager.getJsonData("metodos_pago.json");
        JSONArray array = new JSONArray(json);
        
        for (int i = 0; i < array.length(); i++) {
            metodosPago.add(array.getString(i));
        }
    }
    
    public List<String> getMetodosPago() {
        return new ArrayList<>(metodosPago);
    }
    
    // Simulación de pasarela de pago
    public boolean procesarPago(double monto, String metodo) {
    
        return Math.random() > 0.2;
    }
}
