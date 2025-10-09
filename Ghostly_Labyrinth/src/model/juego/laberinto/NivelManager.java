package model.juego.laberinto;

import java.util.ArrayList;
import java.util.Random;

public class NivelManager {

    private final int cantNiveles;
    private final ArrayList<Integer> nivelesDisponibles;
    private NivelCreator nivelCreator;
    Random rnd;

    public NivelManager() {
        cantNiveles = 2;
        nivelesDisponibles = new ArrayList<>();
        for (int i = 0; i <= cantNiveles; i++) {
            nivelesDisponibles.add(i);
        }
    }

    public Nivel crearSiguienteNivel () {
        rnd = new Random();
        int indiceTemp;
        int nuevoNivel;
        System.out.println("niveles disponibles: " + nivelesDisponibles);
        if (nivelesDisponibles.size() > 0) {
            indiceTemp = rnd.nextInt(nivelesDisponibles.size());
            nuevoNivel = nivelesDisponibles.get(indiceTemp);
            nivelesDisponibles.remove(indiceTemp);
            nivelCreator = new NivelCreator(nuevoNivel);

            // nivelCreator = new NivelCreator(nuevoNivel);
            // Thread hilo = new Thread(nivelCreator);
            // hilo.start();

            // while (!hilo.isAlive()){} //dios sacar esto 
            return nivelCreator.crearnivel(); //buscar bien como devolver el nivel
        }
        System.out.println("devolviendo null pq no hay niveles");

        return null;
    }
    
}
