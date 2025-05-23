package burgervend.models.pago;

public class ProcesadorPagos {
    private EstrategiaPago estrategia;
    
    public void setEstrategia(EstrategiaPago estrategia) {
        this.estrategia = estrategia;
    }
    
    public boolean procesar(double monto, String metodo) {
    if (monto < 0) {
        System.out.println("El monto no puede ser negativo.");
        return false;
    }
    
    if (metodo == null || metodo.isEmpty()) {
        System.out.println("Método de pago no válido.");
        return false;
    }

    if (metodo.equalsIgnoreCase("efectivo")) {
        setEstrategia(new PagoEfectivo());
    } else if (metodo.equalsIgnoreCase("tarjeta")) {
        setEstrategia(new PagoTarjeta());
    } else {
        System.out.println("Método de pago desconocido.");
        return false;
    }

    return estrategia != null && estrategia.pagar(monto);
    }
}
