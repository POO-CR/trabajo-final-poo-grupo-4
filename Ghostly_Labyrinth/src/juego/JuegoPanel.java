package juego;

import javax.imageio.ImageIO;
import javax.swing.*;

import controlador.GameController;
import entidades.laberinto.Labyrinth;
import view.LightView;

import java.awt.Toolkit;
import java.io.IOException;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.FontMetrics;
import java.awt.Font;
import entidades.objetos.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


public class JuegoPanel extends JPanel{

    private GameController controlador;
    private Font fuenteJuego; //SE MODIFICO 01:25
    private Image imagenVida; //SE MODIFICO 01:25
    ///SE MODIFICO 01:25 private Labyrinth laberinto; 
    //SE MODIFICO 01:25 private LightView light;
    // private Objeto[] obj; TAMBIEN ES MODELO
    private BufferedImage up0, up1, up2, down0, down1, down2, left0, left1, left2, right0, right1, right2;
    
    //Caracteristicas de la foto vida
    int x0 = 10; //inicio del x
    int y0 = 10;
    int anchofoto = 45;
    int altofoto = 45;
    int distancia = 10;

    //, Labyrinth laberinto, LightView light SE MODIFICO 01:25 se sacaron los dos parametros
    public JuegoPanel (GameController controlador) {
        this.controlador = controlador;
        //SE MODIFICO 01:25 this.laberinto = laberinto; // ESTO ES VIEW, HAY QUE CAMBIAR NOMBRE 
        //this.obj = controlador.getObjArray();
        setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize()); // para que tome el tamaño de la pantalla nativa!! copado!!
        setBackground(Color.black);
        setDoubleBuffered(true); // guarda 2 buffer en memoria para que no carguen en tiempo real en pan                         // igual según internet no es necesario(?
        setFocusable(true); // el setfocusable es para que se "concentre" en los eventos del teclado

        cargarSpritesJugador(); 
        cargarFuente();
        cargarImagenVida();        
    
    }

    public Font cargarFuente(){
    try{
        //Carga la fuente del texto
        fuenteJuego = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/assets/font/gaspi.otf"));
        fuenteJuego = fuenteJuego.deriveFont(Font.PLAIN,28f);
        } catch (Exception e) {
            e.printStackTrace();     
        }
        return fuenteJuego;
        
    }
    
    public Image cargarImagenVida(){ 
    try{
        //Carga la foto de vida
        imagenVida = ImageIO.read(getClass().getResourceAsStream("/assets/img/vida.png"));
        } catch (Exception e){
            e.printStackTrace();
        }
        return imagenVida; 
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
            e.printStackTrace();
        }
        
    }

    public BufferedImage getImagenJugador(){
        BufferedImage imagen = null;
        switch(controlador.getDireccionJugador()) {
            case "up":
                if (controlador.getSpriteNumJugador() == 1){
                    imagen = up1;
                }
                if (controlador.getSpriteNumJugador() == 2){
                    imagen = up2;
                }
                break;
            case "down":
                if (controlador.getSpriteNumJugador() == 1){
                    imagen = down1;
                }
                if (controlador.getSpriteNumJugador() == 2){
                    imagen = down2;
                }
                break;
            case "left":
                if (controlador.getSpriteNumJugador() == 1){
                    imagen = left1;
                }
                if (controlador.getSpriteNumJugador() == 2){
                    imagen = left2;
                }
                break;
            case "right":
                if (controlador.getSpriteNumJugador() == 1){
                    imagen = right1;
                }
                if (controlador.getSpriteNumJugador() == 2){
                    imagen = right2;
                }   
                break;
            case "quieto":
                if (controlador.getUltimaDireccionJugador().equals("up")){
                    imagen = up0;
                }
                else if (controlador.getUltimaDireccionJugador().equals("down")){
                    imagen = down0;
                }
                else if (controlador.getUltimaDireccionJugador().equals("left")){
                    imagen = left0;
                }
                else if (controlador.getUltimaDireccionJugador().equals("right")){
                    imagen = right0;
                }
                else {
                    imagen = down0;
                }
                break;
            
        default: 
            imagen = down1;
            
            break; 
        }
        return imagen;
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

    public void paintvida(Graphics grafico, int vida){
        
        for (int i = 0; i < vida; i++) {
            int x = x0 + i * (anchofoto + distancia);
            grafico.drawImage(imagenVida, (x + distancia), y0, anchofoto, altofoto, null);
        } 
    }

    

    //metodo para poder dibujar en el JPanel, tanto el jugador laberinto fantasmas 
    //el override hace referencia a que es un metodo heredado de JPanel 
    @Override
    public void paintComponent(Graphics g) {

    super.paintComponent(g);

    // calcular offset de la camara (segun la posicion del jugador) 
    // PASARLO AL CONTROLADORRRR CREO
    int camX = controlador.getMundoXJugador() - controlador.getScreenXJugador();
    int camY = controlador.getMundoYJugador() - controlador.getScreenYJugador();

    Graphics2D g2 = (Graphics2D) g.create();
    g2.translate(-camX, -camY); // mueve todo lo que se dibuje segun la camara

    
    //Se dibuja el laberinto en el panel 
    Labyrinth laberinto = controlador.getLaberinto(); //SE MODIFICO 01:25
    laberinto.paintComponent(g2);


    //Se dibujan los objetos en el panel
    Objeto[] objetos = controlador.getObjArray();//SE MODIFICO 01:25
    for (int i=0; i < objetos.length; i++) {
        if (objetos[i] != null) {
            objetos[i].draw(g2);
        }//SE MODIFICO 01:25 el length
    }

    //jugador.draw(g2); // dibuja el jugador en el mundo, esto hace que funcione mal
    g2.dispose();
    
    // SE DIBUJA JUGADOR EN EL PANEL 
    g.drawImage(getImagenJugador(), controlador.getScreenXJugador(), controlador.getScreenYJugador(), controlador.getAnchoJugador(), controlador.getAltoJugador(), null); // dibuja el jugador en pantalla

    //Se dibuja la luz en el panel 
    LightView light = controlador.getLight();//SE MODIFICO 01:25
    if (light != null) {
        light.paint((Graphics2D) g, getWidth(), getHeight());
    }
    
    //Se dibuja el mensaje en el panel ////////////////////////

    drawMensaje((Graphics2D) g, getWidth(), getHeight()); //SE MODIFICO 01:25


    //Se dibujan las vidas en el panel 
    paintvida(g,controlador.vidaActual());//SE MODIFICO 01:25



    //para dibujar el cronometro tanto el estilo como la posicion
    Stopwatch stopwatch = controlador.getStopwatch(); //SE MODIFICO 01:25
    Font fuenteJuego = cargarFuente(); //SE MODIFICO 01:25
    
    g.setFont(fuenteJuego); //usa la fuente cargada en el constructor de panel juego
    String textoTiempo = "Time: " + stopwatch.formatoTiempo(stopwatch.getSegundos()); //texto que se mostrara en pantalla

    FontMetrics metrics = g.getFontMetrics(fuenteJuego); //FontMetrics da informacion sobre la fuente y g.getFontMetrics le pide al objeto graphics que devuelva las metricas(ancho,alto,espaciado) de la fuente fuenteJuego 
    int anchoTexto = metrics.stringWidth(textoTiempo); //calcula el ancho de pixeles de la cadena textoTiempo, anchoTexto va a contener un entero con la cantidad de pixeles que ocupa en x textoTiempo

    int x = getWidth() - anchoTexto - 20; //se calcula y coloca la posicion x en el paneljuego (getWidth() da el ancho total del panel juego y se le resta el ancho de pixeles del texto y el -20 es un margen de separacion desde el borde derecho de la ventana para que no quede pegado al borde)
    int y = 40; //se coloca en la posicion y en el paneljuego

    g.setColor(Color.WHITE); //se setea el color del texto en blanco
    g.drawString(textoTiempo, x, y); //se dibuja el string textoTiempo en las posiciones x e y calculadas 
    }
        
        
        
       


}




