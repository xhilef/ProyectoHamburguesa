package burgervend.models.ingredientes;

public class Salsa extends Ingrediente {

    // Constructor
    public Salsa(String nombre, double precio, String tipo) {
        super(nombre, precio, tipo);
    }

    @Override
    public String getDescripcion() {
        return "Salsa: " + getNombre();
    }

    @Override
    public double getPrecio() {
        return super.precio;  // Usar el precio de la clase base
    }

    @Override
    public String getTipo() {
        return "Salsa";  // Tipo es fijo como "Salsa"
    }

    @Override
    public String toString() {
        return "Salsa: " + getNombre() + ", Precio: " + getPrecio();
    }
}
