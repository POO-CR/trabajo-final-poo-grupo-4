package controller.menu;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import controller.juego.GameController;
import controller.juego.TutorialController;

import model.juego.RecordDAO;
import model.juego.RecordDAOImpl;
import model.juego.Record;

import model.menu.Music;
import view.menu.MenuView;
import view.menu.VentanaScores;

public class MenuController {

    private MenuView view;
    //private MenuModel model;

    private RecordDAO recordDAO; //se usa recorddao para los scores

    private boolean sonidoActivado = true;
    private Music music;
    GameController gc;

    public MenuController() {
        //this.model = new MenuModel();
        System.out.println("arrancó mc");
        this.view = new MenuView();
        this.actions();
        this.view.setVisible(true);

        //se inicializa el DAO 
        try {
            this.recordDAO = new RecordDAOImpl();
        } catch (Exception e) {
            System.err.println("Error al inicializar RecordDAO: " + e.getMessage());
            // El DAO se queda en null, pero la lógica de mostrarScoreboard lo maneja.
        }

        
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
            
            // 1. Pausar la música del menú
            if (music != null) {
                music.pausar();
            }      
    
            // 2. Crear el nuevo tema de victoria
            Music musicaScore = new Music("/assets/music/temascore.wav");
            musicaScore.reproducirLoop();
        
            // 3. OBTENER LOS DATOS DEL MODELO (DAO)
            List<Record> top10 = new ArrayList<>();
            if (this.recordDAO != null) {
            // Llama al método que creaste en tu DAO (RecordDAOImpl)
            top10 = recordDAO.getTop10Records(); 
            } else {
            System.err.println("ADVERTENCIA: RecordDAO es null. Mostrando tabla de scores vacía.");
            }
    
            // 4. CREAR Y MOSTRAR LA VENTANA DE SCORES, PASANDO LOS DATOS
            VentanaScores ventanaScores = new VentanaScores(view, top10); 
            ventanaScores.setVisible(true);

            // 5. Cuando se cierra la ventana, detener la música del Score y reanudar la del menú
            ventanaScores.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                musicaScore.pausar(); // Detenemos la música del Score
                if (sonidoActivado && music != null) {
                    music.reproducirLoop(); // Volvemos a la música del menú
                }
            }
            });
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
