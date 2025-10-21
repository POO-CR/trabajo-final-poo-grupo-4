package model.juego.entidades;

import java.awt.Rectangle;

import controller.juego.GameController;


public class Ghost extends Entity{
    
    private final GameController gc;
    //private final Random random = new Random();
    private long tiempoUltimoMovimiento = 0;
    private static final long MOVIMIENTO_INTERVALO = 80; //ms entre decisiones
   // private final String[] direcciones = {"up", "down", "left", "right"};
    //private int pasosRestantes = 0; // cuantos pasos faltan para completar 1 tile
    //private final int pasosPorTile = 8; // cuantos mini pasos por tile

    private final float escalaFantasma = 1.5f;
    private final int anchoFantasma;
    private final int altoFantasma;


    public Ghost (GameController gc, int xTile, int yTile) {
        this.gc = gc;
        this.mundoX = xTile * gc.getAnchoBloque();
        this.mundoY = yTile * gc.getAltoBloque();

        this.movimiento = 4; //vel de movimiento
        this.direccion = "down";

        
        anchoFantasma = (int)(gc.getAnchoBloque()*escalaFantasma);
        altoFantasma = (int)(gc.getAltoBloque()*escalaFantasma);


        // 50% y 40% de ancho del tile
        // esta desplazado hacia abajo
        int colisionW = (int)(anchoFantasma * 0.5);
        int colisionH = (int)(altoFantasma * 0.4);
        int offsetX = (int)(anchoFantasma * 0.25); // centrado horizontalmente
        int offsetY = (int)(altoFantasma * 0.55); // más abajo

        this.areaColision = new Rectangle(offsetX, offsetY, colisionW, colisionH);
        this.areaColisionDefaultX = offsetX;
        this.areaColisionDefaultY = offsetY;
    }

    @Override
    public boolean getTieneLlave() {
        return false;
    }

    public long getTiempoUltimoMovimiento() { // devuelve el tiempo del ultimo movimiento
        return tiempoUltimoMovimiento;
    }

    public void setTiempoUltimoMovimiento(long t) { 
        this.tiempoUltimoMovimiento = t;
    }

    public static long getMovimientoIntervalo() { // devuelve el intervalo de movimiento
        return MOVIMIENTO_INTERVALO;
    }

    public int getAltoFantasma(){
        return this.altoFantasma;
    }

    public int getAnchoFantasma(){
        return this.anchoFantasma;
    }

}
