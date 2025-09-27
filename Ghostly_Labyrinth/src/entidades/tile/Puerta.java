package entidades.tile;

import javax.imageio.ImageIO;

public class Puerta extends Tile {
    
    public Puerta() {
        cerrar();
    }

    public void cerrar() {
        try{
            setSprite(ImageIO.read(getClass().getResourceAsStream("/assets/img/puerta_cerrada.png")));
        }catch(Exception e){
            e.printStackTrace();
        }
        colision = true;
    }

    public void abrir() {
        try {
            setSprite(ImageIO.read(getClass().getResourceAsStream("/assets/img/puerta_abierta.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.colision = false;
    }
    
}
