package juego;
import controlador.GameController;
import entidades.Player;
import entidades.laberinto.Labyrinth;

import javax.swing.JFrame;

public class IniciarJuego {
    public static void iniciarJuego(String usuario) {
        Labyrinth lab = new Labyrinth(0);
        JFrame ventana = new JFrame();

        int ancho = lab.getPixelWidth();
        int alto = lab.getPixelHeight();
        String tituloJuego = "Ghostly Labyrinth";

        ventana.setTitle(tituloJuego + " - Jugador: " + usuario);
        ventana.setSize(ancho, alto);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(true);

        // Crear jugador
        Player jugador = new Player(lab);
        jugador.setNombre(usuario);

        // Crear panel de juego
        PanelJuego juego = new PanelJuego(lab, jugador);
        ventana.add(juego);
        //ventana.pack();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);

        // Controlador
        juego.iniciarGameThread();
    }

    
}