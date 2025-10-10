package view.juego;

import javax.swing.*;
import java.awt.*;

public class JuegoPausa extends JDialog {

    private JButton btnContinuar;
    private JButton btnSalir;
    private JButton btnReiniciar;

    public JuegoPausa(JFrame parent) {
        super(parent, "Pausa", true); // modal bloquea la interaccion con otras ventanas de la aplicacion hasta que se cierre en este caso con juegoview
        setSize(400, 250);
        setLocationRelativeTo(parent);
        setUndecorated(true);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(30, 30, 30));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 0, 15, 0);

        btnContinuar = crearBoton("Continuar");
        btnSalir = crearBoton("Salir al menu");
        btnReiniciar = crearBoton("Reiniciar Partida");

        gbc.gridy = 0;
        panel.add(btnContinuar, gbc);

        gbc.gridy = 1;
        panel.add(btnReiniciar, gbc); 

        gbc.gridy = 2;
        panel.add(btnSalir, gbc);

        add(panel);
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 20));
        boton.setBackground(new Color(50, 50, 50));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        boton.setPreferredSize(new Dimension(220, 50));
        return boton;
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
