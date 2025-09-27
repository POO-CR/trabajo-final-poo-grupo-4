package entidades.objetos;

import java.io.IOException;
import juego.PanelJuego;

public class Llave extends Objeto {

    public Llave(PanelJuego pj) {
        super(pj);
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
