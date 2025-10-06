package model.juego;



import model.juego.entidades.Player;
import model.juego.objetos.Objeto;

import java.util.ArrayList;

public class Message {
    // Atributos para el mensaje
    private String texto;
    private boolean visible = false;
    private long tiempoInicio;
    private long duracionMillis;
    private ArrayList<Objeto> obj;
    private Player jugador;

    public Message (ArrayList<Objeto> obj) {
        this.obj = obj;
    }

    //Metodos para el mensaje
    public void mostrar(String texto, int milisegundos) {
        this.texto = texto;
        this.duracionMillis = milisegundos;
        this.tiempoInicio = System.currentTimeMillis();
        this.visible = true; 
    }


    public void updateMsj() {
        if (visible && System.currentTimeMillis() > tiempoInicio + duracionMillis) {
            visible = false;
        }
    }

    public String getTextoMsj() {
        return texto;
    }

    public Boolean getIsVisibleMsj() {
        return visible;
    }

    public void InteraccionDeMensajes (Objeto objeto){
        mostrar("Obtuviste " + objeto.getNombre(),2000);
    } 
}