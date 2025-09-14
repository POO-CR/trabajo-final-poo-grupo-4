package view;
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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class MenuRegisterStart extends JDialog{

    private JButton Volver;
    private JButton Crear;
    private JTextField Usuario; 
    private JTextField Contraseña;
    private JTextField Email;
    private JLabel User;
    private JLabel Key;
    private JLabel Correo;
    private JLabel Resultado;
    
    private MenuBackground backgroundRegisterStart;


    public MenuRegisterStart(JFrame parent) {
        super(parent, "Login", true);
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); //para cerrar bien con la X de la ventana

        // se carga el fondo  
        backgroundRegisterStart = new MenuBackground("/assets/img/backgroundmenuusuario.png");
        backgroundRegisterStart.setLayout(new BorderLayout());
        setContentPane(backgroundRegisterStart);

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
            fila1.add(User = new JLabel("Usuario:")); //se agrega el jlabel user a el panel fila1
            fila1.add(Usuario = new JTextField("Ingrese Usuario",13)); //se agrega el jtextfield usuario a el panel fila1
            panelCentral.add(fila1); //se agrega el panel fila1 al panelcentral

            
            //Segunda fila 
            JPanel fila2 = new JPanel(new FlowLayout(FlowLayout.CENTER)); //el flowlayout se ubica en la zona centrañ
            fila2.setOpaque(false); // transparencia para ver fondo
            fila2.add(Key = new JLabel("Contraseña:")); //se agrega el jlabel key a el panel fila2
            fila2.add(Contraseña = new JTextField("Ingrese Contraseña",13)); //se agrega el jtextfield contraseña a el panel fila2
            panelCentral.add(fila2); //se agrega el panel fila2 al panelcentral

            
            //Tercera fila 
            JPanel fila3 = new JPanel(new FlowLayout(FlowLayout.CENTER)); //el flowlayout se ubica en la zona centrañ
            fila3.setOpaque(false); // transparencia para ver fondo
            fila3.add(Correo = new JLabel("Email:")); //se agrega el jlabel correo a el panel fila3
            fila3.add(Email = new JTextField("Ingrese Email",13)); //se agrega el jtextfield email a el panel fila3
            panelCentral.add(fila3); //se agrega el panel fila3 al panelcentral


            panelPrincipal.add(panelCentral, BorderLayout.CENTER); //se agrega el panelcentral que contiene los paneles fila1,2,3 al panel principal

            //------------SUR-------------//

            //se crea un panel inferior donde estaran los FlowLayout
            JPanel panelInferior = new JPanel(new FlowLayout()); //se crea el panel inferior donde estaran los botones 
            panelInferior.setOpaque(false); // transparencia para ver fondo
            Crear = new JButton("Crear Usuario"); //se instancia el jbutton crear
            Volver = new JButton("Volver"); //se instancia el jbutton volver

            // cargar imagenes
            //BOTON CREAR USUARIO
            ImageIcon originalCrearIcon = new ImageIcon(getClass().getResource("/assets/img/CreateUserButton.png"));
            ImageIcon originalHoverCrearIcon = new ImageIcon(getClass().getResource("/assets/img/CreateUserButtonHover.png"));

            Image newImg = originalCrearIcon.getImage().getScaledInstance(125, 40, Image.SCALE_SMOOTH);
            ImageIcon nuevoiconcreate = new ImageIcon(newImg);

            Image newHoverImg = originalHoverCrearIcon.getImage().getScaledInstance(125, 40, Image.SCALE_SMOOTH);
            ImageIcon nuevoHoverIconCrear = new ImageIcon(newHoverImg); 

            Crear = new JButton(nuevoiconcreate);
            Crear.setRolloverIcon(nuevoHoverIconCrear);
            Crear.setContentAreaFilled(false);
            Crear.setBorderPainted(false);

            //BOTON VOLVER

            ImageIcon originalVolverIcon = new ImageIcon(getClass().getResource("/assets/img/BackButton.png"));
            ImageIcon originalHoverVolverIcon = new ImageIcon(getClass().getResource("/assets/img/BackButtonHover.png"));

            Image newImgvolver = originalVolverIcon.getImage().getScaledInstance(100, 40, Image.SCALE_SMOOTH);
            ImageIcon nuevoiconvolver = new ImageIcon(newImgvolver);

            Image newHoverImgvolver = originalHoverVolverIcon.getImage().getScaledInstance(100, 40, Image.SCALE_SMOOTH);
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
            Font fuenteTerror2 = null;

            try {
                fuenteTerror = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("menu/src/assets/font/shloprg.otf"));
                fuenteTerror2 = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("menu/src/assets/font/shloprg.otf"));
            } catch (Exception e) {
                e.printStackTrace();
            }
    
            //derivar tamaño y estilo
            fuenteTerror = fuenteTerror.deriveFont(Font.PLAIN, 15f); 
            fuenteTerror2 = fuenteTerror.deriveFont(Font.PLAIN, 35f);


            //cambiar fuente a los Jlabel
            User.setFont(fuenteTerror2);
            Key.setFont(fuenteTerror2);
            Correo.setFont(fuenteTerror2);
            //cambiar color a los Jlabel
            User.setForeground(Color.RED);
            Key.setForeground(Color.RED);    
            Correo.setForeground(Color.RED);

            //cambiar fondo a textfields
            Usuario.setBackground(Color.BLACK);
            Usuario.setForeground(Color.BLUE);
            Usuario.setFont(fuenteTerror);

            Contraseña.setBackground(Color.BLACK);  
            Contraseña.setForeground(Color.BLUE);
            Contraseña.setFont(fuenteTerror);

            Email.setBackground(Color.BLACK);
            Email.setForeground(Color.BLUE);
            Email.setFont(fuenteTerror);

            // Placeholder: borra texto al hacer foco se define debajo 
            addPlaceholder(Usuario, "Ingrese Usuario");
            addPlaceholder(Contraseña, "Ingrese Contraseña");
            addPlaceholder(Email, "Ingrese Email"); 

            
            //se llama el metodo esta definido abajo para que es pero no lo use
            //addPasswordPlaceholder(Contraseña, "Ingrese Contraseña"); 
            


            //.trim devuelve la cadena sin espaciado al comienzo

        
        }

        //Placeholder
        private void addPlaceholder(JTextField campo, String placeholder) {
        campo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (campo.getText().equals(placeholder)) {
                    campo.setText("");
                }
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                if (campo.getText().isEmpty()) {
                    campo.setText(placeholder);
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
            Resultado.setForeground(ok ? Color.GREEN : Color.RED);
        }

        public JTextField getContraseña() {
            return Contraseña;
        }

        public String getEmail() {
            return Email.getText();
        }

        public JButton getCrearButton() {
            return Crear; 
        }
        
        public JButton getVolverButton() {
            return Volver; 
        } 
        
        /* ESTO ESTA COMENTADO PORQUE BUSQUE HACER QUE AL POSICIONARSE SOBRE EL JPASSWORD Y SE ESCRIBA EL TEXTO NO SEA VISUAL
        PERO NO ENCONTRE LA FORMA DE HACER QUE FUNCIONE PORQUE EL TEXTO MOSTRADO AL COMIENZO ESTA OCULTO TAMBIEN
        LO DEJE COMO UN JTEXTFIELD NOMAS
        private void addPasswordPlaceholder(JPasswordField campo, String placeholder) {
        // Guardamos el echoChar original (ej: '*')
        final char echoCharDefault = campo.getEchoChar();

        // Estado inicial: placeholder visible
        campo.setEchoChar((char) 0); // muestra texto plano
        campo.setText(placeholder);
        campo.setForeground(Color.BLUE);

        campo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (new String(campo.getPassword()).equals(placeholder)) {
                    campo.setText("");
                    campo.setEchoChar(echoCharDefault); // vuelve a ocultar
                    campo.setForeground(Color.BLUE);
                }
            }
               public void focusLost(java.awt.event.FocusEvent e) {
                if (campo.getPassword().length == 0) {
                    campo.setEchoChar((char) 0); // muestra texto plano
                    campo.setText(placeholder);
                    campo.setForeground(Color.BLUE);
               }
            }
        });
        } */
        //

}





