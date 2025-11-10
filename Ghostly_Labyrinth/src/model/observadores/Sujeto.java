package model.observadores;

public interface Sujeto {

    void registrarObservador(Observador o);
    void removerObservador(Observador o);
    void notificarObservadores(String evento);
    
}
