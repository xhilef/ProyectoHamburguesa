package burgervend.models;

import burgervend.models.ingredientes.Ingrediente;
import java.time.LocalDateTime;
import java.util.*;

public class Pedido {
    private final String id;
    private final Hamburguesa hamburguesaBase;
    private final Map<Ingrediente, Integer> ingredientes;
    private final LocalDateTime fecha;
    private String estado;
    private String metodoPago;
    private boolean pagoAprobado;

    public Pedido(Hamburguesa hamburguesaBase, List<Ingrediente> ingredientes) {
        if (hamburguesaBase == null || ingredientes == null) {
            throw new IllegalArgumentException("Hamburguesa base e ingredientes no pueden ser null");
        }

        this.id = generarIdUnico();
        this.hamburguesaBase = hamburguesaBase;
        this.ingredientes = new HashMap<>();
        for (Ingrediente ing : ingredientes) {
            agregarIngrediente(ing, 1);
        }
        this.fecha = LocalDateTime.now();
        this.estado = "PENDIENTE";
        this.metodoPago = null;
        this.pagoAprobado = false;
    }

    private String generarIdUnico() {
        return "PED-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public void agregarIngrediente(Ingrediente ingrediente, int cantidad) {
        if (ingrediente == null || cantidad <= 0) return;
        ingredientes.put(ingrediente, cantidad);
    }

    public void removerIngrediente(Ingrediente ingrediente) {
        ingredientes.remove(ingrediente);
    }

    public double calcularTotal() {
        double total = hamburguesaBase.getPrecioBase();
        for (Map.Entry<Ingrediente, Integer> entry : ingredientes.entrySet()) {
            total += entry.getKey().getPrecio() * entry.getValue();
        }
        return total;
    }

    public String getIngredientesAsString() {
        if (ingredientes.isEmpty()) {
            return "- Sin ingredientes adicionales";
        }

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Ingrediente, Integer> entry : ingredientes.entrySet()) {
            sb.append(entry.getValue()).append("x ").append(entry.getKey().getNombre()).append(", ");
        }
        return sb.substring(0, sb.length() - 2);
    }

    public String generarResumenPago() {
        StringBuilder sb = new StringBuilder();
        sb.append("\t\t\t\t\t****** CALCULO DE PRECIO TOTAL ******\n\n")
          .append("\t\t\t\t\tEligió: Hamburguesa ").append(hamburguesaBase.getNombre()).append("\n\n")
          .append("\t\t\t\t\tIngredientes adicionales:\n");

        if (ingredientes.isEmpty()) {
            sb.append("- Sin ingredientes adicionales\n");
        } else {
            ingredientes.forEach((ing, qty) ->
                sb.append("\t\t\t\t\t- ").append(qty).append("x ").append(ing.getNombre()).append("\t\t\t\t\n"));
        }

        sb.append("\t\t\t\t\t___________________________________________\n")
          .append("\t\t\t\t\tTOTAL A PAGAR: $").append(String.format("\t\t\t\t%.2f\n", calcularTotal()));

        return sb.toString();
    }
    public String generarResumen() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== RESUMEN DE COMPRA ===\n")
          .append("ID: ").append(id).append("\n")
          .append("Hamburguesa: ").append(hamburguesaBase.getNombre()).append("\n")
          .append("Ingredientes:\n");

        if (ingredientes.isEmpty()) {
            sb.append("- Sin ingredientes adicionales\n");
        } else {
            ingredientes.forEach((ing, qty) ->
                sb.append("- ").append(qty).append("x ").append(ing.getNombre()).append("\n"));
        }

        sb.append("---------------------------\n")
          .append("TOTAL: $").append(String.format("%.2f", calcularTotal()));

        return sb.toString();
    }

    public void aprobarPago(String metodo) {
        if (metodo == null || metodo.isEmpty()) {
            throw new IllegalArgumentException("Método de pago no puede ser null o vacío");
        }
        this.metodoPago = metodo;
        this.pagoAprobado = true;
        this.estado = "PAGADO";
    }

    public void rechazarPago() {
        this.pagoAprobado = false;
        this.estado = "PENDIENTE";
    }

    public void cancelarPedido() {
        this.estado = "CANCELADO";
    }

    // Getters
    public String getId() { return id; }
    public Hamburguesa getHamburguesaBase() { return hamburguesaBase; }
    public Map<Ingrediente, Integer> getIngredientes() { return new HashMap<>(ingredientes); }
    public LocalDateTime getFecha() { return fecha; }
    public String getEstado() { return estado; }
    public String getMetodoPago() { return metodoPago; }
    public boolean isPagoAprobado() { return pagoAprobado; }

    public int getCantidadIngrediente(Ingrediente ing) {
        return ingredientes.getOrDefault(ing, 0);
    }
    
    

    @Override
    public String toString() {
        return "Pedido{" +
                "id='" + id + '\'' +
                ", hamburguesa=" + hamburguesaBase.getNombre() +
                ", ingredientes=" + getIngredientesAsString() +
                ", total=$" + String.format("%.2f", calcularTotal()) +
                ", estado=" + estado +
                '}';
    }
}
