package model.juego.objetos;

import controller.juego.GameController;
import model.juego.entidades.Player;
import model.juego.objetos.efectos.*;

public class Rapidez extends Objeto{
    
    private GameController gc;
    private int duracion;
    private float multiplicador;
    

    public Rapidez (GameController gc) {
        super(gc);
        this.gc = gc;
        setNombre("Rapidez");
        setColision(false);
        tilePermitida = 5;
        duracion = 5; // segundos
        multiplicador = 1.5f; 
        try{
            this.imagen = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/assets/img/objetos/rapidez.png"));
        }catch(Exception e){ 
            e.printStackTrace();
        }
    }

    @Override
    public PowerUpEffect interactuar(Player jugador) {
        RapidezEffect rapidezE = new RapidezEffect(duracion);
        rapidezE.setMultiplicador(multiplicador);
        return rapidezE;
    }

}
