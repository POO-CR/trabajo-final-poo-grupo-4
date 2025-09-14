package model;

import java.util.ArrayList;


// ESTE MODELO ES PARA CUANDO SE QUIERA USAR LA BASE DE DATOS Y HACER QUE SE PUEDAN AGREGAR DATOS Y BOTONES DINAMICAMENTE.
// Serviria para cuando queremos hacer una actualizacion de la botoneria y no queremos tocar el codigo, solo agregamos en la base
// de datos.

public class MenuModel {
    
    private ArrayList<String> elements = new ArrayList<String>();

    public MenuModel() {
        this.getMenu();
    }

    private void getMenu() {
        this.elements.add("Jugar");
        this.elements.add("Tutorial");
        this.elements.add("Opciones");
        this.elements.add("Salir");
    }

    public ArrayList<String> getElements() {
        return this.elements;
    }
 
}
