package view;

import javax.swing.*;

import model.juego.KeyHandler;
import view.juego.CargaPanel;
import view.juego.JuegoPanel;
import view.menu.MenuPanel;

import java.awt.*;

public class FramePrincipal extends JFrame {


    /* MenuController menuController;
    GameController gameController;
    TutorialController tutorialController; */


    // cardlayout que maneje todos los paneles
    // PROPIEDADES de la ventana
    // pantalla completa, setvisible, undecorated
    // NO!! botones, pintar en pantalla NO!!
    // getters setters de los paneles

    private JPanel mainPanel;

    private JuegoPanel juegoPanel;
    private CargaPanel cargaPanel;
    private MenuPanel menuPanel;

    private CardLayout cardLayout;

    private static final String PANEL_CARGA = "carga";
    private static final String PANEL_JUEGO = "juego";
    private static final String PANEL_MENU = "menu";


    
        
    public FramePrincipal (){

        
        // usamos el layout del MenuPanel como BorderLayout y agregamos el fondo dentro
        //this.add(background);

        this.cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        this.menuPanel = new MenuPanel();
        this.cargaPanel = new CargaPanel();
        this.juegoPanel = new JuegoPanel();
        
        
        mainPanel.add(menuPanel, PANEL_MENU);
        mainPanel.add(cargaPanel, PANEL_CARGA);
        mainPanel.add(juegoPanel, PANEL_JUEGO);

        mainPanel.setOpaque(true);

        setTitle("Ghostly Labyrinth");
         // quita la barra de titulo y bordes
        setExtendedState(JFrame.MAXIMIZED_BOTH); // ocupa toda la pantalla
        setUndecorated(true);


        //icono de la ventana
        try {
            ImageIcon windowIcon = new ImageIcon(getClass().getResource("/assets/img/GhostlyMansionLogo.png"));
            if (windowIcon.getImage() != null) this.setIconImage(windowIcon.getImage());
        } catch (Exception e) {
            System.err.println("Error al cargar el icono del juego: " + e.getMessage());
        } 

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        //setVisible(true);
        setContentPane(mainPanel);

        
        setVisible(true);
        mostrarMenu();
    }

    

        
    public void mostrarMenu() {

        System.out.println("mostrando menu");

        cardLayout.show(mainPanel, PANEL_MENU);
        javax.swing.SwingUtilities.invokeLater(() -> {
            mainPanel.validate();
            mainPanel.repaint();
            mainPanel.requestFocusInWindow();
        });
    }

    public void mostrarJuego(KeyHandler keyH) {
        cardLayout.show(mainPanel, PANEL_JUEGO);
        añadirJuegoListener(keyH);
    }

    public void añadirJuegoListener(KeyHandler keyH){
        mainPanel.addKeyListener(keyH);
        javax.swing.SwingUtilities.invokeLater(() -> {
            mainPanel.validate();
            mainPanel.requestFocusInWindow();
            repaint();
        });
    }

    public void mostrarCarga() {
        cardLayout.show(mainPanel, PANEL_CARGA);
            javax.swing.SwingUtilities.invokeLater(() -> {
            mainPanel.validate();
            mainPanel.repaint();
            mainPanel.requestFocusInWindow();
        });
    }

    public MenuPanel getMenuPanel(){
        return this.menuPanel;
    }

    public JuegoPanel getJuegoPanel(){
        return this.juegoPanel;
    }

    public CargaPanel getCargaPanel(){
        return this.cargaPanel;
    }


        
}