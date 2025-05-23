package burgervend.models.pago;

public class PagoEfectivo implements EstrategiaPago {
    @Override
    public boolean pagar(double monto) {
        // Lógica específica para pago en efectivo
        System.out.println("Procesando pago en efectivo: $" + monto);
        return true;
    }
}
