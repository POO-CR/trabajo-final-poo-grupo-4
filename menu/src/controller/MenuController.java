package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import javax.swing.JButton;
import javax.swing.JOptionPane;

//import model.MenuModel;
import model.Music;
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
                JOptionPane.showMessageDialog(view, "Juego iniciado");
            }
        });

        // boton Tutorial
        this.view.getBttnTutorial().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(view,"Tutorial iniciado");
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
