package controller.menu;

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

import controller.juego.GameController;
import controller.juego.TutorialController;
import model.menu.Music;
import view.menu.MenuView;

public class MenuController {

    private MenuView view;
    //private MenuModel model;

    private boolean sonidoActivado = true;
    private Music music;
    GameController gc;

    public MenuController() {
        //this.model = new MenuModel();
        System.out.println("arrancó mc");
        this.view = new MenuView();
        this.actions();
        this.view.setVisible(true);
        
        if (music == null) {
            music = new Music("/assets/music/FireboyAndWatergirlSoundtrack.wav");
        }
        if (!music.isReproduciendo()) {
            music.reproducirLoop();
        }
    }

    private void actions() {

        // boton Jugar
        this.view.getBttnJugar().addActionListener((ActionEvent e) -> {
            
                     //Si hay musica sonando en el menu la pausa antes de iniciar el juego
                    if (music != null) {
                        music.pausar();
                    }

                    //Se crea un nuevo controlador del juego 
                    this.gc = new GameController();

                    gc.iniciarJuego("usuario");

                }); // el controlador
               
            
       
        // boton Tutorial
        this.view.getBttnTutorial().addActionListener((ActionEvent e) -> {
                if (music != null) {
                music.pausar();
                }
                TutorialController tutorialController = new TutorialController();
                tutorialController.iniciarTutorial();
                this.view.dispose();    
        });
            
         // boton Score
        this.view.getBttnScore().addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(view,"Mostrar puntajes");        
        });
    

        // boton Opciones
        this.view.getBttnOpciones().addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(view,"Mostrar el menu de opciones");        
        });
    

        // boton Salir
        this.view.getBttnSalir().addActionListener((ActionEvent e) -> {
            System.exit(0);
        });

        // boton Sonido off/on
        this.view.getBttnSonido().addActionListener((ActionEvent e) -> {
            if (sonidoActivado) {
                // Apagamos musica y sonidos
                music.pausar();
                sonidoActivado = false;
                view.getBttnSonido().setIcon(view.getMusicOffIcon());
                view.getBttnSonido().setText("");
                
                
            } else {
                // La prendemos
                music.reproducirLoop();
                sonidoActivado = true;
                view.getBttnSonido().setIcon(view.getMusicOnIcon());
                view.getBttnSonido().setText("");
                
                
            }
        });



    }
}
