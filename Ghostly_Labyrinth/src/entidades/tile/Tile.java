package entidades.tile;
import java.awt.image.BufferedImage;
import java.awt.Image;

public class Tile {
    
    public int num;
    private BufferedImage sprite;
    private Image tileSet;
    private int tileIndex;
    public boolean colision = false;

    public Tile(BufferedImage tileSet, int tileIndex) {
        this.tileSet = tileSet;
        this.tileIndex = tileIndex;
        generarSprite();
    }

    public Tile () {
    }



    private void generarSprite () {
    //TODO: hacer un tileSize global al juego para no necesitar una instancia de laberinto
        sprite = ((BufferedImage)tileSet).getSubimage(
            ((tileIndex / (tileSet.getWidth(null) / 20))*20), 
            ((tileIndex % (tileSet.getWidth(null) / 20))*20), 
            20, 
            20);
    }

    public void setSprite (BufferedImage sprite) {
        this.sprite = sprite;
    }

    public BufferedImage getSprite (){
        return this.sprite;
    }

    public int getTileIndex () {
        return this.tileIndex;
    }

    public void setTileIndex(int tileIndex) {
        this.tileIndex = tileIndex;
        generarSprite();
    }

}
