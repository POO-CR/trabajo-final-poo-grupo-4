package controller.juego;
import javax.swing.Timer;

import controller.ControladorPrincipal;
import model.juego.laberinto.Nivel;
import model.juego.laberinto.NivelWorker;
import model.juego.objetos.AñadirVida;
import model.juego.objetos.Llave;
import model.juego.objetos.Rapidez;
import model.observadores.SoundEffect;
import view.FramePrincipal;
import view.juego.JuegoPanel;
import controller.ControladorPrincipal;  

public class TutorialController extends GameController{

    private int etapaTutorial = 0;
    private boolean quieto = false;
    private boolean tutorialcompleto =false;
    private SoundEffect visionLossSound;
    private FramePrincipal framePrincipal; 
    private ControladorPrincipal controladorPrincipal;  

    public TutorialController(ControladorPrincipal controladorPrincipal, FramePrincipal framePrincipal){
        super(controladorPrincipal, framePrincipal);
        this.framePrincipal = framePrincipal;
        this.controladorPrincipal = controladorPrincipal;
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
                setLuzActiva(false);
                mostrarMensaje("Para moverte utiliza las teclas WASD o las flechas", 3500);
                juegoPanel.settutorialActivo(true);
                etapaTutorial++;
                
                break;

            case 1:
                if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
                    etapaTutorial++;
                    juegoPanel.settutorialActivo(false);
                }
                break;
                
            case 2:
            mostrarMensaje("Ahora busca una llave y abre la puerta", 5000);                   
                    ejecutarConRetraso(7000,() -> {
                    etapaTutorial = 3;
                    });  
                    etapaTutorial = 5;                      
                break;
                case 3:
                    mostrarMensaje("Cuidado con los fantasmas, si te tocan te sacan una vida", 5000);
                    ejecutarConRetraso(5000, ()->{
                        mostrarMensaje("Por suerte en los mapas hay habilidades que te darán ventajas", 5000);
                    });
                    ejecutarConRetraso(9000, ()->{
                        mostrarMensaje("Tenés 2 tipos de habilidades", 3000);
                    });
                    ejecutarConRetraso(12050, ()->{
                        mostrarMensaje("Vida: Añade una vida hasta que un fantasma te la quite", 3500);
                    });   
                    ejecutarConRetraso(17500, ()->{
                        mostrarMensaje("Velocidad: Aumenta tu velocidad por 5 segundos", 3500);
                    });                    
                    ejecutarConRetraso(22500, ()->{                      
                    mostrarMensaje("Al repetir una habilidad temporal, su duración se reinicia", 3500);
                    });                  
                    ejecutarConRetraso(31500, ()->{
                        etapaTutorial = 4;
                    });     
                    etapaTutorial =5;
                break;
                case 4:      
                    mostrarMensaje("Para pasar de nivel busca la escalera y sube por ella", 4000);
                    ejecutarConRetraso(4500, ()->{mostrarMensaje("Buena suerte", 2500);}); 
                    etapaTutorial = 5;                                  
                break;

                case 5:
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
        if (tutorialcompleto){return;}
            tutorialcompleto = true;
        mostrarMensaje("Felicidades, completaste el tutorial",3000);
        visionLossSound.play(); // Reproduce el sonido de pérdida de visión
        ejecutarConRetraso(2500, ()-> {quieto = true; setLuzActiva(true);cambiarLuz(-279);});    
        ejecutarConRetraso(6500,() -> {
            cambiarLuz(0); 
            mostrarMensaje("Suerte que tienes tu linterna", 3500);                        
            quieto = false; 
        ejecutarConRetraso(4000, ()->{
            mostrarMensaje("Felicidades, ahora tenés una habilidad nueva", 4000);
            ejecutarConRetraso(4500, ()->{
            mostrarMensaje("Luz extra: Aumenta el rango de visión por 8 segundos", 5000);
            });
        }); 
        });
       
        ejecutarConRetraso(24000,()-> {
            pausarMusica();
            salirAlMenuPrincipal();                                                    
        });  
       
    }

    public void iniciarTutorial() { //Metodo para que se inicie el nivel del tutorial
        int nivelTutorial = -1;
        JuegoPanel jp = framePrincipal.getJuegoPanel();
        nivelWorker = new NivelWorker(nivelTutorial, this);
        if (nivelWorker != null) {
            // le pasamos el worker para que actualice la pantalla de carga
            System.out.println("poniendo panel de carga");
            framePrincipal.mostrarCarga();

            super.setupNivelWorker();

            System.out.println("ejecutando worker");
            super.inicioJuego = true;
            nivelWorker.execute();
            jp.setRadioLuz(jp.getRadioDefault() + 1000); 
            
        }
    }

    @Override
    public void setupJuego(){
        System.out.println("Cargando objetos del tutorial");
        Rapidez rapidez = new Rapidez(this);
        rapidez.setX(22 * getAnchoBloque()); //Lo coloca en la columna 22
        rapidez.setY(18 * getAltoBloque()); //Lo coloca en la fila 18
        setObjeto(rapidez);

        //Vida extra
        AñadirVida vida = new AñadirVida(this);
        vida.setX(19 * getAnchoBloque()); //Lo coloca en la columna 22
        vida.setY(18 * getAltoBloque()); //Lo coloca en la fila 18
        setObjeto(vida);

        //LLave
        Llave llave = new Llave(this);
        llave.setX(15 * getAnchoBloque()); //Lo coloca en la columna 15
        llave.setY(2 * getAltoBloque()); //Lo coloca en la fila 2
        setObjeto(llave);

    }

    @Override
    public void reiniciarJuego(){
        super.juegoPausado = false;
        detenerGameLoop();
        if (getStopwatch() != null){
            getStopwatch().detener();
            getStopwatch().reiniciar();
        }
        keyH.resetearTeclas();
        sonidoPasos.pararPasos();
        pausarMusica();
        this.etapaTutorial = 0;
        this.tutorialcompleto = false;
        this.quieto = false;

        iniciarTutorial();
    }
}