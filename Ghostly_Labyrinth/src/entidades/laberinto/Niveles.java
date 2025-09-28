package entidades.laberinto;

import java.util.*;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Niveles {
    ArrayList <int[][]> mapas = new ArrayList<>();
    final int cantNiveles = 2;
    public static record Dimension(int alto, int ancho) {}

    public static Dimension getDimensionMapa(int nivel) {
        return switch (nivel) {
            case 0 -> new Dimension(20, 30);
            case 1 -> new Dimension(20, 60);
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
        // TODO: pensar como hacer para que los mapas sean de distintos tamaños. puede ser una clase mapa.
        Dimension Dim;

        for (int i=0; i<=cantNiveles; i++){
            try {
                Dim = getDimensionMapa(i);

                int[][] mapaTemp = new int[Dim.alto][Dim.ancho];
                InputStream is = getClass().getResourceAsStream(String.format("/assets/mapas/mapa%d.txt", i));
                BufferedReader buffer = new BufferedReader(new InputStreamReader(is));

                int col = 0;
                int fila = 0;
                while (col < Dim.ancho && fila < Dim.alto) {
                    String linea = buffer.readLine();
                    while (col < Dim.ancho) {
                        String numeros[] = linea.split(" ");
                        int num = Integer.parseInt(numeros[col]);
                        mapaTemp[fila][col] = num;
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

    public int[][] getMapa(int nivel){
        return mapas.get(nivel);
    }


}
