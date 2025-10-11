package model.juego.objetos.efectos;
import model.juego.entidades.Player;

public class RapidezEffect extends PowerUpEffect{
    
    public RapidezEffect(int duracionSegundos) {
        super(duracionSegundos);
    }

    @Override
    public void remover(Player jugador) {
        jugador.setMultiplicadorVelocidad(1.0f); // volver a la velocidad normal
    }

}
