package burgervend.services;

import burgervend.models.ingredientes.Ingrediente;
import java.util.List;

public class MotorPersonalizacion {
    private final GestorIngredientes gestorIngredientes;
    
    public MotorPersonalizacion(GestorIngredientes gestorIngredientes) {
        this.gestorIngredientes = gestorIngredientes;
    }
    
    public List<Ingrediente> getOpcionesPersonalizacion() {
        return gestorIngredientes.getIngredientesDisponibles();
    }
      // para aceptar combinaciones permitidas
    public boolean validarCombinacion(List<Ingrediente> ingredientes) {
        
        return !ingredientes.isEmpty();
    }
}
