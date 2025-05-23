package burgervend.models.pago;

public class PagoTarjeta implements EstrategiaPago {
    @Override
    public boolean pagar(double monto) {
        System.out.println("Procesando pago con tarjeta: $" + monto);
        String numeroTarjeta = "1234567812345678"; // Aquí deberías obtener el número real
        if (numeroTarjeta == null || numeroTarjeta.length() != 16 || !numeroTarjeta.matches("\\d+")) {
            System.out.println("Número de tarjeta inválido.");
            return false;
        }
        
        System.out.println("Número de tarjeta válido.");
        return Math.random() > 0.3;  // Simulando un pago aprobado
    }
}



