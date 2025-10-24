package model.juego;


import javax.sound.sampled.*;
import java.net.URL;


public class SonidoObjeto implements Observador {

    private Clip clip;

    public SonidoObjeto() {
        try {
            URL sonidoURL = getClass().getResource("/assets/music/objeto.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(sonidoURL);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(String evento) {
        if (evento.equals("AGARRAR_OBJETO")) {
            if (clip.isRunning()) clip.stop();
            clip.setFramePosition(0);
            clip.start();
        }
    }
}
