package view.juego;

import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class PantallaDerrota extends JPanel {

    private Image imagenFondo;
    private JButton botonReiniciar;
    private JButton botonMenuPrincipal;
    private final int ancho = 150;
    private final int alto = 150;

    public PantallaDerrota() {
        try {
            imagenFondo = new ImageIcon(getClass().getResource("/assets/img/PantallasFinales/PantallaPerder.png")).getImage();
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen de fondo");
        }

        //boton reiniciar
        ImageIcon originalReiniciarIcon = new ImageIcon(getClass().getResource("/assets/img/botones/RestartButton.png"));
        ImageIcon originalHoverReiniciarIcon = new ImageIcon(getClass().getResource("/assets/img/botones/RestartButtonHover.png"));

        Image newImgreiniciar = originalReiniciarIcon.getImage().getScaledInstance(150, 50, Image.SCALE_SMOOTH);
        ImageIcon nuevoiconcreateReiniciar = new ImageIcon(newImgreiniciar);

        Image newHoverImgreiniciar = originalHoverReiniciarIcon.getImage().getScaledInstance(150, 50, Image.SCALE_SMOOTH);
        ImageIcon nuevoHoverIconReiniciar = new ImageIcon(newHoverImgreiniciar); 

        botonReiniciar = new JButton(nuevoiconcreateReiniciar);
        botonReiniciar.setRolloverIcon(nuevoHoverIconReiniciar);
        botonReiniciar.setContentAreaFilled(false);
        botonReiniciar.setBorderPainted(false);

        botonReiniciar.setBounds(300, 400, ancho, alto/3);
        this.add(botonReiniciar);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
    }

    public JButton getBtnReiniciar() {
        return botonReiniciar;
    }
}