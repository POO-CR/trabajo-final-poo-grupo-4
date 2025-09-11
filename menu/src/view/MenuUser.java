package view;

import javax.swing.*;
import java.awt.*;

public class MenuUser extends JDialog {
    
    private JButton bttnNuevoUsuario;
    private JButton bttnCargarUsuario;

    private MenuBackground backgroundUsuario;


    public MenuUser(JFrame parent) {
        super(parent, "Usuario", true);
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); //para cerrar bien con la X de la ventana

        // se carga el fondo **podemos cambiarlo dejo uno random 
        backgroundUsuario = new MenuBackground("/assets/img/backgroundmenuusuario.png");
        backgroundUsuario.setLayout(new BorderLayout());
        setContentPane(backgroundUsuario);
        
        // Panel central para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false); // que se vea el fondo
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));

        // BOTON NUEVO USUARIO
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/assets/img/NewUserButton.png"));
        ImageIcon originalHoverIcon = new ImageIcon(getClass().getResource("/assets/img/NewUserButtonHover.png"));

        Image newImg = originalIcon.getImage().getScaledInstance(250, 70, Image.SCALE_SMOOTH);
        ImageIcon nuevoIcon = new ImageIcon(newImg);

        Image newHoverImg = originalHoverIcon.getImage().getScaledInstance(250, 70, Image.SCALE_SMOOTH);
        ImageIcon nuevoHoverIcon = new ImageIcon(newHoverImg); 

        bttnNuevoUsuario = new JButton(nuevoIcon);
        bttnNuevoUsuario.setRolloverIcon(nuevoHoverIcon);
        bttnNuevoUsuario.setContentAreaFilled(false);
        bttnNuevoUsuario.setBorderPainted(false);
        bttnNuevoUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension botonSize = new Dimension(250, 70);
        bttnNuevoUsuario.setPreferredSize(botonSize);
        bttnNuevoUsuario.setMaximumSize(botonSize);
        bttnNuevoUsuario.setMinimumSize(botonSize);

        // BOTON CARGAR USUARIO
        ImageIcon originalcargarIcon = new ImageIcon(getClass().getResource("/assets/img/LoadUserButton.png"));
        ImageIcon originalcargarHoverIcon = new ImageIcon(getClass().getResource("/assets/img/LoadUserButtonHover.png"));
    
        Image newcargarImg = originalcargarIcon.getImage().getScaledInstance(250, 70, Image.SCALE_SMOOTH);
        ImageIcon nuevocargarIcon = new ImageIcon(newcargarImg);

        Image newcargarHoverImg = originalcargarHoverIcon.getImage().getScaledInstance(250, 70, Image.SCALE_SMOOTH);
        ImageIcon nuevocargarHoverIcon = new ImageIcon(newcargarHoverImg);

        bttnCargarUsuario = new JButton(nuevocargarIcon);
        bttnCargarUsuario.setRolloverIcon(nuevocargarHoverIcon);
        bttnCargarUsuario.setContentAreaFilled(false);
        bttnCargarUsuario.setBorderPainted(false);
        bttnCargarUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);

        bttnCargarUsuario.setPreferredSize(botonSize);
        bttnCargarUsuario.setMaximumSize(botonSize);
        bttnCargarUsuario.setMinimumSize(botonSize);

        // Añadir al panel
        panelBotones.add(Box.createVerticalGlue());
        panelBotones.add(bttnNuevoUsuario);
        panelBotones.add(Box.createVerticalStrut(40)); // espacio entre botones
        panelBotones.add(bttnCargarUsuario);
        panelBotones.add(Box.createVerticalGlue());

        backgroundUsuario.add(panelBotones, BorderLayout.CENTER);
}

    public JButton getBttnNuevoUsuario() {
        return bttnNuevoUsuario;
    }

    public JButton getBttnCargarUsuario() {
        return bttnCargarUsuario;
    }
}
