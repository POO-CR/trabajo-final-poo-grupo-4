package entidades.laberinto;

import entidades.tile.Tile;
import entidades.tile.TileManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Niveles {
    ArrayList <Tile[][]> mapas = new ArrayList<>();
    final int cantNiveles = 2;
    public static record Dimension(int alto, int ancho) {}

    private static Dimension getDimensionMapa(int nivel) {
        return switch (nivel) {
            case 0 -> new Dimension(20, 30);
            case 1 -> new Dimension(4, 6);
            case 2 -> new Dimension(20, 30);
            default -> new Dimension(20, 30);
        };
    }

    public int getMapaAlto (int nivel){
        return getDimensionMapa(nivel).alto;
    }

    public int getMapaAncho (int nivel){
        return getDimensionMapa(nivel).ancho;
    }


    public Niveles(){
        Dimension Dim;
        TileManager tm = new TileManager();

        for (int i=0; i<=cantNiveles; i++){
            try {
                Dim = getDimensionMapa(i);

                Tile[][] mapaTemp = new Tile[Dim.alto][Dim.ancho];
                InputStream is = getClass().getResourceAsStream(String.format("/assets/mapas/mapa%d.txt", i));
                BufferedReader buffer = new BufferedReader(new InputStreamReader(is));

                int col = 0;
                int fila = 0;
                while (col < Dim.ancho && fila < Dim.alto) {
                    String linea = buffer.readLine();
                    while (col < Dim.ancho) {
                        String numeros[] = linea.split(" ");
                        int num = Integer.parseInt(numeros[col]);
                        mapaTemp[fila][col] = tm.crearNuevoObjetoTile(num);

                        col++;
                    }
                    if (col == Dim.ancho) {
                        col = 0;
                        fila++;
                    }
                }
                mapas.add(mapaTemp);
                buffer.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Tile[][] getMapa(int nivel){
        return mapas.get(nivel);
    }

    public boolean indiceEnRango (int nivel, int x, int y) {
        if (x >= getMapaAncho(nivel) || x < 0 || y < 0 || y >= getMapaAlto(nivel)) 
            return false;
        return true;
    }


}
