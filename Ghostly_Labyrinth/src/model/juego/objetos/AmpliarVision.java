package model.juego.objetos;
import controller.juego.GameController;
import model.juego.entidades.Player;
import model.juego.objetos.efectos.*;

public class AmpliarVision extends Objeto{

    private GameController gc;
    private int duracion;
    private int aumentoRadio;
    
    public AmpliarVision (GameController gc) {
        super(gc);
        setNombre("Ampliar Vision");
        setColision(false);
        tilePermitida = 5;
        duracion = 8; //segundos
        aumentoRadio = 200; //pixeles
        try{
            this.imagen = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/assets/img/objetos/visionAmpliada.png"));
        }catch(Exception e){ 
            e.printStackTrace();
        }
    }

    @Override
    public PowerUpEffect interactuar(Player jugador) {
        if (jugador.getUltimaInteraccion() + 1000 < System.currentTimeMillis()){
            jugador.cambiarLuz(aumentoRadio);   
            jugador.setUltimaInteraccion(System.currentTimeMillis());
        }
        return new AmpliarVisionEffect(duracion);
    }
    
}
