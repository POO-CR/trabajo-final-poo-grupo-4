package model.menu;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class MenuLoginModel {

    private final Connection dbConnection;

    public MenuLoginModel(Connection connection){
        this.dbConnection = connection; 

    }


    public boolean validarUsuario(String usuario) {
        // NOTA: A tu código le faltaba el 'return' para la rama true. 
        // Lo corrijo para que devuelva un valor siempre.
        return usuario != null && !usuario.trim().isEmpty();
    }

    public boolean guardarUsuario(String usuario, int tiempoSegundos) {
        
        // Asumiendo que tu tabla se llama 'usuarios' y tiene columnas 'nombre', 'password', 'email'
        String SQL_INSERT = "INSERT INTO partida (nombre, tiempo, fecha) VALUES (?, ?, ?)";

        //1 obtener fecha y hora actual en java
        Timestamp fechaActual = new Timestamp(System.currentTimeMillis());
        
        // Utilizamos un try-with-resources para asegurar el cierre del PreparedStatement
        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(SQL_INSERT)) {
            
            // 2 se asignan los valores a los placeholders
            preparedStatement.setString(1, usuario);
            preparedStatement.setInt(2, tiempoSegundos);
            preparedStatement.setTimestamp(3, fechaActual);

            // 2. Ejecutar la inserción
            int filasAfectadas = preparedStatement.executeUpdate();
            
            // 3. Verificar si se insertó al menos una fila
            if (filasAfectadas > 0) {
                System.out.println("✅ Nuevo record guardado: " + usuario + " (" + tiempoSegundos + "s)");
                return true;
            } else {
                System.out.println("❌ Error al registrar usuario: Filas afectadas 0.");
                return false;
            }

        } catch (SQLException e) {
            // Manejar errores de SQL (ej: usuario ya existe si 'nombre' es UNIQUE)
            System.err.println("❌ Error al guardar usuario en la BD: " + e.getMessage());
            return false;
        }
    }



/* 
     // valida el email con una regex estricta
    public boolean validarEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
    }

    // valida que los campos no esten vacios y que el email sea correcto
    public boolean validarUsuario(String usuario, String pass, String email) {
        if (usuario == null || usuario.trim().isEmpty()) return false;
        if (pass == null || pass.trim().isEmpty()) return false;
        if (email == null || email.trim().isEmpty()) return false;
        return validarEmail(email);
    }

    // Aca mas adelante se puede guardar el usuario en BD o archivo
    public void guardarUsuario(String usuario, String pass, String email) {
        // TODO: lógica de almacenamiento
        System.out.println("Usuario guardado: " + usuario + " - " + email);
    }
        */
}


