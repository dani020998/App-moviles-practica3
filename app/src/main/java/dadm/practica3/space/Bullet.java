package dadm.practica3.space;

import android.widget.TextView;

import dadm.practica3.R;
import dadm.practica3.counter.GameFragment;
import dadm.practica3.engine.GameEngine;
import dadm.practica3.engine.ScreenGameObject;
import dadm.practica3.engine.Sprite;
import dadm.practica3.sound.GameEvent;

public class Bullet extends Sprite {

    private double speedFactor;

    private SpaceShipPlayer parent;
    GameController GameController;

    public Bullet(GameEngine gameEngine, GameController GameCon){
        super(gameEngine, new int[]{R.drawable.fire_ball_yellow}, new int[]{R.drawable.fire_ball_green});
        speedFactor = gameEngine.pixelFactor * 300d / 1000d;
        GameController=GameCon;
    }

    @Override
    public void startGame() {}

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        positionX += speedFactor * elapsedMillis;
        if (positionX > gameEngine.width) {
            gameEngine.removeGameObject(this);
            // And return it to the pool
            parent.releaseBullet(this);
        }
    }


    public void init(SpaceShipPlayer parentPlayer, double initPositionX, double initPositionY) {
        positionX = initPositionX - width/2;
        positionY = initPositionY - height/2;
        parent = parentPlayer;
        this.color=parent.getColor();
    }

    private void removeObject(GameEngine gameEngine) {
        gameEngine.removeGameObject(this);
        // And return it to the pool
        parent.releaseBullet(this);
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {
        if (otherObject instanceof Bird && !((Bird) otherObject).getColor().equals(this.color)) {
            // Remove both from the game (and return them to their pools)
            removeObject(gameEngine);
            Bird a = (Bird) otherObject;
            a.removeObject(gameEngine);
            gameEngine.onGameEvent(GameEvent.AsteroidHit);

            // Add some score
            GameController.anadir_puntuacion();
            GameController.frag.CambioPuntuacion("Puntuacion: "+ GameController.getPuntuacion());
        }
    }
}
