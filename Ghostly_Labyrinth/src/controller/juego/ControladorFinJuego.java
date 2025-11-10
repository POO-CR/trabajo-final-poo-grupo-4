package controller.juego;

import controller.ControladorPrincipal;
import model.menu.RegisterStartmodel;
import view.juego.PanelFinJuego;
import java.awt.event.ActionEvent;


public class ControladorFinJuego {
    private PanelFinJuego view;
    private ControladorPrincipal controladorPrincipal;

    public ControladorFinJuego (PanelFinJuego view, ControladorPrincipal controladorPrincipal){
        this.view = view;
        this.controladorPrincipal = controladorPrincipal;
        this.addListeners();
    }

    private void addListeners(){
        view.getCrearButton().addActionListener((ActionEvent e) -> {
            String apodo = view.getUsuario().trim();
            String mensajeError = RegisterStartmodel.validarApodo(apodo);
            if (mensajeError != null){
                view.setResultado(mensajeError);
            } else {
                controladorPrincipal.guardarRecord(apodo);
            }
        });

        view.getVolverButton().addActionListener((ActionEvent e) -> {
            controladorPrincipal.pasarAMenu();
        });

        view.getBotonReiniciar().addActionListener((ActionEvent e) -> {
            controladorPrincipal.pasarAJuego();
        });
    }

    
}
