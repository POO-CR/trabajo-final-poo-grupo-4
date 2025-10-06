package model.juego.entidades;

import java.awt.Rectangle;

import controller.juego.GameController;
import model.juego.tile.Tile;
 
public class Player extends Entity {
    

    private final GameController gc;
    private final float escalaPersonaje = 1.5f;

    private final int anchoPersonaje;
    private final int altoPersonaje;

    //variable que indica si el jugador agarro la llave
    private boolean tieneLlave = false;

    public final int screenX; // DONDE SE VA A DIBUJAR PLAYER EN PANTALLA
    public final int screenY;

     //Caracteristicas de la vida
    private final int vidaTope = 3;
    private int vida = vidaTope;
    //Caracteristicas al recibir danio
    //TODO: esto y vidaTope probablemente debería estar en otro lado
    private final long tiempoInmune = 2000;
    private long finInmune=0;


    // sprites del jugador
    // las tiles son de 20x2=40 
    // el personaje está escalado 1.5
    // x = 4x2x1.5 = 12
    // y = 10x2x1.5 = 30
    // ancho = 11x2x1.5 = 33
    // alto = 7x2x1.5 = 21

    public Player (GameController gc, int screenAncho, int screenAlto) {

        super();
        this.gc = gc;
        setValoresDefault();

        anchoPersonaje = (int)(gc.getAnchoBloque()*escalaPersonaje);
        altoPersonaje = (int)(gc.getAltoBloque()*escalaPersonaje);

        screenX = screenAncho / 2 - (anchoPersonaje / 2); //centrado en pantalla
        screenY = screenAlto / 2 - (altoPersonaje / 2); //centrado en pantalla

        int colisionW = (int)(anchoPersonaje * 0.5); // ancho del area de colisión (50% del sprite)
        int colisionH = (int)(altoPersonaje * 0.3);  // alto del area de colisión
        int colisionX = (anchoPersonaje - colisionW) / 2; // centrado
        int colisionY = (altoPersonaje - colisionH) / 2 + (int)(altoPersonaje * 0.29); // un poco mas abajo del centro, para los pies

        areaColision = new Rectangle(colisionX, colisionY, colisionW, colisionH);

        areaColisionDefaultX = colisionX;
        areaColisionDefaultY = colisionY; // con estos cambios, player genera una hitbox proporcional a su sprite, para el tema de cambio de tamaño de tiles

        // lo anterior, daba problemas con la colision del player con las paredes
        //areaColision = new Rectangle(10, 30 , 28, 23);
        //areaColisionDefaultX = areaColision.x;
        //areaColisionDefaultY = areaColision.y;

    }


    public void setValoresDefault () {
        // posicion del player en el mundo (NO POSICION EN PANTALLA)
        // ES EL PUNTO DE PARTIDA EN LA MATRIZ DEL LABERINTO
        mundoX = 3 * gc.getAnchoBloque(); // 3 tiles desde la izquierda
        mundoY = 3 *gc.getAltoBloque();  // 3 tiles desde arriba

        movimiento = 6;
        direccion = "down";
        ultimaDireccion = "down";
    }


    public boolean getTieneLlave(){
        return tieneLlave;
    }

    public void setTieneLlave(boolean tieneLlave){
        this.tieneLlave = tieneLlave;
    }

    public void setDireccion(String direccion){
        this.direccion = direccion;
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

    @Override
    public void tieneAcceso (Tile tile) {
        if (tile instanceof model.juego.tile.Puerta && tieneLlave) {
            model.juego.tile.Puerta puerta = (model.juego.tile.Puerta) tile;
            puerta.abrir(); 
        }
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

    public void recibirDanio(int danio){
        long ahora = System.currentTimeMillis();
        if(ahora < finInmune) 
        return;
    if (vida > 0){
            vida--;
            finInmune = ahora + tiempoInmune;
        }
        if (vida == 0){
            System.out.println("GAME OVER");
        }}

}
