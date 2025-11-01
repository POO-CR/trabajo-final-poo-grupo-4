package view.juego;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class PanelFinJuego extends JPanel {
    public enum TipoFin{Victoria,Derrota} //Tipos de finalizacion del juego
    private Image fondoVictoria;
    private Image FondoDerrota;
    private Image fondoActual; //Se le asignara el fondo que corresponda

    //Victoria
    private JButton Crear;
    private JTextField Usuario;  
    private JLabel Resultado;

    //Derrota
    private JButton botonReiniciar;

    //Ambos
    private JButton Volver;

    private final int Ancho__Derrota = 260;
    private final int Alto_Derrota = 60;
    private final int Ancho_Victoria = 200;
    private final int Alto_Victoria = 60;

    public PanelFinJuego(){
        setOpaque(false);
        setLayout(null);
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        cargarRecursos();
        iniciarRecursos();
        SwingUtilities.invokeLater(()-> this.requestFocusInWindow());
    }

    public void cargarRecursos(){
        try{
            fondoVictoria = new ImageIcon(getClass().getResource("/assets/img/PantallasFinales/PantallaGanar.png")).getImage();
            FondoDerrota = new ImageIcon(getClass().getResource("/assets/img/PantallasFinales/PantallaPerder.png")).getImage();
        } catch(Exception e){
            e.printStackTrace();
            System.out.println("Error al cargar las imagenes de fin de juego.");
        }
    }

    private void iniciarRecursos(){ //Organiza y crea los componentes visuales

        Font fuenteTerror = null;
            try {
            fuenteTerror = Font.createFont(Font.TRUETYPE_FONT,
                getClass().getResourceAsStream("/assets/font/gaspi.otf"));
            fuenteTerror = fuenteTerror.deriveFont(Font.PLAIN, 25f);
            } catch (Exception e) {
                e.printStackTrace();
                fuenteTerror = new Font("Arial", Font.PLAIN, 15);
            }
        
        //Boton y jlabel victoria
        Resultado = new JLabel();
        Resultado.setHorizontalAlignment(SwingConstants.CENTER);
        Resultado.setFont(fuenteTerror.deriveFont(20f));
        Resultado.setBounds(540, 560, 450, 60); //Posicion y tamaño del JLabel
        add(Resultado);

        Usuario = new JTextField("Ingresa tu apodo");
        Usuario.setHorizontalAlignment(SwingConstants.CENTER);
        Usuario.setBorder(javax.swing.BorderFactory.createLineBorder(Color.WHITE, 2)); //Genera los bordes blancos
        Usuario.setBackground(Color.BLACK); //Fondo del campo de texto
        Usuario.setForeground(Color.WHITE);//Color del texto
        Usuario.setFont(fuenteTerror);
        Usuario.setBounds(565, 625 ,400, 60); 
        addPlaceholder(Usuario,"Ingresa tu apodo");
        add(Usuario);

        //Boton de guardar
            ImageIcon originalCrearIcon = new ImageIcon(getClass().getResource("/assets/img/SaveButton.png"));
            ImageIcon originalHoverCrearIcon = new ImageIcon(getClass().getResource("/assets/img/SaveButtonHover.png"));
            Image newImg = originalCrearIcon.getImage().getScaledInstance(200, 60, Image.SCALE_SMOOTH);
            ImageIcon nuevoiconcreate = new ImageIcon(newImg);
            Image newHoverImg = originalHoverCrearIcon.getImage().getScaledInstance(200, 60, Image.SCALE_SMOOTH);
            ImageIcon nuevoHoverIconCrear = new ImageIcon(newHoverImg); 
            
            Crear = new JButton(nuevoiconcreate);
            Crear.setRolloverIcon(nuevoHoverIconCrear);
            Crear.setContentAreaFilled(false);
            Crear.setBorderPainted(false);
            Crear.setBounds(558, 715, 200, 60);
            add(Crear);


        //boton reiniciar
        ImageIcon originalReiniciarIcon = new ImageIcon(getClass().getResource("/assets/img/ReiniciarPartida.png"));
        ImageIcon originalHoverReiniciarIcon = new ImageIcon(getClass().getResource("/assets/img/ReiniciarPartidaHover.png"));
        Image newImgreiniciar = originalReiniciarIcon.getImage().getScaledInstance(Ancho__Derrota, Alto_Derrota, Image.SCALE_SMOOTH);
        ImageIcon nuevoiconcreateReiniciar = new ImageIcon(newImgreiniciar);
        Image newHoverImgreiniciar = originalHoverReiniciarIcon.getImage().getScaledInstance(Ancho__Derrota, Alto_Derrota, Image.SCALE_SMOOTH);
        ImageIcon nuevoHoverIconReiniciar = new ImageIcon(newHoverImgreiniciar); 

        botonReiniciar = new JButton(nuevoiconcreateReiniciar);
        botonReiniciar.setRolloverIcon(nuevoHoverIconReiniciar);
        botonReiniciar.setContentAreaFilled(false);
        botonReiniciar.setBorderPainted(false);

        botonReiniciar.setBounds(640, 635, Ancho__Derrota, Alto_Derrota);
        this.add(botonReiniciar);     


        //Boton menu principal
            ImageIcon originalVolverIcon = new ImageIcon(getClass().getResource("/assets/img/BackButton_green.png"));
            ImageIcon originalHoverVolverIcon = new ImageIcon(getClass().getResource("/assets/img/BackButtonHover_green.png"));
            Image newImgvolver = originalVolverIcon.getImage().getScaledInstance(200, 60, Image.SCALE_SMOOTH);
            ImageIcon nuevoiconvolver = new ImageIcon(newImgvolver);
            Image newHoverImgvolver = originalHoverVolverIcon.getImage().getScaledInstance(200, 60, Image.SCALE_SMOOTH);
            ImageIcon nuevoHoverIconVolver = new ImageIcon(newHoverImgvolver); 

            Volver = new JButton(nuevoiconvolver);
            Volver.setRolloverIcon(nuevoHoverIconVolver);
            Volver.setContentAreaFilled(false);
            Volver.setBorderPainted(false);
            add(Volver); 

            //Se ocultan todos los botones y el JLabel 
            Resultado.setVisible(false);
            Usuario.setVisible(false);
            Crear.setVisible(false);
            Volver.setVisible(false);
            botonReiniciar.setVisible(false);
        }   

        public void mostrarFin(TipoFin tipo){ //Segun como haya terminado el juego, se mostrara la pantalla correspondiente
            if (tipo == TipoFin.Victoria){
                fondoActual = fondoVictoria;
                Resultado.setVisible(true);
                Usuario.setVisible(true);
                Crear.setVisible(true);

                configurarBoton(Volver, "/assets/img/BackButton_green.png", "/assets/img/BackButtonHover_green.png", Ancho_Victoria, Alto_Victoria); 
                Volver.setBounds(772,715, Ancho_Victoria, Alto_Victoria);
                Volver.setVisible(true);
                Usuario.setText("Ingresa tu apodo");
                Resultado.setText("");
                botonReiniciar.setVisible(false);
            } else {
                fondoActual = FondoDerrota;
                Resultado.setVisible(false);
                Usuario.setVisible(false);
                Crear.setVisible(false);
                configurarBoton(Volver, "/assets/img/VolverAlMenu.png", "/assets/img/VolverAlMenuHover.png", Ancho__Derrota, Alto_Derrota);
                Volver.setBounds(640,735,Ancho__Derrota,Alto_Derrota);
                Volver.setVisible(true);
                botonReiniciar.setVisible(true);
            }
            SwingUtilities.invokeLater(()-> this.requestFocusInWindow());
            repaint();
        }

        private void configurarBoton(JButton boton, String rutaIcono, String rutaHoverIcono, int ancho, int alto){ //Como se comparte el boton volver, se pasara los parametros necesarios para configurarlo
            try{
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(rutaIcono));
            ImageIcon originalHoverIcon = new ImageIcon(getClass().getResource(rutaHoverIcono));
            Image img = originalIcon.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            Image imgHover = originalHoverIcon.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);

            boton.setIcon(new ImageIcon(img));
            boton.setRolloverIcon(new ImageIcon(imgHover));
            boton.setContentAreaFilled(false);
            boton.setBorderPainted(false);
            boton.setFocusPainted(false);
           } catch(Exception e){
            e.printStackTrace();
           }
        }

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
 }

    @Override
    protected void paintComponent(Graphics g) { //Dibuja el fondo que corresponda
        super.paintComponent(g);
        if (fondoActual != null) {
            g.drawImage(fondoActual, 0, 0, getWidth(), getHeight(), this);
        }
    }

    //Getters para los botones y el campo de texto
    public JButton getCrearButton(){
        return Crear;
    }       
    public JButton getVolverButton(){
        return Volver;
    }
    public JButton getBotonReiniciar(){
        return botonReiniciar;
    }
    public String getUsuario(){
        return Usuario.getText();
    }

    public void setResultado(String msg) {
        Resultado.setText(msg);
        Resultado.setForeground(Color.RED);
    }
    

}

    

