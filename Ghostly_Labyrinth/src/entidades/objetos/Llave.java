package entidades.objetos;

import java.io.IOException;

import controlador.GameController;

public class Llave extends Objeto {

    GameController gc;

    public Llave(GameController gc) {
        super(gc);
        nombre = "Llave";
        colision = false;
        tilePermitida = 3;
        try{
            this.imagen = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/assets/img/objetos/llave.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }



}
