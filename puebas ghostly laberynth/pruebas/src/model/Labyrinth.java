// model/Labyrinth.java
package model;

import java.awt.*;
import java.util.*;
import java.util.List;

public abstract class Labyrinth {
    public static final int FLOOR=0, WALL=1, ROOM=3, KEY_ZONE=4, DOOR=5;

    private final int[][] grid;
    private final int rows, cols, tileW, tileH;
    private boolean puertaAbierta=false;
    private final List<Item> items = new ArrayList<>();

    protected Labyrinth(int[][] grid, int tileW, int tileH){
        this.grid=grid; rows=grid.length; cols=grid[0].length;
        this.tileW=tileW; this.tileH=tileH;
        spawnKeyLejosDeLaPuerta(); // carga 1 llave en un 4 lejano
    }

    // ===== API utilizada arriba =====
    public int getRows(){ return rows; }
    public int getCols(){ return cols; }
    public int getTileW(){ return tileW; }
    public int getTileH(){ return tileH; }
    public boolean isPuertaAbierta(){ return puertaAbierta; }
    public void abrirPuerta(){ puertaAbierta=true; }

    // --- NUEVO: ancho/alto en píxeles para centrar el tablero ---
    public int getPixelWidth(){ return cols * tileW; }      // NUEVO
    public int getPixelHeight(){ return rows * tileH; }     // NUEVO

    // --- NUEVO: lectura de una celda de la matriz ---
    public int getCell(int r,int c){ return grid[r][c]; }   // NUEVO

    // --- NUEVO: exponer items (solo lectura) para dibujar la llave ---
    public List<Item> getItems(){                           // NUEVO
        return Collections.unmodifiableList(items);
    }

    public boolean isDoor(int r,int c){ return in(r,c) && grid[r][c]==DOOR; }
    public boolean isRoom(int r,int c){ return in(r,c) && grid[r][c]==ROOM; }
    public boolean isWalkable(int r,int c){
        if(!in(r,c)) return false;
        int t=grid[r][c];
        if (t==WALL) return false;
        if (t==DOOR && !puertaAbierta) return false;
        return true;
    }

    public Optional<Item> pickupKeyAt(int r,int c){
        for (int i=0;i<items.size();i++){
            Item it = items.get(i);
            if (it.getFila()==r && it.getColumna()==c && Item.KEY.equals(it.getTipo())){ // usa constante
                items.remove(i);
                return Optional.of(it);
            }
        }
        return Optional.empty();
    }

    public void paint(Graphics g, int offX, int offY){
    
    }

    // ===== utilitarios =====
    private boolean in(int r,int c){ return r>=0&&c>=0&&r<rows&&c<cols; }

    private Point findDoor(){
        for(int r=0;r<rows;r++) for(int c=0;c<cols;c++) if(grid[r][c]==DOOR) return new Point(c,r);
        return null;
    }
    private void spawnKeyLejosDeLaPuerta(){
        Point door = findDoor();
        List<Point> zonas = new ArrayList<>();
        for(int r=0;r<rows;r++) for(int c=0;c<cols;c++) if(grid[r][c]==KEY_ZONE) zonas.add(new Point(c,r));
        if(zonas.isEmpty()) return;
        Point elegido = zonas.get(0);
        int best=-1;
        for(Point p: zonas){
            int d = (door==null)?0:Math.abs(p.x-door.x)+Math.abs(p.y-door.y);
            if(d>best){ best=d; elegido=p; }
        }
        items.add(new Item(elegido.y, elegido.x, Item.KEY));
    }
}