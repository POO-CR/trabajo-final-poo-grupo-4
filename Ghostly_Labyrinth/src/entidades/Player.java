package entidades;

import entidades.laberinto.Labyrinth;
import entidades.tile.Tile;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import controlador.GameController;
 
public class Player extends Entity {
    

    private GameController gc;
    private float escalaPersonaje = 1.5f;

    private final int anchoPersonaje;
    private final int altoPersonaje;

    //variable que indica si el jugador agarro la llave
    private boolean tieneLlave = false;

    public final int screenX; // DONDE SE VA A DIBUJAR PLAYER EN PANTALLA
    public final int screenY;

    private static final int vidaTope = 3;
    private int vida = vidaTope;


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


    public void cargarSprites(){
        try {
            up0 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/espalda_0.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/espalda_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/espalda_2.png"));
            down0 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/frente_0.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/frente_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/frente_2.png"));
            left0 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/izq_0.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/izq_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/izq_2.png"));
            right0 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/der_0.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/der_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/der_2.png"));
            
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    public BufferedImage getImagen() {
        BufferedImage imagen = null;
        try {switch(direccion) {
        case "up":
            if (spriteNum == 1){
                imagen = up1;
            }
            if (spriteNum == 2){
                imagen = up2;
            }
            break;
        case "down":
            if (spriteNum == 1){
                imagen = down1;
            }
            if (spriteNum == 2){
                imagen = down2;
            }
            break;
        case "left":
            if (spriteNum == 1){
                imagen = left1;
            }
            if (spriteNum == 2){
                imagen = left2;
            }
            break;
        case "right":
            if (spriteNum == 1){
                imagen = right1;
            }
            if (spriteNum == 2){
                imagen = right2;
            }   
            break;
        case "quieto":
            if (ultimaDireccion.equals("up")){
                imagen = up0;
            }
            else if (ultimaDireccion.equals("down")){
                imagen = down0;
            }
            else if (ultimaDireccion.equals("left")){
                imagen = left0;
            }
            else if (ultimaDireccion.equals("right")){
                imagen = right0;
            }
            else {
                imagen = down0;
            }
            break;

        default:
            imagen = down1;
            break;
            } return imagen;
            } catch (Exception e) {
            e.printStackTrace();
            return imagen;
            }
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
        if (tile instanceof entidades.tile.Puerta && tieneLlave) {
            entidades.tile.Puerta puerta = (entidades.tile.Puerta) tile;
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

}
