package controller.menu;

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

import controller.juego.GameController;
import model.menu.Music;
import view.juego.MenuRegisterStart;
import view.menu.MenuView;

public class MenuController {

    private MenuView view;
    //private MenuModel model;

    private boolean sonidoActivado = true;
    private Music music;
    GameController gc;

    private final GameController gameController;

    public MenuController(GameController gameController) {
        //this.model = new MenuModel();
        this.gameController = gameController;
        System.out.println("arrancó mc");
        this.view = new MenuView();
        this.actions();
        this.view.setVisible(true);
        this.music = new Music("/assets/music/FireboyAndWatergirlSoundtrack.wav");
        this.music.reproducirLoop(); // Se inicializa la musica como encendida

    }

    private void actions() {

        // boton Jugar
        this.view.getBttnJugar().addActionListener((ActionEvent e) -> {
            
                     //Si hay musica sonando en el menu la pausa antes de iniciar el juego
                    if (music != null) {
                        music.pausar();
                    }

                    this.gameController.iniciarJuego("usuario");

                }); // el controlador
               
            
       
        // boton Tutorial
        this.view.getBttnTutorial().addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(view,"Mostrar tutorial");        
        });
            
         // boton Score
        this.view.getBttnScore().addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(view,"Mostrar puntajes");        
        });
    

        // boton Opciones
        this.view.getBttnOpciones().addActionListener((ActionEvent e) -> {
            //JOptionPane.showMessageDialog(view,"Mostrar el menu de opciones"); descomentar luego de que ande 
            //MenuRegisterStart menuRegister = new MenuRegisterStart(view);

            // Acción para cerrar al presionar "Volver"
            //menuRegister.getVolverButton().addActionListener(ev -> {
            //menuRegister.dispose(); // cierra el JDialog
        });

            //menuRegister.setVisible(true);
       // });
    

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
