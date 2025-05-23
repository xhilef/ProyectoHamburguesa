package burgervend.utils;

import burgervend.models.Pedido;

public class AppState {
    private static Pedido pedidoActual;

    public static void setPedidoActual(Pedido pedido) {
        AppState.pedidoActual = pedido;
    }

    public static Pedido getPedidoActual() {
        return pedidoActual;
    }
}
