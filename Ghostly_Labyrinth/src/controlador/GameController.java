package controlador;
import entidades.*;
import entidades.laberinto.*;
import entidades.objetos.*;
import java.awt.Toolkit; //SE MODIFICO 01:25
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import juego.*;
import view.LightView;


public class GameController implements Runnable{
    private Player jugador;

    final private int FPS = 60;
    private Message mensaje;

    private KeyHandler keyH;
    private AssetSetter aSetter;
    private ColisionChecker cChecker;
    private String tituloJuego;
    private NivelManager nm;
    private Nivel nivel;
    private LightView light; ////SE MODIFICO 01:25

    private ArrayList<Objeto> obj;

    Thread gameThread;
    private Stopwatch stopwatch;

    //valores iniciales
    private final int originalTileSize = 20; // tamaño standard de tiles, dijimos de cambiarlo a 32x32
    //final int escala = 2; // escala para agrandar los tiles
    private final int escala = 4; // para que se vea mas de cerca todo
    private final int tileSize = originalTileSize * escala; 

    //para ancho y alto de cada bloque 
    private final int anchoBloque = tileSize;
    private final int altoBloque = tileSize;

    private JuegoView juegoView;


    public GameController() {

        this.nm = new NivelManager();
        this.nivel = nm.getNivel(0);

        this.tituloJuego = "Ghostly Labyrinth";

        // obtener tamaño pantalla
        int screenW = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenH = Toolkit.getDefaultToolkit().getScreenSize().height;

        this.jugador = new Player(this, screenW, screenH);
        
        // seteamos objetos en el mapa
        this.obj = new ArrayList<>();
        this.aSetter = new AssetSetter(this, nivel);

        this.mensaje = new Message(obj);
        
        this.cChecker = new ColisionChecker(this, obj, nivel);

        this.keyH = new KeyHandler();

        this.juegoView = new JuegoView(this);
        juegoView.setVisible(true);

        this.light = new LightView(this); ////SE MODIFICO 01:25

        this.stopwatch = new Stopwatch(this);
    }


    public int getAnchoNivel () {
        return nivel.getAncho();
    }

    
    public int getAltoNivel () {
        return nivel.getAlto();
    }

    public BufferedImage getSprite (int fila, int columna) {
        return nivel.getTile(fila, columna).getSprite();
    }

    public int getAnchoBloque () {
        return this.anchoBloque;
    }

    public int getAltoBloque () {
        return this.altoBloque;
    }

    public Boolean getIsVisible() {
        return mensaje.getIsVisibleMsj();
    }

    public String getTexto() {
        return mensaje.getTextoMsj();
    }

    public void actualizarMovimiento() {

        int nuevaX = jugador.getMundoX();
        int nuevaY = jugador.getMundoY();
        int movimiento = jugador.getMovimiento();
        
        if (keyH.upPressed == true || keyH.downPressed == true ||
            keyH.leftPressed == true || keyH.rightPressed == true){
        
            if (keyH.upPressed == true){
            jugador.setDireccion("up");
            jugador.setUltimaDireccion("up");
            }
            else if (keyH.downPressed == true){
            jugador.setDireccion("down");
            jugador.setUltimaDireccion("down");
            }
            else if (keyH.leftPressed == true){
            jugador.setDireccion("left");
            jugador.setUltimaDireccion("left");
            }
            else if (keyH.rightPressed == true){
            jugador.setDireccion("right");
            jugador.setUltimaDireccion("right");
            }
        }
        
        else {
            jugador.setDireccion("quieto");
        }

        jugador.colisionOn = false;
        cChecker.checkearTile(jugador);

        Objeto objetoColisionado = cChecker.checkearObjeto(jugador);
        if (objetoColisionado != null){
            rtaColisionObjeto(objetoColisionado);
            mensaje.InteraccionDeMensajes(objetoColisionado);
        }

        if (jugador.colisionOn == false) {
            
            switch(jugador.getDireccion()) {
                case "up" -> nuevaY -= movimiento;
                case "down" -> nuevaY += movimiento;
                case "left" -> nuevaX -= movimiento;
                case "right" -> nuevaX += movimiento;
            }
            jugador.setMundoX(nuevaX);
            jugador.setMundoY(nuevaY);
        }

        jugador.spriteCounter++;
        if (jugador.spriteCounter > 10) {
            if (jugador.spriteNum == 1) {
                jugador.spriteNum = 2;
            } else if (jugador.spriteNum == 2) {
                jugador.spriteNum = 1;
            }
            jugador.spriteCounter = 0;
        }

    }

    public void rtaColisionObjeto (Objeto objeto) {
        objeto.interaccionJugador();
        obj.remove(objeto);

    }


    public void iniciarJuego (String usuario) {
        // cerrar todas las ventanas abiertas de Swing
        for (java.awt.Window window : java.awt.Window.getWindows()) {
            if (window.isShowing()) {
                window.dispose();
            }
        }
        
        //this.juegoView = new JuegoView();

        //int ancho = lab.getPixelWidth();
        //int alto = lab.getPixelHeight();
        // YA NO SE USA ESTO, AHORA ES PANTALLA COMPLETA, NO SE TIENE EN CUENTA LAS MEDIDAS DEL LABERINTO

        // Controlador
        actualizarMovimiento();
        juegoView.añadirKeyListener(keyH);


        // juego
        setupJuego(); //tiene que ir antes del juego

        iniciarGameThread(); 
        stopwatch.iniciar(); //inicia el cronometro
    
    }

    
    public void setObjeto (Objeto objeto) {
        this.obj.add(objeto);
    }

    public ArrayList<Objeto> getObjArray () {
        return this.obj;
    }

    public void setupJuego () {
        aSetter.setearObjetosIniciales();
    }

    public String getDireccionJugador () {
        return jugador.direccion;
    }

    public String getUltimaDireccionJugador () {
        return jugador.ultimaDireccion;
    }

    public int getSpriteNumJugador () {
        return jugador.spriteNum;
    }

    public int getScreenXJugador () {
        return jugador.getScreenX();
    }

    public int getScreenYJugador () {
        return jugador.getScreenY();
    }

    public int getMundoXJugador () {
        return jugador.getMundoX();
    }

    public int getMundoYJugador () {
        return jugador.getMundoY();
    }

    public int getAnchoJugador () {
        return jugador.getAnchoPersonaje();
    }

    public int getAltoJugador () {
        return jugador.getAltoPersonaje();
    }

    public LightView getLight() {
        return light;
    } //SE MODIFICO 01:25
    
    public void mostrarMensaje(String texto, int milisegundos){ //SE MODIFICO 01:25
        mensaje.mostrar(texto, milisegundos);
    } //SE MODIFICO 01:25

    //public Message getMensaje(){
      //  return this.mensaje; //SE MODIFICO 01:25
    //} porque no lo  aaa si


    public Player getJugador(){
    return this.jugador;
    } //SE MODIFICO 01:25

    public Stopwatch getStopwatch(){
        return this.stopwatch;
    } //SE MODIFICO 01:25

    public int vidaActual(){
        return jugador.getVida();
    }

    public int getCamX () {
        return getMundoXJugador() - getScreenXJugador();
    }

    public int getCamY () {
        return getMundoYJugador() - getScreenYJugador();
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
        mensaje.updateMsj();
    } 

// ecah ol atohc ednod es on ward le

}
    