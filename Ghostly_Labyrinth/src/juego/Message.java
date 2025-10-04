package juego;



import entidades.objetos.Objeto;
import entidades.Player;
import juego.JuegoView;

public class Message {
    // Atributos para el mensaje
    private String texto;
    private boolean visible = false;
    private long tiempoInicio;
    private long duracionMillis;
    private Objeto obj[];
    private Player jugador;

    public Message (Objeto[] obj) {
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

    public void InteraccionDeMensajes (int i){
        if (i != 999) {
            String nombreObj = obj[i].nombre;
            switch (nombreObj) {
                case "Llave" -> {
                    jugador.setTieneLlave(true);
                    System.out.println("AGARRO LLAVE EN POSICION" + obj[i].getX() + " ; " + obj[i].getY());
                    obj[i] = null;
                    mostrar("Obtuviste la llave",2000);
                }
            } 
        }
    } 
}