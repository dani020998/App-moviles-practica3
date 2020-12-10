package dadm.practica3.space;

import android.support.annotation.Nullable;

import dadm.practica3.R;
import dadm.practica3.engine.GameEngine;
import dadm.practica3.engine.ScreenGameObject;
import dadm.practica3.engine.Sprite;

public class Bird extends Sprite {

    private final GameController gameController;

    private double speed;
    private double speedX;
    private double speedY;
    private double rotationSpeed;

    public Bird(GameController gameController, GameEngine gameEngine, String color) {
        super(gameEngine,new int[]{R.drawable.bird_yellow_1, R.drawable.bird_yellow_2},
                new int[]{R.drawable.bird_green_1, R.drawable.bird_green_2});

        this.speed = 200d * pixelFactor/1000d;
        this.gameController = gameController;
        this.setColor(color);
    }

    public void init(GameEngine gameEngine) {
        // They initialize in a [-30, 30] degrees angle
        //double angle = gameEngine.random.nextDouble()*Math.PI/3d-Math.PI/6d;
        this.speed = (gameEngine.random.nextInt(200 - 100) + 100) * pixelFactor/1000d;
        speedX = -speed;
        speedY = 0;
        // Asteroids initialize in the central 50% of the screen horizontally
        positionX = gameEngine.width ;
        // They initialize outside of the screen vertically
        positionY = gameEngine.random.nextInt(gameEngine.height-this.height);
        rotationSpeed = 0; //angle*(180d / Math.PI)/250d; // They rotate 4 times their ange in a second.
        rotation = 0;
    }

    @Override
    public void startGame() {
    }

    public void removeObject(GameEngine gameEngine) {
        // Return to the pool
        gameEngine.removeGameObject(this);
        gameController.returnToPool(this);
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        this.setCurrentTime(elapsedMillis);
        positionX += speedX * elapsedMillis;
        positionY += speedY * elapsedMillis;
        rotation += rotationSpeed * elapsedMillis;
        if (rotation > 360) {
            rotation = 0;
        }
        else if (rotation < 0) {
            rotation = 360;
        }
        // Check of the sprite goes out of the screen and return it to the pool if so
        if (positionX < 0) {
            // Return to the pool
            gameEngine.removeGameObject(this);
            gameController.returnToPool(this);
        }
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {

    }
}
