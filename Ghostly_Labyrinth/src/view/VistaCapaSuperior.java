package view;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import juego.Stopwatch;

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
if (stopwatch == null) return;

        g.setFont(fuenteJuego);
        String textoTiempo = "Time: " + stopwatch.formatoTiempo(stopwatch.getSegundos());

        FontMetrics metrics = g.getFontMetrics(fuenteJuego);
        int anchoTexto = metrics.stringWidth(textoTiempo);

        int x = panelAncho - anchoTexto - 20; // Alineado a la derecha
        int y = 40;

        g.setColor(Color.WHITE);
        g.drawString(textoTiempo, x, y);
    }
    
}