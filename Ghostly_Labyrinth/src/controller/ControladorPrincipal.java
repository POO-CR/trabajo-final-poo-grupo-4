package controller;

import view.FramePrincipal;
import controller.juego.*;
import controller.menu.*;

public class ControladorPrincipal {

    private final FramePrincipal framePrincipal;
    private GameController gc;
    private MenuController mc;
    private TutorialController tutoC;

    // ir cambiando ventanas usando métodos de FramePrincipal
    public ControladorPrincipal (){
        framePrincipal = new FramePrincipal();
        mc = new MenuController(this, framePrincipal);
        framePrincipal.mostrarMenu();
        framePrincipal.setVisible(true);
    }

    public void pasarAJuego(){
        gc = new GameController(framePrincipal);
        gc.iniciarJuego("usuario");
        framePrincipal.mostrarJuego();
    }

    public void pasarATutorial(){
        tutoC = new TutorialController(framePrincipal);
        tutoC.iniciarTutorial();
        framePrincipal.mostrarJuego();
    }

    public void pasarAMenu(){
        framePrincipal.mostrarMenu();
    }

    

}