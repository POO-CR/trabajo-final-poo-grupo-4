package model.juego;
import controller.juego.GameController;
import java.util.ArrayList;
import model.juego.entidades.*;
import model.juego.laberinto.*;
import model.juego.objetos.*;
import model.juego.tile.*;

public class ColisionChecker {
    
    private GameController gc;
    private ArrayList<Objeto> obj;
    private Nivel nivel;

    public ColisionChecker(GameController gc) {
        this.gc = gc;
    }

    public Boolean estaEnSpawn (Entity personaje){
        this.nivel = gc.getNivel();
        return (personaje.getMundoX() == nivel.getPosDefaultX()*gc.getAnchoBloque() && personaje.getMundoY() == nivel.getPosDefaultY()*gc.getAltoBloque());
    }

    public void checkearTile(Entity entidad) {
        this.nivel = gc.getNivel();
        // calcula la posición de las esquinas del rectángulo de colisión
        int entidadLeftX = entidad.getMundoX() + entidad.areaColisionDefaultX;
        int entidadRightX = entidad.getMundoX() + entidad.areaColisionDefaultX + entidad.areaColision.width;
        int entidadTopY = entidad.getMundoY() + entidad.areaColisionDefaultY;
        int entidadBottomY = entidad.getMundoY() + entidad.areaColisionDefaultY + entidad.areaColision.height;

        // dividimos los valores por el tamaño del tile para obtener las coordenadas en fila y columna
        int entidadColumnaIzquierda = entidadLeftX / gc.getAnchoBloque();
        int entidadColumnaDerecha = entidadRightX / gc.getAnchoBloque();
        int entidadFilaSuperior = entidadTopY / gc.getAltoBloque();
        int entidadFilaInferior = entidadBottomY / gc.getAltoBloque();

        if (entidadColumnaDerecha < 0 ) entidadColumnaDerecha = 0;
        if (entidadColumnaIzquierda < 0 ) entidadColumnaIzquierda = 0;
        if (entidadFilaSuperior < 0 ) entidadFilaSuperior = 0;
        if (entidadFilaInferior < 0 ) entidadFilaInferior = 0;    

        
        Tile tile1, tile2;

        
        // entidad para poder agregar fantasmas también
        switch(entidad.direccion) {
            case "up" -> {
                // predice la siguiente posición (en tiles) según la dirección y velocidad
                entidadFilaSuperior = (entidadTopY - entidad.getMovimiento()) / gc.getAltoBloque();

                // hay solo 2 en frente del personaje, entonces guardamos esas dos tiles
                tile1 = nivel.getTile(entidadFilaSuperior, entidadColumnaIzquierda);
                tile2 = nivel.getTile(entidadFilaSuperior, entidadColumnaDerecha);
                if (tile1.tieneAcceso(this, entidad) || tile2.tieneAcceso(this, entidad)){ 
                    entidad.colisionOn = true;
                }
            }   

            case "down" -> {
                entidadFilaInferior = (entidadBottomY + entidad.getMovimiento()) / gc.getAltoBloque();

                tile1 = nivel.getTile(entidadFilaInferior, entidadColumnaIzquierda);
                tile2 = nivel.getTile(entidadFilaInferior, entidadColumnaDerecha);
                if (tile1.tieneAcceso(this, entidad) || tile2.tieneAcceso(this, entidad)){ 
                    entidad.colisionOn = true;
                }
            } 
            

            case "left" -> {
                entidadColumnaIzquierda = (entidadLeftX - entidad.getMovimiento()) / gc.getAnchoBloque();

                if (entidadColumnaIzquierda < 0 ) entidadColumnaIzquierda = 0;
                tile1 = nivel.getTile(entidadFilaSuperior, entidadColumnaIzquierda);
                tile2 = nivel.getTile(entidadFilaInferior, entidadColumnaIzquierda); 
                if (tile1.tieneAcceso(this, entidad) || tile2.tieneAcceso(this, entidad)){ 
                    entidad.colisionOn = true;
                }
            }

            

            case "right" -> {
                entidadColumnaDerecha = (entidadRightX + entidad.getMovimiento()) / gc.getAnchoBloque();
                if (entidadColumnaDerecha < 0 ) entidadColumnaDerecha = 0;
                tile1 = nivel.getTile(entidadFilaSuperior, entidadColumnaDerecha); 
                tile2 = nivel.getTile(entidadFilaInferior, entidadColumnaDerecha); 

                if (tile1.tieneAcceso(this, entidad) || tile2.tieneAcceso(this, entidad)){ 
                    entidad.colisionOn = true;
                }
            }
            
        }

    }

    public void llamarCambioNivel(){
        gc.cambiarNivel();
    }


    public Objeto checkearObjeto (Entity e) {
        obj = gc.getObjArray();

        for (Objeto objeto: obj) {
            if (objeto != null) {

                // vemos la posición de la hitbox de la entidad segun la posición de la entidad
                e.areaColision.x += e.getMundoX();
                e.areaColision.y += e.getMundoY();

                // vemos la posición de la hitbox del objeto
                objeto.getAreaColision().x += objeto.getX();
                objeto.getAreaColision().y += objeto.getY();

                switch (e.direccion) {
                case "up" -> e.areaColision.y -= e.getMovimiento();
                case "down" -> e.areaColision.y += e.getMovimiento();
                case "left" -> e.areaColision.x -= e.getMovimiento();
                case "right" -> e.areaColision.x += e.getMovimiento();
                }
                if (e.areaColision.intersects(objeto.getAreaColision())){
                    if (objeto.getColision() == true) {
                        e.colisionOn = true;
                        
                    }
                    return objeto;
                }

                objeto.volverDefaultAreaColision();
            }
            e.areaColision.x = e.areaColisionDefaultX;
            e.areaColision.y = e.areaColisionDefaultY;

        }

        return null;

    }

}

