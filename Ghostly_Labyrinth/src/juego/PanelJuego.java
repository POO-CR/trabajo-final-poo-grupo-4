package juego;

import controlador.GameController;
import entidades.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;


//Clase Juego debe heredar los atributos y metodos de JPanel
public class PanelJuego extends JPanel implements Runnable{

    //objetos para poder ser dibujados 
    Labyrinth laberinto = new Labyrinth();
    Player jugador = new Player();

    // propiedades del panel
    final int originalTileSize = 16; // tamaño standard de tiles, dijimos de cambiarlo a 32x32
    final int escala = 3; // escala para agrandar los tiles
    final int tileSize = originalTileSize * escala; 

    final int maxColPantalla = 16; // 16 columnas de tiles en pantalla
    final int maxFilPantalla = 12; // 12 filas de tiles en pantalla

    final int anchoPantalla = tileSize * maxColPantalla; // tamaño en pixeles
    final int altoPantalla = tileSize * maxFilPantalla;

    final int FPS = 60;

    //KeyHandler keyH = new KeyHandler();
    Thread gameThread; //necesita runnable
    GameController controlador = new GameController();



    //constructor de la clase juego 
    public PanelJuego(){
        setPreferredSize(new Dimension(anchoPantalla, altoPantalla));
        // this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize()); // para que tome el tamaño de la pantalla nativa!! copado!!
        setBackground(Color.black);
        setDoubleBuffered(true); // guarda 2 buffer en memoria para que no carguen en tiempo real en pantalla
                                            // igual según internet no es necesario(?
        controlador.añadirKeyListener(this);
        setFocusable(true); // el setfocusable es para que se "concentre" en los eventos del teclado

    }


    public void iniciarGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    // método necesario para runnable, el gameloop
    @Override
    public void run() {

        double intervaloRefresco = 1000000000/FPS; // nos da los nanosegundos que tiene que tener cada frame
        double siguienteRefresco = System.nanoTime() + intervaloRefresco; // refrescamos cada esa cantidad de nanosegundos

        while (gameThread != null) {
            update();
            repaint();

            try {
                double tiempoFaltante = siguienteRefresco - System.nanoTime(); // cuanto falta para el sig refresco
                tiempoFaltante = tiempoFaltante/1000000; // en milis
                if (tiempoFaltante < 0) {
                    tiempoFaltante = 0;
                }
                Thread.sleep((long) tiempoFaltante);
                siguienteRefresco += intervaloRefresco;
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(){
        controlador.actualizarMovimiento(jugador);
    }

    //metodo para poder dibujar en el JPanel, tanto el jugador laberinto fantasmas 
    //el override hace referencia a que es un metodo heredado de JPanel 
    @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    // tamaño del tablero en píxeles
    int boardW = laberinto.getPixelWidth();
    int boardH = laberinto.getPixelHeight();

    // offset para centrar dentro del JPanel actual
    int offX = (getWidth()  - boardW) / 2;
    int offY = (getHeight() - boardH) / 2;

    // dibujar todo trasladado
    Graphics2D g2 = (Graphics2D)g.create();
    g2.translate(offX, offY);

    laberinto.paintComponent(g2);
    jugador.draw(g2);

    g2.dispose();
}


}
