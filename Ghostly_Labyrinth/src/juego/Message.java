package juego;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import entidades.objetos.Objeto;
import entidades.Player;
import juego.JuegoView;

public class Message {
    // Atributos para el mensaje
    private String texto;
    private boolean visible = false;
    private long tiempoInicio;
    private long duracionMillis;
    private Objeto obj[];
        private JuegoView juegoView;
    private Player jugador;

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

            // Texto
            g2.setColor(Color.WHITE);
            g2.drawString(texto, x, y);
        }
    }

        public void InteraccionDeMensajes (int i){
        if (i != 999) {
            String nombreObj = obj[i].nombre;
            switch (nombreObj) {
                case "Llave" -> {
                    jugador.setTieneLlave(true);
                    System.out.println("AGARRO LLAVE EN POSICION" + obj[i].getX() + " ; " + juegoView.obj[i].getY());
                    obj[i] = null;
                    juegoView.mostrarMensaje("Obtuviste la llave",2000);
                }
            }
        }
    }
}