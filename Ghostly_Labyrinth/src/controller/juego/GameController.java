package controller.juego;
import java.awt.Toolkit; //SE MODIFICO 01:25
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;
import model.juego.AssetSetter;
import model.juego.ColisionChecker;
import model.juego.KeyHandler;
import model.juego.Message;
import model.juego.Stopwatch;
import model.juego.entidades.*;
import model.juego.laberinto.*;
import model.juego.objetos.*;
import model.juego.objetos.efectos.PowerUpEffect;
import model.juego.tile.TileManager;
import view.juego.JuegoPausa;
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

    private boolean juegoPausado = false; //para saber el estado del juego

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
    private Boolean cambioLuz = false;
    private int aumentoLuz = 0;


    public GameController() {

        this.nivelManager = new NivelManager();
        this.aSetter = new AssetSetter(this);


        this.tm = new TileManager();

        this.tituloJuego = "Ghostly Labyrinth";

        // obtener tamaño pantalla
        screenW = Toolkit.getDefaultToolkit().getScreenSize().width;
        screenH = Toolkit.getDefaultToolkit().getScreenSize().height;

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
        // detenerGameLoop(); //por alguna razón con el detener e iniciar va más rápido el personaje (capaz algo de las actualizaciones por segundo?)
        System.out.println("CREANDO NIVEL " + indiceprueba);
        this.huboCambioNivel = true;
        this.siguienteNivel = nivelManager.crearSiguienteNivel();
        this.nivel = siguienteNivel;
        if (this.nivel != null) {
            indiceprueba++;
            aSetter.resetearPosicionesUsadas();
            jugador.setTieneLlave(false);
            jugador.setScreenXY(screenW, screenH);
            obj.clear();
            setupJuego();
            this.juegoView.setNivel(nivel);
            System.out.println("poniendo personaje en " + nivel.getPosDefaultX() + ", " + nivel.getPosDefaultY());
            if (this.juegoView != null) {
            this.juegoView.pintarPanel();
            }
            // iniciarGameLoop();
        }
        else {
            System.out.println("GANASTE !!! (acá iría la pantalla de ganar)"); //TODO: pantalla de ganar
        }
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
        PowerUpEffect efectoNuevo = objeto.interactuar(jugador); // interactuamos, y si el objeto tiene efecto, lo devuelve
        if (efectoNuevo != null) {
            jugador.agregarEfecto(efectoNuevo); //agregamos el efecto al array de efectos activos del jugador
        }
        obj.remove(objeto); //sacamos el objeto del array de objetos del nivel
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

    private void reanudarGameLoop() {
        if (gameTimer != null) gameTimer.start();
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

        // Listener del boton de pausa en el panel
        juegoView.getJuegoPanel().getBtnPausa().addActionListener(e -> pausarJuego());
        
        iniciarGameLoop();
        stopwatch.iniciar();

        
    }

    public void cambiarLuz(int aumentoLuz){
        this.cambioLuz = true;
        this.aumentoLuz = aumentoLuz;
    }

    public void chequearPowerups(){
        ArrayList<PowerUpEffect> efectos = jugador.getEfectosActivos();
        Iterator<PowerUpEffect> iterator = efectos.iterator();
        while (iterator.hasNext()) {
            PowerUpEffect efecto = iterator.next();
            if (efecto.isCaducado()) {
                efecto.remover(jugador); //removemos el efecto del jugador
                iterator.remove(); //removemos el efecto del array de efectos activos
            }
        }
    }


    public void update(){
        
        if (huboCambioNivel) {
            huboCambioNivel = false;
            jugador.setTieneLlave(false);
            jugador.setPosicionPersonaje(nivel.getPosDefaultX(), nivel.getPosDefaultY());
        }

        if (cambioLuz){
            System.out.println("cambiando luz en " + aumentoLuz);
            cambioLuz = false;
            juegoView.setRadioLuz(juegoView.getRadioDefault() + aumentoLuz);
        }

        if (keyH.escPressed) { //si se presiona la tecla esc 
            keyH.escPressed = false; //se le asigna false para no repetir la accion varias veces seguidas 
            if (!juegoPausado) { //si el juego esta corriendo
                pausarJuego(); //se pausa
            }else{
                reanudarJuego(); //se reanuda
            }    
        }
        chequearPowerups();
        actualizarMovimiento();
        mensaje.updateMsj();
        System.out.println("se actualiza juego");

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

    public void pausarJuego() {
        juegoPausado = true;
        detenerGameLoop();
        if (stopwatch != null) stopwatch.detener();

        // Mostrar ventana de pausa
        mostrarDialogoPausa();
    }


    private void mostrarDialogoPausa() {
    JuegoPausa Pausa = new JuegoPausa(juegoView);
        

        // cuando se presiona continuar se reanuda el juego y se cierra el dialogo
        Pausa.getbtnContinuar().addActionListener(e -> {
            Pausa.dispose(); //cierra el dialogo
            reanudarJuego(); //llama a reanudar juego
            
        });

        // cuando se presiona reiniciar se cierra el dialogo y se reinicia el juego desde cero
        Pausa.getBtnReiniciar().addActionListener(e -> {
        Pausa.dispose(); // cierra el dialogo 
        reiniciarJuego(); // se reinicia todo el estado del juego
        });

        // cuando se presiona salir vuelve al menu principal
        Pausa.getbtnSalir().addActionListener(e -> salirAlMenuPrincipal());

        // se muestra la ventana de pausa y se bloquea hasta que se cierre
        Pausa.setVisible(true); 
    }

    // metodo que reanuda el juego tras haber estado pausado
    public void reanudarJuego() {
        if (juegoPausado) {  //solo reanuda si realmente estaba pausado
            juegoPausado = false; //cambia el estado a no pausado

            reanudarGameLoop();
            
            if (stopwatch != null) stopwatch.iniciar(); // reanuda el cronometro 


            //Cuando se muestra un JDialog modal (como JuegoPausa con super(parent, "Pausa", true)), Swing bloquea todos los eventos del hilo de la interfaz (EDT) hasta que el dialogo se cierra
            //aunque el Timer corre en el EDT también, cuando el dialogo modal se cierra, a veces el foco no vuelve al JPanel del juego, y entonces:
            //no detecta teclas (ni KeyHandler),
            //y en algunos casos no vuelve a repintar aunque se actualice internamente.
           

            // Recuperar foco del teclado y redibujar panel, esto no me dejaba que luego de tocar continuar en la pausa del boton del panel se repinte el panel pense que era problema del timer
            if (juegoView != null && juegoView.getJuegoPanel() != null) {
            juegoView.getJuegoPanel().requestFocusInWindow();
            juegoView.pintarPanel(); // fuerza repintado inmediato
            }

            System.out.println("Juego reanudado");


         }
    }

    //metodo que reinicia completamente el juego
    public void reiniciarJuego(){

    // se detiene game loop
    if (gameTimer != null) {
        gameTimer.stop();
        gameTimer = null;
    }   

     // se detiene y reinicia cronometro
    if (stopwatch != null) {
        stopwatch.detener();
        stopwatch.reiniciar();
    }

    // Reiniciar nivel y objetos
    nivelManager.reiniciar(); // se vacia y vuelve a generar los niveles disponibles
    nivel = nivelManager.crearSiguienteNivel(); // crea el nuevo nivel inicial
    if (nivel == null) {
        System.out.println("Error: no hay niveles disponibles!");
        return;
    }
    huboCambioNivel = true; //indica que se cargo un nuevo nivel

    obj.clear();
    setupJuego();

    // Reiniciar jugador
    jugador.setPosicionDefault(); //posicion inicial
    jugador.setTieneLlave(false);//elimina cualquier llave obtenida 
    jugador.resetVida(); //restaura la vida 
    jugador.direccion = "down"; //direccion inicial
    jugador.ultimaDireccion = "down";

    // Reiniciar vista 
    if (juegoView != null) {
        juegoView.dispose(); //se cierra la ventana actual
    }
    juegoView = new JuegoView(this); //se crea una nueva vista
    juegoView.setNivel(nivel); //se asigna el nivel actual
    juegoView.setVisible(true); //muestra la ventana
    juegoView.añadirKeyListener(keyH); //se vuelve a escuchar el teclado

    // Se conecta nuevamente el listener del boton pausa esto no dejaba abrir la pausa del boton del panel del juego porque solo estaba en iniciarjuego() 
    juegoView.getJuegoPanel().getBtnPausa().addActionListener(e -> pausarJuego());

    // Reinicia loop y cronometro
    iniciarGameLoop();
    stopwatch.iniciar();

    System.out.println("Juego reiniciado correctamente.");
}


    // metodo que vuelve al menu principal desde el juego
    public void salirAlMenuPrincipal() {

    // se detiene el loop del juego y cronometro
    if (gameTimer != null) gameTimer.stop();
    if (stopwatch != null) stopwatch.detener();

    // se cierra la ventana del juego si esta abierta
    if (juegoView != null) {
        juegoView.dispose();
    }

    // se vuelve a abrir el menu principal
    new controller.menu.MenuController();

}

 


}
    