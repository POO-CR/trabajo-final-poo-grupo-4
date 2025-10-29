package view.juego;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PantallaVictoria extends JPanel {

    private Image imagenFondo;

    public PantallaVictoria() {
        try {
        imagenFondo = new ImageIcon(getClass().getResource("/assets/img/PantallasFinales/PantallaGanar.png")).getImage();
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen de fondo");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
    }
}
