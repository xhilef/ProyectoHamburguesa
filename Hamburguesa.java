package burgervend.models;

import burgervend.models.ingredientes.Ingrediente;
import java.util.List;
import java.util.ArrayList;

public class Hamburguesa {
    private String nombre;
    private double precioBase;
    private List<Ingrediente> ingredientes;
    private String estado;

    // para hamburguesas del menú
    public Hamburguesa(String nombre, double precioBase) {
        this.nombre = nombre;
        this.precioBase = precioBase;
        this.ingredientes = new ArrayList<>();
        this.estado = "DISPONIBLE";
    }

    // para hamburguesas personalizadas
    public Hamburguesa(Pedido pedido) {
        this.nombre = "Personalizada";
        this.precioBase = 0;
        this.ingredientes = new ArrayList<>(pedido.getIngredientes().keySet());
        this.estado = "PREPARADA";
    }

    public void agregarIngrediente(Ingrediente ingrediente) {
        ingredientes.add(ingrediente);
    }

    public void removerIngrediente(Ingrediente ingrediente) {
        ingredientes.remove(ingrediente);
    }

    public double getPrecioTotal() {
        return precioBase + ingredientes.stream().mapToDouble(Ingrediente::getPrecio).sum();
    }

    public String getResumen() {
        StringBuilder sb = new StringBuilder();
        sb.append("Hamburguesa: ").append(nombre).append("\n");
        sb.append("Ingredientes:\n");
        ingredientes.forEach(ing -> sb.append("- ").append(ing.getNombre()).append("\n"));
        sb.append("Total: $").append(String.format("%.2f", getPrecioTotal()));
        return sb.toString();
    }

    @Override
    public String toString() {
        return nombre + " - $" + String.format("%.2f", getPrecioTotal());
    }

    public String getIngredientesTexto() {
        StringBuilder sb = new StringBuilder();
        for (Ingrediente ing : ingredientes) {
            sb.append(ing.getNombre()).append(", ");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }

    public String getNombre() {
        return nombre;
    }

    public List<Ingrediente> getIngredientes() {
        return new ArrayList<>(ingredientes);
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getPrecioBase() {
        return precioBase;
    }
}
