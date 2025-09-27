package juego;

import entidades.laberinto.Labyrinth;
import entidades.objetos.*;
import java.util.List;
import java.awt.Point;

import java.util.ArrayList;
import java.util.Random;

public class AssetSetter {
    
    PanelJuego pj;
    Labyrinth lab;

    public AssetSetter (PanelJuego pj) {
        this.pj = pj;
        this.lab = pj.laberinto;
    }

    public void setearObjetosIniciales () {
        Point p;
        pj.obj[0] = new Llave(this.pj);
        try {
            p = encontrarCoordsObjeto(pj.obj[0]);
            pj.obj[0].setX(p.x * lab.anchoBloque);
            pj.obj[0].setY(p.y * lab.altoBloque);}
        catch (Exception e){
            e.printStackTrace();
        }
        

        //pj.obj[1] = new Llave();
        //try {
        //this.encontrarCoordsObjeto(pj.obj[0]);}
        //catch (Exception e){
        //    e.printStackTrace();
        //}
    }

    private Point encontrarCoordsObjeto (Objeto obj) throws Exception {

        List<Point> opciones = new ArrayList<>();
        Labyrinth lab = pj.laberinto;
        int cols = lab.getCols();
        int rows = lab.getRows();
        Random rnd = new Random();
        for (int i=0; i<rows ; i++) {
            for (int j=0; j<cols; j++){
                if (lab.valorTile(i, j) == obj.getTilePermitida()) {
                    opciones.add(new Point(j, i));
                }
            }
        }
        if (opciones.isEmpty()) {
            throw new Exception("no hay celdas permitidas");
        }

        Point p = opciones.get(rnd.nextInt(opciones.size()));
        System.out.println("point de llave encontrado en x: " + p.x + " y: " + p.y);
        return p;
    }


}
