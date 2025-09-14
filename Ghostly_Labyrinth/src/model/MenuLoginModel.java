package model;

public class MenuLoginModel {

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
}


