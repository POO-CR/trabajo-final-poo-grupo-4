package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entidades.Player;
import entidades.Entity;


public class PlayerView {

    private PlayerView player;
    private BufferedImage up0, up1, up2, down0, down1, down2;
    private BufferedImage left0, left1, left2, right0, right1, right2;
    private Image vidaimg;
    private Image img;
     

     public void cargarSprites(){
        try {
            up0 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/espalda_0.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/espalda_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/espalda_2.png"));
            down0 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/frente_0.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/frente_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/frente_2.png"));
            left0 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/izq_0.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/izq_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/izq_2.png"));
            right0 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/der_0.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/der_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/der_2.png"));
            
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public void draw(Graphics2D grafico){
        
        grafico.drawImage(player.imagen, jugador.getScreenX(), jugador.getScreenX(), jugador.getAnchoPersonaje(), jugador.getAltoPersonaje(), null); // dibuja el jugador en pantalla
    }

    public void paintVida(Graphics g) {
        for (int i = 0; i < player.getVida(); i++) {
            int x = 10 + i * (45 + 10);
            g.drawImage(vidaimg, x + 10, 10, 45, 45, null);
        }
    }


}
