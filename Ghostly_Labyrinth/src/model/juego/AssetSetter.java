package model.juego;

import controller.juego.GameController;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import model.juego.objetos.*;

public class AssetSetter {
    
    GameController gc;

    public AssetSetter (GameController gc) {
        this.gc = gc;
    }

    public void setearObjetosIniciales () {
        Point p;
        try {
            Llave l = new Llave(gc);
            p = encontrarCoordsObjeto(l);
            l.setX(p.x * gc.getAnchoBloque());
            l.setY(p.y * gc.getAltoBloque());
            gc.setObjeto(l); 

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
        int cols = gc.getAnchoNivel();
        int rows = gc.getAltoNivel();
        Random rnd = new Random();
        for (int i=0; i<rows ; i++) {
            for (int j=0; j<cols; j++){
                if (gc.getNivel().getTile(i, j).getNum() == obj.getTilePermitida()) {
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
