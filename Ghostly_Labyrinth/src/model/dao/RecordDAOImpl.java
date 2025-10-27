package model.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import model.singleton.DatabaseManager;

public class RecordDAOImpl implements RecordDAO {

    // Metodo privado para obtener la conexion desde el Singleton
    private Connection getConexion() {
        return DatabaseManager.getInstance().getConnection();
    }

    @Override
    public void save(Record record) {
        Connection conn = getConexion();
        if (conn == null) return;
        
        // La consulta asume que la tabla se llama 'records' y tiene columnas:
        // nombre (VARCHAR), tiempo_ms (BIGINT), fecha (TIMESTAMP WITH DEFAULT CURRENT_TIMESTAMP)
        String sql = "INSERT INTO records (nombre, tiempo_ms) VALUES (?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, record.getNombre());
            pstmt.setLong(2, record.getTiempoMs());
            
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Récord guardado con éxito en la BD para: " + record.getNombre());
            }

        } catch (SQLException e) {
            System.err.println("Error SQL al guardar el récord: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<Record> getTop10Records() {
        List<Record> topRecords = new ArrayList<>();
    String sql = "SELECT nombre, tiempo_ms, fecha FROM records ORDER BY tiempo_ms ASC LIMIT 10";
    
    // 1. Obtener la conexion fuera del try-with-resources para evitar el cierre automatico.
    Connection conn = getConexion(); 
    
    if (conn == null) {
        System.err.println("ADVERTENCIA: Conexión nula, no se puede obtener el Top 10.");
        return topRecords;
    }
    
    // 2. Usar un try/catch para manejar los recursos internos (PreparedStatement y ResultSet)
    try (PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

        while (rs.next()) {
        String nombre = rs.getString("nombre");
        
        // Multiplicar por 1000 para convertir segundos a milisegundos (ms)
        long tiempoEnSegundos = rs.getLong("tiempo_ms"); 
        long tiempo = tiempoEnSegundos * 1000L; // Multiplicamos por 1000 (L forzando long)
        
        Timestamp ts = rs.getTimestamp("fecha"); 
        LocalDateTime fecha = ts != null ? ts.toLocalDateTime() : null;
            
            topRecords.add(new Record(nombre, tiempo, fecha)); 
        }

    } catch (SQLException e) {
        System.err.println("Error al obtener el Top 10 de récords: " + e.getMessage());
        e.printStackTrace();
    }
    //No cerramos la conexión aquí. El DatabaseManager debe gestionarla.
    return topRecords;
    }
}
