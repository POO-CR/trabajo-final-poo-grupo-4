package entidades.tile;

import java.io.IOException;

import javax.imageio.ImageIO;


public class TileManager {
    
    Tile[] tile;

    public TileManager() {
        tile = new Tile[10];
        getTileImage();
    }

    public void getTileImage(){
        try{
            tile[0] = new Tile();
            tile[0].setSprite(ImageIO.read(getClass().getResourceAsStream("/assets/img/baldosa.jpg")));
            tile[0].num = 0;

            tile[1] = new Tile(ImageIO.read(getClass().getResourceAsStream("/assets/img/laberinto/paredTop.png")), 8);
            //tile[1].setSprite(ImageIO.read(getClass().getResourceAsStream("/assets/img/muro.jpg")));
            tile[1].colision = true;
            tile[1].num = 1;

            tile[2] = new Puerta();
            tile[2].num = 2;

            tile[3] = new Tile();
            tile[3].setSprite(ImageIO.read(getClass().getResourceAsStream("/assets/img/baldosa.jpg")));
            tile[3].num = 3;

            tile[4] = new Tile();
            tile[4].setSprite(ImageIO.read(getClass().getResourceAsStream("/assets/img/laberinto/paredFrente.png")));
            tile[4].colision = true;
            tile[4].num = 4;

        }catch(Exception e){
            e.printStackTrace();}
        }

    public Tile[] getTileArray() {
        return tile;
    }


    // TODO: me quedó feo esto
    public Tile crearNuevaTilePared (int i){
        try {
            Tile t = new Tile(ImageIO.read(getClass().getResourceAsStream("/assets/img/laberinto/paredTop.png")), 8);
            t.colision = true;
            t.num = 1;
            return t;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Tile getTile(int i) {
        return tile[i];
    }
}