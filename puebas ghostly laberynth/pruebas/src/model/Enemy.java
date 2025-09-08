// model/Enemy.java
package model;

import java.awt.Point;

public abstract class Enemy extends Entity {
    protected Enemy(Point start) { super(start); }

    // Cada enemigo implementa su IA aca
    public abstract void stepAI(Labyrinth m, Player p);
}