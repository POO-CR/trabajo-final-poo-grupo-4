package controller;


import java.util.function.Consumer;

import model.MenuLoginModel;
import view.MenuRegisterStart;

public class MenuLoginController {

    private MenuLoginModel model;
    private MenuRegisterStart view;
    private Consumer<String> iniciarJuegoCallback; // callback para iniciar el juego


    public MenuLoginController(MenuLoginModel model, MenuRegisterStart view, Consumer<String> iniciarJuegoCallback) {
        this.model = model;
        this.view = view;
        this.iniciarJuegoCallback = iniciarJuegoCallback;
        initController();
    }

    private void initController() {
        
        // accion boton volver
        view.getVolverButton().addActionListener(e -> view.dispose());

        // accion boton crear
        view.getCrearButton().addActionListener(e -> crearUsuario());
    }

    private void crearUsuario() {
        String usuario = view.getUsuario().trim();
        String pass = view.getContraseña().getText().trim();
        String email = view.getEmail().trim();

        
        // chequear placeholders
        if (usuario.equals("Ingrese Usuario") || usuario.isEmpty()) {
            view.setResultado("❌ Tienes que ingresar un usuario", false);
            return;
        }
        if (pass.equals("Ingrese Contraseña") || pass.isEmpty()) {
            view.setResultado("❌ Tienes que ingresar una contraseña", false);
            return;
        }
        if (email.equals("Ingrese Email") || email.isEmpty() || !model.validarEmail(email)) {
            view.setResultado("❌ Tienes que ingresar un email válido", false);
            return;
        }
        
        // guardar usuario
        model.guardarUsuario(usuario, pass, email);
        view.setResultado("✅ Usuario creado correctamente", true);

        // cerrar ventana de registro
        view.dispose();

        // cerrar la ventana de dialogo que esta bloqueando la aplicacion, y despues de eso se llama a iniciarJuegoCallback para iniciar el juego.
        if (iniciarJuegoCallback != null) {
            javax.swing.SwingUtilities.invokeLater(() -> iniciarJuegoCallback.accept(usuario));
        }
    }
}
