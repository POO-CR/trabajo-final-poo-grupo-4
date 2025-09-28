package juego;
import entidades.*;
import entidades.objetos.*;
import entidades.tile.*;

public class ColisionChecker {
    
    PanelJuego panelJuego;

    public ColisionChecker(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
    }

    public void checkearTile(Player entidad) {
        // calcula la posición de las esquinas del rectángulo de colisión
        int entidadLeftX = entidad.getMundoX() + entidad.areaColisionDefaultX;
        int entidadRightX = entidad.getMundoX() + entidad.areaColisionDefaultX + entidad.areaColision.width;
        int entidadTopY = entidad.getMundoY() + entidad.areaColisionDefaultY;
        int entidadBottomY = entidad.getMundoY() + entidad.areaColisionDefaultY + entidad.areaColision.height;

        // dividimos los valores por el tamaño del tile para obtener las coordenadas en fila y columna
        int entidadColumnaIzquierda = entidadLeftX / panelJuego.laberinto.anchoBloque;
        int entidadColumnaDerecha = entidadRightX / panelJuego.laberinto.anchoBloque;
        int entidadFilaSuperior = entidadTopY / panelJuego.laberinto.altoBloque;
        int entidadFilaInferior = entidadBottomY / panelJuego.laberinto.altoBloque;

        if (entidadColumnaDerecha < 0 ) entidadColumnaDerecha = 0;
        if (entidadColumnaIzquierda < 0 ) entidadColumnaIzquierda = 0;
        if (entidadFilaSuperior < 0 ) entidadFilaSuperior = 0;
        if (entidadFilaInferior < 0 ) entidadFilaInferior = 0;    

        
        Tile tile1, tile2;

        // entidad para poder agregar fantasmas también
        switch(entidad.direccion) {
            case "up":
                // predice la siguiente posición (en tiles) según la dirección y velocidad
                entidadFilaSuperior = (entidadTopY - entidad.getMovimiento()) / panelJuego.laberinto.altoBloque;

                // hay solo 2 en frente del personaje, entonces guardamos esas dos tiles
                if (entidadFilaSuperior < 0 ) entidadFilaSuperior = 0;
                tile1 = panelJuego.laberinto.laberintoMat[entidadFilaSuperior] [entidadColumnaIzquierda];
                tile2 = panelJuego.laberinto.laberintoMat[entidadFilaSuperior] [entidadColumnaDerecha];

                entidad.tieneAcceso(tile1);
                entidad.tieneAcceso(tile2);

                if (tile1.colision || tile2.colision){ // && !(tileNum1 == 2 || tileNum2 == 2) && entidad.getTieneLlave() == true
                    entidad.colisionOn = true;
                }
                break;

            case "down":
                entidadFilaInferior = (entidadBottomY + entidad.getMovimiento()) / panelJuego.laberinto.altoBloque;
                if (entidadFilaInferior<0 ) entidadFilaInferior = 0;
                tile1 = panelJuego.laberinto.laberintoMat[entidadFilaInferior] [entidadColumnaIzquierda]; //[entidadColumnaIzquierda][entidadFilaInferior];
                tile2 = panelJuego.laberinto.laberintoMat[entidadFilaInferior] [entidadColumnaDerecha]; //[entidadColumnaDerecha][entidadFilaInferior];

                // tile1 = panelJuego.laberinto.tile[tileNum1];
                // tile2 = panelJuego.laberinto.tile[tileNum2];

                entidad.tieneAcceso(tile1);
                entidad.tieneAcceso(tile2);

                if (tile1.colision || tile2.colision){ // && !(tileNum1 == 2 || tileNum2 == 2) && entidad.getTieneLlave() == true
                    entidad.colisionOn = true;
                }
                break;

            case "left":
                entidadColumnaIzquierda = (entidadLeftX - entidad.getMovimiento()) / panelJuego.laberinto.anchoBloque;
                if (entidadColumnaIzquierda < 0 ) entidadColumnaIzquierda = 0;
                tile1 = panelJuego.laberinto.laberintoMat[entidadFilaSuperior][entidadColumnaIzquierda]; //[entidadColumnaIzquierda][entidadFilaSuperior];
                tile2 = panelJuego.laberinto.laberintoMat[entidadFilaInferior][entidadColumnaIzquierda]; //[entidadColumnaIzquierda][entidadFilaInferior];
                // tile1 = panelJuego.laberinto.tile[tileNum1];
                // tile2 = panelJuego.laberinto.tile[tileNum2];

                entidad.tieneAcceso(tile1);
                entidad.tieneAcceso(tile2);

                if (tile1.colision || tile2.colision){ // && !(tileNum1 == 2 || tileNum2 == 2) && entidad.getTieneLlave() == true
                    entidad.colisionOn = true;
                }
                break;
            case "right":
                entidadColumnaDerecha = (entidadRightX + entidad.getMovimiento()) / panelJuego.laberinto.anchoBloque;
                if (entidadColumnaDerecha < 0 ) entidadColumnaDerecha = 0;
                tile1 = panelJuego.laberinto.laberintoMat[entidadFilaSuperior][entidadColumnaDerecha]; //[entidadColumnaDerecha][entidadFilaSuperior];
                tile2 = panelJuego.laberinto.laberintoMat[entidadFilaInferior][entidadColumnaDerecha]; //[entidadColumnaDerecha][entidadFilaInferior];
                // tile1 = panelJuego.laberinto.tile[tileNum1];
                // tile2 = panelJuego.laberinto.tile[tileNum2];

                entidad.tieneAcceso(tile1);
                entidad.tieneAcceso(tile2);

                if (tile1.colision || tile2.colision){ // && !(tileNum1 == 2 || tileNum2 == 2) && entidad.getTieneLlave() == true
                    entidad.colisionOn = true;
                }
                break;

        }

    }


    // TODO: pensar esto para no estar preguntando si es el player
    public int checkearObjeto (Entity e, boolean jugador) {

        int index = 999;

        for (int i= 0; i < panelJuego.obj.length; i++){
            Objeto objeto = panelJuego.obj[i];
            if (objeto != null) {

                // vemos la posición de la hitbox de la entidad segun la posición de la entidad
                e.areaColision.x += e.getMundoX();
                e.areaColision.y += e.getMundoY();

                // vemos la posición de la hitbox del objeto
                objeto.getAreaColision().x += objeto.getX();
                objeto.getAreaColision().y += objeto.getY();

                switch (e.direccion) {
                case "up":
                    e.areaColision.y -= e.getMovimiento();
                    if (e.areaColision.intersects(objeto.getAreaColision())){
                        if (objeto.colision == true) {
                            e.colisionOn = true;
                        }
                        if (jugador) {
                            return i;
                        }
                    }
                    break;
                case "down":
                    e.areaColision.y += e.getMovimiento();
                    if (e.areaColision.intersects(objeto.getAreaColision())){
                        if (objeto.colision == true) {
                            e.colisionOn = true;
                        }
                        if (jugador) {
                            return i;
                        }
                    }
                    break;
                case "left":
                    e.areaColision.x -= e.getMovimiento();
                    if (e.areaColision.intersects(objeto.getAreaColision())){
                        if (objeto.colision == true) {
                            e.colisionOn = true;
                        }
                        if (jugador) {
                            return i;
                        }    
                    }
                    break;
                case "right":
                    e.areaColision.x += e.getMovimiento();
                    if (e.areaColision.intersects(objeto.getAreaColision())){
                        if (objeto.colision == true) {
                            e.colisionOn = true;
                        }
                        if (jugador) {
                            return i;
                        }
                    }
                    break;
                }
                panelJuego.obj[i].volverDefaultAreaColision();
            }
            e.areaColision.x = e.areaColisionDefaultX;
            e.areaColision.y = e.areaColisionDefaultY;

        }

        return index;

    }

}

