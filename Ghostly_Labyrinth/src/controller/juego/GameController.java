package controller.juego;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import java.util.List;
import java.util.Random;

import javax.swing.SwingWorker;
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


import model.observadores.SonidoGolpe;
import model.observadores.SonidoObjeto;
import model.observadores.SonidoPasos;

import view.juego.JuegoPausa;
import view.FramePrincipal;
import view.juego.CargaPanel;
import view.juego.JuegoPanel;
import model.dao.RecordDAO;
import model.dao.RecordDAOImpl;



//public class GameController implements Runnable{ (VIEJO)
public class GameController {

    private RecordDAO recordDAO;
    
    protected Player jugador;

    final private int FPS = 60;
    private Message mensaje;

    protected KeyHandler keyH;
    private AssetSetter aSetter;
    private ColisionChecker cChecker;
    private String tituloJuego;
    protected NivelManager nivelManager;
    private Nivel nivel;
    protected Boolean inicioJuego;

    private boolean juegoPausado = false; //para saber el estado del juego
    private List<Ghost> fantasmas = new ArrayList<>(); // lista de los fantasmas, se recibe del nivel

    private Timer gameTimer; // NUEVO

    private ArrayList<Objeto> obj;

    //Thread gameThread; (VIEJO)
    private Stopwatch stopwatch;

    //valores iniciales
    private final int originalTileSize = 20; // tamaño standard de tiles, dijimos de cambiarlo a 32x32
    
    private final int escala = 4; // para que se vea mas de cerca todo
    private final int tileSize = originalTileSize * escala; 

    //para ancho y alto de cada bloque 
    private final int anchoBloque = tileSize;
    private final int altoBloque = tileSize;

    int indiceprueba = 0;

    //protected JuegoView juegoView;

    private Boolean huboCambioNivel = false;
    private Boolean cambioLuz = false;
    private int aumentoLuz = 0;

    protected NivelWorker nivelWorker;

    private JuegoPanel juegoPanel;
    private FramePrincipal framePrincipal;
    private CargaPanel cargaPanel;
    private SonidoPasos sonidoPasos;

    public GameController(FramePrincipal framePrincipal) {
        System.out.println("se creo gamecontroller");

        this.recordDAO = new RecordDAOImpl();


        this.framePrincipal = framePrincipal;
        this.juegoPanel = framePrincipal.getJuegoPanel();
        this.jugador = new Player(this);
        juegoPanel.setGameController(this);
        this.cargaPanel = framePrincipal.getCargaPanel();

        this.nivelManager = new NivelManager();
        this.aSetter = new AssetSetter(this);

        this.tituloJuego = "Ghostly Labyrinth";

        // obtener tamaño pantalla

        

        sonidoPasos = new SonidoPasos();
        jugador.registrarObservador(sonidoPasos);
        SonidoGolpe sonidoGolpe = new SonidoGolpe();
        jugador.registrarObservador(sonidoGolpe);
        
        
        SonidoObjeto sonidoObjeto = new SonidoObjeto();
        jugador.registrarObservador(sonidoObjeto);

        ObservadorPerder observadorPerder = new ObservadorPerder(this);
        jugador.registrarObservador(observadorPerder);

        //this.fantasma = new Ghost(this, 100, 100);
        
        // seteamos objetos en el mapa
        this.obj = new ArrayList<>();

        this.mensaje = new Message(obj);
        
        this.cChecker = new ColisionChecker(this);

        this.keyH = new KeyHandler();
        framePrincipal.getJuegoPanel().getBtnPausa().addActionListener(e -> pausarJuego());

        
        // Esto se ejecuta cada segundo sin frenar el juego
        //if (juegoView != null) juegoView.pintarPanel();
        //});
    }

    public void cambiarNivel(){
        System.out.println("llamando a cambiarNivel");
        // detenerGameLoop(); //por alguna razón con el detener e iniciar va más rápido el personaje (capaz algo de las actualizaciones por segundo?)
        keyH.resetearTeclas();
        // para pasar la etiqueta de estado al panel
        // es un consumidor que le setea el status al panel según el paso de carga en el que 
        // está el worker
        this.nivelWorker = nivelManager.crearSiguienteNivelWorker(this);

        if (this.nivelWorker != null) {
            // le pasamos el worker para que actualice la pantalla de carga
            this.huboCambioNivel = true;
            System.out.println("poniendo panel de carga");
            cargaPanel.getProgressBar().setValue(0);

            framePrincipal.mostrarCarga();

            setupNivelWorker();

            System.out.println("ejecutando worker");
            nivelWorker.execute();

        }
        else {
            System.out.println("GANASTE !!! (acá iría la pantalla de ganar)"); //TODO: pantalla de ganar
            keyH.resetearTeclas();
            
            sonidoPasos.pararPasos();
            //se detiene el juego 
            detenerGameLoop();
            if (stopwatch != null) {
                stopwatch.detener();
            }

            framePrincipal.mostrarFinJuego(view.juego.PanelFinJuego.TipoFin.Victoria);
        }
    }

    public void actualizarEtiqueta(String etiqueta){
        cargaPanel.setStatus(etiqueta);
    }

    
    
    protected void setupNivelWorker () {
        //CargaPanel cargaPanel = juegoView.getPanelCarga();
        
        nivelWorker.addPropertyChangeListener(evt -> { // escuchamos cuando cambia la propiedad del worker
            switch (evt.getPropertyName()) {
                case "progress" -> {
                    int progress = (Integer) evt.getNewValue();
                    // Actualizamos la barra de progreso
                    cargaPanel.getProgressBar().setIndeterminate(false);
                    cargaPanel.getProgressBar().setValue(progress);
                    cargaPanel.repaint();
                }
                case "state" -> {
                    if (evt.getNewValue() == SwingWorker.StateValue.DONE) {
                        try {
                            nivel = nivelWorker.get();
                            terminarCompletarNivel();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } 
                    }
                }
            }
        });
    }
        
         
    


    // en un método separado pq se llama solo cuando terminó el worker
    private void terminarCompletarNivel(){
        aSetter.resetearPosicionesUsadas();
        obj.clear();
        setupJuego();
        juegoPanel.setNivel(nivel);
        

        
        fantasmas.clear(); // limpiar lista de fantasmas
        // crear fantasmas en las posiciones definidas en el nivel
        for (Point p : nivel.getPosicionesFantasma()) {
            fantasmas.add(new Ghost(this, p.x, p.y));
        }

        
        //this.juegoView.pintarPanel();

        jugador.setTieneLlave(false);
        jugador.setPosicionPersonaje(nivel.getPosDefaultX(), nivel.getPosDefaultY());

        if (inicioJuego) {
            inicioJuego = false;

            //juegoView.setVisible(true);
            System.out.println("poniendo panel juego");
            jugador.setScreenXY(framePrincipal.getWidth(), framePrincipal.getHeight());
            this.stopwatch = new Stopwatch(() -> juegoPanel.repaint());
            System.out.println("CREANDO STOPWATCH " + stopwatch.toString());
            iniciarGameLoop();
            juegoPanel.setRadioLuz(juegoPanel.getRadioDefault());
        }
        framePrincipal.mostrarJuego(keyH);
        stopwatch.iniciar();
        
    }


    public void actualizarMovimiento() {

        int nuevaX = jugador.getMundoX();
        int nuevaY = jugador.getMundoY();
        int movimiento = jugador.getMovimiento();
        
        if (keyH.upPressed == true || keyH.downPressed == true ||
            keyH.leftPressed == true || keyH.rightPressed == true){
            
            jugador.notificarObservadores("MOVIENDOSE");
        
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
            jugador.notificarObservadores("QUIETO"); 
        }

        jugador.setColisionOn(false);
        cChecker.checkearTile(jugador);

        Objeto objetoColisionado = cChecker.checkearObjeto(jugador);
        if (objetoColisionado != null){
            rtaColisionObjeto(objetoColisionado);
            mensaje.InteraccionDeMensajes(objetoColisionado);
        }

        if (jugador.getColisionOn() == false) {
            
            switch(jugador.getDireccion()) {
                case "up" -> nuevaY -= movimiento;
                case "down" -> nuevaY += movimiento;
                case "left" -> nuevaX -= movimiento;
                case "right" -> nuevaX += movimiento;
            }
            jugador.setMundoX(nuevaX);
            jugador.setMundoY(nuevaY);
        }

        jugador.setSpriteCounter(jugador.getSpriteCounter() + 1);
        if (jugador.getSpriteCounter() > 10) {
            if (jugador.getSpriteNum() == 1) {
                jugador.setSpriteNum(2);
            } else if (jugador.getSpriteNum() == 2) {
                jugador.setSpriteNum(1);
            }
            jugador.setSpriteCounter(0);
        }

    }


    private void setDireccionMovimiento(Ghost fantasma) {
        Random random = new Random();
        int i = random.nextInt(100) + 1; // genera numero entre 1 y 100

        if (i <= 25) {
            fantasma.setDireccion("up");
        } else if (i <= 50) {
            fantasma.setDireccion("down");
        } else if (i <= 75) {
            fantasma.setDireccion("left");
        } else {
            fantasma.setDireccion("right");
        }
    }

    // recibe parametro porque no tenemos 1 solo fantasma en el juego, tenemos una lista de fantasmas,
    // entonces tiene que recibir cual fantasma actualizar
    public void actualizarMovimientoFantasma(Ghost f) {

        int nuevaX = f.getMundoX();
        int nuevaY = f.getMundoY();
        int movimiento = f.getMovimiento();

        // decide que direccion tomar si ya no le quedan pasos en la direccion actual
        if (f.getPasosRestantes() <= 0) {
            setDireccionMovimiento(f);       
            f.setPasosRestantes(70);// mantiene direccion en 70 pasos (frames)
        }

        // revisa las colisiones
        f.setColisionOn(false);
        cChecker.checkearTile(f);

        // si choca, cambia de direccion 
        if (f.getColisionOn()) {
            setDireccionMovimiento(f);
            f.setPasosRestantes(70);
            return; // no sigue moviendo en este frame
        }

        // movimiento segun la direccion
        switch (f.getDireccion()) {
            case "up" -> nuevaY -= movimiento;
            case "down" -> nuevaY += movimiento;
            case "left" -> nuevaX -= movimiento;
            case "right" -> nuevaX += movimiento;
        }

        f.setMundoX(nuevaX);
        f.setMundoY(nuevaY);
        f.setPasosRestantes(f.getPasosRestantes() - 1);

        // animacion fantasmas
        f.setSpriteCounter(f.getSpriteCounter() + 1);
        if (f.getSpriteCounter() > 10) {
            if (f.getSpriteNum() == 1) {
                f.setSpriteNum(2);
            } else if (f.getSpriteNum() == 2) {
                f.setSpriteNum(1);
            }
            f.setSpriteCounter(0);
        }
    }   



    private void verificarColisionJugadorFantasma(Ghost fantasma) {
        // hitboxes actuales del jugador y el fantasma
        Rectangle hitJugador = new Rectangle(jugador.getMundoX() + jugador.getAreaColisionDefaultX(),
            jugador.getMundoY() + jugador.getAreaColisionDefaultY(),
            jugador.getAnchoColision(),
            jugador.getAltoColision()
        );

        Rectangle hitFantasma = new Rectangle(fantasma.getMundoX() + fantasma.getAreaColisionDefaultX(),
            fantasma.getMundoY() + fantasma.getAreaColisionDefaultY(),
            fantasma.getAnchoColision(),
            fantasma.getAltoColision()
        );

        // a ver si se intersectan 
        if (hitJugador.intersects(hitFantasma)) {
        // si se tocan, el jugador recibe daño
            System.out.println("te lastimo un fantasmaaaa");
            jugador.recibirDanio(1);
        }
    }



    public void rtaColisionObjeto (Objeto objeto) {
        PowerUpEffect efectoNuevo = objeto.interactuar(jugador); // interactuamos, y si el objeto tiene efecto, lo devuelve
        if (efectoNuevo != null) {
            efectoNuevo.aplicar(jugador);
            jugador.agregarEfecto(efectoNuevo); //agregamos el efecto al array de efectos activos del jugador
        }
        obj.remove(objeto); //sacamos el objeto del array de objetos del nivel
        jugador.notificarObservadores("AGARRAR_OBJETO");
    }

    
    public void iniciarGameLoop() {
        int delay = 1000 / FPS; // 16 ms aprox

        gameTimer = new Timer(delay, e -> {
            update();
            //juegoView.pintarPanel();
            juegoPanel.repaint();
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

    
    public void iniciarJuego(String usuario) {
        System.out.println("llamando a iniciar juego");
        this.inicioJuego = true;
        iniciarNivel();
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
            juegoPanel.setRadioLuz(juegoPanel.getRadioDefault() + aumentoLuz);
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
        procesarMovimiento();
        mensaje.updateMsj();

        for (Ghost f: fantasmas) {
            actualizarMovimientoFantasma(f);
            verificarColisionJugadorFantasma(f);
        }
    }

    public List<Ghost> getFantasmas() {
        return this.fantasmas;
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
        return jugador.getDireccion();
    }

    public String getUltimaDireccionJugador () {
        return jugador.getUltimaDireccion();
    }

    public int getSpriteNumJugador () {
        return jugador.getSpriteNum();
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

    public int vidaTope(){
        return jugador.getVidaTope();
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
        JuegoPausa pausa = new JuegoPausa(framePrincipal);
        

        // cuando se presiona continuar se reanuda el juego y se cierra el dialogo
        pausa.getbtnContinuar().addActionListener(e -> {
            pausa.dispose(); //cierra el dialogo
            reanudarJuego(); //llama a reanudar juego
            
        });

        // cuando se presiona reiniciar se cierra el dialogo y se reinicia el juego desde cero
        pausa.getBtnReiniciar().addActionListener(e -> {
        pausa.dispose(); // cierra el dialogo 
        reiniciarJuego();
        if (pausa != null) pausa.dispose(); // se reinicia todo el estado del juego
        });

        // cuando se presiona salir vuelve al menu principal
        pausa.getbtnSalir().addActionListener(e -> {pausa.dispose(); salirAlMenuPrincipal(); if (pausa != null) pausa.dispose();});

        // se muestra la ventana de pausa y se bloquea hasta que se cierre
        pausa.setVisible(true); 
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
                framePrincipal.añadirJuegoListener(keyH);
            }

            System.out.println("Juego reanudado");

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
            stopwatch.reiniciar();
            stopwatch.detener();
        }

        // Reiniciar nivel y objetos
        nivelManager.reiniciar(); // se vacia y vuelve a generar los niveles disponibles


        cambiarNivel();
        
        if (nivel == null) {
            System.out.println("Error: no hay niveles disponibles!");
            return;
        }
        huboCambioNivel = true; //indica que se cargo un nuevo nivel

        aSetter.resetearPosicionesUsadas();
        obj.clear();
        setupJuego();

        // Reiniciar jugador
        // jugador.setPosicionDefault(); //posicion inicial
        // jugador.setTieneLlave(false);//elimina cualquier llave obtenida 
        jugador.resetVida(); //restaura la vida 
        jugador.setDireccion("down"); //direccion inicial
        jugador.setUltimaDireccion("down");

        keyH.resetearTeclas();
        juegoPanel.addKeyListener(keyH); //se vuelve a escuchar el teclado

        // Reinicia loop y cronometro
        iniciarGameLoop();

        System.out.println("Juego reiniciado correctamente.");
    }


        // metodo que vuelve al menu principal desde el juego
        public void salirAlMenuPrincipal() {

        // se detiene el loop del juego y cronometro
        if (gameTimer != null) gameTimer.stop();
        if (stopwatch != null) stopwatch.detener();


        // se vuelve a abrir el menu principal
        framePrincipal.mostrarMenu();
    
}

    protected void procesarMovimiento(){ //Metodo generado para realizar un override en el tutorial y que el jugador no se mueva cuando queramos
        actualizarMovimiento();
    }

    protected void iniciarNivel() { //Como se debe cargar un nivel para el tutorial y el juego normal

        System.out.println("llamando a iniciar nivel");
        //carga la nueva ventana
        jugador.registrarObservador(juegoPanel);

        cambiarNivel();


    }

    
    public void mostrarDerrota(){ //Finaliza el juego y muestra la pantalla de derrota
        keyH.resetearTeclas();        
            sonidoPasos.pararPasos(); 
            detenerGameLoop();
            if (stopwatch != null) {
                stopwatch.detener();
            }
            framePrincipal.mostrarFinJuego(view.juego.PanelFinJuego.TipoFin.Derrota);
    }

    public int cantidadFantasmas() {
        return fantasmas.size();
    }

    public BufferedImage getSpriteFantasma(int i) { // obtiene el sprite del fantasma i
        Ghost f = fantasmas.get(i);
        return juegoPanel.getImagenFantasma(f.getDireccion(), f.getSpriteNum());
    }

    public int getScreenXFantasma(int i) { // obtiene la posicion en pantalla del fantasma i
        Ghost f = fantasmas.get(i);
        return f.getMundoX();
    }

    public int getScreenYFantasma(int i) { 
        Ghost f = fantasmas.get(i);
        return f.getMundoY();
    }

    public int getAnchoFantasma(int i) { // obtiene el ancho del fantasma i
        return fantasmas.get(i).getAnchoFantasma();
    }

    public int getAltoFantasma(int i) {
        return fantasmas.get(i).getAltoFantasma();
    }

    
}