package view.menu;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader; // Import necesario para la cabecera
import java.awt.*;
import java.sql.*;
import java.util.List;
import model.juego.Record;

public class VentanaScores extends JDialog {

    private JTable tablaScores;
    private DefaultTableModel modelo;
    private JButton btnVolver;
    private JLabel fondo;
    private Font fuenteTerror; 

    public VentanaScores(Frame parent, List<Record> records) {
        super(parent, "👻 Top 10 Malditos 👻", true);
        setUndecorated(true); 
        setSize(600, 500);
        setLocationRelativeTo(parent);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- Fondo tenebroso ---
        fondo = new JLabel();
        fondo.setIcon(new ImageIcon(getClass().getResource("/assets/img/fondo_miedo.png"))); 
        fondo.setLayout(new BorderLayout());
        add(fondo);

        // Carga de la fuente TERROR
        try {
            fuenteTerror = Font.createFont(Font.TRUETYPE_FONT,
                                getClass().getResourceAsStream("/assets/font/gaspi.otf"));
            fuenteTerror = fuenteTerror.deriveFont(Font.PLAIN, 25f);
        } catch (Exception e) {
            e.printStackTrace();
            fuenteTerror = new Font("Arial", Font.PLAIN, 15);
        }

        // --- Panel superior con título ---
        JLabel lblTitulo = new JLabel("LOS 10 MEJORES SOBREVIVIENTES", SwingConstants.CENTER);
        lblTitulo.setFont(fuenteTerror); 
        lblTitulo.setForeground(Color.GREEN);
        lblTitulo.setOpaque(false);
        fondo.add(lblTitulo, BorderLayout.NORTH);

        // --- Tabla de scores ---
        String[] columnas = {"#", "Jugador", "Tiempo", "Fecha"};
        modelo = new DefaultTableModel(columnas, 0);
        tablaScores = new JTable(modelo);
        tablaScores.setFont(fuenteTerror.deriveFont(22f)); 
        tablaScores.setForeground(Color.WHITE);
        tablaScores.setBackground(new Color(20, 20, 20, 200));
        tablaScores.setRowHeight(35);
        tablaScores.setShowGrid(false);
        tablaScores.setEnabled(false);

        // =========================================================================
        // --- 1. ESTILO DE LA CABECERA (JTableHeader) ---
        // =========================================================================
        JTableHeader header = tablaScores.getTableHeader();
        header.setBackground(Color.BLACK); // Fondo Negro
        header.setForeground(Color.GREEN); // Texto azul
        header.setFont(fuenteTerror.deriveFont(Font.BOLD, 22f)); // Fuente de Terror, Negrita
        header.setResizingAllowed(false); 
        header.setReorderingAllowed(false); 
        
        // Centrar el texto en la cabecera
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);


        // 2. Configuración de Anchos 
        tablaScores.getColumnModel().getColumn(0).setPreferredWidth(50); 
        tablaScores.getColumnModel().getColumn(1).setPreferredWidth(160); 
        tablaScores.getColumnModel().getColumn(2).setPreferredWidth(140); 
        tablaScores.getColumnModel().getColumn(3).setPreferredWidth(150); 
        
        // 3. RENDERER CON FUENTE GENÉRICA PARA MEDALLAS (COLUMNA 0)
        Font fuenteMedalla = new Font("SansSerif", Font.BOLD, 22);

        tablaScores.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setHorizontalAlignment(SwingConstants.CENTER); 
                label.setBackground(new Color(20, 20, 20, 200)); 

                switch (row) {
                    case 0: 
                        label.setForeground(new Color(255, 215, 0)); 
                        label.setFont(fuenteMedalla.deriveFont(Font.BOLD, 30f)); 
                        break;
                    case 1: 
                        label.setForeground(new Color(192, 192, 192)); 
                        label.setFont(fuenteMedalla.deriveFont(Font.BOLD, 28f));
                        break;
                    case 2: 
                        label.setForeground(new Color(205, 127, 50)); 
                        label.setFont(fuenteMedalla.deriveFont(Font.BOLD, 26f));
                        break;
                    default: 
                        label.setForeground(Color.WHITE); 
                        label.setFont(fuenteMedalla.deriveFont(Font.PLAIN, 22f)); 
                        break;
                }
                return label;
            }
        });
        
        // 4. RENDERER DE CENTRADO PARA EL RESTO DE COLUMNAS (1, 2, 3)
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        
        for (int i = 1; i < tablaScores.getColumnCount(); i++) {
            tablaScores.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        // --- Scroll con tabla ---
        JScrollPane scroll = new JScrollPane(tablaScores);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        fondo.add(scroll, BorderLayout.CENTER);

        // --- Botón volver ---
        btnVolver = new JButton("Volver");
        btnVolver.setFont(fuenteTerror);
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setBackground(new Color(100, 0, 0));
        btnVolver.setFocusPainted(false);
        btnVolver.setBorderPainted(false);

        // efecto hover
        btnVolver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnVolver.setBackground(new Color(180, 0, 0));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnVolver.setBackground(new Color(100, 0, 0));
            }
        });

        cargarScoresDesdeLista(records);

        btnVolver.addActionListener(e -> dispose());
        fondo.add(btnVolver, BorderLayout.SOUTH);
    }
    
    // Método auxiliar (sin cambios)
    private void cargarScoresDesdeLista(List<Record> records) {
        modelo.setRowCount(0);

        int pos = 1;
        for (Record record : records) { 
            
            String jugador = record.getNombre();
            String tiempoFormateado = record.getTiempoFormato(); 
            String fecha = record.getFechaFormato(); 

            String medalla = switch (pos) {
                case 1 -> "🥇";
                case 2 -> "🥈";
                case 3 -> "🥉";
                default -> pos + "°";
            };

            modelo.addRow(new Object[]{medalla, jugador, tiempoFormateado, fecha});
            pos++;
        }
    }
}