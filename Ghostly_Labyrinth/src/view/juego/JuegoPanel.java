package view.juego;

import model.juego.Stopwatch;
import model.juego.objetos.*;
import model.observadores.Observador;

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
import model.juego.laberinto.Nivel; // acá
import javax.swing.*;
import javax.tools.Tool;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import controller.juego.GameController;


public class JuegoPanel extends JPanel implements Observador{

    
    private GameController controlador;
    private JButton btnPausa;


    private Font fuenteJuego; //SE MODIFICO 01:25
    private Image imagenVida; //SE MODIFICO 01:25
     private Image imagenVidaPerdida; 
    private Image imagenDibujar; //Guarda la imagen del corazon que hay que mostrar en pantalla
    // posis del jugador según la cámara
    private Graphics2D g2;
    private int vidasMaximas;
    private Font fuentetime;

    private Nivel nivel; // acá

    private ArrayList<Objeto> objetos;
    ///SE MODIFICO 01:25 private Labyrinth laberinto; 
    //SE MODIFICO 01:25 private LightView light;
    // private Objeto[] obj; TAMBIEN ES MODELO
    private BufferedImage up0, up1, up2, down0, down1, down2, left0, left1, left2, right0, right1, right2; // del jugador
    private BufferedImage gUp1, gUp2, gDown1, gDown2, gLeft1, gLeft2, gRight1, gRight2; //del fantasma

    //Caracteristicas de la foto vida
    final private int x0 = 10; //inicio del x
    final private int y0 = 10;
    final private int anchofoto = 45;
    final private int altofoto = 45;
    final private int distancia = 10;

    //características del lab
    private int cantidadFilas;
    private int cantidadColumnas;
    private int anchoBloque;
    private int altoBloque;

    //para la luz
    private int radioLuz = 280;
    private final int radioLuzDefault = 280;
    // Gradiente de la linterna
    private final float[] dist = {0f, 0.65f, 1f};
    private final Color[] cols = {
        new Color(0, 0, 0, 250),  // centro casi opaco
        new Color(0, 0, 0, 100),  // intermedio
        new Color(0, 0, 0, 0)     // borde transparente
    };

    private int offsetXLuz;
    private int offsetYLuz;
    private int cx;
    private int cy;

    private RadialGradientPaint grad;
    private Point centroLuz;
    private BufferedImage layer; 

    private boolean pasoGolpe;
    private long ultimoGolpe;

    //Para el gif del boton
    private Timer tiempoTutorial;
    private BufferedImage[] imagenWASD;
    private int imagenTutotActual = 0;
    private boolean tutorialActivo = false;



    public JuegoPanel () {
        
        super.setBackground(Color.black);
        super.setDoubleBuffered(true); // guarda 2 buffer en memoria para que no carguen en tiempo real en pan                         // igual según internet no es necesario(?
        super.setFocusable(true); // el setfocusable es para que se "concentre" en los eventos del teclado    

        // para que no salte el overridable method call in constructor
        init();
    }

    public void setGameController(GameController controlador){
        this.controlador = controlador;
        this.vidasMaximas = controlador.vidaTope();
    }

    private void init() {
        cargarSpritesJugador(); 
        cargarFuente();
        cargarImagenVida(); 
        cargarImagenVidaPerdida();
        cargarSpritesFantasma(); 
        crearBotonPausa();
        cargarImagenTutorial();

        //Timer para el gif del tutorial
        tiempoTutorial = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imagenTutotActual++;
                if (imagenTutotActual >= 3) {
                    imagenTutotActual = 0;
                }
                repaint(); 
            }
        });
    }

    private void crearBotonPausa() {
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/assets/img/botones/PauseButton.png"));
        ImageIcon iconoOriginalHover = new ImageIcon(getClass().getResource("/assets/img/botones/PauseButtonHover.png"));

        // Escalar imagen a 80x50
        Image imgEscalada = iconoOriginal.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon icono = new ImageIcon(imgEscalada);

        // Escalar imagen hover a 80x50
        Image imgEscaladaHover = iconoOriginalHover.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon iconohover = new ImageIcon(imgEscaladaHover);


        btnPausa = new JButton(icono);
        btnPausa.setRolloverIcon(iconohover);
        btnPausa.setBorderPainted(false);
        btnPausa.setContentAreaFilled(false);
        btnPausa.setFocusPainted(false);

        // Posicion
        int x = Toolkit.getDefaultToolkit().getScreenSize().width - 60 - 20;
        int y = 20;
        btnPausa.setBounds(x, y, 80, 50);

        add(btnPausa);
    }

    public JButton getBtnPausa() {
        return btnPausa;
    }

    public void setNivel(Nivel nivel){
        this.nivel = nivel;
        this.cantidadColumnas = controlador.getAnchoNivel();
        this.cantidadFilas = controlador.getAltoNivel();
        this.altoBloque = controlador.getAltoBloque();
        this.anchoBloque = controlador.getAnchoBloque(); 
        this.objetos = controlador.getObjArray();
    }
    
    public Font cargarFuente(){
    try{
        //Carga la fuente del texto
        fuenteJuego = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/assets/font/gaspi.otf"));
        fuenteJuego = fuenteJuego.deriveFont(Font.PLAIN,28f);
        //System.out.println("Fuente cargada");
         fuentetime = fuenteJuego.deriveFont(Font.PLAIN,35); //instancia la fuente del tiempo
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

    public Image cargarImagenVidaPerdida(){ 
    try{
        //Carga la foto de vida
        imagenVidaPerdida = ImageIO.read(getClass().getResourceAsStream("/assets/img/VidaPerdida.png"));
        } catch (Exception e){
            e.printStackTrace();
        }
        return imagenVidaPerdida;        
    }

    // public void cargarImagenLlave(){

    // }

    private void cargarImagenTutorial() {
        imagenWASD = new BufferedImage[3];
        try {
            imagenWASD[0] = ImageIO.read(getClass().getResourceAsStream("/assets/img/tutorial/wasd_0.png"));
            imagenWASD[1] = ImageIO.read(getClass().getResourceAsStream("/assets/img/tutorial/wasd_1.png"));
            imagenWASD[2] = ImageIO.read(getClass().getResourceAsStream("/assets/img/tutorial/wasd_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.print("Error al cargar las imágenes del tutorial.");
        }
    }

    // -------- JUGADOR --------
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

    // -------- FANTASMAAAA --------
    public void cargarSpritesFantasma() { 
        try {
            gUp1 = ImageIO.read(getClass().getResourceAsStream("/assets/img/ghost/espalda_1.png"));
            gUp2 = ImageIO.read(getClass().getResourceAsStream("/assets/img/ghost/espalda_2.png"));
            gDown1 = ImageIO.read(getClass().getResourceAsStream("/assets/img/ghost/frente_1.png"));
            gDown2 = ImageIO.read(getClass().getResourceAsStream("/assets/img/ghost/frente_2.png"));
            gLeft1 = ImageIO.read(getClass().getResourceAsStream("/assets/img/ghost/izq_1.png"));
            gLeft2 = ImageIO.read(getClass().getResourceAsStream("/assets/img/ghost/izq_2.png"));
            gRight1 = ImageIO.read(getClass().getResourceAsStream("/assets/img/ghost/der_1.png"));
            gRight2 = ImageIO.read(getClass().getResourceAsStream("/assets/img/ghost/der_2.png"));
        } catch (IOException e) {
        e.printStackTrace();
        }
    }

    public BufferedImage getImagenFantasma(String direccion, int spriteNum) {
        return switch (direccion) {
            case "up"    -> (spriteNum == 1) ? gUp1    : gUp2;
            case "down"  -> (spriteNum == 1) ? gDown1  : gDown2;
            case "left"  -> (spriteNum == 1) ? gLeft1  : gLeft2;
            case "right" -> (spriteNum == 1) ? gRight1 : gRight2;
            default      -> gDown1;
        };
    }
    


    public void drawMensaje(Graphics2D g2, int panelAncho, int panelAlto) {
        if (controlador.getIsVisible()) {
            // Fuente y color del mensaje
             Font fuenteMensaje = fuenteJuego.deriveFont(Font.BOLD, 40f);
            g2.setFont(fuenteMensaje);
            FontMetrics metrics = g2.getFontMetrics(fuenteMensaje);

            // Medidas para centrar el texto
            int x = (panelAncho - metrics.stringWidth(controlador.getTexto())) / 2;
            int y = panelAlto / 5;
            
            // Texto
            g2.setColor(Color.WHITE);
            g2.drawString(controlador.getTexto(), x, y); 
        }
    } 

public void paintVida(Graphics grafico, int vidaActual) {
    for (int i = 0; i < vidaActual; i++) {
        int x = x0 + i * (anchofoto + distancia);
        grafico.drawImage(imagenVida, (x + distancia), y0, anchofoto, altofoto, null);
    }
    if (vidaActual < vidasMaximas) {
        for (int i = vidaActual; i < vidasMaximas; i++) {
            int x = x0 + i * (anchofoto + distancia);
            grafico.drawImage(imagenVidaPerdida, (x + distancia), y0, anchofoto, altofoto, null);
        }
    }
}

    public void paintLaberinto (Graphics2D grafico){
        //filas
        for(int fila = 0; fila<this.cantidadFilas; fila++){
            //columnas
            for(int columna = 0; columna < this.cantidadColumnas; columna++){
                    //variable para la iteracion
                    grafico.drawImage(this.nivel.getTile(fila, columna).getSprite(), columna * anchoBloque, fila * altoBloque, anchoBloque, altoBloque, null);
                }     
            }
        }
    
    public void reproducirgifTutorial(){
        this.tutorialActivo = true;
        this.imagenTutotActual =0;
        this.tiempoTutorial.start();
    }

    public void detenergifTutorial(){
        this.tutorialActivo = false;
        this.tiempoTutorial.stop();
        repaint(); 
    }

    public void setRadioLuz(int radio){
        //inicializamos datos de la luz
        offsetXLuz = -10;
        offsetYLuz = -10;
        cx = controlador.getScreenXJugador() + controlador.getAnchoJugador() / 2 ;
        cy = controlador.getScreenYJugador() + controlador.getAltoJugador() / 2 ;
        this.centroLuz = new Point(cx, cy);
        this.radioLuz = radio;
        this.grad = new RadialGradientPaint(centroLuz, radioLuz, dist, cols);

        int anchoPanel = getWidth();
        int altoPanel = getHeight();

        // Capa temporal del tamaño del panel con transparencia
        layer = new BufferedImage(anchoPanel, altoPanel, BufferedImage.TYPE_INT_ARGB);
        Graphics2D lg = layer.createGraphics();

        // pinta toda la pantalla con negro (oscuridad)
        lg.setComposite(AlphaComposite.Src);
        lg.setColor(new Color(0, 0, 0, 255)); // 255 = completamente oscuro
        lg.fillRect(0, 0, anchoPanel, altoPanel);
        lg.setComposite(AlphaComposite.DstOut);
        lg.setPaint(grad);
        lg.fillOval(cx - radioLuz, cy - radioLuz, radioLuz * 2, radioLuz * 2);

        lg.dispose();
    }

    public int getRadioDefault(){
        return radioLuzDefault;
    }

    public void paintLuz(Graphics2D g) {
        if (g == null || controlador == null) return;
        // Dibuja la capa sobre la pantalla
        g.drawImage(layer, 0, 0, null);
    }




    @Override
    public void actualizar(String evento){
        if (evento.equals("GOLPE")){
            pasoGolpe=true;
            ultimoGolpe = System.currentTimeMillis();
        }
    }

    public void paintGolpe(Graphics2D g2){
        g2.setColor(new Color(100, 0, 20, 100));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
    }

    public void settutorialActivo(boolean estado){
        this.tutorialActivo = estado;
        if (estado){
            this.imagenTutotActual =0;
            this.tiempoTutorial.start();
        } else {
            this.tiempoTutorial.stop();
            repaint();
        }
    }



    //metodo para poder dibujar en el JPanel, tanto el jugador laberinto fantasmas
    //el override hace referencia a que es un metodo heredado de JPanel
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // offset calculado según su posición en el mundo y su posición en pantalla
        int camX = controlador.getCamX();
        int camY = controlador.getCamY();

        this.g2 = (Graphics2D) g.create();
        this.g2.translate(-camX, -camY); // mueve todo lo que se dibuje segun la camara

        
        //Se dibuja el laberinto en el panel  //SE MODIFICO 01:25
        //System.out.println("pintando laberinto");
        paintLaberinto(g2);

        //Se dibujan los objetos en el panel
        Objeto obj;
        if (objetos != null) {
            for (int i=0; i < objetos.size(); i++) {
                    if (objetos.get(i) != null) {
                        obj = objetos.get(i);
                        g2.drawImage(obj.getImage(), obj.getX(), obj.getY(), controlador.getAnchoBloque(), controlador.getAltoBloque(), null);
                }
            }
        }

        // SE DIBUJAN LOS FANTASMAS EN EL PANEL 
        // var es para declarar una variable sin definir el tipo, lo infiere solo
        for (var ghost : controlador.getFantasmas()) {
            BufferedImage spriteFantasma = getImagenFantasma(ghost.getDireccion(), ghost.spriteNum);

            int pantallaX = ghost.getMundoX();
            int pantallaY = ghost.getMundoY();

            g2.drawImage(spriteFantasma, pantallaX, pantallaY, ghost.getAnchoFantasma(), ghost.getAltoFantasma(), null);
        }
        
        // SE DIBUJA JUGADOR EN EL PANEL 
        g.drawImage(getImagenJugador(), controlador.getScreenXJugador(), controlador.getScreenYJugador(), controlador.getAnchoJugador(), controlador.getAltoJugador(), null); // dibuja el jugador en pantalla

        //Se dibuja la luz en el panel 
        if (controlador.getLuzActiva()){  
        paintLuz((Graphics2D) g);
        }
        
        if (pasoGolpe){
            if (ultimoGolpe + 300 > System.currentTimeMillis()){
                paintGolpe((Graphics2D) g);
            }
        }
        
        //Se dibuja el mensaje en el panel ////////////////////////
        drawMensaje((Graphics2D) (g), getWidth(), getHeight()); //SE MODIFICO 01:25


        //Se dibujan las vidas en el panel 
        paintVida(g,controlador.vidaActual());//SE MODIFICO 01:25

        if (tutorialActivo) {
            BufferedImage imagenActual = imagenWASD[imagenTutotActual];
            int x = (getWidth() - (imagenActual.getWidth())/3) / 2;
            int y = getHeight() - (imagenActual.getHeight()/3) - 150; 
            g.drawImage(imagenActual, x, y, imagenActual.getWidth()/3, imagenActual.getHeight()/3, null);
        }


        //para dibujar el cronometro tanto el estilo como la posicion
        Stopwatch stopwatch = controlador.getStopwatch(); //SE MODIFICO 01:25
        fuenteJuego = cargarFuente(); //SE MODIFICO 01:25
        
        g.setFont(fuentetime); //usa la fuente cargada en el constructor de panel juego
        String textoTiempo = "Tiempo: " + stopwatch.formatoTiempo(stopwatch.getSegundos()); //texto que se mostrara en pantalla

        FontMetrics metrics = g.getFontMetrics(fuenteJuego); //FontMetrics da informacion sobre la fuente y g.getFontMetrics le pide al objeto graphics que devuelva las metricas(ancho,alto,espaciado) de la fuente fuenteJuego 
        int anchoTexto = metrics.stringWidth(textoTiempo); //calcula el ancho de pixeles de la cadena textoTiempo, anchoTexto va a contener un entero con la cantidad de pixeles que ocupa en x textoTiempo

        int x = getWidth() - anchoTexto - 60; //se calcula y coloca la posicion x en el paneljuego (getWidth() da el ancho total del panel juego y se le resta el ancho de pixeles del texto y el -20 es un margen de separacion desde el borde derecho de la ventana para que no quede pegado al borde)
        int y = 40; //se coloca en la posicion y en el paneljuego

        g.setColor(Color.WHITE); //se setea el color del texto en blanco
        g.drawString(textoTiempo, x, y); //se dibuja el string textoTiempo en las posiciones x e y calculadas 

        g2.dispose();
    }
        
        

}



