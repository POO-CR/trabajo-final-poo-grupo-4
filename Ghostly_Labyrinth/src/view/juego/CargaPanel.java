package view.juego;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CargaPanel extends JPanel {

    private JProgressBar progressBar;
    private JLabel statusLabel;
    private int anchoBarra;
    private int barX;
    private int progreso;
    private int spriteADibujar; //variable que usaremos para saber en que porcentaje estamos y saber que sprite dibujar
    private int altoYAnchoPersonaje = 60;
    private int progresoEnPixeles;
    private int personajeX;
    private int personajeY; 
    private BufferedImage[] spritesCaminar; 

    BufferedImage background;

    public CargaPanel() {
        super(new GridBagLayout());
        
        super.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize()); 

        try {
            background = ImageIO.read(getClass().getResource("/assets/img/carga/backgroundCarga.png"));
            spritesCaminar = new BufferedImage[2]; 
            spritesCaminar[0] = ImageIO.read(getClass().getResource("/assets/img/player/der_1.png")); 
            spritesCaminar[1] = ImageIO.read(getClass().getResource("/assets/img/player/der_2.png")); 
        }
        catch(IOException e) {System.out.println("Error al cargar imagen");}
        this.setDoubleBuffered(true);
        this.setBackground(Color.BLACK);  
        this.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);

        statusLabel = new JLabel("Comenzando carga, por favor espere...", SwingConstants.CENTER);
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        
        
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setIndeterminate(true); // Se inicia en modo animación
        progressBar.setPreferredSize(new Dimension((Toolkit.getDefaultToolkit().getScreenSize().width)/3, (Toolkit.getDefaultToolkit().getScreenSize().height)/20));
        // panelProgreso.add(progressBar);
        progressBar.setForeground(new Color(0, 74, 173));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;

        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.SOUTH;
        this.add(progressBar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.SOUTH;
        this.add(statusLabel, gbc);
    }

    public void setStatus(String status) {
        SwingUtilities.invokeLater(() -> {
            statusLabel.setText(status);
        });
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    @Override
    protected void paintComponent (Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        
        g2.drawImage(background,0,0,this.getWidth(), this.getHeight(), null);
        
        if (spritesCaminar != null) {

            anchoBarra = progressBar.getWidth();
            if(anchoBarra <= 0) {
                 g2.dispose();
                 return;
            }

            progreso = progressBar.getValue();
            spriteADibujar = progreso % 2;
            
            if(spritesCaminar[spriteADibujar] == null) {
                g2.dispose();
                return;
            }
            
            barX = (getWidth() - anchoBarra) / 2;
            progresoEnPixeles = (int) ((progreso / 100.0) * anchoBarra); 
            personajeX = barX + progresoEnPixeles - (altoYAnchoPersonaje /2 );
            personajeY = progressBar.getY() - altoYAnchoPersonaje;

           g2.drawImage(spritesCaminar[spriteADibujar], personajeX, personajeY, altoYAnchoPersonaje, altoYAnchoPersonaje, null);
        }
        
        g2.dispose(); 
    }

    
}
