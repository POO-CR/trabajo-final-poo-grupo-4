import javax.sound.sampled.*;
import java.net.URL;

public class TestMusic {
    public static void main(String[] args) throws Exception {
        URL url = TestMusic.class.getResource("/assets/music/FireboyAndWatergirlSoundtrack.wav");
        if (url == null) {
            System.out.println("No se encontró el archivo de audio!");
            return;
        }

        Clip clip = AudioSystem.getClip();
        AudioInputStream ais = AudioSystem.getAudioInputStream(url);
        clip.open(ais);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();

        // Mantener el programa vivo mientras suena
        Thread.sleep(10000); 
    }
}