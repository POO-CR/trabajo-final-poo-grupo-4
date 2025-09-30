package controlador;
import entidades.*;
import javax.swing.JFrame;
import juego.*;
import entidades.objetos.*;
import entidades.laberinto.*r;

import java.awt.Toolkit;


public class GameController implements Runnable{

    private Labyrinth lab;
    private JuegoView juegoView;
    private Player jugador;

    final private int FPS = 60;
    private Message mensaje;

    private KeyHandler keyH;
    private AssetSetter aSetter;
    private ColisionChecker cChecker;
    private String tituloJuego;
    private NivelManager nm;
    private Nivel nivel;

    private Objeto obj[];

    Thread gameThread;
    private Stopwatch stopwatch;

    public GameController() {

        this.nm = new NivelManager();
        this.nivel = nm.getNivel(0);
        this.lab = new Labyrinth(nivel);
        this.tituloJuego = "Ghostly Labyrinth";

        // obtener tamaño pantalla
        int screenW = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenH = Toolkit.getDefaultToolkit().getScreenSize().height;


        this.mensaje = new Message();
        this.jugador = new Player(lab, screenW, screenH);
        this.obj = new Objeto[10];
        this.aSetter = new AssetSetter(this, lab);
        this.cChecker = new ColisionChecker(lab, obj);

        this.keyH = new KeyHandler();
    }

    public void iniciarJuego (String usuario) {
        // cerrar todas las ventanas abiertas de Swing
        for (java.awt.Window window : java.awt.Window.getWindows()) {
            if (window.isShowing()) {
                window.dispose();
            }
        }
        
        this.juegoView = new JuegoView(tituloJuego, usuario, lab, jugador, this, mensaje, obj);

        //int ancho = lab.getPixelWidth();
        //int alto = lab.getPixelHeight();
        // YA NO SE USA ESTO, AHORA ES PANTALLA COMPLETA, NO SE TIENE EN CUENTA LAS MEDIDAS DEL LABERINTO
        
        

        // Controlador
        actualizarMovimiento();
        juegoView.añadirKeyListener(keyH);


        // juego
        setupJuego(); //tiene que ir antes del juego

        stopwatch.iniciar(); //inicia el cronometro
    
    }

    
    public void setObjeto (int indice, Objeto objeto) {
        this.obj[indice] = objeto;
    }

    public void setupJuego () {
        aSetter.setearObjetosIniciales();
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
            juegoView.pintarPanel();
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
        actualizarMovimiento();
        mensaje.update();
    }

}
    