package model.juego.laberinto;

import java.util.ArrayList;
import java.util.Random;

import controller.juego.GameController;

public class NivelManager {

    private final int cantNivelesAJugar;
    private final ArrayList<Integer> nivelesDisponibles;
    private Random rnd;
    private final int cantMapas;
    private int cantNivelesHechos;

    public NivelManager() {
        cantNivelesAJugar = 3;
        cantMapas = 2;
        nivelesDisponibles = new ArrayList<>();
        reiniciar(); //llena la lista y deja todo en estado inicial
    }

    public NivelWorker crearSiguienteNivelWorker (int nivel, GameController gc){
        return new NivelWorker(nivel, gc);
    }

    public NivelWorker crearSiguienteNivelWorker (GameController gc) {
        rnd = new Random();
        int indiceTemp;
        int nuevoNivel;
        System.out.println("niveles disponibles: " + nivelesDisponibles);
        if (nivelesDisponibles.size() > 0 & cantNivelesHechos < cantNivelesAJugar) {
            indiceTemp = rnd.nextInt(nivelesDisponibles.size());
            nuevoNivel = nivelesDisponibles.get(indiceTemp);
            nivelesDisponibles.remove(indiceTemp);
            cantNivelesHechos++;

            return new NivelWorker(nuevoNivel, gc);
        }
        System.out.println("devolviendo null pq se hicieron los" + cantNivelesAJugar);

        return null;
    }

    // metodo para reiniciar el NivelManager(la lista de niveles vuelve a su estado inicial y los niveles se eligen aleatoriamente desde cero)
    public void reiniciar() {
        nivelesDisponibles.clear(); // se vacia la lista de niveles disponibles anterior
        cantNivelesHechos = 0;
        for (int i = 0; i <= cantMapas; i++) {
            nivelesDisponibles.add(i); // se reagregan todos los niveles del 0 al cantMapas
        }
        rnd = new Random(); //se reinicia el generador de numeros aleatorios
        System.out.println("NivelManager reiniciado. Niveles disponibles: " + nivelesDisponibles);
    }


    
}
