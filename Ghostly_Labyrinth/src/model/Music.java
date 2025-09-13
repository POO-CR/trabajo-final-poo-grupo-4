package model;

import javax.sound.sampled.*;
import java.net.URL;
import java.io.IOException;

public class Music {

    private Clip clip;

    public Music(String resourcePath) {
        try {
            URL url = getClass().getResource(resourcePath);
            if (url == null) {
                System.err.println("No se encontró el archivo de música: " + resourcePath);
                return;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Para iniciar la primera vez
    public void reproducirLoop() {
        if (clip != null) {
            if (!clip.isRunning()) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
            }
        }
    }

    // Para pausar la musica del juego
    public void pausar() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    // Reanuda desde donde se quedo
    public void reanudar() {
        if (clip != null && !clip.isRunning()) {
            clip.start(); // No setFramePosition, arranca desde donde quedó
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    // Para saber si se esta reproduciendo
    public boolean isReproduciendo() {
        return clip != null && clip.isRunning();
    }
}