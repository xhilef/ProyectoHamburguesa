package burgervend.services;

import burgervend.models.Pedido;
import java.sql.*;

public class DatabaseService {
    private static final String URL = "jdbc:sqlite:burger_vend.db";
    
    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Error cargando driver SQLite: " + e.getMessage());
        }
    }
    
    public static void initDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS pedidos (" +
            "id TEXT PRIMARY KEY," +
            "fecha TEXT NOT NULL," +
            "hamburguesa TEXT NOT NULL," +
            "ingredientes TEXT NOT NULL," +
            "total REAL NOT NULL," +
            "metodo_pago TEXT," +
            "estado TEXT NOT NULL)";
        
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error inicializando BD: " + e.getMessage());
        }
    }
    
    public static void guardarPedido(Pedido pedido) {
        String sql = "INSERT INTO pedidos(id, fecha, hamburguesa, ingredientes, total, metodo_pago, estado) " +
                   "VALUES(?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, pedido.getId());
            pstmt.setString(2, pedido.getFecha().toString());
            pstmt.setString(3, pedido.getHamburguesaBase().getNombre());
            pstmt.setString(4, pedido.getIngredientesAsString());
            pstmt.setDouble(5, pedido.calcularTotal());
            pstmt.setString(6, pedido.getMetodoPago());
            pstmt.setString(7, pedido.getEstado());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error guardando pedido: " + e.getMessage());
        }
    }
}