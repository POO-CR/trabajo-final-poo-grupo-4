package model.juego;

import javax.swing.Timer;


public class Stopwatch {
    
    //contador de segundos transcurridos 
    private int segundos;

    //timer  de swing que ejecuta una accion cada intervalo de tiempo
    private Timer timer; 

    //constructor del cronometro 
    //onTick Runnable que se ejecuta cada segundo (usualmente para actualizar el panel del juego)
    public Stopwatch(Runnable onTick){
        segundos = 0; // se inicializa en 0 
        
        // se crea un timer de swing que se ejecuta cada 1000ms (1 segundo)
        timer = new Timer(1000, e -> {
            segundos ++; //se incrementa contador de segundos 
            if (onTick != null) {
                onTick.run(); //ejecutar accion pasada, por ejemplo redibujar el panel           
            }
        });
    }

    //metodo para mostrar el formato de tiempo transcurrido
    //requiere como parametro de entrada los segundos transcurridos
    public String formatoTiempo(int totalSegundos) {

    // calcular cuantas horas completas hay en totalSegundos
    // (3600 segundos = 1 hora) la division entera no toma en cuenta decimales
    int horas = totalSegundos / 3600; 
    
    // totalSegundos % 3600 devuelve el resto de segundos que sobran
    // despues de extraer todas las horas completas
    // el resto que se obtiene se lo convierte a minutos dividiendolo entre 60.
    int minutos = (totalSegundos % 3600) / 60;
    
    // totalSegundos % 60 devuelve el resto de segundos que sobran
    // despues de extraer todos los minutos se completa (0..59).
    int segundos = totalSegundos % 60;

    StringBuilder sb = new StringBuilder(); //StringBuilder se usa para armar el texto final 

    //si hay mas de una hora se agrega
    if (horas > 0) {
        sb.append(horas).append("h ");
    }

    // si hay minutos o hay horas (queremos mostrar "0m" cuando hay horas)
    // se agregan los minutos por ejemplo "20m"
    if (minutos > 0 || horas > 0) { // si hay horas, mostramos minutos aunque sean 0
        sb.append(minutos).append("m ");
    }

    //siempre se agregan los segundos
    sb.append(segundos).append("s");  

    return sb.toString(); //retorna el texto final con el tiempo 
    }


    //metodo para iniciar el cronometro 
    public void iniciar() {
        timer.start();
    }
    
    //metodo para detener el cronometro 
    public void detener() {
        timer.stop();
    }

    //metodo para reiniciar en 0 el cronometro 
    public void reiniciar() {
        segundos = 0;
    }

    //metodo que retorna los segundos transcurridos 
    public int getSegundos() {
        return segundos;
    }

}
