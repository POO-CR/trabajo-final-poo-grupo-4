package controller.juego;
import java.awt.Toolkit; //SE MODIFICO 01:25
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.Timer;
import model.juego.AssetSetter;
import model.juego.ColisionChecker;
import model.juego.KeyHandler;
import model.juego.Message;
import model.juego.Stopwatch;
import model.juego.entidades.*;
import model.juego.laberinto.*;
import model.juego.objetos.*;
import model.juego.tile.TileManager;
import view.juego.JuegoView;

//public class GameController implements Runnable{ (VIEJO)
public class GameController {
    private Player jugador;

    final private int FPS = 60;
    private Message mensaje;

    private KeyHandler keyH;
    private AssetSetter aSetter;
    private ColisionChecker cChecker;
    private String tituloJuego;
    private NivelManager nivelManager;
    private Nivel nivel;
    private Nivel siguienteNivel;
    private TileManager tm;


    private Timer gameTimer; // NUEVO

    private ArrayList<Objeto> obj;

    //Thread gameThread; (VIEJO)
    private Stopwatch stopwatch;

    //valores iniciales
    private final int originalTileSize = 20; // tamaño standard de tiles, dijimos de cambiarlo a 32x32
    //final int escala = 2; // escala para agrandar los tiles
    private final int escala = 4; // para que se vea mas de cerca todo
    private final int tileSize = originalTileSize * escala; 

    //para ancho y alto de cada bloque 
    private final int anchoBloque = tileSize;
    private final int altoBloque = tileSize;
    int indiceprueba = 0;

    private JuegoView juegoView;


    public GameController() {

        this.nivelManager = new NivelManager();
        this.aSetter = new AssetSetter(this);


        this.tm = new TileManager();

        this.tituloJuego = "Ghostly Labyrinth";

        // obtener tamaño pantalla
        int screenW = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenH = Toolkit.getDefaultToolkit().getScreenSize().height;

        this.jugador = new Player(this, screenW, screenH);
        
        // seteamos objetos en el mapa
        this.obj = new ArrayList<>();

        this.mensaje = new Message(obj);
        
        this.cChecker = new ColisionChecker(this);

        this.keyH = new KeyHandler();

        this.stopwatch = new Stopwatch(() -> {
        // Esto se ejecuta cada segundo sin frenar el juego
        if (juegoView != null) juegoView.pintarPanel();
        });
    }

    public void cambiarNivel(){
        System.out.println("CREANDO NIVEL " + indiceprueba);
        this.siguienteNivel = nivelManager.crearSiguienteNivel();
        this.nivel = siguienteNivel;
        if (this.nivel != null) {
            indiceprueba++;
            obj.clear();
            setupJuego();
            this.juegoView.setNivel(nivel);
            System.out.println("poniendo personaje en " + nivel.getPosDefaultX() + ", " + nivel.getPosDefaultY());
            this.jugador.setPosicionPersonaje(nivel.getPosDefaultX(), nivel.getPosDefaultY());
            System.out.println("personaje en " + jugador.getMundoX() + ", " + jugador.getMundoY());
            if (this.juegoView != null) {
            this.juegoView.pintarPanel(); 
        }
        }
        else {
            System.out.println("GANASTE !!! (acá iría la pantalla de ganar)"); //TODO: pantalla de ganar
        }
        // jugador.setMundoX(0);
        // jugador.setMundoY(0);
        // jugador.setTieneLlave(false);
        // cChecker = new ColisionChecker(this, obj, nivel);
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

    
    public void iniciarGameLoop() {
        int delay = 1000 / FPS; // 16 ms aprox

        gameTimer = new Timer(delay, e -> {
            update();
            juegoView.pintarPanel();
        });

        gameTimer.start();
    }

    public void detenerGameLoop() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
    }

    // NUEVO
    public void iniciarJuego(String usuario) {
        for (java.awt.Window window : java.awt.Window.getWindows()) {
            if (window.isShowing()) window.dispose();
        }

        this.juegoView = new JuegoView(this);
        cambiarNivel();
        juegoView.setVisible(true);
        juegoView.añadirKeyListener(keyH);

        iniciarGameLoop();
        stopwatch.iniciar();
    }


    public void update(){
        actualizarMovimiento();
        mensaje.updateMsj();
    }

    public int getTileEspecial(){
        return tm.getTileESpecial();
    }

    public int getAnchoNivel () {
        return nivel.getAncho();
    }

    
    public int getAltoNivel () {
        return nivel.getAlto();
    }

    public BufferedImage getSprite (int fila, int columna) {
        return nivel.getTile(columna, fila).getSprite();
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

    public Nivel getNivel () {
        return this.nivel;
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

    
    public void mostrarMensaje(String texto, int milisegundos){ 
        mensaje.mostrar(texto, milisegundos);
    } 


    public Player getJugador(){
    return this.jugador;
    }

    public int vidaActual(){
        return jugador.getVida();
    }

    public int getCamX () {
        return getMundoXJugador() - getScreenXJugador();
    }

    public int getCamY () {
        return getMundoYJugador() - getScreenYJugador();
    }

    //metodo para cronometro
    public Stopwatch getStopwatch() {
    return this.stopwatch;
    }
 


}
    