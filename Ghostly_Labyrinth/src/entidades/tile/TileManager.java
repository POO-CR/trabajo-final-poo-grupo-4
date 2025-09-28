package entidades.tile;

import javax.imageio.ImageIO;


public class TileManager {
    
    Tile[] tile;
    final private int max = 4;

    public TileManager() {
        tile = new Tile[max];
        getTileImage();
    }

    public void getTileImage(){
        for (int i = 0; i < max; i++){
            tile[i] = crearNuevoObjetoTile(i);
        }
    }

    public Tile[] getTileArray() {
        return tile;
    }

    public Tile crearNuevoObjetoTile (int i){
        try{
            Tile t;
            switch (i) {
                case 0 : {t = new Tile(ImageIO.read(getClass().getResourceAsStream("/assets/img/laberinto/pisoRandom2.png"))); 
                    break;}
                case 1 :  {t = new Tile(ImageIO.read(getClass().getResourceAsStream("/assets/img/laberinto/paredTop.png")));
                    t.colision = true;
                    break;}
                case 2 : {t = new Puerta();
                    break;}
                case 3 : {t = new Tile();
                    t.setSprite(ImageIO.read(getClass().getResourceAsStream("/assets/img/laberinto/pisoSolo.png")));
                    break;}
                case 4: {t = new Tile();
                    t.setSprite(ImageIO.read(getClass().getResourceAsStream("/assets/img/laberinto/paredFrente.png")));
                    t.colision = true;
                    break;}
                default: t = new Tile();
            }
            t.num = i;
            return t;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Tile getTile(int i) {
        return tile[i];
    }
}