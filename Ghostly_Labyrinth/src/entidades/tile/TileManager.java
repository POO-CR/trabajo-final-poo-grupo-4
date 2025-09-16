package entidades.tile;

import javax.imageio.ImageIO;

import juego.PanelJuego;
import java.awt.Graphics2D;


public class TileManager {
    
    PanelJuego panel;
    Tile[] tile;

    public TileManager(){;

        tile = new Tile[10];
        getTileImage();
    }

    public void getTileImage(){
        try{
            tile[0] = new Tile();
            tile[0].imagen = ImageIO.read(getClass().getResourceAsStream("/assets/img/baldosa.jpg"));

            tile[1] = new Tile();
            tile[1].imagen = ImageIO.read(getClass().getResourceAsStream("/assets/img/muro.jpg"));
            tile[1].colision = true;

            tile[2] = new Tile();
            tile[2].imagen = ImageIO.read(getClass().getResourceAsStream("/assets/img/puerta.jpg"));
            tile[2].colision = true;

            tile[3] = new Tile();
            tile[3].imagen = ImageIO.read(getClass().getResourceAsStream("/assets/img/llave.jpg"));

        }catch(Exception e){
            e.printStackTrace();}
        }

    public Tile[] getTile() {
        return tile;
    }
}