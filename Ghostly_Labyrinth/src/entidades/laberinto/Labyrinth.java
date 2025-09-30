package entidades.laberinto;
import entidades.tile.*;
import java.awt.Graphics2D;


public class Labyrinth {

    //valores iniciales
    final int originalTileSize = 20; // tamaño standard de tiles, dijimos de cambiarlo a 32x32
    //final int escala = 2; // escala para agrandar los tiles
    final int escala = 4; // para que se vea mas de cerca todo
    final int tileSize = originalTileSize * escala; 

    //para cantidad de fil y col
    public int cantidadFilas; // 16 columnas de tiles en pantalla
    public int cantidadColumnas; // 12 filas de tiles en pantalla

    //para ancho y alto de cada bloque 
    public final int anchoBloque = tileSize;
    public final int altoBloque = tileSize;

    private Nivel nivel;

    TileManager tileManager;
    TileSpriteSetter tileSS;
    public Tile[] tile;
    public Tile [][] laberintoMat;


    // public Labyrinth(){
    //     System.out.println("creando lab sin nivel");

    //     // crea el mapa de lucas por default
    //     this.nivel = 0;
    //     this.laberintoMat = niveles.getMapa(0);
    //     cantidadColumnas = niveles.getMapaAncho(0);
    //     cantidadFilas = niveles.getMapaAlto(0);
    //     tileManager = new TileManager();
    //     tile = tileManager.getTileArray();
    //     tileSS = new TileSpriteSetter(this);
    //     tileSS.setearTileSegunVecinos(tileManager.getTile(1));
        //items = new ArrayList<>();
        //generar la llave al inicio
        //generarLlaveRandom();

        //items.add(new Item(1, 28, "llave"));
    }

    public Labyrinth(Nivel nivel){
        System.out.println("creando lab con nivel");
        // crea un mapa con el nivel especificado en el panel del juego
        this.nivel = nivel;
        cantidadColumnas = nivel.getAncho();
        cantidadFilas = nivel.getAlto();
        tileManager = new TileManager();
        tile = tileManager.getTileArray();
        tileSS = new TileSpriteSetter(this);
        tileSS.setearTileSegunVecinos(tileManager.getTile(1)); // TODO: está medio feo esto. El setear tile pide tiles para poder hacerlo con 
                                                        // varias tiles distintas, pero acá solo usamos la parte de arriba de las paredes
        tileSS.setearTileRandom(tileManager.getTile(0));
    }


    public Tile[][] obtenerLaberinto() {
        return laberintoMat;
    }



    public int valorTile(int fila, int columna) {
        return laberintoMat[fila][columna].num;
    }





    //Para poder utilizar graficos
    public void paintComponent(Graphics2D grafico){
        //filas
        for(int fila = 0; fila<cantidadFilas; fila++){
            //columnas
            for(int columna = 0; columna<cantidadColumnas; columna++){
                    //variable para la iteracion
                    grafico.drawImage(nivel.matBaldosas[fila][columna].getSprite(), columna * anchoBloque, fila * altoBloque, anchoBloque, altoBloque, null);
                }
                
            }
        }

        //dibujar el mensaje de la puerta abierta
        // if (mostrarMensaje) {
        // grafico.setColor(Color.YELLOW);
        // grafico.setFont(new Font("Arial", Font.BOLD, 20));
        // grafico.drawString("PUERTA ABIERTA", 200, 30); // coordenadas en la ventana

    



    public int getCols() { return cantidadColumnas; }
    public int getRows() { return cantidadFilas; }
    public int getTileW() { return anchoBloque; }
    public int getTileH() { return altoBloque; }
    public int getPixelWidth()  { return cantidadColumnas * anchoBloque; }
    public int getPixelHeight() { return cantidadFilas    * altoBloque; }


}
