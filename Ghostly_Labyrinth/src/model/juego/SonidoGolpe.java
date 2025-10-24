package model.juego;

import javax.sound.sampled.*;
import java.net.URL;
import model.juego.Observador;

public class SonidoGolpe implements Observador {

    private Clip clip;

    public SonidoGolpe() {
        try {
            URL sonidoURL = getClass().getResource("/assets/music/golpe.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(sonidoURL);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(String evento) {
        if (evento.equals("GOLPE")) {
            if (clip.isRunning()) clip.stop(); // reinicia si ya estaba sonando
            clip.setFramePosition(0);
            clip.start();
        }
    }
}

