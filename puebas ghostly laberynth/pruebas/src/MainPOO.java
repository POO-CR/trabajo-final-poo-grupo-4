// MainPOO.java  (ajustá package/imports a tu estructura)
import javax.swing.*;
import java.awt.*;
//import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

// IMPORTANTE: estas clases deben existir en tu proyecto:
// model.Labyrinth, model.Player, model.Enemy, model.Ghost, model.Item
import model.*;

public class MainPOO {

    // --- panel de juego mínimo que dibuja grid + entidades ---
    static class GamePanel extends JPanel {
    private final Labyrinth lab;
    private final Player player;
    private final java.util.List<Enemy> enemies;

    // NUEVO: guardar el timer para poder frenarlo y un flag de estado
    private javax.swing.Timer enemyTimer;   // NUEVO
    private boolean gameOver = false;       // NUEVO

    GamePanel(Labyrinth lab, Player player, java.util.List<Enemy> enemies){
        this.lab = lab; this.player = player; this.enemies = enemies;
        setFocusable(true);

        // teclas: mover jugador y chequear colisión
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override public void keyPressed(java.awt.event.KeyEvent e) {
                if (gameOver) return;                 // NUEVO
                player.teclaPresionada(e, lab, () -> {
                    javax.swing.JOptionPane.showMessageDialog(GamePanel.this, "¡Nivel completado!");
                    // acá podés cargar el siguiente nivel con tu MazeFactory
                });
                checkCollision();                     // NUEVO
                repaint();
            }
        });

        // loop de IA enemigos + repintado
        enemyTimer = new javax.swing.Timer(100, ev -> { // guardado en campo (NUEVO)
            if (gameOver) return;                      // NUEVO
            for (Enemy en : enemies) en.stepAI(lab, player);
            checkCollision();                          // NUEVO
            repaint();
        });
        enemyTimer.start();
    }

    // NUEVO: detecta si algun enemigo esta en la misma celda que el jugador
    private void checkCollision() {
        if (gameOver) return;
        java.awt.Point pp = player.getPos();
        for (Enemy e : enemies) {
            if (e.getPos().equals(pp)) {
                endGame();
                break;
            }
        }
    }

    // NUEVO: detiene el juego y muestra overlay / diálogo
    private void endGame() {
        gameOver = true;
        if (enemyTimer != null) enemyTimer.stop();
        java.awt.Toolkit.getDefaultToolkit().beep();
        //javax.swing.SwingUtilities.invokeLater(() ->
            //javax.swing.JOptionPane.showMessageDialog(this, "¡Game Over! Te atrapó un fantasma.")
        //);
        // cerrar la ventana automaticamente:
        // java.awt.Window w = javax.swing.SwingUtilities.getWindowAncestor(this);
        // if (w != null) w.dispose();
    }

    @Override protected void paintComponent(java.awt.Graphics g){
        super.paintComponent(g);
        int offX = (getWidth()  - lab.getPixelWidth())  / 2;
        int offY = (getHeight() - lab.getPixelHeight()) / 2;

        // Dibujo del mapa segun codigos (0,1,3,4,5)
        for (int r=0; r<lab.getRows(); r++){
            for (int c=0; c<lab.getCols(); c++){
                int x = offX + c*lab.getTileW();
                int y = offY + r*lab.getTileH();
                int t = lab.getCell(r,c);

                switch (t){
                    case Labyrinth.WALL      -> g.setColor(java.awt.Color.DARK_GRAY);
                    case Labyrinth.ROOM      -> g.setColor(new java.awt.Color(120,200,120));
                    case Labyrinth.KEY_ZONE  -> g.setColor(new java.awt.Color(120,160,220));
                    case Labyrinth.DOOR      -> g.setColor(lab.isPuertaAbierta()
                                                           ? new java.awt.Color(100,200,100)
                                                           : new java.awt.Color(200,80,80));
                    default                  -> g.setColor(new java.awt.Color(230,230,230)); // FLOOR
                }
                g.fillRect(x, y, lab.getTileW(), lab.getTileH());
                g.setColor(java.awt.Color.BLACK);
                g.drawRect(x, y, lab.getTileW(), lab.getTileH());
            }
        }

        // Items (llave)
        for (Item it : lab.getItems()){
            int x = offX + it.getColumna()*lab.getTileW();
            int y = offY + it.getFila()*lab.getTileH();
            if (Item.KEY.equals(it.getTipo())){
                g.setColor(java.awt.Color.YELLOW);
                g.fillOval(x + lab.getTileW()/4, y + lab.getTileH()/4, lab.getTileW()/2, lab.getTileH()/2);
                g.setColor(java.awt.Color.BLACK);
                g.drawOval(x + lab.getTileW()/4, y + lab.getTileH()/4, lab.getTileW()/2, lab.getTileH()/2);
            }
        }

        // Entidades
        player.paint(g, lab.getTileW(), lab.getTileH(), offX, offY);
        for (Enemy e : enemies) e.paint(g, lab.getTileW(), lab.getTileH(), offX, offY);

        // NUEVO: overlay “Game Over”
        if (gameOver) {
            java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
            g2.setColor(new java.awt.Color(0,0,0,150));
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.setColor(java.awt.Color.WHITE);
            g2.setFont(getFont().deriveFont(java.awt.Font.BOLD, 42f));
            String msg = "GAME OVER";
            int w = g2.getFontMetrics().stringWidth(msg);
            g2.drawString(msg, (getWidth()-w)/2, getHeight()/2);
            g2.dispose();
        }
    }
}

    
    static class TutorialMaze extends Labyrinth {
        private static final int[][] MAPA = {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,4,1},
            {1,0,1,1,0,1,1,1,1,1,1,1,0,0,1},
            {1,0,0,0,0,0,0,5,3,3,3,1,0,0,1}, // 5 puerta, 3 habitación
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
        };
        public TutorialMaze(){ super(MAPA, 40, 40); } // tiles de 40px
    }


    // Laberinto grande 21x21
static class Level1Maze extends Labyrinth {
    private static final int[][] MAPA = {
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,0,0,0,1,0,0,0,1,0,0,0,0,0,1},
        {1,1,1,0,1,0,1,0,1,0,1,1,1,0,1},
        {1,0,1,0,1,0,1,0,1,0,0,4,0,0,1},
        {1,0,1,0,1,0,1,0,1,1,1,1,1,0,1},
        {1,0,1,0,1,0,1,0,1,0,0,0,0,0,1},
        {1,0,1,0,1,0,1,0,1,0,1,1,1,0,1},
        {1,0,0,0,0,0,0,0,0,0,1,0,0,0,1},
        {1,0,1,1,1,1,1,1,1,0,1,1,1,1,1},
        {1,0,1,3,3,3,3,3,1,0,0,0,0,0,1},
        {1,0,1,3,3,3,3,3,1,0,1,1,1,0,1},
        {1,0,1,3,3,3,3,3,1,0,1,0,0,0,1},
        {1,0,1,1,1,5,1,1,1,1,1,0,1,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,1,4,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
    };
    public Level1Maze(){ super(MAPA, 40, 40); }
        // 40 px por celda 
         // tiles de 32 px
}
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Mundo
            //Labyrinth lab = new TutorialMaze();
            Labyrinth lab = new Level1Maze();     // ahora usa el grande
            Player player = new Player(new Point(1,13));
            List<Enemy> enemies = new ArrayList<>();
            // antes: enemies.add(new Ghost(new Point(7,3)));
            //enemies.add(new RandomGhost(new Point(5,3)).withCooldown(4));
            enemies.add(new RandomGhost(new Point(7,3)).withCooldown(4));  // (col=7, fila=3) es piso
        
            enemies.add(new RandomGhost(new Point(12,9)).withCooldown(5));
            // Ventana
            JFrame f = new JFrame("Ghostly Mansion — Demo POO");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            GamePanel panel = new GamePanel(lab, player, enemies);
            f.setContentPane(panel);
            f.setSize(900, 700);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
            panel.requestFocusInWindow();
        });
    }
}