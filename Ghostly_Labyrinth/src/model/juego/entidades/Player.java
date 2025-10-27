package model.juego.entidades;

import java.awt.Rectangle;

import controller.juego.GameController;
import model.juego.tile.Tile;
import model.observadores.Observador;
import model.observadores.Sujeto;
import model.juego.objetos.efectos.PowerUpEffect;
import java.util.ArrayList;
import java.util.List;
 
public class Player extends Entity implements Sujeto {

    private final List<Observador> observadores = new ArrayList<>();

    private final GameController gc;
    private final float escalaPersonaje = 1.5f;

    private final int anchoPersonaje;
    private final int altoPersonaje;

    //variable que indica si el jugador agarro la llave
    private boolean tieneLlave = false;

    public int screenX; // DONDE SE VA A DIBUJAR PLAYER EN PANTALLA
    public int screenY;

     //Caracteristicas de la vida
    private final int vidaTope = 3;
    private int vida = vidaTope;
    //Caracteristicas al recibir danio
    //TODO: esto y vidaTope probablemente debería estar en otro lado
    private final long tiempoInmune = 2000;
    private long finInmune=0;

    public float multiplicadorVelocidad;

    private long ultimaInteraccion;

    private ArrayList<PowerUpEffect> efectosActivos;


    // sprites del jugador
    // las tiles son de 20x2=40 
    // el personaje está escalado 1.5
    // x = 4x2x1.5 = 12
    // y = 10x2x1.5 = 30
    // ancho = 11x2x1.5 = 33
    // alto = 7x2x1.5 = 21

    public Player (GameController gc) {

        super();
        this.gc = gc;
        setValoresDefault();

        anchoPersonaje = (int)(gc.getAnchoBloque()*escalaPersonaje);
        altoPersonaje = (int)(gc.getAltoBloque()*escalaPersonaje);

        int colisionW = (int)(anchoPersonaje * 0.4); // ancho del area de colisión (40% del sprite)
        int colisionH = (int)(altoPersonaje * 0.3);  // alto del area de colisión
        int colisionX = (anchoPersonaje - colisionW) / 2; // centrado
        int colisionY = (altoPersonaje - colisionH) / 2 + (int)(altoPersonaje * 0.29); // un poco mas abajo del centro, para los pies

        areaColision = new Rectangle(colisionX, colisionY, colisionW, colisionH);

        areaColisionDefaultX = colisionX;
        areaColisionDefaultY = colisionY; // con estos cambios, player genera una hitbox proporcional a su sprite, para el tema de cambio de tamaño de tiles

        efectosActivos = new ArrayList<>();
        ultimaInteraccion = System.currentTimeMillis();

    }

    public void setUltimaInteraccion(long ultimaInteraccion){
        this.ultimaInteraccion = ultimaInteraccion;
    }

    public long getUltimaInteraccion(){
        return this.ultimaInteraccion;
    }

    public void agregarEfecto(PowerUpEffect efecto) {
        for (PowerUpEffect efectosactuales : efectosActivos)
        if (efectosactuales.getClass().equals(efecto.getClass())){
            efectosactuales.reiniciarPoder();
            return;
        }
        efectosActivos.add(efecto);
    }

    public void removerEfecto(PowerUpEffect efecto) {
        efectosActivos.remove(efecto);
    }

    public ArrayList<PowerUpEffect> getEfectosActivos() {
        return efectosActivos;
    }

    public void setValoresDefault () {
        //setScreenXY(screenAncho, screenAlto);
        setMovimiento(10); // en pixeles
        direccion = "down";
        ultimaDireccion = "down";
    }

    public void setScreenXY (int screenAncho, int screenAlto){
        screenX = screenAncho / 2 - (anchoPersonaje / 2) ; //centrado en pantalla
        screenY = screenAlto / 2 - (altoPersonaje / 2) ; //centrado en pantalla
    }

    public void setPosicionDefault(){
        super.setMundoX(3 * gc.getAnchoBloque()); 
        super.setMundoY(3 *gc.getAltoBloque());  // 3 tiles desde arriba
    }

    public void setPosicionPersonaje(int x, int y){
        super.setMundoX(x * gc.getAnchoBloque() -10); // 3 tiles desde la izquierda. menos dos pixeles pq spawneaba medio corrido
        super.setMundoY(y *gc.getAltoBloque() +5);
    }


    @Override
    public boolean getTieneLlave(){
        return tieneLlave;
    }

    public void setTieneLlave(boolean tieneLlave){
        this.tieneLlave = tieneLlave;
    }

    public void setDireccion(String direccion){
        this.direccion = direccion;
    }


    public void recibirDanio(int danio){
        long ahora = System.currentTimeMillis();
        if(ahora < finInmune) 
        return;
        if (vida > 0){
            vida--;
            finInmune = ahora + tiempoInmune;
            notificarObservadores("GOLPE");
        }
        if (vida == 0){
            System.out.println("GAME OVER");
        }}

    public void aumentarVida(){
        vida++;
    }

    public void resetVida() {
        vida = vidaTope;
        finInmune = 0;
    } //para reiniciar partida

    public void cambiarLuz(int nuevoRadioLuz){
        gc.cambiarLuz(nuevoRadioLuz);
    }

    @Override
    public void registrarObservador(Observador o) {
        observadores.add(o);
    }

    @Override
    public void removerObservador(Observador o) {
        observadores.remove(o);
    }

    @Override
    public void notificarObservadores(String evento) {
        for (Observador o : observadores) {
            o.actualizar(evento);
        }
    }

        public String getDireccion(){
        return direccion;
    }

    public int getVida() {
        return vida;
    }

    public int getVidaTope() {
        return vidaTope;
    }

    public boolean estaVivo(){
        return vida > 0;
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    //para el LightView
    public int getCentroX() {
        return mundoX + anchoPersonaje / 2;
    }

    public int getCentroY() {
        return mundoY + altoPersonaje / 2;
    }

    public int getAnchoPersonaje() {
    return anchoPersonaje;
}

    public int getAltoPersonaje() {
        return altoPersonaje;
    }

    public boolean esInmune(){
        return System.currentTimeMillis() < finInmune;
    }

}
