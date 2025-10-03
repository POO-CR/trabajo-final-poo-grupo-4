package entidades.laberinto;
import entidades.tile.*;
import java.awt.Graphics2D;
import controlador.GameController;


public class Labyrinth {

    TileManager tileManager;
    TileSpriteSetter tileSS;
    private GameController gc;
    public Tile[] tile;
    public Tile [][] laberintoMat;
    private int cantidadFilas;
    private int cantidadColumnas;
    private int anchoBloque;
    private int altoBloque;

    public Labyrinth(GameController gc){
        this.gc = gc;
        tileManager = new TileManager();
        tile = tileManager.getTileArray();
        this.cantidadColumnas = gc.getAnchoNivel();
        this.cantidadFilas = gc.getAltoNivel();
        this.altoBloque = gc.getAltoBloque();
        this.anchoBloque = gc.getAnchoBloque();
    }

    // 
    //Para poder utilizar graficos
    public void paintComponent(Graphics2D grafico){
        //filas
        for(int fila = 0; fila<cantidadFilas; fila++){
            //columnas
            for(int columna = 0; columna < cantidadColumnas; columna++){
                    //variable para la iteracion
                    grafico.drawImage(gc.getSprite(fila, columna), columna * anchoBloque, fila * altoBloque, anchoBloque, altoBloque, null);
                }     
            }
        }

        //dibujar el mensaje de la puerta abierta
        // if (mostrarMensaje) {
        // grafico.setColor(Color.YELLOW);
        // grafico.setFont(new Font("Arial", Font.BOLD, 20));
        // grafico.drawString("PUERTA ABIERTA", 200, 30); // coordenadas en la ventana

    


    // TODO: pasar esto a nivel o a otro lado
    // public int getCols() { return cantidadColumnas; }
    // public int getRows() { return cantidadFilas; }
    // public int getTileW() { return anchoBloque; }
    // public int getTileH() { return altoBloque; }
    // public int getPixelWidth()  { return cantidadColumnas * anchoBloque; }
    // public int getPixelHeight() { return cantidadFilas    * altoBloque; }


}
