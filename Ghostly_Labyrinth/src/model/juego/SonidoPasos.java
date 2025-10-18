package model.juego;

import javax.sound.sampled.*;
import java.net.URL;
import model.juego.entidades.Player;
import model.juego.Observador;

public class SonidoPasos implements Observador {
    
    private Clip clip;

    public SonidoPasos() {
        try {
            URL sonidoURL = getClass().getResource("/assets/music/pasos.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(sonidoURL);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(String evento) {
        if (evento.equals("MOVIENDOSE")) {
            if (!clip.isRunning()) {
                clip.setFramePosition(0);
                clip.start();
            }
        } else if (evento.equals("QUIETO")) {
            clip.stop();
        }
    }
}
