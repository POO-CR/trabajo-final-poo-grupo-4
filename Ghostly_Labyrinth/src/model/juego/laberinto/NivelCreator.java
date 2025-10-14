package model.juego.laberinto;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import model.juego.tile.Tile;
import model.juego.tile.TileManager;
import model.juego.tile.TileSpriteSetter;

public class NivelCreator implements Runnable{
    ArrayList <Tile[][]> mapas = new ArrayList<>();
    private static record Dimension(int alto, int ancho) {}
    private static record Posicion(int x, int y) {}
    private Dimension dim;
    private TileManager tm;
    private TileSpriteSetter tileSS;
    private int nivelNumero;
    private Nivel nivel;

    private static Dimension getDimensionMapa(int nivel) {
        return switch (nivel) {
            case 0 -> new Dimension(20, 30);
            case 1 -> new Dimension(40, 40);
            case 2 -> new Dimension(40, 40);
            default -> new Dimension(20, 30);
        };
    }

    private static Posicion getPosicionDefaultMapa(int nivel) {
        return switch (nivel) {
            case 0 -> new Posicion (2, 5);
            case 1 -> new Posicion (19, 3); //TODO: cambiar a otras cosas cuando hagamos más mapas
            case 2 -> new Posicion (37, 5);
            default -> new Posicion (3, 3);
        };
    }

    public int getMapaAlto (int nivel){
        return getDimensionMapa(nivel).alto;
    }

    public int getMapaAncho (int nivel){
        return getDimensionMapa(nivel).ancho;
    }


    public NivelCreator(int nivelNumero) {
        this.nivelNumero = nivelNumero;
        tm = new TileManager();
    }

    // public Nivel getSiguienteNivel() {
    //     crearnivel();
    //     return nivel;
    // }

    public Nivel crearnivel (){
        try {
                this.dim = getDimensionMapa(this.nivelNumero);
                this.nivel = new Nivel(dim.alto, dim.ancho);
                nivel.setPosicionDefault(getPosicionDefaultMapa(this.nivelNumero).x, getPosicionDefaultMapa(this.nivelNumero).y);
                InputStream is = getClass().getResourceAsStream(String.format("/assets/mapas/mapa%d.txt", nivelNumero));
            try (BufferedReader buffer = new BufferedReader(new InputStreamReader(is))) {
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
            }
                this.tileSS = new TileSpriteSetter(nivel);
                tileSS.setearTileRandom(tm.getTile(0));
                tileSS.setearTileSegunVecinos(tm.getTile(1));

                // para agregar las posiciones de los fantasmas segun el nivelll
                switch (nivelNumero) {
                    case 0 -> {
                        nivel.agregarFantasma(2, 3);
                        nivel.agregarFantasma(15, 8);
                    }
                    case 1 -> {
                        nivel.agregarFantasma(3, 4);
                    }
                    case 2 -> {
                        nivel.agregarFantasma(2, 2);
                        nivel.agregarFantasma(4, 2);
                    }
                }


                return nivel;
                
            }catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        
    }

    //no puede devolver el nivel después de crearlo porque es un hilo
    @Override
    public synchronized void run (){
        try {
                this.dim = getDimensionMapa(this.nivelNumero);
                this.nivel = new Nivel(dim.alto, dim.ancho);
                InputStream is = getClass().getResourceAsStream(String.format("/assets/mapas/mapa%d.txt", nivelNumero));
            try (BufferedReader buffer = new BufferedReader(new InputStreamReader(is))) {
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
            }
                this.tileSS = new TileSpriteSetter(nivel);
                tileSS.setearTileRandom(tm.getTile(0));
                tileSS.setearTileSegunVecinos(tm.getTile(1));
            }catch (Exception e) {
                e.printStackTrace();
            }
        
    }



}
