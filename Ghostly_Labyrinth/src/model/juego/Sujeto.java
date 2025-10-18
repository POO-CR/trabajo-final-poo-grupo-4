package model.juego;

public interface Sujeto {

    void registrarObservador(Observador o);
    void removerObservador(Observador o);
    void notificarObservadores(String evento);
    
}
