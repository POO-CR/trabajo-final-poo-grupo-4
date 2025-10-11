package model.juego;

import controller.juego.GameController;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import model.juego.objetos.*;

public class AssetSetter {
    
    GameController gc;
    private ArrayList<Point> posicionesUsadas = new ArrayList<>();

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

            Random rnd = new Random();

            // para q genere entre 5 y 15 powerups 
            int cantPowerUps = rnd.nextInt(5, 15);
            System.out.println("generando " + cantPowerUps + " powerups");
            for (int i=0; i<=cantPowerUps; i++) {
                //para q genere un powerup al azar
                int tipoObjeto = rnd.nextInt(3);

                System.out.println("generando objeto tipo: " + tipoObjeto);

                Objeto nuevoObjeto = switch (tipoObjeto) {
                    case 0 -> new AñadirVida(gc);
                    case 1 -> new AmpliarVision(gc);
                    case 2 -> new Rapidez(gc);
                    default -> new Rapidez(gc);
                };

                p = encontrarCoordsObjeto(nuevoObjeto);
                if (p == null) break; // sale del loop si no hay más posiciones disponibles
                nuevoObjeto.setX(p.x * gc.getAnchoBloque());
                nuevoObjeto.setY(p.y * gc.getAltoBloque());
                gc.setObjeto(nuevoObjeto);
            }


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

        //para que no se pongan dos objetos en la misma tile 
        Point p = opciones.get(rnd.nextInt(opciones.size()));
        while (posicionesUsadas.contains(p) && posicionesUsadas.size() < opciones.size()) {
            p = opciones.get(rnd.nextInt(opciones.size()));
        }

        System.out.println("eligio point x: " + p.x + " y: " + p.y);

        //para que no se pongan más objetos que lugares disponibles, si hay lugar devuelve el point, si no null
        //TENER EN CUENTA QUE CUENTA A LA LLAVE
        if (posicionesUsadas.size() <= opciones.size()) {
            posicionesUsadas.add(p);
            System.out.println("point de objeto encontrado en x: " + p.x + " y: " + p.y);
            return p;
        }
        else{System.out.println("devolviendo null porque " + posicionesUsadas.size() + " es igual a " + opciones.size()); return null;}
        

    }

    public void resetearPosicionesUsadas () {
        posicionesUsadas.clear();
    }


}
