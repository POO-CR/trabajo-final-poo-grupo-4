package entidades.tile;

import entidades.laberinto.Nivel;
import java.util.Random;

public class TileSpriteSetter {

    private final Nivel nivel;

    public TileSpriteSetter (Nivel nivel) {
        this.nivel = nivel;
    }



    public void setearTileSegunVecinos (Tile tile) {
        int cantidadFilas = nivel.getAlto();
        int cantidadColumnas = nivel.getAncho();
        int valor, posY, posX, index;
        for(int fila = 0; fila<cantidadFilas; fila++){
            
            //columnas
            for(int columna = 0; columna<cantidadColumnas; columna++){
                    //variable para la iteracion 
                valor = nivel.getTile(fila, columna).getNum();      
                if (valor == tile.getNum()) {
                    posX = fila;
                    posY = columna;
                    index = switch (getTilesVecinas(valor, posX, posY)) { //chequeo los vecinos de esa baldosa
                        case "0010" -> 0;
                        case "0110" -> 1;
                        case "0100" -> 2;
                        case "0001" -> 3;
                        case "1111" -> 4;
                        case "0111" -> 5;
                        case "1101" -> 6;
                        case "1001" -> 7;
                        case "0000" -> 8;
                        case "1011" -> 9;
                        case "1110" -> 10;
                        case "1000" -> 11;
                        case "0011" -> 12;
                        case "0101" -> 13;
                        case "1010" -> 14;
                        case "1100" -> 15;

                        default -> 8;
                        };
                    nivel.getTile(posX, posY).setTileIndex(index);
                    }
                }
                
            }
    }

    
    private String getTilesVecinas (int valorActual, int posX, int posY) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int x = posY -1 ; x < posY + 2; x++){
            for (int y = posX -1 ; y < posX + 2; y++){
                if (!nivel.indiceEnRango(x, y)) {
                    stringBuilder.append(0);
                    continue;
                }

                if (nivel.getTile(y,x).getNum() == valorActual) {
                    stringBuilder.append(1);
                }
                else 
                    stringBuilder.append(0);
                
            }
        }
        //saco esquinas y centro
        StringBuilder sb2 = new StringBuilder();
        sb2.append(stringBuilder.charAt(1));
        sb2.append(stringBuilder.charAt(3));
        sb2.append(stringBuilder.charAt(5));
        sb2.append(stringBuilder.charAt(7));
        return sb2.toString();
    }

    public void setearTileRandom (Tile tile) {
        int cantidadFilas = nivel.getAlto();
        int cantidadColumnas = nivel.getAncho();
        Tile tileTemp;
        Random rnd = new Random();
        for(int fila = 0; fila<cantidadFilas; fila++){
            //columnas
            for(int columna = 0; columna<cantidadColumnas; columna++){
                    //variable para la iteracion 
                tileTemp = nivel.getTile(fila, columna);
                if (tileTemp.getNum() == tile.getNum()) {
                    tileTemp.setTileIndex(rnd.nextInt(tileTemp.getMaxIndex())); //seteo un indice random entre los indices del tileSet de la baldosa
                }
            }  

        }
    }
}
