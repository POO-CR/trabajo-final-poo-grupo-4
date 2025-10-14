import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Gestiona la conexion a la base de datos PostgreSQL utilizando el patron Singleton.
 */
public class DatabaseManager {

    private static DatabaseManager instancia;
    private Connection conexion;

    // --- Parámetros de Conexión para PostgreSQL ---
    // ¡Asegúrate de cambiar estos valores por los de tu base de datos!
    private static final String URL = "jdbc:postgresql://localhost:5432/ghostly_mansion"; 
    private static final String USER = "postgres"; 
    private static final String PASSWORD = "mundial2022";

    // Constructor privado para el patron Singleton
    private DatabaseManager() {
        try {
            // Cargar la conexión (ya no se usa Class.forName en versiones recientes de JDBC)
            this.conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión con PostgreSQL establecida.");
            inicializarBaseDeDatos();

        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos PostgreSQL: " + e.getMessage());
            this.conexion = null; // Asegura que la conexión sea null si falla
        }
    }

    // Método Singleton para obtener la única instancia
    public static synchronized DatabaseManager getInstance() {
        if (instancia == null) {
            instancia = new DatabaseManager();
        }
        return instancia;
    }

    public Connection getConnection() {
        // Verificar si la conexión es válida antes de devolverla
        try {
            if (this.conexion != null && !this.conexion.isClosed()) {
                return this.conexion;
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar la conexión: " + e.getMessage());
        }
        // Si la conexión falló inicialmente o se cerró, intenta reconectar (opcional) o devuelve null
        return null; 
    }

    private void inicializarBaseDeDatos() {
        // La tabla contendra ID, Nombre, Tiempo (en segundos) y Fecha.
        try (Statement stmt = this.conexion.createStatement()) {
            
            // PostgreSQL usa SERIAL para autoincremento y TIMESTAMPTZ (o TIMESTAMP) para fecha y hora.
            String sqlCreateTable = "CREATE TABLE IF NOT EXISTS records (" +
                                    "id SERIAL PRIMARY KEY," + 
                                    "nombre VARCHAR(100) NOT NULL," +
                                    "tiempo_ms BIGINT NOT NULL," + // Guardamos el tiempo en milisegundos
                                    "fecha TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP);";
            stmt.execute(sqlCreateTable);
            System.out.println("Tabla 'records' creada o ya existente.");
            
        } catch (SQLException e) {
            System.err.println("Error al inicializar la base de datos y crear la tabla 'records': " + e.getMessage());
        }
    }
    
    // Metodo opcional para cerrar la conexion cuando la aplicación finaliza
    public void closeConnection() {
        if (this.conexion != null) {
            try {
                this.conexion.close();
                System.out.println("Conexion con PostgreSQL cerrada.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}

