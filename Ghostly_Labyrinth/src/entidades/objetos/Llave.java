package entidades.objetos;

import java.io.IOException;
import entidades.laberinto.*;

public class Llave extends Objeto {

    public Llave(Labyrinth lab) {
        super(lab);
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
