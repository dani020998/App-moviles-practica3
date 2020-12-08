package dadm.practica3.space;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

import dadm.practica3.engine.GameEngine;
import dadm.practica3.engine.GameObject;

public class GameController extends GameObject {

    private static final int TIME_BETWEEN_ENEMIES = 500;
    private long currentMillis;
    //private List<Asteroid> asteroidPool = new ArrayList<Asteroid>();
    private List<Bird> birdYellowPool = new ArrayList<Bird>();
    private List<Bird> birdGreenPool = new ArrayList<Bird>();
    private int enemiesSpawned;

    public GameController(GameEngine gameEngine) {
        // We initialize the pool of items now
        /*for (int i=0; i<10; i++) {
            asteroidPool.add(new Asteroid(this, gameEngine));
        }*/
        for(int i=0; i<10;i++){
            birdYellowPool.add(new Bird(this, gameEngine, "yellow"));
        }
        for(int i=0; i<10;i++){
            birdGreenPool.add(new Bird(this, gameEngine, "green"));
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
            /*Asteroid a = asteroidPool.remove(0);
            a.init(gameEngine);
            gameEngine.addGameObject(a);
            enemiesSpawned++;
            return;*/
            Bird b;
            if(gameEngine.random.nextDouble()<0.5){
                b = birdYellowPool.remove(0);
            }else{
                b = birdGreenPool.remove(0);
            }
            b.init(gameEngine);
            gameEngine.addGameObject(b);
            enemiesSpawned++;
            return;
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
}