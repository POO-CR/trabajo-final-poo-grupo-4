import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



//Clase Juego debe heredar los atributos y metodos de JPanel
public class Juego extends JPanel{

    //objetos para poder ser dibujados 
    Labyrinth laberinto = new Labyrinth();
    Player jugador = new Player();


    //constructor de la clase juego 
    public Juego(){
        addKeyListener(new KeyListener(){
           @Override
           public void keyPressed(KeyEvent e) {
              jugador.teclaPresionada(e);
           }

           @Override
           public void keyReleased(KeyEvent e) {
           }
            
           @Override
           public void keyTyped(KeyEvent e) {
           }

        });
        setFocusable(true);
    }



    //metodo para poder dibujar en el JPanel, tanto el jugador laberinto fantasmas 
    //el override hace referencia a que es un metodo heredado de JPanel 
    @Override
    public void paint(Graphics grafico){
        laberinto.paintComponent(grafico);
        jugador.paint(grafico);

    }


    public static void main(String[] args) {
        
        //JFrame para mostrar graficos de la aplicacion
        JFrame ventana = new JFrame("Ghostly Labyrinth");

        //Objeto de la clase para poderla implementar
        Juego game = new Juego();
        
        //Se agrega a JFrame el objeto de la clase juego 
        ventana.add(game);

        //Propiedades de la ventana 

        //Para establecer el tamaño del objeto ventana
        ventana.setSize(700,500);
        
        //Donde se va a posicionar en mi pantalla la ventana
        ventana.setLocation(200,100);

        //Para que la ventana se muestre en la ventana
        ventana.setVisible(true);
        
        //Bucle 
         while (true) {
            //hilo para mostrar el retraso en milisegundos 
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
            game.repaint();
            
        }
    }
}
