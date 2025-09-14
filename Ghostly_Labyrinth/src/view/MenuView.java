package view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MenuView extends JFrame {

    private static final long serialVersionUID = 1L;

    // botones principales del menu
    private JButton bttnJugar;
    private JButton bttnTutorial;
    private JButton bttnOpciones;
    private JButton bttnSalir;
    private JButton bttnSonido;

    // iconos de musica on/off
    private ImageIcon musicOnIcon;
    private ImageIcon musicOffIcon;

    // fondo y logo
    private MenuBackground background;
    private JLabel lblLogo;

    // lista por si despues queremos manipular los botones mediante base de datos
    private ArrayList<JButton> buttons = new ArrayList<>();

    public MenuView() {

        // titulo y config basica de la ventana
        this.setTitle("Ghostly Mansion");
        this.setSize(1236, 724);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // centrada en la pantalla

        // icono de la ventana
        try {
            ImageIcon windowIcon = new ImageIcon(getClass().getResource("/assets/img/GhostlyMansionLogo.png"));
            if (windowIcon.getImage() != null) this.setIconImage(windowIcon.getImage());
        } catch (Exception e) {
            System.err.println("Error al cargar el icono del juego: " + e.getMessage());
        }

        // fondo principal
        background = new MenuBackground("/assets/img/Background1.png");
        background.setLayout(new BorderLayout());
        this.setContentPane(background);

        // dimension general de los botones
        Dimension botonSize = new Dimension(210, 70);

        // BOTONES DEL MENU PRINCIPAL

        // jugar
        ImageIcon playIcon = new ImageIcon(getClass().getResource("/assets/img/PlayButton.png"));
        ImageIcon playIconHover = new ImageIcon(getClass().getResource("/assets/img/PlayButtonHover.png"));
        Image scaledPlayIcon = playIcon.getImage().getScaledInstance(210, 70, Image.SCALE_SMOOTH);
        Image scaledPlayIconHover = playIconHover.getImage().getScaledInstance(210, 70, Image.SCALE_SMOOTH);
        bttnJugar = new JButton(new ImageIcon(scaledPlayIcon));
        styleMenuButton(bttnJugar, botonSize, new ImageIcon(scaledPlayIconHover));

        // tutorial
        ImageIcon tutorialIcon = new ImageIcon(getClass().getResource("/assets/img/TutorialButton.png"));
        ImageIcon tutorialIconHover = new ImageIcon(getClass().getResource("/assets/img/TutorialButtonHover.png"));
        Image scaledTutorialIcon = tutorialIcon.getImage().getScaledInstance(210, 70, Image.SCALE_SMOOTH);
        Image scaledTutorialIconHover = tutorialIconHover.getImage().getScaledInstance(210, 70, Image.SCALE_SMOOTH);
        bttnTutorial = new JButton(new ImageIcon(scaledTutorialIcon));
        styleMenuButton(bttnTutorial, botonSize, new ImageIcon(scaledTutorialIconHover));

        // opciones
        ImageIcon optionsIcon = new ImageIcon(getClass().getResource("/assets/img/OptionsButton.png"));
        ImageIcon optionsIconHover = new ImageIcon(getClass().getResource("/assets/img/OptionsButtonHover.png"));
        Image scaledOptionsIcon = optionsIcon.getImage().getScaledInstance(210, 70, Image.SCALE_SMOOTH);
        Image scaledOptionsIconHover = optionsIconHover.getImage().getScaledInstance(210, 70, Image.SCALE_SMOOTH);
        bttnOpciones = new JButton(new ImageIcon(scaledOptionsIcon));
        styleMenuButton(bttnOpciones, botonSize, new ImageIcon(scaledOptionsIconHover));

        // salir
        ImageIcon exitIcon = new ImageIcon(getClass().getResource("/assets/img/ExitButton.png"));
        ImageIcon exitIconHover = new ImageIcon(getClass().getResource("/assets/img/ExitButtonHover.png"));
        Image scaledExitIcon = exitIcon.getImage().getScaledInstance(210, 70, Image.SCALE_SMOOTH);
        Image scaledExitIconHover = exitIconHover.getImage().getScaledInstance(210, 70, Image.SCALE_SMOOTH);
        bttnSalir = new JButton(new ImageIcon(scaledExitIcon));
        styleMenuButton(bttnSalir, botonSize, new ImageIcon(scaledExitIconHover));

        // LOGO
        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/assets/img/GhostlyMansionLogo.png"));
            Image scaledLogoImage = logoIcon.getImage().getScaledInstance(400, 328, Image.SCALE_SMOOTH);
            lblLogo = new JLabel(new ImageIcon(scaledLogoImage));
        } catch (Exception e) {
            System.err.println("Error al cargar el logo en el menu: " + e.getMessage());
            lblLogo = new JLabel("LOGO");
            lblLogo.setForeground(Color.RED);
            lblLogo.setFont(new Font("Calibri", Font.BOLD, 22));
        }

        // BOTON SONIDO
        musicOnIcon = new ImageIcon(new ImageIcon(getClass().getResource("/assets/img/MusicOnButton.png"))
                .getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        musicOffIcon = new ImageIcon(new ImageIcon(getClass().getResource("/assets/img/MusicOffButton.png"))
                .getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        ImageIcon musicOnHover = new ImageIcon(new ImageIcon(getClass().getResource("/assets/img/MusicOnButtonHover.png"))
                .getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));

        bttnSonido = new JButton(musicOnIcon);
        bttnSonido.setOpaque(false);
        bttnSonido.setContentAreaFilled(false);
        bttnSonido.setBorderPainted(false);
        bttnSonido.setFocusPainted(false);
        bttnSonido.setRolloverIcon(musicOnHover);

        // version del juego
        JLabel version = new JLabel("Version: 0.0.3");
        version.setForeground(Color.white);
        version.setFont(new Font("Calibri", Font.BOLD, 18));

        // LAYOUT DE 3 COLUMNAS PARA ACOMODAR LOS BOTONES Y EL LOGO

        JPanel main = new JPanel(new BorderLayout());
        main.setOpaque(false);

        // columna izquierda (logo abajo)
        JPanel leftCol = new JPanel(new BorderLayout());
        leftCol.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 0)); // margen para correr el logo
        leftCol.setOpaque(false);
        leftCol.add(lblLogo, BorderLayout.SOUTH);

        // columna central (los botones con gridbaglayout)
        JPanel centerCol = new JPanel(new GridBagLayout());
        centerCol.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 0, 10, 250);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridy = 0;
        centerCol.add(bttnJugar, gbc);

        gbc.gridy = 1;
        centerCol.add(bttnTutorial, gbc);

        gbc.gridy = 2;
        centerCol.add(bttnOpciones, gbc);

        gbc.gridy = 3;
        centerCol.add(bttnSalir, gbc);

        // columna derecha (boton sonido + version)
        JPanel rightCol = new JPanel(new BorderLayout());
        rightCol.setOpaque(false);

        JPanel rightBottom = new JPanel();
        rightBottom.setOpaque(false);
        rightBottom.setLayout(new BoxLayout(rightBottom, BoxLayout.Y_AXIS));
        rightBottom.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 40)); // margen para no pegarse tanto al borde
        bttnSonido.setAlignmentX(Component.RIGHT_ALIGNMENT);
        version.setAlignmentX(Component.RIGHT_ALIGNMENT);

        rightBottom.add(bttnSonido);
        rightBottom.add(Box.createVerticalStrut(6));
        rightBottom.add(version);
        rightCol.add(rightBottom, BorderLayout.SOUTH);

        // ensamblado final
        main.add(leftCol, BorderLayout.WEST);
        main.add(centerCol, BorderLayout.CENTER);
        main.add(rightCol, BorderLayout.EAST);

        background.add(main, BorderLayout.CENTER);
    }

    // metodo para darle estilo a los botones con imagen (es un helper)

    private static void styleMenuButton(JButton b, Dimension size, Icon rollover) {
        b.setOpaque(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setPreferredSize(size);
        b.setMaximumSize(size);
        b.setMinimumSize(size);
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        if (rollover != null) b.setRolloverIcon(rollover);
    }

    // getters para acceder a los botones

    public ArrayList<JButton> getButtons() { 
        return this.buttons; 
    }

    public JButton getBttnJugar() { 
        return this.bttnJugar; 
    }

    public JButton getBttnTutorial() { 
        return this.bttnTutorial; 
    }

    public JButton getBttnOpciones() { 
        return this.bttnOpciones; 
    }

    public JButton getBttnSalir() { 
        return this.bttnSalir; 
    }

    public JButton getBttnSonido() { 
        return this.bttnSonido; 
    }

    public ImageIcon getMusicOnIcon() { 
        return musicOnIcon; 
    }

    public ImageIcon getMusicOffIcon() { 
        return musicOffIcon; 
    }

}