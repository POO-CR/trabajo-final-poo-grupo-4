package model.juego.objetos;

import model.juego.objetos.efectos.PowerUpEffect;
import model.juego.entidades.Player;
import controller.juego.GameController;


public class AñadirVida extends Objeto{
    
    public AñadirVida (GameController gc) {
        super(gc);
        setNombre("Añadir Vida");
        setColision(false);
        tilePermitida = 5;
        try{
            this.imagen = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/assets/img/objetos/añadirVida.png"));
        }catch(Exception e){ 
            e.printStackTrace();
        }
    }

    @Override
    public PowerUpEffect interactuar (Player jugador) {
        if (jugador.getUltimaInteraccion() + 1000 < System.currentTimeMillis()){
            jugador.aumentarVida();
            jugador.setUltimaInteraccion(System.currentTimeMillis());
        }
        return null; // no es un efecto temporal
    }
    
}
