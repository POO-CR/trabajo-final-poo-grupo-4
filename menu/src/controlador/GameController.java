package controlador;
import main.PanelJuego;
import entidades.*;

public class GameController {

    public GameController() {
    }


    KeyHandler keyH = new KeyHandler();
    Labyrinth lab = new Labyrinth();

    public void añadirKeyListener(PanelJuego juego){
        juego.addKeyListener(keyH);
    }


    public void actualizarMovimiento(Player jugador) {
        int nuevaX = jugador.getX();
        int nuevaY = jugador.getY();
        int movimiento = jugador.getMovimiento();
        
        if (keyH.upPressed == true){
            nuevaY -= movimiento;
        }
        else if (keyH.downPressed == true){
            nuevaY += movimiento;
        }
        else if (keyH.leftPressed == true){
            nuevaX -= movimiento;
        }
        else if (keyH.rightPressed == true){
            nuevaX += movimiento;
        }

    // Convertir a fila y columna de la matriz
    int filNueva = nuevaY / 20;
    int colNueva = nuevaX / 20;

    // Validar límites y muros
    if(filNueva >= 0 && filNueva < lab.obtenerLaberinto().length &&
    colNueva >= 0 && colNueva < lab.obtenerLaberinto()[0].length) {

        int valor = lab.obtenerLaberinto()[filNueva][colNueva];

        // Bloqueamos puerta cerrada si no tenemos llave
        if(valor != 1 && !(valor == 2 && !jugador.getTieneLlave())){
            jugador.setX(nuevaX);
            jugador.setY(nuevaY);
        }
    }

    //Verificar si el jugador pisa la llave
    //TODO: arreglar colisiones
    /**el círculo se superpone con las paredes a la derecha y abajo, porque solo chequeamos la esquina superior izquierda
    *hay que calcular la colisión con todo el tamaño del circulo
    *int leftCol   =  nuevaX          / tile;
    *int rightCol  = (nuevaX + w - 1) / tile;
    *int topRow    =  nuevaY          / tile;
    *int bottomRow = (nuevaY + h - 1) / tile;
    */

    int filaJugador = jugador.getY() / 20;
    int colJugador = jugador.getX() / 20;

    for(int i = 0; i < lab.getItems().size(); i++){
        Item item = lab.getItems().get(i);
        if(item.getFila() == filaJugador && item.getColumna() == colJugador && item.getTipo().equals("llave")){ // TODO: manejar los tipos de item con un enum
                jugador.setTieneLlave(true);
                lab.getItems().remove(i); // quitar llave del laberinto
                lab.abrirPuerta();        // abrir la puerta
                System.out.println("PUERTA ABIERTA");
                break;
            
        }
    }

    }
}