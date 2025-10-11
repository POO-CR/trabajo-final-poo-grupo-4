package model.juego.objetos.efectos;
import model.juego.entidades.Player;;

public class AmpliarVisionEffect extends PowerUpEffect {
    
    public AmpliarVisionEffect(int duracionSegundos){
        super(duracionSegundos);
    }

    @Override
    public void remover(Player jugador) {
        jugador.cambiarLuz(0); // el radio se suma al original, entonces para remover ponemos 0
    }

}
