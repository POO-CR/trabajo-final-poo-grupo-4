package entidades.tile;

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
            tile[0].imagen = ImageIO.read(getClass().getResourceAsStream("/assets/img/baldosa.jpg"));
            tile[0].num = 0;

            tile[1] = new Tile();
            tile[1].imagen = ImageIO.read(getClass().getResourceAsStream("/assets/img/muro.jpg"));
            tile[1].colision = true;
            tile[1].num = 1;

            tile[2] = new Puerta();
            tile[2].num = 2;

            tile[3] = new Tile();
            tile[3].imagen = ImageIO.read(getClass().getResourceAsStream("/assets/img/baldosa.jpg"));
            tile[3].num = 3;

        }catch(Exception e){
            e.printStackTrace();}
        }

    public Tile[] getTile() {
        return tile;
    }
}