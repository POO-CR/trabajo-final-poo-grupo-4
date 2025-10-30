package controller.juego;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

import controller.ControladorPrincipal;
import controller.juego.GameController;
import controller.juego.TutorialController;
import view.FramePrincipal;
import view.menu.MenuPanel;
import view.juego.PantallaDerrota;


public class ControladorDerrota {
    private PantallaDerrota view;
    private ControladorPrincipal controladorPrincipal;
    private FramePrincipal framePrincipal;

    public ControladorDerrota (ControladorPrincipal controladorPrincipal, FramePrincipal framePrincipal) {
        this.framePrincipal = framePrincipal;
        this.view = framePrincipal.getPantallaDerrota();
        this.controladorPrincipal = controladorPrincipal;
        this.actions();
    }

    private void actions() {
        // boton reiniciar
        this.view.getBtnReiniciar().addActionListener((ActionEvent e) -> {
            // Reiniciar el juego
            this.controladorPrincipal.pasarAJuego();
        });

        this.view.getBtnMenuPrincipal().addActionListener((ActionEvent e) -> {
            // Volver al menú principal
            this.controladorPrincipal.pasarAMenu();
        });
    }


}
