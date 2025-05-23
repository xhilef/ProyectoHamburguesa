package burgervend.models;

import burgervend.models.ingredientes.Ingrediente;
import burgervend.models.pago.ProcesadorPagos;
import java.util.ArrayList;
import java.util.List;

public class MaquinaExpendedora {
    public enum Estado {
        ESPERANDO_PEDIDO, PERSONALIZANDO, PAGO_PENDIENTE, ENTREGANDO
    }
    
    private Estado estadoActual;
    private Pedido pedidoActual;
    private final ProcesadorPagos procesadorPagos;
    
    public MaquinaExpendedora(ProcesadorPagos procesadorPagos) {
        this.estadoActual = Estado.ESPERANDO_PEDIDO;
        this.procesadorPagos = procesadorPagos;
    }
    
    public void iniciarPedido(Hamburguesa hamburguesa) {
        if (estadoActual == Estado.ESPERANDO_PEDIDO) {
            // Crear el Pedido con lista vacía de ingredientes
            this.pedidoActual = new Pedido(hamburguesa, new ArrayList<Ingrediente>());
            this.estadoActual = Estado.PERSONALIZANDO;
        }
    }
    
    public void agregarIngrediente(Ingrediente ingrediente) {
        if (estadoActual == Estado.PERSONALIZANDO && pedidoActual != null) {
            pedidoActual.agregarIngrediente(ingrediente, 1);
        }
    }
    
    public boolean procesarPago(String metodoPago) {
        if (estadoActual == Estado.PERSONALIZANDO && pedidoActual != null) {
            // Asegurarse de que se pase un double a procesarPago
            boolean exito = procesadorPagos.procesar(pedidoActual.calcularTotal(), metodoPago);
            if (exito) {
                estadoActual = Estado.ENTREGANDO;
                return true;
            }
        }
        return false;
    }
    
    public Pedido dispensarPedido() {
        if (estadoActual == Estado.ENTREGANDO && pedidoActual != null) {
            estadoActual = Estado.ESPERANDO_PEDIDO;
            Pedido pedido = pedidoActual;
            pedidoActual = null;
            return pedido;
        }
        return null;
    }
    
    public Estado getEstadoActual() {
        return estadoActual;
    }
}
