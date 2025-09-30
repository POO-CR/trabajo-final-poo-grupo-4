package controlador;
import java.nio.Buffer;

import entidades.Player;
import juego.ColisionChecker;
import view.PlayerView;
import juego.KeyHandler;
import juego.Message;


public class PlayerController {

    private Player jugador;
    private Message mensaje;
    private PlayerView vistaJugador;
    private ColisionChecker cChecker;
    private KeyHandler keyH;
    private int anchoPersonaje;
    private int altoPersonaje;
    private int screenX;
    private int screenY;
    private BufferedImage imagen;

    public PlayerController(Player jugador) {
        this.jugador = jugador;
        this.anchoPersonaje = jugador.getAnchoPersonaje();
        this.altoPersonaje = jugador.getAltoPersonaje();
        this.screenX = jugador.getScreenX();
        this.screenY = jugador.getScreenY();
        this.imagen = jugador.getImagen();
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

        int indiceObj = cChecker.checkearObjeto(jugador, true);
        mensaje.InteraccionDeMensajes(indiceObj);

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
}
