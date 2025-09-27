package entidades.tile;

import entidades.laberinto.Labyrinth;

public class TileSpriteSetter {
    
    private Labyrinth lab;

    public TileSpriteSetter (Labyrinth lab) {
        this.lab = lab;
    }



    public void setearTile (Tile tile) {
        int cantidadFilas = lab.cantidadFilas;
        int cantidadColumnas = lab.cantidadColumnas;
        for(int fila = 0; fila<cantidadFilas; fila++){
            
            //columnas
            for(int columna = 0; columna<cantidadColumnas; columna++){
                    //variable para la iteracion 
                int valor = lab.laberintoMat[fila][columna].num;      
                if (valor == 1) {
                    int posX = fila;
                    int posY = columna;
                    int index = switch (getTilesVecinas(posX, posY)) { //chequeo los vecinos de esa baldosa
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
                    lab.laberintoMat[posX][posY].setTileIndex(index);
                    }
                }
                
            }

    }

    
    private String getTilesVecinas (int posX, int posY) {
        StringBuilder stringBuilder = new StringBuilder();
        int valorActual = lab.laberintoMat[posX][posY].num;
        for (int x = posY -1 ; x < posY + 2; x++){
            for (int y = posX -1 ; y < posX + 2; y++){
                if (!lab.indiceEnLaberinto(x, y)) {
                    stringBuilder.append(0);
                    continue;
                }

                if (lab.laberintoMat[y][x].num == valorActual) {
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


}
