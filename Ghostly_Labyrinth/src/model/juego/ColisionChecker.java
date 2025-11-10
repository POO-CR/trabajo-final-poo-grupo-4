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
    private long ultimoCambioNivel;

    public ColisionChecker(GameController gc) {
        this.gc = gc;
    }


    public void checkearTile(Entity entidad) {
        this.nivel = gc.getNivel();

        // calcula la posición de las esquinas del rectángulo de colisión
        int entidadLeftX = entidad.getMundoX() + entidad.getAreaColisionDefaultX();
        int entidadRightX = entidad.getMundoX() + entidad.getAreaColisionDefaultX() + entidad.getAnchoColision();
        int entidadTopY = entidad.getMundoY() + entidad.getAreaColisionDefaultY();
        int entidadBottomY = entidad.getMundoY() + entidad.getAreaColisionDefaultY() + entidad.getAltoColision();

        // dividimos los valores por el tamaño del tile para obtener las coordenadas en fila y columna
        // EJEMPLO: si el personaje esta en x=48 y el tile es de 20, entonces 48/20=2.4 -> fila 2 (ya que la fila 0 es de 0 a 19, fila 1 de 20 a 39, fila 2 de 40 a 59)
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
        switch(entidad.getDireccion()) {
            case "up" -> {
                // predice la siguiente posición (en tiles) según la dirección y velocidad
                entidadFilaSuperior = (entidadTopY - entidad.getMovimiento()) / gc.getAltoBloque();

                // hay solo 2 en frente del personaje, entonces guardamos esas dos tiles
                // y vemos si alguna de las dos tiene colisión
                tile1 = nivel.getTile(entidadFilaSuperior, entidadColumnaIzquierda);
                tile2 = nivel.getTile(entidadFilaSuperior, entidadColumnaDerecha);
                if (tile1.tieneAcceso(this, entidad) || tile2.tieneAcceso(this, entidad)){ 
                    entidad.setColisionOn(true);
                }
            }   

            case "down" -> {
                entidadFilaInferior = (entidadBottomY + entidad.getMovimiento()) / gc.getAltoBloque();

                tile1 = nivel.getTile(entidadFilaInferior, entidadColumnaIzquierda);
                tile2 = nivel.getTile(entidadFilaInferior, entidadColumnaDerecha);
                if (tile1.tieneAcceso(this, entidad) || tile2.tieneAcceso(this, entidad)){ 
                    entidad.setColisionOn(true);
                }
            } 
            

            case "left" -> {
                entidadColumnaIzquierda = (entidadLeftX - entidad.getMovimiento()) / gc.getAnchoBloque();

                if (entidadColumnaIzquierda < 0 ) entidadColumnaIzquierda = 0;
                tile1 = nivel.getTile(entidadFilaSuperior, entidadColumnaIzquierda);
                tile2 = nivel.getTile(entidadFilaInferior, entidadColumnaIzquierda); 
                if (tile1.tieneAcceso(this, entidad) || tile2.tieneAcceso(this, entidad)){ 
                    entidad.setColisionOn(true);
                }
            }

            

            case "right" -> {
                entidadColumnaDerecha = (entidadRightX + entidad.getMovimiento()) / gc.getAnchoBloque();
                if (entidadColumnaDerecha < 0 ) entidadColumnaDerecha = 0;
                tile1 = nivel.getTile(entidadFilaSuperior, entidadColumnaDerecha); 
                tile2 = nivel.getTile(entidadFilaInferior, entidadColumnaDerecha); 

                if (tile1.tieneAcceso(this, entidad) || tile2.tieneAcceso(this, entidad)){ 
                    entidad.setColisionOn(true);
                }
            }
            
        }

    }

    public void llamarCambioNivel(){
        ultimoCambioNivel = System.currentTimeMillis();
        gc.cambiarNivel();
    }


    public Objeto checkearObjeto (Entity e) {
        obj = gc.getObjArray();

        for (Objeto objeto: obj) {
            if (objeto != null) {

                // vemos la posición de la hitbox de la entidad segun la posición de la entidad
                e.getAreaColision().x += e.getMundoX();
                e.getAreaColision().y += e.getMundoY();

                // vemos la posición de la hitbox del objeto
                objeto.getAreaColision().x += objeto.getX();
                objeto.getAreaColision().y += objeto.getY();

                switch (e.getDireccion()) {
                case "up" -> e.getAreaColision().y -= e.getMovimiento();
                case "down" -> e.getAreaColision().y += e.getMovimiento();
                case "left" -> e.getAreaColision().x -= e.getMovimiento();
                case "right" -> e.getAreaColision().x += e.getMovimiento();
                }
                if (e.getAreaColision().intersects(objeto.getAreaColision())){
                    if (objeto.getColision() == true) {
                        e.setColisionOn(true);

                    }
                    return objeto;
                }

                objeto.volverDefaultAreaColision();
            }
            e.getAreaColision().x = e.getAreaColisionDefaultX();
            e.getAreaColision().y = e.getAreaColisionDefaultY();

        }

        return null;

    }

    public long getUltimoCambioNivel(){
        return this.ultimoCambioNivel;
    }

}

