package model.juego.objetos.efectos;
import model.juego.entidades.Player;

public class RapidezEffect extends PowerUpEffect{

    private float multiplicador;
    
    public RapidezEffect(int duracionSegundos) {
        super(duracionSegundos);
    }

    public void setMultiplicador(float multiplicador){
        this.multiplicador = multiplicador;
    }

    @Override 
    public void aplicar(Player jugador){
        if (jugador.getUltimaInteraccion() + 1000 < System.currentTimeMillis()){
            jugador.setMultiplicadorVelocidad(multiplicador);
            jugador.setUltimaInteraccion(System.currentTimeMillis());
        }
    }

    @Override
    public void remover(Player jugador) {
        jugador.setMultiplicadorVelocidad(1.0f); // volver a la velocidad normal
    }

}
