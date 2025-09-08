// model/Player.java
package model;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.Point;

public class Player extends Entity {
    private boolean tieneLlave = false;

    public Player(Point start) { super(start); }

    public boolean hasKey() { return tieneLlave; }
    public void setHasKey(boolean v){ tieneLlave = v; }

    public void teclaPresionada(KeyEvent e, Labyrinth lab, Runnable onNextLevel){
        int dx=0, dy=0;
        switch (e.getKeyCode()){
            case KeyEvent.VK_W -> dy = -1;
            case KeyEvent.VK_S -> dy =  1;
            case KeyEvent.VK_A -> dx = -1;
            case KeyEvent.VK_D -> dx =  1;
            default -> { return; }
        }

        // puerta (5) cerrada: si tengo llave, se abre
        var nextR = getPos().y + dy;
        var nextC = getPos().x + dx;
        if (lab.isDoor(nextR, nextC) && !lab.isPuertaAbierta()){
            if (tieneLlave) lab.abrirPuerta(); else return;
        }

        if (tryMove(lab, dx, dy)) {
            // recoger llave si hay
            lab.pickupKeyAt(getPos().y, getPos().x).ifPresent(it -> tieneLlave = true);
            // hab. especial (3) → siguiente nivel
            if (lab.isRoom(getPos().y, getPos().x)) onNextLevel.run();
        }
    }

    @Override
    public void paint(Graphics g, int tw, int th, int offX, int offY) {
        Point p = getPos();
        int x = offX + p.x * tw;
        int y = offY + p.y * th;
        g.fillRect(x + tw/8, y + th/8, (int)(tw*0.75), (int)(th*0.75));
    }
}