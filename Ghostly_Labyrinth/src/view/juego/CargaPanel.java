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

    BufferedImage background;

    public CargaPanel() {
        super(new GridBagLayout());
        
        super.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize()); 

        try {
            background = ImageIO.read(getClass().getResource("/assets/img/carga/backgroundCarga.png"));
        }
        catch(IOException e) {}
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


    // ESTE SET STATUS ES EL QUE INVOCA EL CONSUMER STRING
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
        
    }

    
}
