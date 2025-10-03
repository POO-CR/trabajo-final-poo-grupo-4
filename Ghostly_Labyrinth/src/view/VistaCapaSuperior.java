package view;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class VistaCapaSuperior {
    private Image imagenVida;
    private Font fuenteJuego;
    
    public VistaCapaSuperior() {
        cargar();
    }

    
    //Carga la fuente
    public void cargar(){
    try{
        fuenteJuego = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/assets/font/gaspi.otf"));
        fuenteJuego = fuenteJuego.deriveFont(Font.PLAIN,28f);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //  try {
        //     // carga desde recursos dentro del proyecto /assets/font/shloprg.otf
        //     fuenteTerror = Font.createFont(Font.TRUETYPE_FONT,
        //                     getClass().getResourceAsStream("/assets/font/shloprg.otf"));
        //     fuenteTerror2 = fuenteTerror.deriveFont(Font.PLAIN, 35f);
        //     fuenteTerror = fuenteTerror.deriveFont(Font.PLAIN, 15f);
        //     } catch (Exception e) {
        //         e.printStackTrace();
        //         // derivar tamaño y estilo 
        //         fuenteTerror = new Font("Arial", Font.PLAIN, 15);
        //         fuenteTerror2 = new Font("Arial", Font.PLAIN, 35);
        //     }
    
    try{
        //Carga la foto de vida
        imagenVida = ImageIO.read(getClass().getResourceAsStream("/assets/img/vida.png"));
        } catch (Exception e){
        e.printStackTrace();
        }
    }

    private void dibujarcronometro(Graphics2D g, Stopwatch stopwatch, int panelAncho){       
     String textoTiempo = "Time: " + jv.stopwatch.formatoTiempo(jv.stopwatch.getSegundos()); //texto que se mostrara en pantalla
     FontMetrics metrics = g.getFontMetrics(jv.fuenteJuego); //FontMetrics da informacion sobre la fuente y g.getFontMetrics le pide al objeto graphics que devuelva las metricas(ancho,alto,espaciado) de la fuente fuenteJuego 
    int anchoTexto = metrics.stringWidth(textoTiempo); //calcula el ancho de pixeles de la cadena textoTiempo, anchoTexto va a contener un entero con la cantidad de pixeles que ocupa en x textoTiempo

    int x = getWidth() - anchoTexto - 20; //se calcula y coloca la posicion x en el paneljuego (getWidth() da el ancho total del panel juego y se le resta el ancho de pixeles del texto y el -20 es un margen de separacion desde el borde derecho de la ventana para que no quede pegado al borde)
    int y = 40; //se coloca en la posicion y en el paneljuego

    g.setColor(Color.WHITE); //se setea el color del texto en blanco
    g.drawString(textoTiempo, x, y); //se dibuja el string textoTiempo en las posiciones x e y calculadas 
    }
    
}