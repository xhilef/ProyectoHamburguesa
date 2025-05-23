package burgervend.models.ingredientes;

public class IngredienteConcretoB extends Ingrediente {
    public IngredienteConcretoB() {
        super("Ingrediente B", 1.5, "Tipo B");  // Pass 'tipo' to the super class constructor
    }

    @Override
    public String getDescripcion() {
        return "Ingrediente B: " + nombre;
    }

    @Override
    public double getPrecio() {
        return precio;
    }

    @Override
    public String getTipo() {
        return "Tipo B"; // Provide the implementation for getTipo()
    }
}
