package entidades.laberinto;
import entidades.tile.*;

public class Nivel {

    Tile [][] matBaldosas;
    private static record Dimension(int alto, int ancho){}
    Dimension dim;

    public Nivel (int alto, int ancho) {
        this.dim = new Dimension(alto, ancho);
        this.matBaldosas = new Tile[alto][ancho];
    }

    public int getAlto () {
        return dim.alto;
    }

    public int getAncho() {
        return dim.ancho;
    }

    public boolean indiceEnRango (int x, int y) {
        if (x >= getAncho() || x < 0 || y < 0 || y >= getAlto()) 
            return false;
        return true;
    }

    public Tile getTile (int x, int y) {
        return matBaldosas[x][y];
    }

}