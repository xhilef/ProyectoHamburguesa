package burgervend.models.builder;

import burgervend.models.Hamburguesa;
import burgervend.models.Pedido;

public class HamburguesaBuilder {
    private final ConstructorHamburguesa constructor;

    public HamburguesaBuilder() {
        this.constructor = new ConstructorHamburguesa();
    }

    public HamburguesaBuilder withCarne(String tipo, double precio) {
        constructor.agregarCarne(tipo, precio);
        return this;
    }

    public HamburguesaBuilder withQueso(String tipo, double precio) {
        constructor.agregarQueso(tipo, precio);
        return this;
    }

    public HamburguesaBuilder withVegetal(String tipo, double precio) {
        constructor.agregarVegetal(tipo, precio);
        return this;
    }

    public HamburguesaBuilder withSalsa(String tipo, double precio) {
        constructor.agregarSalsa(tipo, precio);
        return this;
    }

    public Hamburguesa build() {
        Pedido pedido = constructor.construir();
        return new Hamburguesa(pedido);
    }
}
