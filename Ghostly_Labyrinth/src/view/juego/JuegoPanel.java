package view.juego;

import model.juego.Stopwatch;
import model.juego.objetos.*;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

import controller.juego.GameController;


public class JuegoPanel extends JPanel{

    final private GameController controlador;
    private Font fuenteJuego; //SE MODIFICO 01:25
    private Image imagenVida; //SE MODIFICO 01:25
    // posis del jugador según la cámara
    private int camX;
    private int camY;
    final private ArrayList<Objeto> objetos;
    ///SE MODIFICO 01:25 private Labyrinth laberinto; 
    //SE MODIFICO 01:25 private LightView light;
    // private Objeto[] obj; TAMBIEN ES MODELO
    private BufferedImage up0, up1, up2, down0, down1, down2, left0, left1, left2, right0, right1, right2;
    
    //Caracteristicas de la foto vida
    final private int x0 = 10; //inicio del x
    final private int y0 = 10;
    final private int anchofoto = 45;
    final private int altofoto = 45;
    final private int distancia = 10;

    //características del lab
    private final int cantidadFilas;
    private final int cantidadColumnas;
    private final int anchoBloque;
    private final int altoBloque;


    //, Labyrinth laberinto, LightView light SE MODIFICO 01:25 se sacaron los dos parametros
    public JuegoPanel (GameController controlador) {
        this.controlador = controlador;
        //SE MODIFICO 01:25 this.laberinto = laberinto; // ESTO ES VIEW, HAY QUE CAMBIAR NOMBRE 
        //this.obj = controlador.getObjArray();  
        this.cantidadColumnas = controlador.getAnchoNivel();
        this.cantidadFilas = controlador.getAltoNivel();
        this.altoBloque = controlador.getAltoBloque();
        this.anchoBloque = controlador.getAnchoBloque(); 
        this.objetos = controlador.getObjArray();
        super.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize()); // para que tome el tamaño de la pantalla nativa!! copado!!
        super.setBackground(Color.black);
        super.setDoubleBuffered(true); // guarda 2 buffer en memoria para que no carguen en tiempo real en pan                         // igual según internet no es necesario(?
        super.setFocusable(true); // el setfocusable es para que se "concentre" en los eventos del teclado    
        // para que no salte el overridable method call in constructor
        init();
    }

    private void init() {
        cargarSpritesJugador(); 
        cargarFuente();
        cargarImagenVida(); 
    }

    public Font cargarFuente(){
    try{
        //Carga la fuente del texto
        fuenteJuego = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/assets/font/gaspi.otf"));
        fuenteJuego = fuenteJuego.deriveFont(Font.PLAIN,28f);
        //System.out.println("Fuente cargada");
        } catch (FontFormatException | IOException e) {
            fuenteJuego = new Font("Arial", Font.PLAIN, 24);
            //System.out.println("Fuente no pudo ser cargada");
    }
        return fuenteJuego;   
    }
    
    public Image cargarImagenVida(){ 
    try{
        //Carga la foto de vida
        imagenVida = ImageIO.read(getClass().getResourceAsStream("/assets/img/vida.png"));
        } catch (IOException e){
        }
        return imagenVida; 
    }

    public void cargarImagenLlave(){

    }

        
    public void cargarSpritesJugador(){
        try {
            up0 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/espalda_0.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/espalda_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/espalda_2.png"));
            down0 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/frente_0.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/frente_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/frente_2.png"));
            left0 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/izq_0.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/izq_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/izq_2.png"));
            right0 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/der_0.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/der_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/der_2.png"));
            
        }catch (IOException e) {
        }
        
    }

    public BufferedImage getImagenJugador() {
        return switch (controlador.getDireccionJugador()) {
            case "up"    -> (controlador.getSpriteNumJugador() == 1) ? up1    : up2;
            case "down"  -> (controlador.getSpriteNumJugador() == 1) ? down1  : down2;
            case "left"  -> (controlador.getSpriteNumJugador() == 1) ? left1  : left2;
            case "right" -> (controlador.getSpriteNumJugador() == 1) ? right1 : right2;
            case "quieto" -> switch (controlador.getUltimaDireccionJugador()) {
                case "up"    -> up0;
                case "down"  -> down0;
                case "left"  -> left0;
                case "right" -> right0;
                default      -> down1;
            };
            default -> down1;
        };
    }
    


    public void drawMensaje(Graphics2D g2, int panelAncho, int panelAlto) {
        if (controlador.getIsVisible()) {
            // Fuente y color del mensaje
            Font fuente = new Font("Arial", Font.BOLD, 40);
            g2.setFont(fuente);
            FontMetrics metrics = g2.getFontMetrics(fuente);

            // Medidas para centrar el texto
            int x = (panelAncho - metrics.stringWidth(controlador.getTexto())) / 2;
            int y = panelAlto / 2;
            
            // Texto
            g2.setColor(Color.WHITE);
            g2.drawString(controlador.getTexto(), x, y); 
        }
    } 

    public void paintVida(Graphics grafico, int vida){
        
        for (int i = 0; i < vida; i++) {
            int x = x0 + i * (anchofoto + distancia);
            grafico.drawImage(imagenVida, (x + distancia), y0, anchofoto, altofoto, null);
        } 
    }

    public void paintLaberinto (Graphics2D grafico){
        //filas
        for(int fila = 0; fila<this.cantidadFilas; fila++){
            //columnas
            for(int columna = 0; columna < this.cantidadColumnas; columna++){
                    //variable para la iteracion
                    grafico.drawImage(controlador.getSprite(fila, columna), columna * anchoBloque, fila * altoBloque, anchoBloque, altoBloque, null);
                }     
            }
        }




    public void paintLuz(Graphics2D g) {
        if (g == null || controlador == null) return;

        int anchoPanel = getWidth();
        int altoPanel = getHeight();

        // Capa temporal del tamaño del panel con transparencia
        BufferedImage layer = new BufferedImage(anchoPanel, altoPanel, BufferedImage.TYPE_INT_ARGB);
        Graphics2D lg = layer.createGraphics();

        // Pinta toda la pantalla con negro (oscuridad)
        lg.setComposite(AlphaComposite.Src);
        lg.setColor(new Color(0, 0, 0, 255)); // 255 = completamente oscuro
        lg.fillRect(0, 0, anchoPanel, altoPanel);

    // Centro del círculo de luz (en coordenadas de pantalla)
        int offsetX = -5;
        int offsetY = -5;
        int cx = controlador.getScreenXJugador() + controlador.getAnchoJugador() / 2 + offsetX;
        int cy = controlador.getScreenYJugador() + controlador.getAltoJugador() / 2 + offsetY;

        int radiusPx = 280;

        // Gradiente de la linterna
        float[] dist = {0f, 0.65f, 1f};
        Color[] cols = {
            new Color(0, 0, 0, 250),  // centro casi opaco
            new Color(0, 0, 0, 100),  // intermedio
            new Color(0, 0, 0, 0)     // borde transparente
        };

        RadialGradientPaint grad = new RadialGradientPaint(new Point(cx, cy), radiusPx, dist, cols);

        // Corta el agujero circular en la oscuridad
        lg.setComposite(AlphaComposite.DstOut);
        lg.setPaint(grad);
        lg.fillOval(cx - radiusPx, cy - radiusPx, radiusPx * 2, radiusPx * 2);

        lg.dispose();

        // Dibuja la capa sobre la pantalla
        g.drawImage(layer, 0, 0, null);
    }


    //metodo para poder dibujar en el JPanel, tanto el jugador laberinto fantasmas
    //el override hace referencia a que es un metodo heredado de JPanel
    @Override
    public void paintComponent(Graphics g) {
        //System.out.println("LLAMADO PAINTCOMPONENT");

        super.paintComponent(g);

        // offset calculado según su posición en el mundo y su posición en pantalla
        camX = controlador.getCamX();
        camY = controlador.getCamY();

        Graphics2D g2 = (Graphics2D) g.create();
        g2.translate(-camX, -camY); // mueve todo lo que se dibuje segun la camara

        
        //Se dibuja el laberinto en el panel  //SE MODIFICO 01:25
        //System.out.println("pintando laberinto");
        paintLaberinto(g2);

        //Se dibujan los objetos en el panel
        //System.out.println("pintando objetos");
        //System.out.println("Cantidad de objetos recibidos en vista: " + objetos.size());
        Objeto obj;
        for (int i=0; i < objetos.size(); i++) {
                if (objetos.get(i) != null) {
                    obj = objetos.get(i);
                    g2.drawImage(obj.getImage(), obj.getX(), obj.getY(), controlador.getAnchoBloque(), controlador.getAltoBloque(), null);
            }//SE MODIFICO 01:25 el length
        }

        //jugador.draw(g2); // dibuja el jugador en el mundo, esto hace que funcione mal
        
        // SE DIBUJA JUGADOR EN EL PANEL 
        //System.out.println("pintando jugador");
        g.drawImage(getImagenJugador(), controlador.getScreenXJugador(), controlador.getScreenYJugador(), controlador.getAnchoJugador(), controlador.getAltoJugador(), null); // dibuja el jugador en pantalla

        //Se dibuja la luz en el panel 
        paintLuz((Graphics2D) g);
        
        //Se dibuja el mensaje en el panel ////////////////////////

        drawMensaje((Graphics2D) g, getWidth(), getHeight()); //SE MODIFICO 01:25


        //Se dibujan las vidas en el panel 
        paintVida(g,controlador.vidaActual());//SE MODIFICO 01:25



        //para dibujar el cronometro tanto el estilo como la posicion
        Stopwatch stopwatch = controlador.getStopwatch(); //SE MODIFICO 01:25
        fuenteJuego = cargarFuente(); //SE MODIFICO 01:25
        
        g.setFont(fuenteJuego); //usa la fuente cargada en el constructor de panel juego
        String textoTiempo = "Time: " + stopwatch.formatoTiempo(stopwatch.getSegundos()); //texto que se mostrara en pantalla

        FontMetrics metrics = g.getFontMetrics(fuenteJuego); //FontMetrics da informacion sobre la fuente y g.getFontMetrics le pide al objeto graphics que devuelva las metricas(ancho,alto,espaciado) de la fuente fuenteJuego 
        int anchoTexto = metrics.stringWidth(textoTiempo); //calcula el ancho de pixeles de la cadena textoTiempo, anchoTexto va a contener un entero con la cantidad de pixeles que ocupa en x textoTiempo

        int x = getWidth() - anchoTexto - 20; //se calcula y coloca la posicion x en el paneljuego (getWidth() da el ancho total del panel juego y se le resta el ancho de pixeles del texto y el -20 es un margen de separacion desde el borde derecho de la ventana para que no quede pegado al borde)
        int y = 40; //se coloca en la posicion y en el paneljuego

        g.setColor(Color.WHITE); //se setea el color del texto en blanco
        g.drawString(textoTiempo, x, y); //se dibuja el string textoTiempo en las posiciones x e y calculadas 

        g2.dispose();
    }
        
        

}




