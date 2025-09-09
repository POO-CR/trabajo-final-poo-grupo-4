package main;
import javax.swing.JFrame;

import controlador.GameController;

public class App {
    public static void main(String[] args) {
        JFrame ventana = new JFrame();

        int ancho = 800; // esto habría que moverlo a otro lado
        int alto = 600;

        String tituloJuego = "Ghostly Labyrinth";


        ventana.setTitle(tituloJuego);
        ventana.setSize(ancho,alto);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(true);

        PanelJuego juego = new PanelJuego();
        // le añadimos a la ventana el panel del juego
        ventana.add(juego);
        ventana.setVisible(true);

        GameController controlador = new GameController();
        juego.iniciarGameThread();

        
        
        

    }
}