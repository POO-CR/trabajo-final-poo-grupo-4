package main;
import javax.swing.JFrame;
import entidades.Player;

import controlador.GameController;

public class App {
    public static void iniciarJuego(String usuario) {
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

        // Crear panel de juego
        PanelJuego juego = new PanelJuego();
        ventana.add(juego);
        ventana.setVisible(true);

        // Controlador
        GameController controlador = new GameController();
        juego.iniciarGameThread();
    }

    // main solo para testear
    
}