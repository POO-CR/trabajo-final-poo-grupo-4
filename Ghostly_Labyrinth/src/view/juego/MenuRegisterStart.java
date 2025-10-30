package view.juego;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.io.FileInputStream;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import view.menu.MenuBackground;

public class MenuRegisterStart extends JDialog{

    private JButton Volver;
    private JButton Crear;
    private JTextField Usuario; 
    
    private JLabel Resultado;
    
    private MenuBackground backgroundRegisterStart;

    private controller.juego.GameController gameController;

    public MenuRegisterStart(JFrame parent, controller.juego.GameController gameController) {
        super(parent, "Login", true);
        setUndecorated(true); //borra la barra blanca de la ventana que decia login
        setBackground(new java.awt.Color(0, 0, 0, 0)); //transparencia
        setSize(550, 450);
        setLocation(490, 480);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); //para cerrar bien con la X de la ventana

        // se carga el fondo  
        // backgroundRegisterStart = new MenuBackground("/assets/img/backgroundPausa.png");
        // backgroundRegisterStart.setLayout(new BorderLayout());
        // setContentPane(backgroundRegisterStart);

        //se agrega la lamina de texto 
        LaminaTexto milamina = new LaminaTexto(); //instancia de la lamina  

        add(milamina); 
  
        //esto permite que el foco inicial este en el propio contenedor, no en ningun JTextField, 
        //dejando que se muestre el texto ingrese usuario 
        SwingUtilities.invokeLater(() -> this.getContentPane().requestFocusInWindow());
        

    }

    class LaminaTexto extends JPanel{

        public LaminaTexto(){

            setOpaque(false); // deja ver el fondo sino el panel tapa el fondo 

            //panel principal con BorderLayout

            JPanel panelPrincipal = new JPanel(new BorderLayout(10,10));
            panelPrincipal.setOpaque(false); // transparencia para ver fondo 

            //para que el panel principal se centre en la ventana
            setLayout(new java.awt.GridBagLayout()); // el layout del contenedor LaminaTexto
            java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            add(panelPrincipal, gbc);

            //------------NORTE-------------//
            //JLabel en la posicion north

            Resultado = new JLabel(); //se instancia el jlabel superior
            Resultado.setHorizontalAlignment(SwingConstants.CENTER); //se centra el texto
            panelPrincipal.add(Resultado, BorderLayout.NORTH); //se agrega el Jlabel resultado al panel principal en la parte superior


            //------------CENTRO-------------//
            //Parte central del panel principal
            //se crea un panel central donde estaran los FlowLayout
            JPanel panelCentral = new JPanel();
            panelCentral.setOpaque(false); // transparencia para ver fondo
            panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS)); //para que esten en columnas 

            //Primera fila 
            JPanel fila1 = new JPanel(new FlowLayout(FlowLayout.CENTER)); //el flowlayout se ubica en la zona centrañ
            fila1.setOpaque(false); // transparencia para ver fondo
            fila1.add(Usuario = new JTextField("Ingresa tu apodo")); //se agrega el jtextfield usuario a el panel fila1
            Usuario.setHorizontalAlignment(SwingConstants.CENTER); //centra texto del jfield 
            Usuario.setPreferredSize(new java.awt.Dimension(270, 50));
            Usuario.setBorder(javax.swing.BorderFactory.createLineBorder(Color.WHITE, 2));
            panelCentral.add(fila1); //se agrega el panel fila1 al panelcentral


            panelPrincipal.add(panelCentral, BorderLayout.CENTER); //se agrega el panelcentral que contiene los paneles fila1,2,3 al panel principal

            //------------SUR-------------//

            //se crea un panel inferior donde estaran los FlowLayout
            JPanel panelInferior = new JPanel(new FlowLayout()); //se crea el panel inferior donde estaran los botones 
            panelInferior.setOpaque(false); // transparencia para ver fondo
            panelInferior.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 0, 0,0));
            Crear = new JButton("Crear Usuario"); //se instancia el jbutton crear
            Volver = new JButton("Volver"); //se instancia el jbutton volver

            // cargar imagenes
            //BOTON CREAR USUARIO
            ImageIcon originalCrearIcon = new ImageIcon(getClass().getResource("/assets/img/SaveButton.png"));
            ImageIcon originalHoverCrearIcon = new ImageIcon(getClass().getResource("/assets/img/SaveButtonHover.png"));

            Image newImg = originalCrearIcon.getImage().getScaledInstance(150, 40, Image.SCALE_SMOOTH);
            ImageIcon nuevoiconcreate = new ImageIcon(newImg);

            Image newHoverImg = originalHoverCrearIcon.getImage().getScaledInstance(150, 40, Image.SCALE_SMOOTH);
            ImageIcon nuevoHoverIconCrear = new ImageIcon(newHoverImg); 

            Crear = new JButton(nuevoiconcreate);
            Crear.setRolloverIcon(nuevoHoverIconCrear);
            Crear.setContentAreaFilled(false);
            Crear.setBorderPainted(false);

            //BOTON VOLVER

            ImageIcon originalVolverIcon = new ImageIcon(getClass().getResource("/assets/img/BackButton_green.png"));
            ImageIcon originalHoverVolverIcon = new ImageIcon(getClass().getResource("/assets/img/BackButtonHover_green.png"));

            Image newImgvolver = originalVolverIcon.getImage().getScaledInstance(150, 40, Image.SCALE_SMOOTH);
            ImageIcon nuevoiconvolver = new ImageIcon(newImgvolver);

            Image newHoverImgvolver = originalHoverVolverIcon.getImage().getScaledInstance(150, 40, Image.SCALE_SMOOTH);
            ImageIcon nuevoHoverIconVolver = new ImageIcon(newHoverImgvolver); 

            Volver = new JButton(nuevoiconvolver);
            Volver.setRolloverIcon(nuevoHoverIconVolver);
            Volver.setContentAreaFilled(false);
            Volver.setBorderPainted(false);

            panelInferior.add(Crear); //se agrega al panel inferior el boton crear
            panelInferior.add(Volver); //se agrega al panel inferior el boton volver

            panelPrincipal.add(panelInferior, BorderLayout.SOUTH); //se agrega al panelprincipal el panel inferior 


            //-----------------------------------//

            //cargar fuente desde archivo
            Font fuenteTerror = null;


            try {
            // carga desde recursos dentro del proyecto /assets/font/gaspi.otf
            fuenteTerror = Font.createFont(Font.TRUETYPE_FONT,
                            getClass().getResourceAsStream("/assets/font/gaspi.otf"));
            fuenteTerror = fuenteTerror.deriveFont(Font.PLAIN, 25f);
            } catch (Exception e) {
                e.printStackTrace();
                // derivar tamaño y estilo 
                fuenteTerror = new Font("Arial", Font.PLAIN, 15);
            }

            //cambiar fondo a textfields
            Usuario.setBackground(Color.BLACK);
            Usuario.setForeground(Color.WHITE);
            Usuario.setFont(fuenteTerror);

            // Placeholder: borra texto al hacer foco se define debajo 
            addPlaceholder(Usuario, "Ingresa tu apodo");
            
            //.trim devuelve la cadena sin espaciado al comienzo

            Volver.addActionListener(e -> {
             // Cierra el diálogo actual
            SwingUtilities.getWindowAncestor(Volver).dispose();
        });

        }

        //Placeholder
        private void addPlaceholder(JTextField campo, String placeholder) {
            campo.setText(placeholder);
            campo.setForeground(Color.WHITE);

            campo.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
            if (campo.getText().equals(placeholder)) {
                campo.setText("");
                campo.setForeground(Color.WHITE); // color del texto real
            }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
            if (campo.getText().isEmpty()) {
                campo.setText(placeholder);
                campo.setForeground(Color.WHITE);
            }
            }
        });

    }}
        
        // getter para poder acceder al texto desde afuera y los botones
        public String getUsuario() {
            return Usuario.getText();
        }

        public void setResultado(String msg, boolean ok) {
            Resultado.setText(msg);
            Resultado.setForeground(ok ? Color.WHITE : Color.RED);
        }

        public JButton getCrearButton() {
            return Crear; 
        }
        
        public JButton getVolverButton() {
            return Volver;

        }

}





