package view.juego;

import java.awt.BorderLayout;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

public class CargaPanel extends JPanel {

    private JProgressBar progressBar;
    private JLabel statusLabel;

    public CargaPanel() {
        super(new GridBagLayout());
        super.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize()); 
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

    
}
