package juego;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyHandler extends KeyAdapter{

    public boolean upPressed, downPressed, leftPressed, rightPressed;

    @Override
    public void keyPressed (KeyEvent e){
        // int [][] laberinto = lab.obtenerLaberinto();

        int code = e.getKeyCode();
        // int posX = jugador.getX();
        // int posY = jugador.getY();
        // int movimiento = jugador.getMovimiento();

        // Movimiento segun tecla
        switch(code){
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> leftPressed = true;
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> rightPressed = true;
            case KeyEvent.VK_UP, KeyEvent.VK_W -> upPressed = true;
            case KeyEvent.VK_DOWN, KeyEvent.VK_S -> downPressed = true;

            }
        }

    
    @Override
    public void keyReleased(KeyEvent e){
        int code = e.getKeyCode();
        // Movimiento segun tecla
        switch(code){
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> leftPressed = false;
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> rightPressed = false;
            case KeyEvent.VK_UP, KeyEvent.VK_W -> upPressed = false;
            case KeyEvent.VK_DOWN, KeyEvent.VK_S -> downPressed = false;
            }
        }
    
}
