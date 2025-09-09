package entidades;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Player extends Entity{

    //este objeto de la clase laberinto permite obtener la matriz laberinto 
    Labyrinth lab = new Labyrinth();

    //variable que indica si el jugador agarro la llave
    private boolean tieneLlave = false;

    private int x = 20;
    private int y = 20;
    private final int ancho = 20;
    private final int alto = 20;
    private final int movimiento = 5;

    public void paint(Graphics grafico){
        grafico.setColor(Color.red);
        grafico.fillOval(x, y, ancho, alto);
        grafico.setColor(Color.black);
        grafico.drawOval(x, y, ancho, alto);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getMovimiento(){
        return movimiento;
    }

    public boolean getTieneLlave(){
        return tieneLlave;
    }

    public void setTieneLlave(boolean tieneLlave){
        this.tieneLlave = tieneLlave;
    }


    // public void teclaPresionada(KeyEvent event){
        
    // int [][] laberinto = lab.obtenerLaberinto();

    // int nuevaX = x;
    // int nuevaY = y;

    // // Movimiento segun tecla
    // switch(event.getKeyCode()){
    //     case KeyEvent.VK_LEFT:  nuevaX = x - movimiento; break;
    //     case KeyEvent.VK_RIGHT: nuevaX = x + movimiento; break;
    //     case KeyEvent.VK_UP:    nuevaY = y - movimiento; break;
    //     case KeyEvent.VK_DOWN:  nuevaY = y + movimiento; break;
    //     default: return; // si no es flecha, salir
    // }

    // // Convertir a fila y columna de la matriz
    // int filNueva = nuevaY / 20;
    // int colNueva = nuevaX / 20;

    // // Validar límites y muros
    // if(filNueva >= 0 && filNueva < lab.obtenerLaberinto().length &&
    // colNueva >= 0 && colNueva < lab.obtenerLaberinto()[0].length) {

    //     int valor = lab.obtenerLaberinto()[filNueva][colNueva];

    //     // Bloqueamos puerta cerrada si no tenemos llave
    //     if(valor != 1 && !(valor == 2 && !tieneLlave)){
    //         x = nuevaX;
    //         y = nuevaY;
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


