// view/LightView.java
package view;

import entidades.Player;
import java.awt.*;
import java.awt.image.BufferedImage;

import controlador.GameController;

/*
 * LightView dibuja un overlay oscuro en la pantalla,
 * dejando un circulo iluminado alrededor del jugador (efecto "linterna").
 */
public class LightView {

    //private final Player player;

    // parametros fijos
    private final int radiusPx = 280;  // radio de luz en pixeles
    private final int alphaFondo = 255; // nivel de oscuridad general (0-255)

    private GameController controlador;

    public LightView(GameController controlador) {
        //this.player = player;
        this.controlador = controlador;
    }

    /*
     * el metodo principal de dibujo.
     * Se llama en el paintComponent del panel despues de dibujar el laberinto y el jugador
     *
     * g -> Graphics2D del panel
     * anchoPanel -> ancho actual del panel
     * altoPanel -> alto actual del panel
     * offX -> offset horizontal de la camara (cuanto esta corrido el mundo)
     * offY -> offset vertical de la camara
     */
    public void paint(Graphics2D g, int anchoPanel, int altoPanel) {
        if (g == null || anchoPanel <= 0 || altoPanel <= 0 || controlador == null) return;

        // capa temporal del tamaño del viewport (ARGB con transparencia)
        BufferedImage layer = new BufferedImage(anchoPanel, altoPanel, BufferedImage.TYPE_INT_ARGB);
        Graphics2D lg = layer.createGraphics();

        // pinta toda la pantalla con negro semitransparente
        lg.setComposite(AlphaComposite.Src);
        lg.setColor(new Color(0, 0, 0, alphaFondo));
        lg.fillRect(0, 0, anchoPanel, altoPanel);

        // calculamos el centro de la luz (ajustado un poco hacia arriba/izquierda)
        int offsetX = -5; // desplaza la luz horizontalmente
        int offsetY = -5; // desplaza la luz verticalmente
        int cx = controlador.getScreenXJugador() + controlador.getAnchoJugador() / 2 + offsetX;
        int cy = controlador.getScreenYJugador() + controlador.getAltoJugador() / 2 + offsetY;

        // gradiente circular
        float[] dist = {0f, 0.65f, 1f}; // posiciones relativas desde el centro hasta el borde, define en que puntos cambia el color
        Color[] cols = {
            new Color(0,0,0,250), // centro casi opaco
            new Color(0,0,0,100), // zona intermedia, en 65% desde el centro
            new Color(0,0,0,0)    // borde 
        }; // cols seria como el pincel que va a borrar el agujero (algo asi), con los gradientes de luz

        RadialGradientPaint p = new RadialGradientPaint(new Point(cx, cy), radiusPx, dist, cols); // crea el gradiente
        // Point es una clase que representa un punto (x,y), en este caso con las coordenadas del player

        //agujero circular en la oscuridad
        lg.setComposite(AlphaComposite.DstOut); // dstOut hace que lo proximo que se dibuje corte lo que ya esta dibujado
        lg.setPaint(p); // como se va a pintar
        lg.fillOval(cx - radiusPx, cy - radiusPx, radiusPx * 2, radiusPx * 2); // dibuja el circulo (x,y,ancho,alto)

        lg.dispose();

        // se dibuja  el overlay en el panel
        g.drawImage(layer, 0, 0, null);
    }
}
