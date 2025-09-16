package controlador;
import entidades.*;
import entidades.laberinto.Labyrinth;
import juego.PanelJuego;

public class GameController {
    private Labyrinth lab;

    public GameController(Labyrinth lab) {
        this.lab = lab;
    }


    KeyHandler keyH = new KeyHandler();
    

    public void añadirKeyListener(PanelJuego juego){
        juego.addKeyListener(keyH);
    }


    public void actualizarMovimiento(Player jugador) {
        int nuevaX = jugador.getX();
        int nuevaY = jugador.getY();
        int movimiento = jugador.getMovimiento();
        
        if (keyH.upPressed == true || keyH.downPressed == true ||
            keyH.leftPressed == true || keyH.rightPressed == true){
        
                if (keyH.upPressed == true){
            jugador.setDireccion("up");
            jugador.setUltimaDireccion("up");
            nuevaY -= movimiento;
            }
            else if (keyH.downPressed == true){
            jugador.setDireccion("down");
            jugador.setUltimaDireccion("down");
            nuevaY += movimiento;
            }
            else if (keyH.leftPressed == true){
            jugador.setDireccion("left");
            jugador.setUltimaDireccion("left");
            nuevaX -= movimiento;
            }
            else if (keyH.rightPressed == true){
            jugador.setDireccion("right");
            jugador.setUltimaDireccion("right");
            nuevaX += movimiento;
            }
        }
        
        else {
            jugador.setDireccion("quieto");
        }

        jugador.spriteCounter++;
        if (jugador.spriteCounter > 10) {
            if (jugador.spriteNum == 1) {
                jugador.spriteNum = 2;
            } else if (jugador.spriteNum == 2) {
                jugador.spriteNum = 1;
            }
            jugador.spriteCounter = 0;
        }

    // Convertir a fila y columna de la matriz
    int filNueva = nuevaY / lab.anchoBloque;
    int colNueva = nuevaX / lab.altoBloque;

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

    int filaJugador = jugador.getY() / lab.anchoBloque;
    int colJugador = jugador.getX() / lab.altoBloque;

    for(int i = 0; i < lab.getItems().size(); i++){
        Item item = lab.getItems().get(i);
        if(item.getFila() == filaJugador && item.getColumna() == colJugador && item.getTipo().equals("llave")){ // TODO: manejar los tipos de item con un enum
                jugador.recibirDanio(1);
                jugador.setTieneLlave(true);
                //lab.getItems().remove(i); // quitar llave del laberinto
                lab.abrirPuerta();        // abrir la puerta
                //System.out.println("PUERTA ABIERTA");
                break;
            
        }
    }

    }
}