package view.juego;

import java.awt.Toolkit;
import javax.swing.*;

import controller.juego.GameController;
import model.juego.KeyHandler;

import model.juego.laberinto.Nivel; //TODO: preg esto

//Clase Juego debe heredar los atributos y metodos de JPanel
public class JuegoView extends JFrame {

    //objetos para poder ser dibujados 
    /*Labyrinth laberinto;
    Player jugador;
    protected LightView light;
    protected Stopwatch stopwatch; //atributo para el Stopwatch
    protected Font fuenteJuego; //atributo para la fuente
    protected Message mensaje;
    protected Objeto obj[];
    protected JPanel panelJuego;

    // propiedades del panel
    int anchoPantalla;
    public int altoPantalla;*/

    public final JuegoPanel jp;

    // panel juego es la vista

    // juegoview => JFrame
    // juegoview llama a JuegoPanel => JPanel
    // JuegoPanel tiene @override paintComponent

    //public JuegoView (String tituloJuego, String usuario, Labyrinth lab, Player jugador, 
       //                 GameController controlador, Message mensaje, Objeto[] obj){
       public JuegoView (GameController controlador) {

        setTitle("Ghostly Labyrinth");
        //setSize(ancho, alto);

        setSize(Toolkit.getDefaultToolkit().getScreenSize()); // pantalla completa

        // POR AHORA ESTO NO PORQUE NO TENEMOS MENU PAUSAR, Y NO SE PUEDE SALIR
        //setUndecorated(true); // quita la barra de titulo y bordes
        //setExtendedState(JFrame.MAXIMIZED_BOTH); // ocupa toda la pantalla

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        jp = new JuegoPanel(controlador);
        add(jp);
        javax.swing.SwingUtilities.invokeLater(() -> {
            jp.requestFocusInWindow();
        });
        //this.addKeyListener(controlador); // esto lo ponia el profe, vamos a ver que hace
        setContentPane(jp);
       }

    public void pintarPanel () {
        //System.out.println("LLAMANDO A REPAINT");
        jp.repaint();
    }

    public void setNivel(Nivel nivel){
        this.jp.setNivel(nivel);
    }



    public void añadirKeyListener (KeyHandler kh){
        jp.addKeyListener(kh);
    }
        


}
