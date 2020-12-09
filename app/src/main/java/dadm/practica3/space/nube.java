package dadm.practica3.space;

import android.widget.ImageView;

import dadm.practica3.R;
import dadm.practica3.engine.GameEngine;
import dadm.practica3.engine.ScreenGameObject;
import dadm.practica3.engine.Sprite;

public class nube extends Sprite {

    private double speedFactor;
    private int posicion;
    private int width;

    public nube(GameEngine gameEngine, String color, int pos) {
        super(gameEngine,new int[]{R.drawable.nube},new int[]{R.drawable.nube});
        posicion=pos;
        speedFactor = -pixelFactor * 100d / 4000d;
        this.setColor(color);
        width=this.getwidth()+154;
    }

    @Override
    public void startGame() {
        positionX = 0+posicion*width;
        positionY =100;
    }


    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        this.setCurrentTime(elapsedMillis);
        positionX += speedFactor * elapsedMillis;
        //positionY += speedY * elapsedMillis;
        if(positionX<-width){
            positionX=width;
        }
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {

    }
}
