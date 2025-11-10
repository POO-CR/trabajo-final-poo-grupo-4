package model.juego.entidades;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import model.juego.tile.Tile;

public abstract class Entity {
    protected int mundoX, mundoY; // posicion en el mundo
    protected int movimiento;
    private float multiplicadorVelocidad = 1.0f;
    protected String nombre;

    protected BufferedImage up0, up1, up2, down0, down1, down2, 
    left0, left1, left2, right0, right1, right2;
    protected String direccion;
    protected String ultimaDireccion;
    
    protected int spriteCounter = 0;
    protected int spriteNum = 1;

    protected Rectangle areaColision;
    protected int areaColisionDefaultX, areaColisionDefaultY;
    protected boolean colisionOn = false;

    public abstract boolean getTieneLlave(); // para saber si la entidad tiene la llave, cada entidad lo implementa a su manera

    public int getMundoX() {
        return mundoX;
    }
    
    public void setMundoX(int mundoX) {
        this.mundoX = mundoX;
    }

    public int getMundoY() {
        return mundoY;
    }

    public void setMundoY(int mundoY) {
        this.mundoY = mundoY;
    }

    public int getSpriteCounter() {
        return spriteCounter;
    }

    public void setSpriteCounter(int spriteCounter) {
        this.spriteCounter = spriteCounter;
    }

    public int getAreaColisionDefaultX() {
        return areaColisionDefaultX;
    }

    public int getAreaColisionDefaultY() {
        return areaColisionDefaultY;
    }

    public int getAnchoColision() {
        return areaColision.width;
    }

    public int getAltoColision() {
        return areaColision.height;
    }

    public Rectangle getAreaColision() {
        return areaColision;
    }

    public boolean getColisionOn() {
        return colisionOn;
    }

    public boolean setColisionOn(boolean estado) {
        this.colisionOn = estado;
        return colisionOn;
    }

    public int getSpriteNum() {
        return spriteNum;
    }

    public void setSpriteNum(int spriteNum) {
        this.spriteNum = spriteNum;
    }   

    public String getUltimaDireccion() {
        return ultimaDireccion;
    }




    public int getMovimiento() {
        return (int) (multiplicadorVelocidad * movimiento);
    }

    public void setMultiplicadorVelocidad(float multiplicador) {
        this.multiplicadorVelocidad = multiplicador;
    }

    public void setMovimiento(int m) {
        this.movimiento = m;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setUltimaDireccion(String ultimaDireccion) {
        this.ultimaDireccion = ultimaDireccion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String d) {
        this.direccion = d;
    }


}
