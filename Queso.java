package burgervend.models.ingredientes;

public class Queso extends Ingrediente {

    // Constructor
    public Queso(String nombre, double precio, String tipo) {
        super(nombre, precio, tipo);
    }

    @Override
    public String getDescripcion() {
        return "Queso: " + getNombre();
    }

    @Override
    public double getPrecio() {
        return super.precio;  // Usar el precio de la clase base
    }

    @Override
    public String getTipo() {
        return "Queso";  // Tipo es fijo como "Queso"
    }

    @Override
    public String toString() {
        return "Queso: " + getNombre() + ", Precio: " + getPrecio();
    }
}
