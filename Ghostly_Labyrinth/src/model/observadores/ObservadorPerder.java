package model.observadores;

import controller.juego.GameController;
import model.observadores.SoundEffect;

public class ObservadorPerder implements Observador {

    private GameController gameController;
    private SoundEffect perderSound;

    public ObservadorPerder(GameController gameController) {
        this.gameController = gameController;
        this.perderSound = new SoundEffect("/assets/music/perdio.wav");
    }

    @Override
    public void actualizar(String evento) {
        if (evento.equals("JUGADOR_PERDIO")) {
            perderSound.play();
            gameController.mostrarDerrota();
        }
    }
    
}
