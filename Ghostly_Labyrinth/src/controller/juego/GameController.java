package controller.juego;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit; //SE MODIFICO 01:25
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

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
    //private Ghost fantasma;

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

    private List<Ghost> fantasmas = new ArrayList<>(); // lista de los fantasmas, se recibe del nivel

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

    private final int screenW;
    private final int screenH;
    int indiceprueba = 0;

    private JuegoView juegoView;

    private Boolean huboCambioNivel = false;


    public GameController() {

        this.nivelManager = new NivelManager();
        this.aSetter = new AssetSetter(this);


        this.tm = new TileManager();

        this.tituloJuego = "Ghostly Labyrinth";

        // obtener tamaño pantalla
        screenW = Toolkit.getDefaultToolkit().getScreenSize().width;
        screenH = Toolkit.getDefaultToolkit().getScreenSize().height;

        this.jugador = new Player(this, screenW, screenH);

        //this.fantasma = new Ghost(this, 100, 100);
        
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
        // detenerGameLoop(); //por alguna razón con el detener e iniciar va más rápido el personaje (capaz algo de las actualizaciones por segundo?)
        System.out.println("CREANDO NIVEL " + indiceprueba);
        this.huboCambioNivel = true;
        this.siguienteNivel = nivelManager.crearSiguienteNivel();
        this.nivel = siguienteNivel;
        if (this.nivel != null) {
            indiceprueba++;
            jugador.setScreenXY(screenW, screenH);
            obj.clear();
            setupJuego();
            this.juegoView.setNivel(nivel);
            System.out.println("poniendo personaje en " + nivel.getPosDefaultX() + ", " + nivel.getPosDefaultY());

            
            fantasmas.clear(); // limpiar lista de fantasmas
            // crear fantasmas en las posiciones definidas en el nivel
            for (Point p : nivel.getPosicionesFantasma()) {
                fantasmas.add(new Ghost(this, p.x, p.y));
            }
            System.out.println("Fantasmas creados: " + fantasmas.size());
            for (Ghost g : fantasmas) {
                System.out.println(" - Fantasma en tiles (" + g.getMundoX() + ", " + g.getMundoY() + ")");
            }

            
            if (this.juegoView != null) {
            this.juegoView.pintarPanel();
            }
            // iniciarGameLoop();
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

    // recibe parametro porque no tenemos 1 solo fantasma en el juego, tenemos una lista de fantasmas, tonces tiene que recibir cual fantasma actualizar
    public void actualizarMovimientoFantasma(Ghost fantasma) {

        long ahora = System.currentTimeMillis(); // tiempo actual

        // esperar hasta el siguiente movimiento permitido
        if (ahora - fantasma.getTiempoUltimoMovimiento() < Ghost.getMovimientoIntervalo()) { // 80ms
          return;
        }

        int pasos = getAnchoBloque() / 8; // se movera en 8 por tile 

        // revisar colision antes de moverse
        fantasma.colisionOn = false;
        cChecker.checkearTile(fantasma);

        if (fantasma.colisionOn) {
            // si hay colison gira
            String[] dirs = {"up", "down", "left", "right"};
            String nuevaDir;
            do {
                nuevaDir = dirs[(int)(Math.random() * dirs.length)]; // elige una dir al azar
            } while (nuevaDir.equals(fantasma.getDireccion())); // evita repetir la misma direccion
            fantasma.setDireccion(nuevaDir);
        } 
        else {
            // mover solo si no hay colision
            switch (fantasma.getDireccion()) {
                case "up" -> fantasma.setMundoY(fantasma.getMundoY() - pasos);
                case "down" -> fantasma.setMundoY(fantasma.getMundoY() + pasos);
                case "left" -> fantasma.setMundoX(fantasma.getMundoX() - pasos);
                case "right" -> fantasma.setMundoX(fantasma.getMundoX() + pasos);
            }
        }

        // animacion
        fantasma.spriteCounter++;
        if (fantasma.spriteCounter > 10) {
            fantasma.spriteNum = (fantasma.spriteNum == 1) ? 2 : 1;
            fantasma.spriteCounter = 0;
        }

        fantasma.setTiempoUltimoMovimiento(ahora);
    }



    private void verificarColisionJugadorFantasma(Ghost fantasma) {
        // hitboxes actuales del jugador y el fantasma
        Rectangle hitJugador = new Rectangle(
            jugador.getMundoX() + jugador.areaColisionDefaultX,
            jugador.getMundoY() + jugador.areaColisionDefaultY,
            jugador.areaColision.width,
            jugador.areaColision.height
        );

        Rectangle hitFantasma = new Rectangle(
            fantasma.getMundoX() + fantasma.areaColisionDefaultX,
            fantasma.getMundoY() + fantasma.areaColisionDefaultY,
            fantasma.areaColision.width,
            fantasma.areaColision.height
        );

        // a ver si se intersectan 
        if (hitJugador.intersects(hitFantasma)) {
        // si se tocan, el jugador recibe daño
            System.out.println("te lastimo un fantasmaaaa");
            jugador.recibirDanio(1);
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
        if (huboCambioNivel) {
            huboCambioNivel = false;
            jugador.setPosicionPersonaje(nivel.getPosDefaultX(), nivel.getPosDefaultY());
        }
        actualizarMovimiento();
        mensaje.updateMsj();

        for (Ghost f: fantasmas) {
            actualizarMovimientoFantasma(f);
            verificarColisionJugadorFantasma(f);
        }
    }

    public List<Ghost> getFantasmas() {
        return this.fantasmas;
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
        return jugador.getMundoX() - jugador.getScreenX();
    }

    public int getCamY () {
        return jugador.getMundoY() - jugador.getScreenY();
    }

    //metodo para cronometro
    public Stopwatch getStopwatch() {
    return this.stopwatch;
    }
 


}
    