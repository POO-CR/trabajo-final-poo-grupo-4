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
    private final int ancho = 260;
    private final int alto = 60;

    public PantallaDerrota() {
        this.setLayout(null);
        try {
            imagenFondo = new ImageIcon(getClass().getResource("/assets/img/PantallasFinales/PantallaPerder.png")).getImage();
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen de fondo");
        }

        //boton reiniciar
        ImageIcon originalReiniciarIcon = new ImageIcon(getClass().getResource("/assets/img/ReiniciarPartida.png"));
        ImageIcon originalHoverReiniciarIcon = new ImageIcon(getClass().getResource("/assets/img/ReiniciarPartidaHover.png"));

        Image newImgreiniciar = originalReiniciarIcon.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        ImageIcon nuevoiconcreateReiniciar = new ImageIcon(newImgreiniciar);

        Image newHoverImgreiniciar = originalHoverReiniciarIcon.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        ImageIcon nuevoHoverIconReiniciar = new ImageIcon(newHoverImgreiniciar); 

        botonReiniciar = new JButton(nuevoiconcreateReiniciar);
        botonReiniciar.setRolloverIcon(nuevoHoverIconReiniciar);
        botonReiniciar.setContentAreaFilled(false);
        botonReiniciar.setBorderPainted(false);

        botonReiniciar.setBounds(640, 635, ancho, alto);
        this.add(botonReiniciar);


        //Boton menu principal
        ImageIcon originalMenuPrincipalIcon = new ImageIcon(getClass().getResource("/assets/img/VolverAlMenu.png"));
        ImageIcon originalHoverMenuPrincipalIcon = new ImageIcon(getClass().getResource("/assets/img/VolverAlMenuHover.png"));

        Image newImgMenuPrincipal = originalMenuPrincipalIcon.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        ImageIcon nuevoiconcreateMenuPrincipal = new ImageIcon(newImgMenuPrincipal);

        Image newHoverImgMenuPrincipal = originalHoverMenuPrincipalIcon.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        ImageIcon nuevoHoverIconMenuPrincipal = new ImageIcon(newHoverImgMenuPrincipal); 

        botonMenuPrincipal = new JButton(nuevoiconcreateMenuPrincipal);
        botonMenuPrincipal.setRolloverIcon(nuevoHoverIconMenuPrincipal);
        botonMenuPrincipal.setContentAreaFilled(false);
        botonMenuPrincipal.setBorderPainted(false);

        botonMenuPrincipal.setBounds(640, 735, ancho, alto);
        this.add(botonMenuPrincipal);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
    }

    public JButton getBtnReiniciar() {
        return botonReiniciar;
    }

    public JButton getBtnMenuPrincipal() {
        return botonMenuPrincipal;
    }
}