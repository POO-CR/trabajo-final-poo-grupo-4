package controlador;

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import model.MenuLoginModel;
import model.Music;
import view.MenuRegisterStart;
import view.MenuUser;
import view.MenuView;

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
        this.gc = new GameController();
        this.music = new Music("/assets/music/FireboyAndWatergirlSoundtrack.wav");
        this.music.reproducirLoop(); // Se inicializa la musica como encendida

    }

    private void actions() {

        // boton Jugar
        this.view.getBttnJugar().addActionListener((ActionEvent e) -> {
            // Abre el menú de usuario
            MenuUser seleccionarUsuario = new MenuUser(view);

            // accion para abrir la ventana de registro al apretar nuevo usuario
            seleccionarUsuario.getBttnNuevoUsuario().addActionListener((ActionEvent e1) -> {
                MenuRegisterStart ventanaRegistro = new MenuRegisterStart(view); // la vista
                MenuLoginModel modeloRegistro = new MenuLoginModel();           // el modelo
                MenuLoginController controladorRegistro = new MenuLoginController(modeloRegistro, ventanaRegistro, usuario -> {
                    // Se inicia el juego con el usuario recien creado
                    gc.iniciarJuego(usuario);
                }); // el controlador
                ventanaRegistro.setVisible(true);
            });
            
            // para la opcion de cargar usuario
            seleccionarUsuario.getBttnCargarUsuario().addActionListener(ev -> {
                String nombre = JOptionPane.showInputDialog(seleccionarUsuario, "Ingrese nombre del usuario a cargar:");
                if (nombre != null && !nombre.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(seleccionarUsuario, "Usuario cargado: " + nombre);
                    seleccionarUsuario.dispose();
                    // aca tambien se puede iniciar el juego
                }
            });
            
            seleccionarUsuario.setVisible(true);
        });

       
        // boton Tutorial
        this.view.getBttnTutorial().addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(view,"Mostrar tutorial");        
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
