package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
//import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import juego.IniciarJuego;
//import model.MenuModel;
import model.Music;
import view.MenuUser;
import view.MenuView;

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

            MenuUser seleccionarUsuario = new MenuUser(view);

            // para la opcion de nuevo usuario
            seleccionarUsuario.getBttnNuevoUsuario().addActionListener(ev -> {

            JTextField txtUsuario = new JTextField();
            JPasswordField txtPassword = new JPasswordField();

            // limitar contraseña a 10 caracteres
            txtPassword.setDocument(new javax.swing.text.PlainDocument() {
                @Override
                public void insertString(int offs, String str, javax.swing.text.AttributeSet a) 
                        throws javax.swing.text.BadLocationException {
                    if (str == null) return;
                    if ((getLength() + str.length()) <= 10) {
                        super.insertString(offs, str, a);
                    }
                }
            });

            // TODO: pasar esto a una vista
            // Panel con usuario + contraseña
            JPanel panel = new JPanel(new java.awt.GridLayout(2, 2, 5, 5));
            panel.add(new JLabel("Usuario:"));
            panel.add(txtUsuario);
            panel.add(new JLabel("Contraseña:"));
            panel.add(txtPassword);

            int result = JOptionPane.showConfirmDialog(
                    seleccionarUsuario,
                    panel,
                    "Crear nuevo usuario",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                String nombre = txtUsuario.getText().trim();
                String contrasena = new String(txtPassword.getPassword());

                if (!nombre.isEmpty() && !contrasena.isEmpty()) {
                    JOptionPane.showMessageDialog(seleccionarUsuario, 
                        "Usuario creado: " + nombre);
                    
                    seleccionarUsuario.dispose();

                    //se abre el juego
                    IniciarJuego.iniciarJuego(nombre);
                }
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
