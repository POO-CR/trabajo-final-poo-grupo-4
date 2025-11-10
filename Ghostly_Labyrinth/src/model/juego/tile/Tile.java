package model.juego.tile;
import java.awt.Image;
import java.awt.image.BufferedImage;

import model.juego.entidades.*;
import model.juego.ColisionChecker;

public class Tile {
    
    protected int num;
    private BufferedImage sprite;
    private Image tileSet;
    private int tileIndex;
    private int maxIndex;
    public boolean colision = false;

    public Tile(BufferedImage tileSet) {
        this.tileSet = tileSet;
        this.tileIndex = 0;
        maxIndex = (tileSet.getWidth(null) / 20) * (tileSet.getWidth(null)/20);
        generarSprite();
    }

    public Tile () {
    }



    private void generarSprite () {
        sprite = ((BufferedImage)tileSet).getSubimage(
            ((tileIndex / (tileSet.getWidth(null) / 20))*20), 
            ((tileIndex % (tileSet.getWidth(null) / 20))*20), 
            20, 
            20);
    }

    public void setSprite (BufferedImage sprite) {
        this.sprite = sprite;
    }

    // si es tile, devuelve la colisión simplemente(es para la puerta más que nada)
    public boolean tieneAcceso(ColisionChecker cs, Entity entidad) {
        return colision;
    }

    public BufferedImage getSprite (){
        return this.sprite;
    }

    public int getTileIndex () {
        return this.tileIndex;
    }

    public int getNum () {
        return this.num;
    }

    public void setTileIndex(int tileIndex) {
        this.tileIndex = tileIndex;
        generarSprite();
    }

    public int getMaxIndex () {
        return this.maxIndex;
    }

}
