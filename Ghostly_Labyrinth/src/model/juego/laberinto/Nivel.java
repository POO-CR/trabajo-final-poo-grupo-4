package model.juego.laberinto;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import model.juego.tile.*;

public class Nivel {

    protected Tile [][] matBaldosas;
    private static record Dimension(int alto, int ancho){}
    private static record Posicion(int x, int y){}
    Dimension dim;
    Posicion posDefault;

    private List<Point> posicionesFantasma = new ArrayList<>(); // posiciones de los fantasmas en el nivel

    public Nivel (int alto, int ancho) {
        this.dim = new Dimension(alto, ancho);
        this.matBaldosas = new Tile[alto][ancho];
    }

    
    
    public void agregarFantasma(int x, int y) { // agrega una posicion de fantasma al nivel
        posicionesFantasma.add(new Point(x, y)); // usa point para guardar x e y juntos
    }

    public List<Point> getPosicionesFantasma() { // devuelve la lista de posiciones de fantasmas
        return posicionesFantasma;
    }



    public void setPosicionDefault(int x, int y){
        this.posDefault = new Posicion(x, y);
    }

    public int getPosDefaultX(){
        return posDefault.x;
    }

    public int getPosDefaultY(){
        return posDefault.y;
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