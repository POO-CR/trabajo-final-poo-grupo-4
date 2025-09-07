import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.awt.Font;


public class Labyrinth {
    
    //variable para mostrar el mensaje de la puerta abierta
    private boolean mostrarMensaje = false;

    //variable para el estado de la puerta
    private boolean puertaAbierta = false;

    //lista de items en el laberinto 
    private ArrayList<Item> items;

    //variable imagen
    private Image baldosaImg;
    private Image muroImg;
    private Image puertaImg;
    private Image llaveImg;

    public Labyrinth(){
        baldosaImg = new ImageIcon("C:/Users/Lucas/Documents/POO/Ghostly_Labyrinth/img/baldosa.jpg").getImage();
        muroImg = new ImageIcon("C:/Users/Lucas/Documents/POO/Ghostly_Labyrinth/img/muro.jpg").getImage();
        puertaImg = new ImageIcon("C:/Users/Lucas/Documents/POO/Ghostly_Labyrinth/img/puerta.jpg").getImage();
        llaveImg = new ImageIcon("C:/Users/Lucas/Documents/POO/Ghostly_Labyrinth/img/llave.jpg").getImage();

        //incializa la lista de items
        items = new ArrayList<>();
        //generar la llave al inicio
        //generarLlaveRandom();

        items.add(new Item(1, 28, "llave"));
    }

    // metodo para devolver la lista de ítems
    public ArrayList<Item> getItems() {
        return items;
    }

    // metodo para la puerta abierta
    public void abrirPuerta() {
    puertaAbierta = true;
    mostrarMensaje = true;


    //para mostrar el mensaje solo 2 seg
    new Thread(() -> {
        try {
            Thread.sleep(2000); // mostrar 2 segundos
        } catch (InterruptedException e) {}
        mostrarMensaje = false;
    }).start();

    }
    
    public boolean isPuertaAbierta() {
        return puertaAbierta;
    }
    
    //metodo para generar la llave aleatoreamente
    //public void generarLlaveRandom() {
    //Random random = new Random();
    //int[][] laberinto = obtenerLaberinto();
    //int f, c;

    //do {
    //    f = random.nextInt(cantidadFila);
    //    c = random.nextInt(cantidadColumnas);
    //} while (laberinto[f][c] != 0); // buscamos baldosa libre

    //Item llave = new Item(f, c, "llave");
    //items.add(llave);
    //}




    //variables para matriz 
    //para contadores
    private int fila = 0;
    private int columna = 0;
    //para cantidad de fil y col
    private final int cantidadFila = 20;
    private final int cantidadColumnas = 30;
    //para ancho y alto de cada bloque 
    private final int anchoBloque = 20;
    private final int altoBloque = 20;




    //Para poder utilizar graficos
    public void paintComponent(Graphics grafico){
        int [][]laberinto = obtenerLaberinto();


        //filas
        for(fila = 0; fila<cantidadFila; fila++){
            
            //columnas
            for(columna = 0; columna<cantidadColumnas; columna++){
               
            boolean dibujarItem = false;
            
            // buscar si hay un item en esta celda
            for(Item item : items){
                if(item.fila == fila && item.columna == columna){
                    if(item.tipo.equals("llave")){
                        grafico.drawImage(llaveImg, columna * anchoBloque, fila * altoBloque, anchoBloque, altoBloque, null);
                        dibujarItem = true;
                        break;
                    }
                }
            }





                if(!dibujarItem){

                //variable para la iteracion 
                int valor = laberinto[fila][columna];
                
                //iteracion case  
                switch (valor) {

                    case 0: // baldosa
                        grafico.drawImage(baldosaImg, columna * anchoBloque, fila * altoBloque, anchoBloque, altoBloque, null);
                        grafico.setColor(Color.black);
                        grafico.drawRect(columna * anchoBloque, fila * altoBloque, anchoBloque, altoBloque);
                        break;
                    
                    case 1: // muro
                        grafico.drawImage(muroImg, columna * anchoBloque, fila * altoBloque, anchoBloque, altoBloque, null);
                        grafico.setColor(Color.black);
                        grafico.drawRect(columna * anchoBloque, fila * altoBloque, anchoBloque, altoBloque);
                        break;
                    
                    case 2: // otro tipo de casilla (ejemplo: puerta)
                        //grafico.drawImage(puertaImg, columna * anchoBloque, fila * altoBloque, anchoBloque, altoBloque, null);
                        //grafico.setColor(Color.black);
                        //grafico.drawRect(columna * anchoBloque, fila * altoBloque, anchoBloque, altoBloque);
                        if(puertaAbierta){
                            grafico.drawImage(baldosaImg, columna*anchoBloque, fila*altoBloque, anchoBloque, altoBloque, null); // dibuja baldosa abierta
                        } else {
                            grafico.drawImage(puertaImg, columna*anchoBloque, fila*altoBloque, anchoBloque, altoBloque, null); // puerta cerrada
                        }
                        break;

                    default: 
                        break;
                    }
                } 
            }
        }

        //dibujar el mensaje de la puerta abierta
        if (mostrarMensaje) {
        grafico.setColor(Color.YELLOW);
        grafico.setFont(new Font("Arial", Font.BOLD, 20));
        grafico.drawString("PUERTA ABIERTA", 200, 30); // coordenadas en la ventana
    }



    }


    //matriz del laberinto
    public int[][] obtenerLaberinto(){
        int laberinto[][] = 
        { { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
          { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
          { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
          { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
          { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
          { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
          { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
          { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
          { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
          { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
          { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
          { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
          { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
          { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
          { 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
          { 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
          { 1, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
          { 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
          { 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
          { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },

        };
        return laberinto;

    }


}
