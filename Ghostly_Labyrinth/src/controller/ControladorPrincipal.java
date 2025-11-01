package controller;

import view.FramePrincipal;

import java.util.List;

import model.dao.RecordDAO;
import model.dao.RecordDAOImpl;
import controller.juego.*;
import controller.menu.*;
import model.juego.KeyHandler;
import view.juego.PanelFinJuego;

public class ControladorPrincipal {

    private final FramePrincipal framePrincipal;
    private final RecordDAO recordDAO;
    private GameController gc;
    private MenuController mc;
    private TutorialController tutoC;
    private ScoreController scoC;
    private ControladorFinJuego cfj;


    
    public ControladorPrincipal (){
        framePrincipal = new FramePrincipal();
        this.recordDAO = new RecordDAOImpl();
        mc = new MenuController(this, framePrincipal);
        cfj = new ControladorFinJuego(framePrincipal.getPanelFinJuego(), this);
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
        List<model.dao.Record> top10 = recordDAO.getTop10Records(); 
        scoC = new ScoreController(this, framePrincipal, top10);
        framePrincipal.mostrarScore(); 
    }

    public void pasarAMenu(){
        if (gc != null){  
            gc.detenerGameLoop();
            if (gc.getStopwatch() != null){
                gc.getStopwatch().detener();
            }
            gc = null;
        }
        framePrincipal.mostrarMenu();

        if (mc != null) {
            mc.iniciarMusica();
        }
    }

    public void guardarRecord (String nombreJugador) {   
        long tiempoMs = gc.getStopwatch().getSegundos(); 
        model.dao.Record nuevoRecord = new model.dao.Record(nombreJugador.trim(), tiempoMs);
        recordDAO.save(nuevoRecord);
        System.out.println("Récord guardado de " + nombreJugador + " en la base de datos.");
        pasarAMenu(); 
    }

    

    

}