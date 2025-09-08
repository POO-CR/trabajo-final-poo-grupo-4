// model/Ghost.java
package model;

import java.awt.*;
import java.awt.Point;

public class Ghost extends Enemy {
    public Ghost(Point start) { super(start); }

    @Override
    public void stepAI(Labyrinth m, Player p) {
        // IA simple: moverse 1 celda hacia el jugador si puede
        Point me = getPos();
        Point pl = p.getPos();
        int dx = Integer.compare(pl.x, me.x);
        int dy = Integer.compare(pl.y, me.y);
        // probar eje X primero, si no se puede, probar Y
        if (!tryMove(m, dx, 0)) {
            tryMove(m, 0, dy);
        }
    }

    @Override
    public void paint(Graphics g, int tw, int th, int offX, int offY) {
        Point p = getPos();
        int x = offX + p.x * tw;
        int y = offY + p.y * th;
        g.fillOval(x + tw/6, y + th/6, (int)(tw*0.7), (int)(th*0.7));
    }
}