package burgervend.models.ingredientes;

public abstract class Ingrediente {
    protected String nombre;
    protected double precio;
    protected String tipo;

    // Constructor
    public Ingrediente(String nombre, double precio, String tipo) {
        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
    }

    // Métodos abstractos
    public abstract String getDescripcion();
    public abstract double getPrecio();
    public abstract String getTipo();
    
    // Método getter para el nombre
    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "Ingrediente: " + nombre + ", Precio: " + precio;
    }
}
