package burgervend.models.ingredientes;

public class Vegetal extends Ingrediente {

    // Constructor
    public Vegetal(String nombre, double precio, String tipo) {
        super(nombre, precio, tipo);
    }

    @Override
    public String getDescripcion() {
        return "Vegetal: " + getNombre();
    }

    @Override
    public double getPrecio() {
        return super.precio;  // Usar el precio de la clase base
    }

    @Override
    public String getTipo() {
        return "Vegetal";  // Tipo es fijo como "Vegetal"
    }

    @Override
    public String toString() {
        return "Vegetal: " + getNombre() + ", Precio: " + getPrecio();
    }
}
