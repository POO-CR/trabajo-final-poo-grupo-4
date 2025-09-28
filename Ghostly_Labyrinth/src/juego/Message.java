package juego;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class Message {
    // Atributos para el mensaje
    private String texto;
    private boolean visible = false;
    private long tiempoInicio;
    private long duracionMillis;

    //Metodos para el mensaje
    public void mostrar(String texto, int milisegundos) {
        this.texto = texto;
        this.duracionMillis = milisegundos;
        this.tiempoInicio = System.currentTimeMillis();
        this.visible = true;
    }

    public void update() {
        if (visible && System.currentTimeMillis() > tiempoInicio + duracionMillis) {
            visible = false;
        }
    }

    public void draw(Graphics2D g2, int panelAncho, int panelAlto) {
        if (visible) {
            // Fuente y color del mensaje
            Font fuente = new Font("Arial", Font.BOLD, 40);
            g2.setFont(fuente);
            FontMetrics metrics = g2.getFontMetrics(fuente);

            // Medidas para centrar el texto
            int x = (panelAncho - metrics.stringWidth(texto)) / 2;
            int y = panelAlto / 2;

            // Fondo para nuestro texto
            g2.setColor(new Color(0, 0, 0, 170));
            g2.fillRoundRect(x - 20, y - metrics.getAscent(), metrics.stringWidth(texto) + 40, metrics.getHeight() + 10, 15, 15);

            // Texto
            g2.setColor(Color.WHITE);
            g2.drawString(texto, x, y);
        }
    }
}