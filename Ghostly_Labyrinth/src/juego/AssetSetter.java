package juego;

import entidades.laberinto.Labyrinth;
import entidades.objetos.*;
import java.util.List;
import java.awt.Point;

import controlador.GameController;
import java.util.ArrayList;
import java.util.Random;

public class AssetSetter {
    
    GameController gc;
    Labyrinth lab;

    public AssetSetter (GameController gc, Labyrinth lab) {
        this.gc = gc;
        this.lab = lab;
    }

    public void setearObjetosIniciales () {
        Point p;
        try {
            Llave l = new Llave(lab);
            p = encontrarCoordsObjeto(l);
            l.setX(p.x * lab.anchoBloque);
            l.setY(p.y * lab.altoBloque);
            gc.setObjeto(0, l); //TODO: ojo con esto q está medio hardcoded
        }
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
