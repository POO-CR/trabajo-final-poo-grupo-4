// model/Entity.java
package model;

import java.awt.Graphics;
import java.awt.Point;

public abstract class Entity {
    private final Point pos;       // ENCAPSULADO
    protected int speed = 1;       // celdas por tick

    protected Entity(Point start) { // protected: solo subclases pueden construir
        this.pos = new Point(start);
    }

    public final Point getPos() {           // lectura segura (copia)
        return new Point(pos);
    }

    // helper para mover respetando paredes/puertas
    protected final boolean tryMove(Labyrinth m, int dx, int dy) {
        int r = pos.y + dy, c = pos.x + dx;
        if (m.isWalkable(r, c)) {
            pos.translate(dx, dy);
            return true;
        }
        return false;
    }

    // Cada subclase define cómo se dibuja
    public abstract void paint(Graphics g, int tileW, int tileH, int offX, int offY);
}