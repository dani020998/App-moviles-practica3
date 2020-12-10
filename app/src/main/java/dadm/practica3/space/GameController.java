package dadm.practica3.space;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

import dadm.practica3.ScaffoldActivity;
import dadm.practica3.counter.PuntuacionFragment;
import dadm.practica3.counter.GameFragment;
import dadm.practica3.engine.GameEngine;
import dadm.practica3.engine.GameObject;

public class GameController extends GameObject {

    private static GameController cont;
    //private static GameFragment frag;
    public GameFragment frag;
    private int puntuacion=0;
    private static final int TIME_BETWEEN_ENEMIES_MIN = 200;
    private static final int TIME_BETWEEN_ENEMIES_MAX = 700;
    private long currentMillis, waveTimeGreen, waveTimeYellow;
    //private List<Asteroid> asteroidPool = new ArrayList<Asteroid>();
    private List<Bird> birdYellowPool = new ArrayList<Bird>();
    private List<Bird> birdGreenPool = new ArrayList<Bird>();
    private int puntuacionVictoria=2000;

    public GameController(GameEngine gameEngine, GameFragment GameFrag) {
        cont=this;
        frag=GameFrag;
        waveTimeGreen=gameEngine.random.nextInt(TIME_BETWEEN_ENEMIES_MAX-TIME_BETWEEN_ENEMIES_MIN) + TIME_BETWEEN_ENEMIES_MIN;
        waveTimeYellow=gameEngine.random.nextInt(TIME_BETWEEN_ENEMIES_MAX-TIME_BETWEEN_ENEMIES_MIN) + TIME_BETWEEN_ENEMIES_MIN;
        for(int i=0; i<30;i++){
            birdYellowPool.add(new Bird(this, gameEngine, "yellow"));
        }
        for(int i=0; i<30;i++){
            birdGreenPool.add(new Bird(this, gameEngine, "green"));
        }
    }

    @Override
    public void startGame() {
        currentMillis = 0;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        currentMillis += elapsedMillis;

        if (currentMillis > waveTimeGreen) {
            Bird b;
            b = birdGreenPool.remove(0);
            b.init(gameEngine);
            gameEngine.addGameObject(b);
            waveTimeGreen = currentMillis +
                    gameEngine.random.nextInt(TIME_BETWEEN_ENEMIES_MAX-TIME_BETWEEN_ENEMIES_MIN) + TIME_BETWEEN_ENEMIES_MIN;
        }
        if (currentMillis > waveTimeYellow) {
            Bird b;
            b = birdYellowPool.remove(0);
            b.init(gameEngine);
            gameEngine.addGameObject(b);
            waveTimeYellow = currentMillis +
                    gameEngine.random.nextInt(TIME_BETWEEN_ENEMIES_MAX-TIME_BETWEEN_ENEMIES_MIN) + TIME_BETWEEN_ENEMIES_MIN;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        // This game object does not draw anything
    }

    public void returnToPool(Bird bird) {
        if(bird.getColor().equals("green")){
            birdGreenPool.add(bird);
        }else{
            birdYellowPool.add(bird);
        }
    }

    public void anadir_puntuacion(GameEngine gameEngine){
        puntuacion+=50;
        if (puntuacion>=puntuacionVictoria){
            FinJuego(gameEngine);
        }
    }

    public void FinJuego(GameEngine gameEngine){
        gameEngine.stopGame();
        ((ScaffoldActivity)gameEngine.getMainActivity()).startPuntuaciones();
    }

    public static GameController get_GameController()
    {
        return cont;
    }
    //public static GameFragment get_GameFragment(){return frag;}

    public int getPuntuacion(){
        return puntuacion;
    }
    public int getPuntuacionVictoria(){return puntuacionVictoria;}
}
