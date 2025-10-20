package model.juego.tile;

import model.juego.ColisionChecker;
import model.juego.entidades.Entity;

public class TileEspecial extends Tile {

        @Override
    public boolean tieneAcceso(ColisionChecker cs, Entity entidad) {
        cs.llamarCambioNivel();
        return colision;
    }
    
}
