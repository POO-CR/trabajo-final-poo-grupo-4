package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
//import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import main.App;
import model.MenuLoginModel;
//import model.MenuModel;
import model.Music;
import view.MenuUser;
import view.MenuView;
import view.MenuRegisterStart;

public class MenuController {

    private MenuView view;
    //private MenuModel model;

    private boolean sonidoActivado = true;
    private Music music;

    public MenuController() {
        //this.model = new MenuModel();
        this.view = new MenuView();
        this.actions();
        this.view.setVisible(true);

        this.music = new Music("/assets/music/FireboyAndWatergirlSoundtrack.wav");
        this.music.reproducirLoop(); // Se inicializa la musica como encendida

    }

    private void actions() {

        // boton Jugar
        this.view.getBttnJugar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            // Abre el menú de usuario
            MenuUser seleccionarUsuario = new MenuUser(view);

            // accion para abrir la ventana de registro al apretar nuevo usuario
            seleccionarUsuario.getBttnNuevoUsuario().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                MenuRegisterStart ventanaRegistro = new MenuRegisterStart(view); // la vista
                MenuLoginModel modeloRegistro = new MenuLoginModel();           // el modelo
                MenuLoginController controladorRegistro = new MenuLoginController(modeloRegistro, ventanaRegistro, usuario -> {
                    // Se inicia el juego con el usuario recien creado
                    App.iniciarJuego(usuario);
                }); // el controlador

                ventanaRegistro.setVisible(true);

                


            }
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
        }
        });

       
        // boton Tutorial
        this.view.getBttnTutorial().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(view,"Mostrar tutorial");
        }        
        });
            
        
    

        // boton Opciones
        this.view.getBttnOpciones().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(view,"Mostrar el menu de opciones");
        }        
        });
    

        // boton Salir
        this.view.getBttnSalir().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

        // boton Sonido off/on
        this.view.getBttnSonido().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

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
    
		    }
		});



    }
}
