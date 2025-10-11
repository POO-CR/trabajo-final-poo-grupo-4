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
        rnd = new Random(); //se inicializa el generador de numeros aleatorios
        reiniciar(); //llena la lista y deja todo en estado inicial
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

    // metodo para reiniciar el NivelManager(la lista de niveles vuelve a su estado inicial y los niveles se eligen aleatoriamente desde cero)
    public void reiniciar() {
        nivelesDisponibles.clear(); // se vacia la lista de niveles disponibles anterior
        for (int i = 0; i <= cantNiveles; i++) {
            nivelesDisponibles.add(i); // se reagregan todos los niveles del 0 al cantNiveles
        }
        nivelCreator = null; //se reinicia el creador de niveles
        rnd = new Random(); //se reinicia el generador de numeros aleatorios
        System.out.println("NivelManager reiniciado. Niveles disponibles: " + nivelesDisponibles);
    }

    

    
}
