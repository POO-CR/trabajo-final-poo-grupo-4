package model;

import java.awt.*;
import java.util.*;

public class RandomGhost extends Enemy {
    private final Random rng = new Random();

    // --- velocidad ---
    private int moveCooldown = 0;       // ticks restantes para el proximo paso
    private int COOLDOWN_TICKS = 2;     // mueve 1 celda cada 2 ticks 

    // direccion actual + pasos a mantenerla (estetica del movimiento)
    private int dx = 0, dy = 0;
    private int stepsLeft = 0;

    public RandomGhost(Point start) { super(start); }

    /** Permite ajustar la “lentitud” desde afuera: 2..6 suele sentirse bien */
    public RandomGhost withCooldown(int ticks) { this.COOLDOWN_TICKS = Math.max(1, ticks); return this; }

    @Override
public void stepAI(Labyrinth m, Player p) {
    // cooldown: ¿toca mover este tick?
    if (moveCooldown > 0) { moveCooldown--; return; }

    // si ya no quedan pasos en la dirección, elegir una nueva
    if (stepsLeft <= 0) {
        pickNewDirection(m);
    }

    // intentar mover 1 celda (solo una vez)
    boolean moved = tryMove(m, dx, dy);

    if (moved) {
        stepsLeft = Math.max(0, stepsLeft - 1);
    } else {
        stepsLeft = 0;
    }

    // reinicia cooldown
    moveCooldown = COOLDOWN_TICKS;
}

    private void pickNewDirection(Labyrinth m) {
        int[][] dirs = { {1,0}, {-1,0}, {0,1}, {0,-1} };
        int r = getPos().y, c = getPos().x;

        java.util.List<int[]> opciones = new ArrayList<>();
        for (int[] d : dirs) {
            int nr = r + d[1], nc = c + d[0];
            if (m.isWalkable(nr, nc) && !(d[0]==-dx && d[1]==-dy)) {
                opciones.add(d);
            }
        }
        if (opciones.isEmpty()) {
            for (int[] d : dirs) {
                int nr = r + d[1], nc = c + d[0];
                if (m.isWalkable(nr, nc)) opciones.add(d);
            }
        }

        int[] chosen = opciones.isEmpty() ? new int[]{0,0} : opciones.get(rng.nextInt(opciones.size()));
        dx = chosen[0]; dy = chosen[1];
        stepsLeft = 2 + rng.nextInt(5);
    }

    @Override
    public void paint(Graphics g, int tw, int th, int offX, int offY) {
        Point p = getPos();
        int x = offX + p.x * tw;
        int y = offY + p.y * th;
        g.setColor(new Color(150, 100, 200));
        g.fillOval(x + tw/6, y + th/6, (int)(tw*0.7), (int)(th*0.7));
        g.setColor(Color.BLACK);
        g.drawOval(x + tw/6, y + th/6, (int)(tw*0.7), (int)(th*0.7));
    }
}