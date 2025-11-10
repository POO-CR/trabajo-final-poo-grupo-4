package model.juego.tile;

import javax.imageio.ImageIO;

import model.juego.ColisionChecker;
import model.juego.entidades.Entity;

public class Puerta extends Tile {
    
    public Puerta() {
        cerrar();
    }

    @Override
    public boolean tieneAcceso(ColisionChecker cs, Entity entidad) {
        if (entidad.getTieneLlave()) {
            abrir();
        }
        return colision;
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
