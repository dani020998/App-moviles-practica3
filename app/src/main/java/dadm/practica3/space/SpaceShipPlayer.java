package dadm.practica3.space;

import java.util.ArrayList;
import java.util.List;

import dadm.practica3.R;
import dadm.practica3.ScaffoldActivity;
import dadm.practica3.engine.GameEngine;
import dadm.practica3.engine.ScreenGameObject;
import dadm.practica3.engine.Sprite;
import dadm.practica3.input.InputController;
import dadm.practica3.sound.GameEvent;

public class SpaceShipPlayer extends Sprite {

    private static final int INITIAL_BULLET_POOL_AMOUNT = 6;
    private static final int INITIAL_TORPEDO_POOL_AMOUNT = 3;
    private static final long TIME_BETWEEN_BULLETS = 700;
    private static final long TIME_BETWEEN_TORPEDOS = 5000;
    List<Bullet> bullets = new ArrayList<Bullet>();
    List<Torpedo> torpedoes = new ArrayList<Torpedo>();
    private long timeSinceLastBulletFire, timeSinceLastTorpedoFire;

    private int maxX;
    private int maxY;
    private double speedFactor;
    private boolean shootTorpedo;


    public SpaceShipPlayer(GameEngine gameEngine, int yellowTypePlane, int greenTypePlane){
        super(gameEngine, new int[]{yellowTypePlane},
                new int[]{greenTypePlane});
        speedFactor = pixelFactor * 100d / 1000d; // We want to move at 100px per second on a 400px tall screen
        maxX = gameEngine.width - width;
        maxY = gameEngine.height - height;
        shootTorpedo=false;
        timeSinceLastTorpedoFire=TIME_BETWEEN_TORPEDOS;
        this.setColor("yellow");

        initBulletPool(gameEngine);
    }

    private void initBulletPool(GameEngine gameEngine) {
        for (int i=0; i<INITIAL_BULLET_POOL_AMOUNT; i++) {
            bullets.add(new Bullet(gameEngine,GameController.get_GameController()));
        }
        for(int i=0; i<INITIAL_TORPEDO_POOL_AMOUNT; i++){
            torpedoes.add(new Torpedo(gameEngine, GameController.get_GameController()));
        }
    }

    private Bullet getBullet() {
        if (bullets.isEmpty()) {
            return null;
        }
        return bullets.remove(0);
    }

    private Torpedo getTorpedo(){
        if(torpedoes.isEmpty()){
            return null;
        }
        return torpedoes.remove(0);
    }

    void releaseBullet(Bullet bullet) {
        bullets.add(bullet);
    }
    void releaseTorpedo(Torpedo torpedo) { torpedoes.add(torpedo); }

    public void shootTorpedo(){
        shootTorpedo=true;
    }

    public long getTimeBetweenTorpedoes(){
        return TIME_BETWEEN_TORPEDOS;
    }

    public long getTimeSinceLastTorpedoFire(){
        return timeSinceLastTorpedoFire;
    }

    @Override
    public void startGame() {
        positionX = maxX / 6;
        positionY = maxY / 2;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        // Get the info from the inputController
        updatePosition(elapsedMillis, gameEngine.theInputController);
        fireBullet(elapsedMillis, gameEngine);
        fireTorpedo(elapsedMillis, gameEngine);
    }

    private void updatePosition(long elapsedMillis, InputController inputController) {
        positionX += speedFactor * inputController.horizontalFactor * elapsedMillis;
        if (positionX < 0) {
            positionX = 0;
        }
        if (positionX > maxX) {
            positionX = maxX;
        }
        positionY += speedFactor * inputController.verticalFactor * elapsedMillis;
        if (positionY < 0) {
            positionY = 0;
        }
        if (positionY > maxY) {
            positionY = maxY;
        }
    }

    private void fireBullet(long elapsedMillis, GameEngine gameEngine) {
        if (timeSinceLastBulletFire > TIME_BETWEEN_BULLETS) {
            Bullet bullet = getBullet();
            if (bullet == null) {
                return;
            }
            bullet.init(this, positionX + width, positionY+height/2);
            gameEngine.addGameObject(bullet);
            timeSinceLastBulletFire = 0;
            gameEngine.onGameEvent(GameEvent.LaserFired);
        }
        else {
            timeSinceLastBulletFire += elapsedMillis;
        }
    }

    private void fireTorpedo(long elapsedMillis, GameEngine gameEngine){
        if(shootTorpedo){
            for(int i=0;i<3;i++){
                Torpedo torpedo = getTorpedo();
                if(torpedo==null){
                    return;
                }
                torpedo.init(this, positionX + width, positionY+height/2, i);
                gameEngine.addGameObject(torpedo);
                timeSinceLastTorpedoFire=0;
                shootTorpedo=false;
            }

        }else{
            timeSinceLastTorpedoFire+=elapsedMillis;
        }
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {
        if (otherObject instanceof Bird && !((Bird) otherObject).getColor().equals(this.color)) {
            gameEngine.removeGameObject(this);
            //gameEngine.stopGame();
            Bird b = (Bird) otherObject;
            b.removeObject(gameEngine);
            gameEngine.onGameEvent(GameEvent.SpaceshipHit);
            GameController aaa;
            aaa=GameController.get_GameController();
            GameController.get_GameController().FinJuego(gameEngine);
        }
    }
}
