package juego;
import entidades.Player;
import entidades.Entity;


public class CCheckerPlayer extends ColisionChecker{

    public CCheckerPlayer (PanelJuego panelJuego) {
        super(panelJuego);
    }

    @Override
    public void checkearTile(Player jugador) {
        super.checkearTile(jugador);
        if ((tileNum1 == 2 || tileNum2 == 2) & jugador.getTieneLlave()) {
            jugador.colisionOn = false;
        }

    }

}
