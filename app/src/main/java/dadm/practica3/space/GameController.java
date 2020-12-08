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
    private static final int TIME_BETWEEN_ENEMIES = 500;
    private long currentMillis;
    private List<Asteroid> asteroidPool = new ArrayList<Asteroid>();
    private int enemiesSpawned;

    public GameController(GameEngine gameEngine) {
        cont=this;
        // We initialize the pool of items now
        for (int i=0; i<10; i++) {
            asteroidPool.add(new Asteroid(this, gameEngine));
        }
    }

    @Override
    public void startGame() {
        currentMillis = 0;
        enemiesSpawned = 0;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        currentMillis += elapsedMillis;

        long waveTimestamp = enemiesSpawned*TIME_BETWEEN_ENEMIES;
        if (currentMillis > waveTimestamp) {
            // Spawn a new enemy
            Asteroid a = asteroidPool.remove(0);
            a.init(gameEngine);
            gameEngine.addGameObject(a);
            enemiesSpawned++;
            return;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        // This game object does not draw anything
    }

    public void returnToPool(Asteroid asteroid) {
        asteroidPool.add(asteroid);
    }


    public void FinJuego(GameEngine gameEngine){
        gameEngine.stopGame();
        ((ScaffoldActivity)gameEngine.getMainActivity()).startPuntuaciones();
    }

    public static GameController get_GameController()
    {
        return cont;
    }
}
