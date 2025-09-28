package entidades.laberinto;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;

import entidades.tile.*;


public class Labyrinth {

    //variables para matriz 
    //para contadores
    private int fila = 0;
    private int columna = 0;
    //valores iniciales
    final int originalTileSize = 20; // tamaño standard de tiles, dijimos de cambiarlo a 32x32
    final int escala = 2; // escala para agrandar los tiles
    final int tileSize = originalTileSize * escala; 

    //para cantidad de fil y col
    public int cantidadFila; // 16 columnas de tiles en pantalla
    public int cantidadColumnas; // 12 filas de tiles en pantalla

    //para ancho y alto de cada bloque 
    public final int anchoBloque = tileSize;
    public final int altoBloque = tileSize;

    TileManager tileManager = new TileManager();
    public Tile[] tile = tileManager.getTile();
    public int [][] laberintoNum;

    Niveles niveles;




    public Labyrinth(){
        System.out.println("creando lab sin nivel");
        this.niveles = new Niveles();

        // crea el mapa de lucas por default
        this.laberintoNum = niveles.getMapa(0);
        cantidadColumnas = niveles.getMapaAncho(0);
        cantidadFila = niveles.getMapaAlto(0);
        //items = new ArrayList<>();
        //generar la llave al inicio
        //generarLlaveRandom();

        //items.add(new Item(1, 28, "llave"));
    }

    public Labyrinth(int nivel){
        System.out.println("creando lab con nivel");
        // crea un mapa con el nivel especificado en el panel del juego
        this.niveles = new Niveles();
        this.laberintoNum = niveles.getMapa(nivel);
        cantidadColumnas = niveles.getMapaAncho(nivel);
        cantidadFila = niveles.getMapaAlto(nivel);
        //items = new ArrayList<>();
        //generar la llave al inicio
        //generarLlaveRandom();

        //items.add(new Item(1, 28, "llave"));
    }

    public void setMapaLaberinto(int nivel) {
        this.laberintoNum = niveles.getMapa(nivel); 
    }

    
    //variable para mostrar el mensaje de la puerta abierta

    //variable para el estado de la puerta
    private boolean puertaAbierta = false;

    //lista de items en el laberinto 
    //private ArrayList<Item> items;

    //variable imagen
    private BufferedImage llaveImg;

    // // metodo para devolver la lista de ítems
    // public ArrayList<Item> getItems() {
    //     return items;
    // }

    // metodo para la puerta abierta
    public void abrirPuerta() {
        puertaAbierta = true;
        for (int f = 0; f < cantidadFila; f++) {
            for (int c = 0; c < cantidadColumnas; c++) {
                if (laberintoNum[f][c] == 2) { // si es una puerta cerrada
                    laberintoNum[f][c] = 0; // la convertimos en baldosa libre
                }
            }
        }
    }
    
    public boolean isPuertaAbierta() {
        return puertaAbierta;
    }
    
    //metodo para generar la llave aleatoreamente
    //public void generarLlaveRandom() {
    //Random random = new Random();
    //int[][] laberinto = obtenerLaberinto();
    //int f, c;

    //do {
    //    f = random.nextInt(cantidadFila);
    //    c = random.nextInt(cantidadColumnas);
    //} while (laberinto[f][c] != 0); // buscamos baldosa libre

    //Item llave = new Item(f, c, "llave");
    //items.add(llave);
    //}


    public int[][] obtenerLaberinto() {
        return laberintoNum;
    }


    public int valorTile(int fila, int columna) {
        return laberintoNum[fila][columna];
    }





    //Para poder utilizar graficos
    public void paintComponent(Graphics2D grafico){
        //filas
        for(fila = 0; fila<cantidadFila; fila++){
            
            //columnas
            for(columna = 0; columna<cantidadColumnas; columna++){

                // boolean dibujarItem = false;
                
                // // buscar si hay un item en esta celda
                // for(Item item : items){
                //     if(item.getFila() == fila && item.getColumna() == columna){
                //         if(item.getTipo().equals("llave")){
                //             try {
                //                 llaveImg = ImageIO.read(getClass().getResourceAsStream("/assets/img/llave.jpg"));
                //             } catch (Exception e) {
                //                 e.printStackTrace();
                //             }
                //             grafico.drawImage(llaveImg, columna * anchoBloque, fila * altoBloque, anchoBloque, altoBloque, null);
                //             dibujarItem = true;
                //             break;
                //         }
                //     }
                // }

                //     if(!dibujarItem){

                    //variable para la iteracion 
                int valor = laberintoNum[fila][columna];
                    
                grafico.drawImage(tile[valor].imagen, columna * anchoBloque, fila * altoBloque, anchoBloque, altoBloque, null);
                
            }
        
    }



    }



    public int getCols() { return cantidadColumnas; }
    public int getRows() { return cantidadFila; }
    public int getTileW() { return anchoBloque; }
    public int getTileH() { return altoBloque; }
    public int getPixelWidth()  { return cantidadColumnas * anchoBloque; }
    public int getPixelHeight() { return cantidadFila    * altoBloque; }


}
