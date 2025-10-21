package controller.juego;
import javax.swing.Timer;

import model.juego.laberinto.Nivel;
import model.juego.laberinto.NivelWorker;
import model.observadores.SoundEffect;
import view.juego.JuegoView;

public class TutorialController extends GameController{
    private int etapaTutorial = 0;
    private boolean quieto = false;
    private boolean tutorialcompleto =false;
    private SoundEffect visionLossSound;

    public TutorialController(){
        super();
        this.visionLossSound = new SoundEffect("/assets/music/Vision_perdida.wav");
    }
     private void ejecutarConRetraso(int delayMillis, Runnable accion) { //Metodo para generar Delay en el tutorial
        Timer timer = new Timer(delayMillis, e -> accion.run());
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    public void update(){
        switch (etapaTutorial) {
            case 0:
                mostrarMensaje("Para moverte utiliza las teclas WASD", 3000);
                etapaTutorial++;
                break;

            case 1:
                if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
                    etapaTutorial++;
                }
                break;
                
            case 2:
            mostrarMensaje("Ahora busca la llave y ve a la puerta", 2000);
                    visionLossSound.play(); // Reproduce el sonido de pérdida de visión
                    ejecutarConRetraso(2000,() -> {
                    quieto = true;     //Hacemos que el jugador no se pueda mover                             
                    cambiarLuz(-279);     
                    });
                    ejecutarConRetraso(3500,() -> {
                        cambiarLuz(0); 
                        mostrarMensaje("Suerte que tienes tu linterna", 2000);                        
                            quieto = false; 
                            etapaTutorial = 3;
                    });                   
                    etapaTutorial = 4;              
                break;
                case 3:      
                    ejecutarConRetraso(3000, () -> {;        
                    mostrarMensaje("A lo largo del mapa encontraras distintas habilidades", 2000);
                    etapaTutorial++;
                    });
                break;

                case 4:
                break;           
        }
        super.update();
    }

    @Override
    public void procesarMovimiento(){  //Metodo para que el jugador no se mueva si es que lo requerimos
        if (!quieto){
            super.procesarMovimiento();
        } else{
            jugador.setDireccion("quieto");
        }
    }

    @Override
    public void cambiarNivel(){ //Metodo para que en vez de salir de nivel se vuelva al menu
        if (!tutorialcompleto){
            tutorialcompleto = true;
        mostrarMensaje("Felicidades, completaste el tutorial",3000);
        ejecutarConRetraso(2500,()-> {
            salirAlMenuPrincipal();                                                    
        });      
        };
    }

    public void iniciarTutorial() { //Metodo para que se inicie el nivel del tutorial
        int nivelTutorial = -1;
        this.juegoView = new JuegoView(this);
        java.util.function.Consumer<String> statusUpdater = juegoView.getPanelCarga()::setStatus;
        nivelWorker = new NivelWorker(nivelTutorial, statusUpdater);
        if (nivelWorker != null) {
            // le pasamos el worker para que actualice la pantalla de carga
            System.out.println("poniendo panel de carga");
            juegoView.ponerPanelCarga();
            juegoView.setVisible(true);

            super.setupNivelWorker();

            System.out.println("ejecutando worker");
            super.inicioJuego = true;
            nivelWorker.execute();
            if (this.juegoView != null) {
                this.juegoView.setRadioLuz(juegoView.getRadioDefault() + 1000); 
            }
        }
    }
}