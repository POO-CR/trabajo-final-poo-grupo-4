package view.juego;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import javax.swing.*;

import controller.juego.GameController;
import model.juego.KeyHandler;

import model.juego.laberinto.Nivel; //TODO: preg esto

//Clase Juego debe heredar los atributos y metodos de JPanel
public class JuegoView extends JFrame {

    private final JuegoPanel jp;
    private final CargaPanel cargaPanel;

    private JPanel contenedor;




    public JuegoView (GameController controlador) {

        setTitle("Ghostly Labyrinth");

        setUndecorated(true); // quita la barra de titulo y bordes
        setExtendedState(JFrame.MAXIMIZED_BOTH); // ocupa toda la pantalla

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        jp = new JuegoPanel(controlador);
        cargaPanel = new CargaPanel();

        contenedor = new JPanel();
        contenedor.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        add(contenedor);
        setContentPane(contenedor);


        // add(jp);
        // javax.swing.SwingUtilities.invokeLater(() -> {
        //     jp.requestFocusInWindow();
        // });
        // setContentPane(jp);

    }

    public void pintarPanel () {
        jp.repaint();
    }

    public void setNivel(Nivel nivel){
        this.jp.setNivel(nivel);
    }

    public void añadirKeyListener (KeyHandler kh){
        jp.addKeyListener(kh);
    }

    public JuegoPanel getJuegoPanel() {
    return this.jp;
    }

    public CargaPanel getPanelCarga() {
        return cargaPanel;
    }
    
    public void ponerPanelJuego(){
        setContenedor(jp);
    }

    public void ponerPanelCarga(){
        setContenedor(cargaPanel);
    }

    public void setContenedor(JPanel panel){
        contenedor.removeAll();
        contenedor.add(panel, BorderLayout.CENTER);
        contenedor.revalidate();
        contenedor.repaint();
        javax.swing.SwingUtilities.invokeLater(() -> {
            panel.requestFocusInWindow();
        });

        revalidate();
        repaint();
    }


    public void setRadioLuz(int radio){
        this.jp.setRadioLuz(radio);
    }

    public int getRadioDefault(){
        return this.jp.getRadioDefault();
    }

}





