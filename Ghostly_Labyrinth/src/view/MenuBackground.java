package view;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class MenuBackground extends JPanel{

    private static final long serialVersionUID = 1L; // Para serializar

    private Image background;

    public MenuBackground(String url) { try { this.background = new ImageIcon(getClass().getResource(url)).getImage(); } catch (Exception e) { System.err.println("No se pudo cargar la imagen de fondo: " + url); } }

    @Override 
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.background != null) {
            g.drawImage(this.background, 0, 0, getWidth(), getHeight(), this);
        }
    }

}
    
