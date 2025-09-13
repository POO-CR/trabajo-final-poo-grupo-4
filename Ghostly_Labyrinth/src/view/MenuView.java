package view;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color; 
import java.awt.Font;
import java.awt.Image;
//import java.awt.Menu; 
import java.util.ArrayList;

public class MenuView extends JFrame {

    private static final long serialVersionUID = 1L;

    private JButton bttnJugar;
    private JButton bttnTutorial;
    private JButton bttnOpciones;
    private JButton bttnSalir;
    private JButton bttnSonido;

    private ImageIcon musicOnIcon;
    private ImageIcon musicOffIcon;
   
    private MenuBackground background;

    private JLabel lblLogo;

    private ArrayList<JButton> buttons = new ArrayList<JButton>(); // Para el dinamismo de botones con la base de datos.

    public MenuView() {

        this.setTitle("Ghostly Mansion");
        this.setSize(1236, 724);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        try {
            ImageIcon windowIcon = new ImageIcon(getClass().getResource("/assets/img/GhostlyMansionLogo.png"));

            if (windowIcon.getImage() != null) {
                this.setIconImage(windowIcon.getImage());
            } else {
                System.err.println("No se pudo cargar la imagen del icono del juego.");
            }

		} catch (Exception e) {
			System.err.println("Error al cargar el icono del juego: " + e.getMessage());
		}

        background = new MenuBackground("/assets/img/Background1.png");
        background.setLayout(null);

        this.setContentPane(background);

        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/assets/img/GhostlyMansionLogo.png"));
            Image scaledLogoImage = logoIcon.getImage().getScaledInstance(420, 320, Image.SCALE_SMOOTH);
            lblLogo = new JLabel(new ImageIcon(scaledLogoImage));
            lblLogo.setBounds(20,350,420,320);
            background.add(lblLogo);
        } catch(Exception e) {
            System.err.println("Error al cargar el logo en el menú: " + e.getMessage());
			lblLogo = new JLabel("LOGO");
            lblLogo.setForeground(Color.RED);
            lblLogo.setBounds(30,550,300,200);
            background.add(lblLogo);
        }

        ImageIcon playIcon = new ImageIcon(getClass().getResource("/assets/img/PlayButton.png"));
        Image scaledPlayIcon = playIcon.getImage().getScaledInstance(200,60,Image.SCALE_SMOOTH);

        ImageIcon playIconHover = new ImageIcon(getClass().getResource("/assets/img/PlayButtonHover.png"));
        Image scaledPlayIconHover = playIconHover.getImage().getScaledInstance(200,60,Image.SCALE_SMOOTH);

        this.bttnJugar = new JButton(new ImageIcon(scaledPlayIcon));
        this.bttnJugar.setBounds(520, 200, 200, 60);
        bttnJugar.setOpaque(false);       // hace el fondo transparente
        bttnJugar.setContentAreaFilled(false); // quita el area rellena
        bttnJugar.setBorderPainted(false);     // quita el borde
        bttnJugar.setRolloverIcon(new ImageIcon(scaledPlayIconHover));
        background.add(this.bttnJugar);


        ImageIcon tutorialIcon = new ImageIcon(getClass().getResource("/assets/img/TutorialButton.png"));
        Image scaledTutorialIcon = tutorialIcon.getImage().getScaledInstance(200,60,Image.SCALE_SMOOTH);

        ImageIcon tutorialIconHover = new ImageIcon(getClass().getResource("/assets/img/TutorialButtonHover.png"));
        Image scaledTutorialIconHover = tutorialIconHover.getImage().getScaledInstance(200,60,Image.SCALE_SMOOTH);

        this.bttnTutorial = new JButton(new ImageIcon(scaledTutorialIcon));
        this.bttnTutorial.setBounds(520, 270, 200,60);
        bttnTutorial.setOpaque(false);       
        bttnTutorial.setContentAreaFilled(false); 
        bttnTutorial.setBorderPainted(false); 
        bttnTutorial.setRolloverIcon(new ImageIcon(scaledTutorialIconHover));
        background.add(this.bttnTutorial);


        ImageIcon optionsIcon = new ImageIcon(getClass().getResource("/assets/img/OptionsButton.png"));
        Image scaledOptionsIcon = optionsIcon.getImage().getScaledInstance(200,60,Image.SCALE_SMOOTH);

        ImageIcon optionsIconHover = new ImageIcon(getClass().getResource("/assets/img/OptionsButtonHover.png"));
        Image scaledOptionsIconHover = optionsIconHover.getImage().getScaledInstance(200,60,Image.SCALE_SMOOTH);

        this.bttnOpciones = new JButton(new ImageIcon(scaledOptionsIcon));
        this.bttnOpciones.setBounds(520, 340, 200,60);
        bttnOpciones.setOpaque(false);       
        bttnOpciones.setContentAreaFilled(false); 
        bttnOpciones.setBorderPainted(false);
        bttnOpciones.setRolloverIcon(new ImageIcon(scaledOptionsIconHover));
        background.add(this.bttnOpciones);


        ImageIcon exitIcon = new ImageIcon(getClass().getResource("/assets/img/ExitButton.png"));
        Image scaledExitIcon = exitIcon.getImage().getScaledInstance(200,60,Image.SCALE_SMOOTH);
        ImageIcon exitIconHover = new ImageIcon(getClass().getResource("/assets/img/ExitButtonHover.png"));
        Image scaledExitIconHover = exitIconHover.getImage().getScaledInstance(200,60,Image.SCALE_SMOOTH);


        this.bttnSalir = new JButton(new ImageIcon(scaledExitIcon));
        this.bttnSalir.setBounds(520, 410, 200,60);
        bttnSalir.setOpaque(false);       
        bttnSalir.setContentAreaFilled(false); 
        bttnSalir.setBorderPainted(false);
        bttnSalir.setRolloverIcon(new ImageIcon(scaledExitIconHover));
        background.add(this.bttnSalir);


        musicOnIcon = new ImageIcon(getClass().getResource("/assets/img/MusicOnButton.png"));
        musicOffIcon = new ImageIcon(getClass().getResource("/assets/img/MusicOffButton.png"));

        Image scaledMusicOn = musicOnIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        Image scaledMusicOff = musicOffIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);

        musicOnIcon = new ImageIcon(scaledMusicOn);
        musicOffIcon = new ImageIcon(scaledMusicOff);

        ImageIcon musicOnHover = new ImageIcon(getClass().getResource("/assets/img/MusicOnButtonHover.png"));
        ImageIcon musicOffHover = new ImageIcon(getClass().getResource("/assets/img/MusicOffButtonHover.png"));
        musicOnHover = new ImageIcon(musicOnHover.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        musicOffHover = new ImageIcon(musicOffHover.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));

        this.bttnSonido = new JButton(musicOnIcon);
        this.bttnSonido.setBounds(1130, 590, 50, 50);
        bttnSonido.setOpaque(false);       
        bttnSonido.setContentAreaFilled(false); 
        bttnSonido.setBorderPainted(false);

        bttnSonido.setRolloverIcon(musicOnHover);

        background.add(bttnSonido);

        /* 
        ImageIcon musicIcon = new ImageIcon(getClass().getResource("/assets/img/MusicOnButton.png"));
        Image scaledMusicIcon = musicIcon.getImage().getScaledInstance(10,10,Image.SCALE_SMOOTH);
        this.bttnSonido = new JButton(new ImageIcon(scaledMusicIcon));
        this.bttnSonido.setBounds(1400, 950, 10, 10);
        background.add(this.bttnSonido);
        */


        JLabel version = new JLabel("Version: 0.0.2");
		version.setForeground(Color.white); 
		version.setFont(new Font("Calibri", Font.BOLD, 18));
		version.setBounds(1100, 650, 150, 30); 
		background.add(version);
    }

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