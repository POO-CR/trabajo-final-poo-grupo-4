package model.juego.objetos.efectos;
import model.juego.entidades.Player;;

public class AmpliarVisionEffect extends PowerUpEffect {

    private int aumentoRadio;
    
    public AmpliarVisionEffect(int duracionSegundos){
        super(duracionSegundos);
    }

    public void setAumentoRadio(int aumentoRadio){
        this.aumentoRadio = aumentoRadio;
    }

    @Override
    public void aplicar(Player jugador){
        if (jugador.getUltimaInteraccion() + 1000 < System.currentTimeMillis()){
            jugador.cambiarLuz(aumentoRadio);   
            jugador.setUltimaInteraccion(System.currentTimeMillis());
        }
    }

    @Override
    public void remover(Player jugador) {
        jugador.cambiarLuz(0); // el radio se suma al original, entonces para remover ponemos 0
    }

}
