package entidades;
public class Item {
    private int fila, columna;
    String tipo;

    public Item(int fila, int columna, String tipo){
        this.fila = fila;
        this.columna = columna;
        this.tipo = tipo;
    }

    public int getFila(){
        return fila;
    }

    public int getColumna(){
        return columna;
    }

    public String getTipo(){
        return tipo;
    }

}
