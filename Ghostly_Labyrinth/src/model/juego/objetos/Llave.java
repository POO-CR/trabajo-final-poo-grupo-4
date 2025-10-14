package model.juego.objetos;

import java.awt.Image;
import java.io.IOException;

import controller.juego.GameController;
import model.juego.entidades.Player;

import model.juego.objetos.efectos.PowerUpEffect;


public class Llave extends Objeto {

    GameController gc;
    //Image imagen;

    public Llave(GameController gc) {
        super(gc);
        this.gc = gc; // faltaba esta linea nomas
        setNombre("Llave");
        setColision(false);
        tilePermitida = 3;
        try{
            this.imagen = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/assets/img/objetos/llave.png"));
        }catch(IOException e){ // TODO: aca esto ponerlo en panelll porque carga la imagen!!!!!!! 
            e.printStackTrace();
        }
    }

    @Override
    public PowerUpEffect interactuar(Player jugador) {
        jugador.setTieneLlave(true);
        return null; // la llave no efectua un efecto temporal
    }



}
