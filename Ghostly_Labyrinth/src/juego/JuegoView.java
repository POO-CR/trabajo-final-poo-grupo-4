package juego;

import controlador.GameController;
import entidades.*;
import entidades.laberinto.Labyrinth;
import entidades.objetos.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import javax.swing.JPanel;
import view.LightView;
import juego.Stopwatch;
import java.awt.Font;
import java.awt.FontMetrics;
import javax.swing.*;


//Clase Juego debe heredar los atributos y metodos de JPanel
public class JuegoView extends JFrame {

    //objetos para poder ser dibujados 
    Labyrinth laberinto;
    Player jugador;
    protected LightView light;
    protected Stopwatch stopwatch; //atributo para el Stopwatch
    protected Font fuenteJuego; //atributo para la fuente
    protected Message mensaje;
    protected Objeto obj[];
    protected JPanel panelJuego;

    // propiedades del panel
    int anchoPantalla;
    public int altoPantalla;
    GameController controlador;

    // panel juego es la vista

    public JuegoView (String tituloJuego, String usuario, Labyrinth lab, Player jugador, 
                        GameController controlador, Message mensaje, Objeto[] obj){

        setTitle(tituloJuego + " - Jugador: " + usuario);
        //setSize(ancho, alto);

        setSize(Toolkit.getDefaultToolkit().getScreenSize()); // pantalla completa

        // POR AHORA ESTO NO PORQUE NO TENEMOS MENU PAUSAR, Y NO SE PUEDE SALIR
        //setUndecorated(true); // quita la barra de titulo y bordes
        //setExtendedState(JFrame.MAXIMIZED_BOTH); // ocupa toda la pantalla

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        // Crear jugador
        jugador.setNombre(usuario);

        this.laberinto = lab;
        this.jugador = jugador;
        this.mensaje = mensaje;
        this.light = new LightView(this.jugador);


        panelJuego.setFocusable(true);       //puede recibir foco
        panelJuego.requestFocusInWindow();   //pedir foco para teclado

        add(panelJuego);
        setVisible(true);
        setLocationRelativeTo(null);
        setVisible(true);

        

        stopwatch = new Stopwatch(() -> repaint());  //cada vez que el stopwatch incrementa el tiempo en 1 segundo se redibuja el panelJuego 
        stopwatch.iniciar();

        //para la fuente de todos los textos del juego
        try {
            fuenteJuego = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/assets/font/shloprg.otf"));
            fuenteJuego = fuenteJuego.deriveFont(Font.PLAIN, 28f); // estilo y tamaño
        } catch (Exception e) {
            e.printStackTrace();
            // en el caso de que falle se pone arial es para que no tire exception y se rompa 
            fuenteJuego = new Font("Arial", Font.PLAIN, 28);

            
        // se fuerza que este al frente y tenga foco
        toFront();
        requestFocus();
        setAlwaysOnTop(true);   // para que quede al frente
        setAlwaysOnTop(false);  // lo volvemos a normal

        // despues de hacer visible la ventana, asegurar foco en el panel
        javax.swing.SwingUtilities.invokeLater(() -> {
        panelJuego.requestFocusInWindow();
        });}

    }

    public void pintarPanel () {
        panelJuego.repaint();
    }


    public void mostrarMensaje (String texto, int milisegundos){
        mensaje.mostrar(texto,milisegundos);
    }

    public void añadirKeyListener (KeyHandler kh){
        panelJuego.addKeyListener(kh);
    }



}
