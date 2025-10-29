package model.menu;

public class RegisterStartmodel {
    private static final int MAX_LENGTH = 15;
    private static final String DEFAULT_PLACEHOLDER = "Ingresa tu apodo"; // Valor del JTextField por defecto

    /**
     * Valida el apodo del jugador.
     * @param apodo El String ingresado por el usuario (puede incluir el placeholder).
     * @return Un mensaje de error (String) si la validación falla, o null si es válido.
     */
    public static String validarApodo(String apodo) {
        String apodoLimpio = apodo.trim();

        if (apodoLimpio.isEmpty() || apodoLimpio.equals(DEFAULT_PLACEHOLDER)) {
            return "El apodo no puede estar vacío.";
        }
        
        if (apodoLimpio.length() > MAX_LENGTH) {
            return "El apodo no puede tener más de " + MAX_LENGTH + " caracteres.";
        }
        
        return null; // El apodo es válido
    }



}
