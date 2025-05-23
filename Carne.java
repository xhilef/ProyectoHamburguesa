package burgervend.models.ingredientes;

public class Carne extends Ingrediente {

    // Constructor
    public Carne(String nombre, double precio, String tipo) {
        super(nombre, precio, tipo);  // Llamada al constructor de la clase base
    }

    @Override
    public String getDescripcion() {
        return "Carne: " + getNombre();
    }

    @Override
    public double getPrecio() {
        return super.precio;  // Usar el precio de la clase base
    }

    @Override
    public String getTipo() {
        return "Carne";  // Tipo es fijo como "Carne"
    }

    @Override
    public String toString() {
        return "Carne: " + getNombre() + ", Precio: " + getPrecio();
    }
}
