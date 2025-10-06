package juego;
import controlador.GameController;
import entidades.*;
import entidades.laberinto.*;
import entidades.objetos.*;
import entidades.tile.*;
import java.util.ArrayList;

public class ColisionChecker {
    
    GameController gc;
    ArrayList<Objeto> obj;
    Nivel nivel;

    public ColisionChecker(GameController gc, ArrayList<Objeto> obj, Nivel nivel) {
        this.gc = gc;
        this.obj = obj;
        this.nivel = nivel;
    }

    public void checkearTile(Entity entidad) {
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
                if (entidadFilaSuperior < 0 ) entidadFilaSuperior = 0;
                tile1 = nivel.getTile(entidadFilaSuperior, entidadColumnaIzquierda);
                tile2 = nivel.getTile(entidadFilaSuperior, entidadColumnaDerecha);

                entidad.tieneAcceso(tile1);
                entidad.tieneAcceso(tile2);

                if (tile1.colision || tile2.colision){ // && !(tileNum1 == 2 || tileNum2 == 2) && entidad.getTieneLlave() == true
                    entidad.colisionOn = true;
                }
            }

            case "down" -> {
                entidadFilaInferior = (entidadBottomY + entidad.getMovimiento()) / gc.getAltoBloque();
                if (entidadFilaInferior<0 ) entidadFilaInferior = 0;
                tile1 = nivel.getTile(entidadFilaInferior, entidadColumnaIzquierda);
                tile2 = nivel.getTile(entidadFilaInferior, entidadColumnaDerecha);
                
                // tile1 = lab.tile[tileNum1];
                // tile2 = lab.tile[tileNum2];

                entidad.tieneAcceso(tile1);
                entidad.tieneAcceso(tile2);

                if (tile1.colision || tile2.colision){ // && !(tileNum1 == 2 || tileNum2 == 2) && entidad.getTieneLlave() == true
                    entidad.colisionOn = true;
                }
            }

            case "left" -> {
                entidadColumnaIzquierda = (entidadLeftX - entidad.getMovimiento()) / gc.getAnchoBloque();
                if (entidadColumnaIzquierda < 0 ) entidadColumnaIzquierda = 0;
                tile1 = nivel.getTile(entidadFilaSuperior, entidadColumnaIzquierda);
                tile2 = nivel.getTile(entidadFilaInferior, entidadColumnaIzquierda); 
                // tile1 = lab.tile[tileNum1];
                // tile2 = lab.tile[tileNum2];

                entidad.tieneAcceso(tile1);
                entidad.tieneAcceso(tile2);

                if (tile1.colision || tile2.colision){ // && !(tileNum1 == 2 || tileNum2 == 2) && entidad.getTieneLlave() == true
                    entidad.colisionOn = true;
                }
            }
            case "right" -> {
                entidadColumnaDerecha = (entidadRightX + entidad.getMovimiento()) / gc.getAnchoBloque();
                if (entidadColumnaDerecha < 0 ) entidadColumnaDerecha = 0;
                tile1 = nivel.getTile(entidadFilaSuperior, entidadColumnaDerecha); //[entidadColumnaDerecha][entidadFilaSuperior];
                tile2 = nivel.getTile(entidadFilaInferior, entidadColumnaDerecha); //[entidadColumnaDerecha][entidadFilaInferior];
                // tile1 = lab.tile[tileNum1];
                // tile2 = lab.tile[tileNum2];

                entidad.tieneAcceso(tile1);
                entidad.tieneAcceso(tile2);

                if (tile1.colision || tile2.colision){ // && !(tileNum1 == 2 || tileNum2 == 2) && entidad.getTieneLlave() == true
                    entidad.colisionOn = true;
                }
            }

        }

    }


    // TODO: pensar esto para no estar preguntando si es el player
    public Objeto checkearObjeto (Entity e) {

        for (Objeto objeto: obj) {
            if (objeto != null) {

                // vemos la posición de la hitbox de la entidad segun la posición de la entidad
                e.areaColision.x += e.getMundoX();
                e.areaColision.y += e.getMundoY();

                // vemos la posición de la hitbox del objeto
                objeto.getAreaColision().x += objeto.getX();
                objeto.getAreaColision().y += objeto.getY();

                // TODO: esto lo cambié, chequear si funciona bien
                switch (e.direccion) {
                case "up":
                    e.areaColision.y -= e.getMovimiento();
                    break;
                case "down":
                    e.areaColision.y += e.getMovimiento();
                    break;
                case "left":
                    e.areaColision.x -= e.getMovimiento();
                    break;
                case "right":
                    e.areaColision.x += e.getMovimiento();
                    break;
                }
                if (e.areaColision.intersects(objeto.getAreaColision())){
                    if (objeto.getColision() == true) {
                        e.colisionOn = true;
                        return objeto;
                    }
                }

                objeto.volverDefaultAreaColision();
            }
            e.areaColision.x = e.areaColisionDefaultX;
            e.areaColision.y = e.areaColisionDefaultY;

        }

        return null;

    }

}

