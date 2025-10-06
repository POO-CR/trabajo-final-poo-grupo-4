package model.menu;

import java.util.ArrayList;


// ESTE MODELO ES PARA CUANDO SE QUIERA USAR LA BASE DE DATOS Y HACER QUE SE PUEDAN AGREGAR DATOS Y BOTONES DINAMICAMENTE.
// Serviria para cuando queremos hacer una actualizacion de la botoneria y no queremos tocar el codigo, solo agregamos en la base
// de datos.

public class MenuUserModel {
    
    private ArrayList<String> elements = new ArrayList<String>();

    public MenuUserModel() {
        this.getMenu();
    }

    private void getMenu() {
        this.elements.add("Nuevo usuario");
        this.elements.add("Cargar Usuario");
        this.elements.add("Volver");
    }

    public ArrayList<String> getElements() {
        return this.elements;
    }
 
}