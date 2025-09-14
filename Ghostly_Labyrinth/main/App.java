package main;
import javax.swing.JFrame;
import entidades.Player;
import juego.PanelJuego;

import controlador.GameController;

public class App {


    public static void iniciarJuego(String usuario) {
    
        // cerrar todas las ventanas abiertas de Swing
        for (java.awt.Window window : java.awt.Window.getWindows()) {
            if (window.isShowing()) {
                window.dispose();
            }
        }
        
        
        JFrame ventana = new JFrame();

        int ancho = 800;
        int alto = 600;
        String tituloJuego = "Ghostly Labyrinth";

        ventana.setTitle(tituloJuego + " - Jugador: " + usuario);
        ventana.setSize(ancho, alto);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(true);

        // Crear jugador
        Player jugador = new Player();
        jugador.setNombre(usuario);

        PanelJuego juego = new PanelJuego();
        juego.setFocusable(true);       //puede recibir foco
        juego.requestFocusInWindow();   //pedir foco para teclado

        ventana.add(juego);
        ventana.setVisible(true);

            
        // se fuerza que este al frente y tenga foco
        ventana.toFront();
        ventana.requestFocus();
        ventana.setAlwaysOnTop(true);   // para que quede al frente
        ventana.setAlwaysOnTop(false);  // lo volvemos a normal

        // despues de hacer visible la ventana, asegurar foco en el panel
        javax.swing.SwingUtilities.invokeLater(() -> {
        juego.requestFocusInWindow();
        });

        // Controlador
        GameController controlador = new GameController();
        juego.iniciarGameThread();
    }
    
}