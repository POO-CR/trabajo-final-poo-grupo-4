package view.juego;

import javax.swing.*;
import java.awt.*;

public class JuegoPausa extends JDialog {

    private JButton btnContinuar;
    private JButton btnSalir;
    private JButton btnReiniciar;
    private Image backgroundPausa; 

    public JuegoPausa(JFrame parent) {
        super(parent, "Pausa", true); // modal bloquea la interaccion con otras ventanas de la aplicacion hasta que se cierre en este caso con juegoview
        setSize(500, 350);
        setLocationRelativeTo(parent);
        setUndecorated(true);
    
        // se carga la imagen del fonod
        backgroundPausa = new ImageIcon(getClass().getResource("/assets/img/backgroundPausa.png")).getImage();

        // panel principal con fondo
        JPanel panelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundPausa, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelFondo.setLayout(new GridBagLayout());
        setContentPane(panelFondo);
        
        //panel de botones
        JPanel panel = new JPanel();
        panel.setOpaque(false); //deja ver el fondo
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 0, 15, 0);

        //BOTON CONTINUAR
        ImageIcon originalContinuarIcon = new ImageIcon(getClass().getResource("/assets/img/botones/ContinueButton.png"));
        ImageIcon originalHoverContinuarIcon = new ImageIcon(getClass().getResource("/assets/img/botones/ContinueButtonHover.png"));

        Image newImg = originalContinuarIcon.getImage().getScaledInstance(150, 50, Image.SCALE_SMOOTH);
        ImageIcon nuevoiconcreate = new ImageIcon(newImg);

        Image newHoverImg = originalHoverContinuarIcon.getImage().getScaledInstance(150, 50, Image.SCALE_SMOOTH);
        ImageIcon nuevoHoverIconCrear = new ImageIcon(newHoverImg); 

        btnContinuar = new JButton(nuevoiconcreate);
        btnContinuar.setRolloverIcon(nuevoHoverIconCrear);
        btnContinuar.setContentAreaFilled(false);
        btnContinuar.setBorderPainted(false);

        //BOTON REINICIAR
        ImageIcon originalReiniciarIcon = new ImageIcon(getClass().getResource("/assets/img/botones/RestartButton.png"));
        ImageIcon originalHoverReiniciarIcon = new ImageIcon(getClass().getResource("/assets/img/botones/RestartButtonHover.png"));

        Image newImgreiniciar = originalReiniciarIcon.getImage().getScaledInstance(150, 50, Image.SCALE_SMOOTH);
        ImageIcon nuevoiconcreateReiniciar = new ImageIcon(newImgreiniciar);

        Image newHoverImgreiniciar = originalHoverReiniciarIcon.getImage().getScaledInstance(150, 50, Image.SCALE_SMOOTH);
        ImageIcon nuevoHoverIconReiniciar = new ImageIcon(newHoverImgreiniciar); 

        btnReiniciar = new JButton(nuevoiconcreateReiniciar);
        btnReiniciar.setRolloverIcon(nuevoHoverIconReiniciar);
        btnReiniciar.setContentAreaFilled(false);
        btnReiniciar.setBorderPainted(false);

        //BOTON MENU
        ImageIcon originalMenuIcon = new ImageIcon(getClass().getResource("/assets/img/botones/MenuButton.png"));
        ImageIcon originalHoverMenuIcon = new ImageIcon(getClass().getResource("/assets/img/botones/MenuButtonHover.png"));

        Image newImgMenu = originalMenuIcon.getImage().getScaledInstance(150, 50, Image.SCALE_SMOOTH);
        ImageIcon nuevoiconcreateMenu = new ImageIcon(newImgMenu);

        Image newHoverImgMenu = originalHoverMenuIcon.getImage().getScaledInstance(150, 50, Image.SCALE_SMOOTH);
        ImageIcon nuevoHoverIconMenu = new ImageIcon(newHoverImgMenu); 

        btnSalir = new JButton(nuevoiconcreateMenu);
        btnSalir.setRolloverIcon(nuevoHoverIconMenu);
        btnSalir.setContentAreaFilled(false);
        btnSalir.setBorderPainted(false);

        //se agregan los botones
        gbc.gridy = 0;
        panel.add(btnContinuar, gbc);

        gbc.gridy = 1;
        panel.add(btnReiniciar, gbc); 

        gbc.gridy = 2;
        panel.add(btnSalir, gbc);

        //para que no se haga el borde blanco que Swing dibuja como un indicador de foco (focus outline) cuando el boton esta seleccionado.
        btnContinuar.setFocusPainted(false);
        btnReiniciar.setFocusPainted(false);
        btnSalir.setFocusPainted(false);

        //se añade el panel de botones al centro del panel con fondo
        panelFondo.add(panel, new GridBagConstraints());
    }
    
    // getters para que el controlador conecte los botones
    public JButton getbtnContinuar() {
        return btnContinuar;
    }

    public JButton getbtnSalir() {
        return btnSalir;
    }

    public JButton getBtnReiniciar() {
    return btnReiniciar;
    }
}
