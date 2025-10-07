package model.juego.objetos;

import java.awt.Image;
import java.io.IOException;

import controller.juego.GameController;

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

    public void interaccionJugador () {
        gc.getJugador().setTieneLlave(true);
    }



}
