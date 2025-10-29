package view.menu;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;
import model.dao.Record;

public class ScorePanel extends JPanel {

    private JTable tablaScores;
    private DefaultTableModel modelo;
    private JButton btnVolver;
    private Font fuenteTerror;
    
    // Nueva propiedad para el fondo
    private Image backgroundImage; 

    public ScorePanel() {
        // Establecer el Layout principal como BorderLayout
        setLayout(new BorderLayout()); 
        setOpaque(false);

        // Cargar fuente y la imagen de fondo una sola vez
        try {
            fuenteTerror = Font.createFont(Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/assets/font/gaspi.otf"));
            fuenteTerror = fuenteTerror.deriveFont(Font.PLAIN, 25f);
        } catch (Exception e) {
            e.printStackTrace();
            fuenteTerror = new Font("Arial", Font.PLAIN, 15);
        }
        
        // Cargar imagen de fondo para el método paintComponent
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/assets/img/fondo_miedo.png"));
            backgroundImage = icon.getImage();
        } catch (Exception e) {
             System.err.println("Error al cargar la imagen de fondo: " + e.getMessage());
        }


        // --- 1. Contenedor Central para la Tabla y el Título ---
        // Usamos un JPanel con GridBagLayout para centrar el contenido
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setOpaque(false); // Es crucial que sea transparente
        
        // El GridBagConstraints se usara para posicionar los elementos
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL; // Por defecto: horizontal

        // Titulo
        JLabel lblTitulo = new JLabel("LOS 10 MEJORES SOBREVIVIENTES", SwingConstants.CENTER);
        lblTitulo.setFont(fuenteTerror.deriveFont(Font.BOLD, 30f)); 
        lblTitulo.setForeground(new Color(255, 100, 100)); 
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0; // No toma espacio vertical extra
        contentPanel.add(lblTitulo, gbc);


        // --- 2. Configuración de la Tabla (JTable) ---
        String[] columnas = {"#", "Jugador", "Tiempo", "Fecha"};
        modelo = new DefaultTableModel(columnas, 0);
        tablaScores = new JTable(modelo);
        
        // Estilos de la tabla (mantener los tuyos, ajustando colores)
        tablaScores.setFont(fuenteTerror.deriveFont(22f));
        tablaScores.setForeground(Color.WHITE);
        // Usamos un color negro semi-transparente para que el fondo se vea
        tablaScores.setBackground(new Color(0, 0, 0, 150)); 
        tablaScores.setRowHeight(35);
        tablaScores.setShowGrid(false);
        tablaScores.setEnabled(false);

        JTableHeader header = tablaScores.getTableHeader();
        header.setBackground(Color.DARK_GRAY);
        header.setForeground(Color.WHITE);
        header.setFont(fuenteTerror.deriveFont(Font.BOLD, 22f));
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        
        // Establecer renderers (mantienes el codigo existente para centrado y medallas)
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        // Renderers para celdas
        for (int i = 1; i < tablaScores.getColumnCount(); i++) {
            tablaScores.getColumnModel().getColumn(i).setCellRenderer(center);
        }
        
        // Renderer de Medallas
        tablaScores.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            Font fuenteMedalla = new Font("SansSerif", Font.BOLD, 22);
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setBackground(new Color(0, 0, 0, 150)); // Usar el mismo fondo semi-transparente

                switch (row) {
                    case 0 -> { label.setForeground(new Color(255, 215, 0)); label.setFont(fuenteMedalla.deriveFont(30f)); }
                    case 1 -> { label.setForeground(new Color(192, 192, 192)); label.setFont(fuenteMedalla.deriveFont(28f)); }
                    case 2 -> { label.setForeground(new Color(205, 127, 50)); label.setFont(fuenteMedalla.deriveFont(26f)); }
                    default -> { label.setForeground(Color.WHITE); label.setFont(fuenteMedalla.deriveFont(22f)); }
                }
                return label;
            }
        });


        //3. JScrollPane y adicion al panel de contenido
        JScrollPane scroll = new JScrollPane(tablaScores);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        //Ajustar el tamaño preferido para que se vea bien centrado
        scroll.setPreferredSize(new Dimension(800, 400)); 
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 

        gbc.gridy = 1;
        gbc.weighty = 1; // IMPORTANTE: Esto hace que la tabla ocupe el espacio vertical extra disponible
        gbc.fill = GridBagConstraints.BOTH; // La tabla se estira en ambas direcciones
        contentPanel.add(scroll, gbc);


        //--- 4. Boton Volver ---
        btnVolver = new JButton("VOLVER");
        btnVolver.setFont(fuenteTerror.deriveFont(26f));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setBackground(new Color(100, 0, 0));
        btnVolver.setFocusPainted(false);
        btnVolver.setBorderPainted(false);
        // Crear un contenedor para el boton para darle un poco de margen visual
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(btnVolver);
        
        
        //--- 5. Agregar los componentes al ScorePanel (principal) ---
        add(contentPanel, BorderLayout.CENTER); // El contenido centrado va al centro
        add(buttonPanel, BorderLayout.SOUTH); // El botón va abajo
    }
    
    
    //Dibuja la imagen de fondo.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // Dibuja la imagen para que cubra todo el panel
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
    

    public JButton getBtnVolver() {
        return btnVolver;
    }

    public void mostrarScores(List<Record> records) {
        modelo.setRowCount(0);
        int pos = 1;
        for (Record record : records) {
            String medalla = switch (pos) {
                case 1 -> "🥇";
                case 2 -> "🥈";
                case 3 -> "🥉";
                default -> pos + "°";
            };
            modelo.addRow(new Object[]{medalla, record.getNombre(), record.getTiempoFormato(), record.getFechaFormato()});
            pos++;
        }
        
        // Esto es util para forzar la actualización de la tabla despues de cargar los datos
        // aunque el controlador ya llama a validate/repaint del mainPanel.
        modelo.fireTableDataChanged();
    }
}