package model.juego.objetos.efectos;
import model.juego.entidades.Player;


// clase abstracta de comando para los powerups
public abstract class PowerUpEffect {

    private long tiempoInicio;
    private final int duracionMs;

    //al crear el efecto le damos los segundos de duración y el t de inicio
    public PowerUpEffect(int duracionSegundos) {
        this.duracionMs = duracionSegundos * 1000;
        this.tiempoInicio = System.currentTimeMillis();
    }

    public boolean isCaducado(){
        return (System.currentTimeMillis() - tiempoInicio) >= duracionMs;
    }

    public abstract void remover(Player jugador);

    public abstract void aplicar(Player jugador);

    public void reiniciarPoder(){
        this.tiempoInicio = System.currentTimeMillis();
    }
    
}
