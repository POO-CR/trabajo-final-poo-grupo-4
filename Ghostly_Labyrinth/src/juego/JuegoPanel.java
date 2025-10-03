package juego;

import javax.swing.*;

import controlador.GameController;

import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.FontMetrics;

public class JuegoPanel extends JPanel{

    private GameController controlador;
    
    public JuegoPanel (GameController controlador) {
        this.controlador = controlador;
        setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize()); // para que tome el tamaño de la pantalla nativa!! copado!!
        setBackground(Color.black);
        setDoubleBuffered(true); // guarda 2 buffer en memoria para que no carguen en tiempo real en pan                         // igual según internet no es necesario(?
        setFocusable(true); // el setfocusable es para que se "concentre" en los eventos del teclado
    }

    //metodo para poder dibujar en el JPanel, tanto el jugador laberinto fantasmas 
    //el override hace referencia a que es un metodo heredado de JPanel 
    @Override
    public void paintComponent(Graphics g) {

    super.paintComponent(g);

    // calcular offset de la camara (segun la posicion del jugador) 
    // PASARLO AL CONTROLADORRRR
    int camX = controlador.getMundoXJugador() - controlador.getScreenXJugador();
    int camY = jv.jugador.getMundoY() - jv.jugador.screenY;

    Graphics2D g2 = (Graphics2D) g.create();
    g2.translate(-camX, -camY); // mueve todo lo que se dibuje segun la camara


    jv.laberinto.paintComponent(g2);

    for (int i=0; i<jv.obj.length; i++) {
        if (jv.obj[i] != null) {
            jv.obj[i].draw(g2);
        }
    }

    //jugador.draw(g2); // dibuja el jugador en el mundo, esto hace que funcione mal
    g2.dispose();
    
    g.drawImage(jv.getImagenJugador(), jv.jugador.getScreenX(), jv.jugador.getScreenX(), jv.jugador.getAnchoPersonaje(), jv.jugador.getAltoPersonaje(), null); // dibuja el jugador en pantalla

    if (jv.light != null) {
        jv.light.paint((Graphics2D) g, getWidth(), getHeight());
    }
    
    jv.mensaje.draw((Graphics2D) g, getWidth(), getHeight());
    
    jv.jugador.paintvida(g);

    //para dibujar el cronometro tanto el estilo como la posicion

    g.setFont(jv.fuenteJuego); //usa la fuente cargada en el constructor de panel juego
    String textoTiempo = "Time: " + jv.stopwatch.formatoTiempo(jv.stopwatch.getSegundos()); //texto que se mostrara en pantalla

    FontMetrics metrics = g.getFontMetrics(jv.fuenteJuego); //FontMetrics da informacion sobre la fuente y g.getFontMetrics le pide al objeto graphics que devuelva las metricas(ancho,alto,espaciado) de la fuente fuenteJuego 
    int anchoTexto = metrics.stringWidth(textoTiempo); //calcula el ancho de pixeles de la cadena textoTiempo, anchoTexto va a contener un entero con la cantidad de pixeles que ocupa en x textoTiempo

    int x = getWidth() - anchoTexto - 20; //se calcula y coloca la posicion x en el paneljuego (getWidth() da el ancho total del panel juego y se le resta el ancho de pixeles del texto y el -20 es un margen de separacion desde el borde derecho de la ventana para que no quede pegado al borde)
    int y = 40; //se coloca en la posicion y en el paneljuego

    g.setColor(Color.WHITE); //se setea el color del texto en blanco
    g.drawString(textoTiempo, x, y); //se dibuja el string textoTiempo en las posiciones x e y calculadas 
}




}