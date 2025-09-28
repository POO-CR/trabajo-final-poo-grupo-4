package controlador;
import entidades.*;
import entidades.laberinto.Labyrinth;
import javax.swing.JFrame;
import juego.KeyHandler;
import juego.PanelJuego;

public class GameController {

    private Labyrinth lab;
    private PanelJuego panelJuego;
    private Player jugador;
    KeyHandler keyH;

    public GameController() {
        this.lab = new Labyrinth(0);
        this.jugador = new Player(lab);
        this.panelJuego = new PanelJuego(lab, jugador, this);
        this.keyH = new KeyHandler();
    }

    public void iniciarJuego (String usuario) {
    
        // cerrar todas las ventanas abiertas de Swing
        for (java.awt.Window window : java.awt.Window.getWindows()) {
            if (window.isShowing()) {
                window.dispose();
            }
        }
        JFrame ventana = new JFrame();

        int ancho = lab.getPixelWidth();
        int alto = lab.getPixelHeight();
        String tituloJuego = "Ghostly Labyrinth";

        ventana.setTitle(tituloJuego + " - Jugador: " + usuario);
        ventana.setSize(ancho, alto);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(true);

        // Crear jugador
        jugador.setNombre(usuario);

        panelJuego.setFocusable(true);       //puede recibir foco
        panelJuego.requestFocusInWindow();   //pedir foco para teclado
        ventana.add(panelJuego);
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);

            
        // se fuerza que este al frente y tenga foco
        ventana.toFront();
        ventana.requestFocus();
        ventana.setAlwaysOnTop(true);   // para que quede al frente
        ventana.setAlwaysOnTop(false);  // lo volvemos a normal

        // despues de hacer visible la ventana, asegurar foco en el panel
        javax.swing.SwingUtilities.invokeLater(() -> {
        panelJuego.requestFocusInWindow();
        });

        // Controlador
        actualizarMovimiento();
        añadirKeyListener(panelJuego);


        // juego
        panelJuego.setupJuego(); //tiene que ir antes del juego
        panelJuego.iniciarGameThread();
    
    }

    

    public void añadirKeyListener(PanelJuego juego){
        panelJuego.addKeyListener(keyH);
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
                case "up" -> nuevaY -= movimiento;
                case "down" -> nuevaY += movimiento;
                case "left" -> nuevaX -= movimiento;
                case "right" -> nuevaX += movimiento;
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
                    case "Llave" -> {
                        jugador.setTieneLlave(true);
                        System.out.println("AGARRO LLAVE EN POSICION" + panelJuego.obj[i].getX() + " ; " + panelJuego.obj[i].getY());
                        panelJuego.obj[i] = null;
                        panelJuego.mostrarMensaje("Obtuviste la llave",2000);
                    }
                }
            }
        }

    }
    