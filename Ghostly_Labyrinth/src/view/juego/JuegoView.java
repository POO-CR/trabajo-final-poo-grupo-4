package view.juego;

import java.awt.BorderLayout;
import javax.swing.*;

import controller.juego.GameController;
import model.juego.KeyHandler;

import model.juego.laberinto.Nivel; //TODO: preg esto

//Clase Juego debe heredar los atributos y metodos de JPanel
public class JuegoView extends JFrame {

    // private final JuegoPanel jp;
    // private final CargaPanel cargaPanel;



    // public JuegoView (GameController controlador) {


    //     jp = new JuegoPanel();
    //     cargaPanel = new CargaPanel();

    //     setLayout(new BorderLayout());
    //     setUndecorated(true);


    //     // add(jp);
    //     // javax.swing.SwingUtilities.invokeLater(() -> {
    //     //     jp.requestFocusInWindow();
    //     // });
    //     // setContentPane(jp);

    // }

    // public void pintarPanel () {
    //     jp.repaint();
    // }

    // public void setNivel(Nivel nivel){
    //     this.jp.setNivel(nivel);
    // }

    // public void añadirKeyListener (KeyHandler kh){
    //     jp.addKeyListener(kh);
    // }

    // public void setearLuzInicial(){
    //     jp.setRadioLuz(jp.getRadioDefault());
    // }

    // public JuegoPanel getJuegoPanel() {
    // return this.jp;
    // }

    // public CargaPanel getPanelCarga() {
    //     return cargaPanel;
    // }
    
    // public void ponerPanelJuego(){
    //     setContenedor(jp);
    // }

    // public void ponerPanelCarga(){
    //     setContenedor(cargaPanel);
    // }

    // public void setContenedor(JPanel panel){
    //     getContentPane().removeAll();
    //     getContentPane().add(panel, BorderLayout.CENTER);
    //     revalidate();
    //     repaint();
    //     javax.swing.SwingUtilities.invokeLater(() -> {
    //         panel.requestFocusInWindow();
    //     });

    //     revalidate();
    //     repaint();
    // }

    

    // public void setRadioLuz(int radio){
    //     this.jp.setRadioLuz(radio);
    // }


    // public int getRadioDefault(){
    //     return this.jp.getRadioDefault();
    // }

}





