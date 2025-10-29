package model.juego.objetos;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import controller.juego.GameController;
import model.juego.entidades.Player;

import model.juego.objetos.efectos.PowerUpEffect;

// esta es la superclase para todos los objetos
public abstract class Objeto {

    protected BufferedImage imagen;
    private String nombre;
    private boolean colision = false;
    protected int tilePermitida;
    private int x, y;
    protected Rectangle areaColision;
    protected int areaColisionDefaultX = 0;
    protected int areaColisionDefaultY = 0;

    public Objeto (GameController gc) {
        //el area de colisión se puede customizar en cada objeto 
        this.areaColision = new Rectangle(0, 0, gc.getAnchoBloque(), gc.getAltoBloque());
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

    // para interactuar con el jugador, devuelve un comando concreto
    public abstract PowerUpEffect interactuar(Player jugador);

    
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

    public BufferedImage getImage () {
        return imagen;
    }

    public String getNombre (){
        return this.nombre;
    }

    protected void setNombre(String nombre) {
        this.nombre = nombre; 
    }

    public void setColision(Boolean colision) {
        this.colision = colision;
    }

    public Boolean getColision (){
        return this.colision;
    }
    
}
