package model.juego.entidades;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import model.juego.tile.Tile;

public abstract class Entity {
    protected int mundoX, mundoY; // posicion en el mundo
    //protected int x;
    //protected int y;
    private int movimiento;
    private float multiplicadorVelocidad = 1.0f;
    protected String nombre;

    public BufferedImage up0, up1, up2, down0, down1, down2, 
    left0, left1, left2, right0, right1, right2;
    public String direccion;
    public String ultimaDireccion;
    
    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle areaColision;
    public int areaColisionDefaultX, areaColisionDefaultY;
    public boolean colisionOn = false;

    public abstract void tieneAcceso(Tile tile);

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
