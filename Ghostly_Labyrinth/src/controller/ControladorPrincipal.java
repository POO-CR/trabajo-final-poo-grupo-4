package controller;

import view.FramePrincipal;

import java.util.List;

import model.dao.RecordDAO;
import model.dao.RecordDAOImpl;
import controller.juego.*;
import controller.menu.*;
import model.juego.KeyHandler;

public class ControladorPrincipal {

    private final FramePrincipal framePrincipal;
    private final RecordDAO recordDAO;
    private GameController gc;
    private MenuController mc;
    private TutorialController tutoC;
    private ScoreController scoC;


    // ir cambiando ventanas usando métodos de FramePrincipal
    public ControladorPrincipal (){
        framePrincipal = new FramePrincipal();
        this.recordDAO = new RecordDAOImpl();
        mc = new MenuController(this, framePrincipal);
        framePrincipal.mostrarMenu();
        framePrincipal.setVisible(true);
    }

    public void pasarAJuego(){
        gc = new GameController(framePrincipal);
        gc.iniciarJuego("usuario");
        //framePrincipal.mostrarJuego();
    }

    public void pasarATutorial(){
        tutoC = new TutorialController(framePrincipal);
        tutoC.iniciarTutorial();
        // framePrincipal.mostrarJuego(new KeyHandler());
    }

    public void pasarAScore(){
        //Llamar al DAO a través de la instancia
        List<model.dao.Record> top10 = recordDAO.getTop10Records(); 
        
        // Crear el ScoreController y pasarle los datos
        scoC = new ScoreController(this, framePrincipal, top10);
        
        // La vista ScorePanel debe ser inicializada antes de mostrarse
        // La inicialización del ScorePanel debería ocurrir en el constructor de FramePrincipal o ser llamada aquí.
        // Si FramePrincipal maneja la creación de paneles, esto es suficiente:
        framePrincipal.mostrarScore(); 
    }

    public void pasarAMenu(){
        framePrincipal.mostrarMenu();

        if (mc != null) {
            mc.iniciarMusica();
        }
    }

    

    

}