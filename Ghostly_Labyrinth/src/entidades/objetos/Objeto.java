package entidades.objetos;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entidades.laberinto.Labyrinth;
import juego.PanelJuego;

import java.awt.Rectangle;

// esta es la superclase para todos los objetos
public class Objeto {

    public BufferedImage imagen;
    public String nombre;
    public boolean colision = false;
    private PanelJuego pj;
    private Labyrinth lab;
    protected int tilePermitida;
    protected int x, y;
    protected Rectangle areaColision;
    protected int areaColisionDefaultX = 0;
    protected int areaColisionDefaultY = 0;

    public Objeto (PanelJuego pj) {
        this.pj = pj;
        lab = this.pj.getLaberinto();
        //el area de colisión se puede customizar en cada objeto 
        this.areaColision = new Rectangle(0, 0, lab.anchoBloque, lab.altoBloque);
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(imagen, this.x, this.y, lab.anchoBloque, lab.altoBloque, null);
    }

    public int getTilePermitida() {
        return this.tilePermitida;
    }

    public Rectangle getAreaColision () {
        return this.areaColision;
    }

    public void volverDefaultAreaColision () {
        this.areaColision.x = areaColisionDefaultX;
        this.areaColision.y = areaColisionDefaultY;
    }

    
    public int getX () {
        return x;
    }

    public void setX (int x) {
        this.x = x;
    }

    public int getY () {
        return y;
    }

    public void setY (int y) {
        this.y = y;
    }
    
}
