package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entidades.Player;
import entidades.Entity;

import controlador.GameController;

public class PlayerView {

   /* private PlayerView player;
    private BufferedImage up0, up1, up2, down0, down1, down2;
    private BufferedImage left0, left1, left2, right0, right1, right2;
    private Image vidaimg;
    private Image img; */
    
    public PlayerView (GameController controlador) {
        this.controlador = controlador;
    }

    


    public void paintVida(Graphics g) {
        for (int i = 0; i < player.getVida(); i++) {
            int x = 10 + i * (45 + 10);
            g.drawImage(vidaimg, x + 10, 10, 45, 45, null);
        }
    }


}
