package controller.juego;

import model.observadores.Observador;

public class ObservadorPerder implements Observador {

    private GameController gameController;

    public ObservadorPerder(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void actualizar(String evento) {
        if (evento.equals("JUGADOR_PERDIO")) {
            gameController.mostrarDerrota();
        }
    }
    
}
