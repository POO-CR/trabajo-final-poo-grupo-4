package controller.menu;

import java.awt.event.ActionEvent;
import java.util.List;
import model.dao.Record;
import model.menu.Music;
import view.FramePrincipal;
import view.menu.ScorePanel;
import controller.ControladorPrincipal;

public class ScoreController {

    private ScorePanel view;
    private ControladorPrincipal controladorPrincipal;
    private FramePrincipal framePrincipal;

    public ScoreController(ControladorPrincipal controladorPrincipal, FramePrincipal framePrincipal, List<Record> top10) {
        this.controladorPrincipal = controladorPrincipal;
        this.framePrincipal = framePrincipal;
        this.view = framePrincipal.getScorePanel();

        view.mostrarScores(top10);

        view.setVisible(true);
        
        // Crear y reproducir la música de Scores
            Music scoreMusic = new Music("/assets/music/temascore.wav");
            scoreMusic.reproducirLoop();

        view.getBtnVolver().addActionListener((ActionEvent e) -> {
            scoreMusic.pausar();
            controladorPrincipal.pasarAMenu();
        });
    }
}
