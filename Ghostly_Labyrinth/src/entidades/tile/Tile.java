package entidades.tile;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Tile {
    
    public int num;
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

    public int getMaxIndex () {
        return this.maxIndex;
    }

}
