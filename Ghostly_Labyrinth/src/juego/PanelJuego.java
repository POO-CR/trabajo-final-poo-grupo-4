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


//Clase Juego debe heredar los atributos y metodos de JPanel
public class PanelJuego extends JPanel implements Runnable{

    //objetos para poder ser dibujados 
    Labyrinth laberinto;
    Player jugador;
    private LightView light;
    Message mensaje;
    private Stopwatch stopwatch; //atributo para el Stopwatch
    private Font fuenteJuego; //atributo para la fuente

    public ColisionChecker cChecker = new ColisionChecker(this);
    public AssetSetter aSetter;
    public Objeto obj[] = new Objeto[10]; // solo podemos tener 10 objetos en pantalla simultáneamente

    // propiedades del panel
    int anchoPantalla;
    public int altoPantalla;
    final int FPS = 60;
    GameController controlador;

    //KeyHandler keyH = new KeyHandler();
    Thread gameThread; //necesita runnable

    // panel juego es la vista



    //constructor de la clase juego 
    public PanelJuego(Labyrinth lab, Player jugador, GameController controlador){
        this.laberinto = lab;
        this.jugador = jugador;
        this.mensaje = new Message();
        this.aSetter = new AssetSetter(this);
        this.anchoPantalla = laberinto.anchoBloque * laberinto.cantidadColumnas; // tamaño en pixeles
        this.altoPantalla = laberinto.altoBloque * laberinto.cantidadFilas;
        this.controlador = controlador;
        //setPreferredSize(new Dimension(anchoPantalla, altoPantalla));
        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize()); // para que tome el tamaño de la pantalla nativa!! copado!!
        setBackground(Color.black);
        setDoubleBuffered(true); // guarda 2 buffer en memoria para que no carguen en tiempo real en pantalla
                                            // igual según internet no es necesario(?
        setFocusable(true); // el setfocusable es para que se "concentre" en los eventos del teclado
        
        this.light = new LightView(this.jugador);

        stopwatch = new Stopwatch(() -> repaint()); // cada tick redibuja
        stopwatch.iniciar();

        //para la fuente de todos los textos del juego
        try {
            fuenteJuego = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/assets/font/shloprg.otf"));
            fuenteJuego = fuenteJuego.deriveFont(Font.PLAIN, 28f); // estilo y tamaño
        } catch (Exception e) {
            e.printStackTrace();
            // en el caso de que falle se pone arial es para que no tire exception y se rompa 
            fuenteJuego = new Font("Arial", Font.PLAIN, 28);
}

    }
    public void mostrarMensaje (String texto, int milisegundos){
        mensaje.mostrar(texto,milisegundos);
    }

    public Labyrinth getLaberinto () {
        return laberinto;
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
        controlador.actualizarMovimiento();
        mensaje.update();
    }

    //metodo para poder dibujar en el JPanel, tanto el jugador laberinto fantasmas 
    //el override hace referencia a que es un metodo heredado de JPanel 
    @Override
    protected void paintComponent(Graphics g) {

    super.paintComponent(g);

    // calcular offset de la camara (segun la posicion del jugador)
    int camX = jugador.getMundoX() - jugador.screenX; 
    int camY = jugador.getMundoY() - jugador.screenY;

    Graphics2D g2 = (Graphics2D) g.create();
    g2.translate(-camX, -camY); // mueve todo lo que se dibuje segun la camara

    laberinto.paintComponent(g2);

    for (int i=0; i<obj.length; i++) {
        if (obj[i] != null) {
            obj[i].draw(g2);
        }
    }

    //jugador.draw(g2); // dibuja el jugador en el mundo, esto hace que funcione mal
    g2.dispose();
    
    jugador.draw((Graphics2D) g); // dibuja el jugador en pantalla (no en el mundo)

    if (light != null) {
        light.paint((Graphics2D) g, getWidth(), getHeight());
    }
    
    mensaje.draw((Graphics2D) g, getWidth(), getHeight());
    
    jugador.paintvida(g);

    //para dibujar el cronometro tanto el estilo como la posicion

    g.setFont(fuenteJuego); //usa la fuente cargada en el constructor de panel juego
    String textoTiempo = "Time: " + stopwatch.formatoTiempo(stopwatch.getSegundos()); //texto que se mostrara en pantalla

    FontMetrics metrics = g.getFontMetrics(fuenteJuego); //FontMetrics da informacion sobre la fuente y g.getFontMetrics le pide al objeto graphics que devuelva las metricas(ancho,alto,espaciado) de la fuente fuenteJuego 
    int anchoTexto = metrics.stringWidth(textoTiempo); //calcula el ancho de pixeles de la cadena textoTiempo, anchoTexto va a contener un entero con la cantidad de pixeles que ocupa en x textoTiempo

    int x = getWidth() - anchoTexto - 20; //se calcula y coloca la posicion x en el paneljuego (getWidth() da el ancho total del panel juego y se le resta el ancho de pixeles del texto y el -20 es un margen de separacion desde el borde derecho de la ventana para que no quede pegado al borde)
    int y = 40; //se coloca en la posicion y en el paneljuego

    g.setColor(Color.WHITE); //se setea el color del texto en blanco
    g.drawString(textoTiempo, x, y); //se dibuja el string textoTiempo en las posiciones x e y calculadas 
}


}
