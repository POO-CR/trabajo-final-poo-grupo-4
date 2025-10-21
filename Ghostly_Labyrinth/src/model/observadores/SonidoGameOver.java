package model.observadores;

import javax.sound.sampled.*;


import java.net.URL;

public class SonidoGameOver implements Observador {

    private Clip clip;

    public SonidoGameOver() {
        try {
            URL sonidoURL = getClass().getResource("/assets/music/perdio.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(sonidoURL);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(String evento) {
        if (evento.equals("GAME_OVER")) {
            if (clip.isRunning()) clip.stop();
            clip.setFramePosition(0);
            clip.start();
        }
    }
}
