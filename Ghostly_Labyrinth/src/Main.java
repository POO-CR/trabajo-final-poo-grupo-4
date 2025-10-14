import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.SwingUtilities;

import controller.juego.GameController;
import controller.menu.MenuController;



public class Main {
    public static void main(String[] args) throws Exception {

        DatabaseManager dbManager = DatabaseManager.getInstance();

        Connection conn = dbManager.getConnection();

        final GameController gameController;

        if (conn != null) {
            System.out.println("Base de datos conectada. Inicializando GameController.");
            // 1. Crear el GameController con la conexión.
            gameController = new GameController(conn);  

        }else{
            System.err.println("ERROR: No se pudo conectar a la base de datos. Récords deshabilitados.");
            // Si la conexión falla, se llama al constructor que ignora la BD 
            // (Necesitas un constructor GameController() o pasar null).
            // Ya que GameController(Connection) maneja null, podemos hacer:
            gameController = new GameController(null); // Pasa null para que el constructor maneje la falla
        }

        // Al final de la aplicación, es buena práctica cerrar la conexión
        // dbManager.closeConnection(); 

        SwingUtilities.invokeLater(() -> {
            System.out.println("arrancó main");
            MenuController mc = new MenuController(gameController);
        });

        

    }
}
