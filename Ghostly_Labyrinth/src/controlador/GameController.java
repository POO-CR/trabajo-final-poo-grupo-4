package controlador;
import entidades.*;
import entidades.laberinto.Labyrinth;
import juego.PanelJuego;

public class GameController {

    private Labyrinth lab;
    private PanelJuego panelJuego;
    private Player jugador;

    public GameController(Labyrinth lab, PanelJuego juego, Player jugador) {
        this.lab = lab;
        this.panelJuego = juego;
        this.jugador =jugador;
    }


    KeyHandler keyH = new KeyHandler();
    

    public void añadirKeyListener(PanelJuego juego){
        juego.addKeyListener(keyH);
    }


    public void actualizarMovimiento() {
        int nuevaX = jugador.getX();
        int nuevaY = jugador.getY();
        int movimiento = jugador.getMovimiento();
        
        if (keyH.upPressed == true || keyH.downPressed == true ||
            keyH.leftPressed == true || keyH.rightPressed == true){
        
                if (keyH.upPressed == true){
            jugador.setDireccion("up");
            jugador.setUltimaDireccion("up");
            }
            else if (keyH.downPressed == true){
            jugador.setDireccion("down");
            jugador.setUltimaDireccion("down");
            }
            else if (keyH.leftPressed == true){
            jugador.setDireccion("left");
            jugador.setUltimaDireccion("left");
            }
            else if (keyH.rightPressed == true){
            jugador.setDireccion("right");
            jugador.setUltimaDireccion("right");
            }
        }
        
        else {
            jugador.setDireccion("quieto");
        }

        jugador.colisionOn = false;
        panelJuego.cChecker.checkearTile(jugador);

        int indiceObj = panelJuego.cChecker.checkearObjeto(jugador, true);
        InteraccionDeMensajes(indiceObj);

        if (jugador.colisionOn == false) {
            
            switch(jugador.getDireccion()) {
                case "up":
                    nuevaY -= movimiento;
                    break;
                case "down":
                    nuevaY += movimiento;
                    break;
                case "left":
                    nuevaX -= movimiento;
                    break;
                case "right":
                    nuevaX += movimiento;
                    break;
            }
            jugador.setX(nuevaX);
            jugador.setY(nuevaY);
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

        }

        public void InteraccionDeMensajes (int i){
            if (i != 999) {
                String nombreObj = panelJuego.obj[i].nombre;
                switch (nombreObj) {
                    case "Llave":
                        jugador.setTieneLlave(true);
                        System.out.println("AGARRO LLAVE EN POSICION" + panelJuego.obj[i].getX() + " ; " + panelJuego.obj[i].getY());
                        panelJuego.obj[i] = null;
                        panelJuego.mostrarMensaje("Obtuviste la llave",2000);
                        break;
                    default:
                        break;
                }
            }
        }

    }
    