package controller.menu;

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

import controller.ControladorPrincipal;
import controller.juego.GameController;
import controller.juego.TutorialController;
import model.menu.Music;
import view.FramePrincipal;
import view.menu.MenuPanel;
import java.awt.event.ComponentAdapter;  
import java.awt.event.ComponentEvent;  

public class MenuController {

    private MenuPanel view;
    //private MenuModel model;

    private boolean sonidoActivado = true;
    private Music music;

    private ControladorPrincipal controladorPrincipal;
    private FramePrincipal framePrincipal;
    private boolean estadoMusica;  

    public MenuController(ControladorPrincipal controladorPrincipal, FramePrincipal framePrincipal) {
        //this.model = new MenuModel();
        System.out.println("arrancó mc");
        this.controladorPrincipal = controladorPrincipal;
        this.framePrincipal = framePrincipal;
        this.view = framePrincipal.getMenuPanel();
        this.actions();
        this.view.setVisible(true);
        estadoMusica =controladorPrincipal.musicaActiva();  
        
        
        if (music == null) {
            music = new Music("/assets/music/FireboyAndWatergirlSoundtrack.wav");
        }

        iniciarMusica();

        this.view.addComponentListener(new ComponentAdapter() {   //componentAdapter es un conjunto de metodos vacios para escuchar eventos de componentes
            @Override
            public void componentShown(ComponentEvent e) { //Este listener actua cuando ocurre en evento de mostrar el panel
                iniciarMusica();
            }
        });
    }

    
    private void actions() {

        // boton Jugar
        this.view.getBttnJugar().addActionListener((ActionEvent e) -> {
            
                     //Si hay musica sonando en el menu la pausa antes de iniciar el juego
                    if (music != null) {
                        music.pausar();
                    }
                    
                    //Se crea un nuevo controlador del juego 
                    this.controladorPrincipal.pasarAJuego();

                }); // el controlador
               
            
       
        // boton Tutorial
        this.view.getBttnTutorial().addActionListener((ActionEvent e) -> {
                if (music != null) {
                music.pausar();
                }
                controladorPrincipal.pasarATutorial();
        });
            
         // boton Score
        this.view.getBttnScore().addActionListener((ActionEvent e) -> {
            if (music != null) music.pausar();

            this.view.setVisible(false);

            controladorPrincipal.pasarAScore();       
        });
    

        // boton Opciones
        //this.view.getBttnOpciones().addActionListener((ActionEvent e) -> {
          //  JOptionPane.showMessageDialog(view,"Mostrar el menu de opciones");        
        //});
    

        // boton Salir
        this.view.getBttnSalir().addActionListener((ActionEvent e) -> {
            System.exit(0);
        });

        // boton Sonido off/on
        this.view.getBttnSonido().addActionListener((ActionEvent e) -> {
            controladorPrincipal.modificarEstadoMusica();
            estadoMusica = controladorPrincipal.musicaActiva();
            if (!estadoMusica) {
                view.getBttnSonido().setIcon(view.getMusicOffIcon());
                view.getBttnSonido().setText("");
                
                
            } else {
                // La prendemos
                view.getBttnSonido().setIcon(view.getMusicOnIcon());
                view.getBttnSonido().setText("");
                
                
            }
        });



    }

    public void iniciarMusica() {
        if (music != null && !music.isReproduciendo() && estadoMusica) {  
            music.reproducirLoop();
        }
    }

    public void cambiarEstadoMusica() {  
        estadoMusica = !estadoMusica;
        if (estadoMusica) {
            music.reproducirLoop();
        } else {
            music.pausar();
        }
    }
}
