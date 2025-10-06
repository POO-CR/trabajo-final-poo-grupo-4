package entidades.laberinto;

import entidades.tile.Tile;
import entidades.tile.TileManager;
import entidades.tile.TileSpriteSetter;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class NivelManager {
    ArrayList <Tile[][]> mapas = new ArrayList<>();
    private final int cantNiveles;
    private static record Dimension(int alto, int ancho) {}
    private Dimension dim;
    private TileManager tm;
    private TileSpriteSetter tileSS;
    Nivel nivel;

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


    public NivelManager(){
        tm = new TileManager();
        cantNiveles = 2;
    }

    public Nivel getNivel(int nivelNumero){
        try {
                this.dim = getDimensionMapa(nivelNumero);
                this.nivel = new Nivel(dim.alto, dim.ancho);
                InputStream is = getClass().getResourceAsStream(String.format("/assets/mapas/mapa%d.txt", nivelNumero));
                BufferedReader buffer = new BufferedReader(new InputStreamReader(is));

                int col = 0;
                int fila = 0;
                while (col < dim.ancho && fila < dim.alto) {
                    String linea = buffer.readLine();
                    while (col < dim.ancho) {
                        String numeros[] = linea.split(" ");
                        int num = Integer.parseInt(numeros[col]);
                        nivel.matBaldosas[fila][col] = tm.crearNuevoObjetoTile(num);

                        col++;
                    }
                    if (col == dim.ancho) {
                        col = 0;
                        fila++;
                    }
                }
                buffer.close();
                this.tileSS = new TileSpriteSetter(nivel);
                tileSS.setearTileRandom(tm.getTile(0));
                tileSS.setearTileSegunVecinos(tm.getTile(1));
                return nivel;
            }catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        
    }



}
