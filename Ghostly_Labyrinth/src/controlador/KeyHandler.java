package controlador;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import entidades.Player;
import entidades.Labyrinth;

public class KeyHandler extends KeyAdapter{

    Player jugador = new Player();
    Labyrinth lab = new Labyrinth();
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
            case KeyEvent.VK_LEFT, KeyEvent.VK_A: leftPressed = true; break;
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D: rightPressed = true; break;
            case KeyEvent.VK_UP, KeyEvent.VK_W: upPressed = true; break;
            case KeyEvent.VK_DOWN, KeyEvent.VK_S:  downPressed = true; break;
            default: return;} // si no es flecha, salir
        }

    
    @Override
    public void keyReleased(KeyEvent e){
        int code = e.getKeyCode();
        // Movimiento segun tecla
        switch(code){
            case KeyEvent.VK_LEFT, KeyEvent.VK_A: leftPressed = false; break;
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D: rightPressed = false; break;
            case KeyEvent.VK_UP, KeyEvent.VK_W: upPressed = false; break;
            case KeyEvent.VK_DOWN, KeyEvent.VK_S:  downPressed = false; break;
            default: return; 
        }
    }
    
}
