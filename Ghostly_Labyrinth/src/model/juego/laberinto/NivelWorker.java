package model.juego.laberinto;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.SwingWorker;

import model.juego.tile.Tile;
import model.juego.tile.TileManager;
import model.juego.tile.TileSpriteSetter;

// string para devolver mensajes de progreso
public class NivelWorker extends SwingWorker<Nivel, String>{

    ArrayList <Tile[][]> mapas = new ArrayList<>();
    private static record Dimension(int alto, int ancho) {}
    private static record Posicion(int x, int y) {}
    private Dimension dim;
    private TileManager tm;
    private TileSpriteSetter tileSS;
    private int nivelNumero;
    private final java.util.function.Consumer<String> statusUpdater;
    private Nivel nivel;

    public NivelWorker(int nivelNumero, java.util.function.Consumer<String> statusUpdater){
        this.nivelNumero = nivelNumero;
        this.statusUpdater = statusUpdater;
        tm = new TileManager();
    }

    // método para trabajar en 2do plano
    @Override
    protected Nivel doInBackground() throws Exception {

        publish("Empezando creación...");
        setProgress(0);
        int progreso = 0;
        long ultimaActualizacion = System.currentTimeMillis();

        try {
                this.dim = getDimensionMapa(this.nivelNumero);
                this.nivel = new Nivel(dim.alto, dim.ancho);
                nivel.setPosicionDefault(getPosicionDefaultMapa(this.nivelNumero).x, getPosicionDefaultMapa(this.nivelNumero).y);
                InputStream is = getClass().getResourceAsStream(String.format("/assets/mapas/mapa%d.txt", nivelNumero));
            try (BufferedReader buffer = new BufferedReader(new InputStreamReader(is))) {
                int col = 0;
                int fila = 0;
                while (ultimaActualizacion + 800 > System.currentTimeMillis()) {
                    // esperamos un toque para que no actualice tan rápido
                }
                publish ("Cargando laberinto fantasmal...");
                progreso++;
                setProgress(progreso * 25);
                ultimaActualizacion = System.currentTimeMillis();

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

                while (ultimaActualizacion + 800 > System.currentTimeMillis()) {
                    // esperamos un toque para que no actualice tan rápido
                }
                publish ("Poniendo screamers...");
                progreso++;
                setProgress(progreso * 25);
                ultimaActualizacion = System.currentTimeMillis();
                this.tileSS = new TileSpriteSetter(nivel);
                tileSS.setearTileRandom(tm.getTile(0));
                tileSS.setearTileSegunVecinos(tm.getTile(1));

                while (ultimaActualizacion + 800 > System.currentTimeMillis()) {
                    // esperamos un toque para que no actualice tan rápido
                }
                publish ("Colocando fantasmas...");
                progreso++;
                setProgress(progreso * 25);
                ultimaActualizacion = System.currentTimeMillis();

                // para agregar las posiciones de los fantasmas segun el nivelll
                switch (nivelNumero) {
                    case 0 -> {
                        nivel.agregarFantasma(26, 3);
                        nivel.agregarFantasma(3, 3);
                        nivel.agregarFantasma(18, 4);
                        nivel.agregarFantasma(10, 10);
                        nivel.agregarFantasma(23, 12);
                        nivel.agregarFantasma(6, 16);
                        nivel.agregarFantasma(27, 7);
                        
                    }
                    case 1 -> {
                        nivel.agregarFantasma(3, 3);
                        nivel.agregarFantasma(5, 2);
                        nivel.agregarFantasma(33, 2);
                        nivel.agregarFantasma(15, 7);
                        //nivel.agregarFantasma(25, 10);
                        //nivel.agregarFantasma(8, 14);
                        //nivel.agregarFantasma(36, 19);
                        //nivel.agregarFantasma(4, 20);
                        //nivel.agregarFantasma(28, 23);
                        
                    
                    }
                    case 2 -> {
                        nivel.agregarFantasma(2, 2);
                        nivel.agregarFantasma(7, 2);
                        nivel.agregarFantasma(28, 3);
                        //nivel.agregarFantasma(4, 7);
                        //nivel.agregarFantasma(33, 9);
                        //nivel.agregarFantasma(11, 13);
                        //nivel.agregarFantasma(25, 17);
                        //nivel.agregarFantasma(8, 22);
                        //nivel.agregarFantasma(30, 27);
                        //nivel.agregarFantasma(5, 33);
                        //nivel.agregarFantasma(36, 41);
                        nivel.agregarFantasma(4, 2);
                        
                    }
                }

                while (ultimaActualizacion + 800 > System.currentTimeMillis()) {
                    // esperamos un toque para que no actualice tan rápido
                }
                publish ("Últimos retoques...");
                progreso++;
                setProgress(progreso * 25);
                ultimaActualizacion = System.currentTimeMillis();

                while (ultimaActualizacion + 200 > System.currentTimeMillis()) {
                }

                return nivel;            
                
            }catch (Exception e) {
                e.printStackTrace();
                return null;
            }
    }

    @Override
    protected void process(java.util.List<String> chunks) {
        // obtener el último mensaje publicado
        String mensaje = chunks.get(chunks.size() - 1);
        statusUpdater.accept(mensaje);
    }

    @Override
    protected void done() {
        try {
            Nivel nivelCreado = get();
            System.out.println("Nivel " + nivelNumero + " creado");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Dimension getDimensionMapa(int nivel) {
        return switch (nivel) {
            case -1 -> new Dimension(15, 15);
            case 0 -> new Dimension(20, 30);
            case 1 -> new Dimension(40, 40);
            case 2 -> new Dimension(40, 40);
            default -> new Dimension(20, 30);
        };
    }

    private static Posicion getPosicionDefaultMapa(int nivel) {
        return switch (nivel) {
            case -1 -> new Posicion (1, 2);
            case 0 -> new Posicion (2, 5);
            case 1 -> new Posicion (19, 3); 
            case 2 -> new Posicion (37, 5);
            default -> new Posicion (3, 3);
        };
    }

    
}
