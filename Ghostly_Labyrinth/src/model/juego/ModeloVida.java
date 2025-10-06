package model.juego;
 
 public class ModeloVida {
    //Caracteristicas al recibir danio
    private long tiempoInmune;
    private long finInmune=0;

    public ModeloVida(long duracionInmunidad) {
        this.tiempoInmune = duracionInmunidad;
        this.finInmune =0;
    }
    public boolean estaInmune(){
        return System.currentTimeMillis() < finInmune;
    }

    public void inmune(){
        this.finInmune = System.currentTimeMillis() + tiempoInmune;
    }
        public int aplicarDanio(int vidaActual, int cantidadDanio) {
        if (!estaInmune() && vidaActual > 0) {
            // Activa la inmunidad 
            inmune();
            
            // Devuelve la vida restante.
            return vidaActual - cantidadDanio;
        }
        return vidaActual;
    }

        public boolean estaVivo(int vidaActual){
        return vidaActual > 0;
    }


}