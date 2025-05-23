package burgervend.models.ingredientes;

public class IngredienteConcretoA extends Ingrediente {
    public IngredienteConcretoA() {
        super("Ingrediente A", 1.0, "Tipo A");  // Pass 'tipo' to the super class constructor
    }

    @Override
    public String getDescripcion() {
        return "Ingrediente A: " + nombre;
    }

    @Override
    public double getPrecio() {
        return precio;
    }

    @Override
    public String getTipo() {
        return "Tipo A"; // Provide the implementation for getTipo()
    }
}
