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
            default: return; // si no es flecha, salir
        }

        // posX-=movimiento; break;
        // posX+= movimiento; break;
        // posY-= movimiento; break;
        // posY+= movimiento; break;

        // Convertir a fila y columna de la matriz
        // int filNueva = posX / 20;
        // int colNueva = posY / 20;

        // // Validar límites y muros
        // if(filNueva >= 0 && filNueva < lab.obtenerLaberinto().length &&
        // colNueva >= 0 && colNueva < lab.obtenerLaberinto()[0].length) {

        //     int valor = lab.obtenerLaberinto()[filNueva][colNueva];

        //     // Bloqueamos puerta cerrada si no tenemos llave
        //     if(valor != 1 && !(valor == 2 && !jugador.getTieneLlave())){
        //         jugador.setX(posX);
        //         jugador.setY(posY);
        //     }

        // }
        // Verificar si el jugador pisa la llave

        // int filaJugador = y / 20;
        // int colJugador = x / 20;

        // for(int i = 0; i < lab.getItems().size(); i++){
        //     Item item = lab.getItems().get(i);
        //     if(item.fila == filaJugador && item.columna == colJugador && item.tipo.equals("llave")){
        //             tieneLlave = true;
        //             lab.getItems().remove(i); // quitar llave del laberinto
        //             lab.abrirPuerta();        // abrir la puerta
        //             System.out.println("PUERTA ABIERTA");
        //             break;
                
        //     }
        // }

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
