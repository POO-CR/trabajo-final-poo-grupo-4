package controlador;
import java.nio.Buffer;

import entidades.Player;
import juego.ColisionChecker;
import view.PlayerView;
import juego.KeyHandler;
import juego.Message;
import java.awt.image.BufferedImage;


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


    
}
