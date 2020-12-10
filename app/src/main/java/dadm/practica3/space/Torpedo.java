package dadm.practica3.space;

import android.widget.TextView;

import dadm.practica3.R;
import dadm.practica3.counter.GameFragment;
import dadm.practica3.engine.GameEngine;
import dadm.practica3.engine.ScreenGameObject;
import dadm.practica3.engine.Sprite;
import dadm.practica3.sound.GameEvent;

public class Torpedo extends Sprite {

    private double speedFactorX, speedFactorY;
    private int id;

    private SpaceShipPlayer parent;
    GameController GameController;

    public Torpedo(GameEngine gameEngine, GameController GameCon){
        super(gameEngine, new int[]{R.drawable.torpedo_yellow}, new int[]{R.drawable.torpedo_green});
        speedFactorX = gameEngine.pixelFactor * 300d / 1000d;
        speedFactorY = gameEngine.pixelFactor * 100d / 1000d;
        GameController=GameCon;
    }

    @Override
    public void startGame() {}

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        positionX += speedFactorX * elapsedMillis;
        switch (id){
            case 0:
                positionY += speedFactorY * elapsedMillis;
                break;
            case 1:
                positionY -= speedFactorY * elapsedMillis;
                break;
        }
        if (positionX > gameEngine.width) {
            gameEngine.removeGameObject(this);
            // And return it to the pool
            parent.releaseTorpedo(this);
        }
    }


    public void init(SpaceShipPlayer parentPlayer, double initPositionX, double initPositionY, int id) {
        positionX = initPositionX + width/2;
        positionY = initPositionY + height/2;
        parent = parentPlayer;
        this.id=id;
        this.color=parent.getColor();
    }

    private void removeObject(GameEngine gameEngine) {
        gameEngine.removeGameObject(this);
        // And return it to the pool
        parent.releaseTorpedo(this);
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {
        if (otherObject instanceof Bird && !((Bird) otherObject).getColor().equals(this.color)) {
            // Remove both from the game (and return them to their pools)
            removeObject(gameEngine);
            Bird a = (Bird) otherObject;
            a.removeObject(gameEngine);
            gameEngine.onGameEvent(GameEvent.HitBird);

            // Add some score
            GameController.anadir_puntuacion(gameEngine);
            GameController.frag.CambioPuntuacion("Puntuacion: "+ GameController.getPuntuacion());
        }
    }
}
