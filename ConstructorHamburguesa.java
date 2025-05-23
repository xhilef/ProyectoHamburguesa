package burgervend.models.builder;

import burgervend.models.*;
import burgervend.models.ingredientes.*;
import java.util.ArrayList;

public class ConstructorHamburguesa {
    private final Pedido pedido;

    public ConstructorHamburguesa() {
        Hamburguesa base = new Hamburguesa("Personalizada", 0);
        this.pedido = new Pedido(base, new ArrayList<>());
    }

    public ConstructorHamburguesa agregarCarne(String tipo, double precio) {
        pedido.agregarIngrediente(new Carne(tipo, precio, "carne"), 1);
        return this;
    }

    public ConstructorHamburguesa agregarQueso(String tipo, double precio) {
        pedido.agregarIngrediente(new Queso(tipo, precio, "queso"), 1);
        return this;
    }

    public ConstructorHamburguesa agregarVegetal(String tipo, double precio) {
        pedido.agregarIngrediente(new Vegetal(tipo, precio, "vegetal"), 1);
        return this;
    }

    public ConstructorHamburguesa agregarSalsa(String tipo, double precio) {
        pedido.agregarIngrediente(new Salsa(tipo, precio, "salsa"), 1);
        return this;
    }

    public Pedido construir() {
        return pedido;
    }
}
