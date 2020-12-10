package dadm.practica3.space;

import android.util.Log;
import android.widget.ImageView;

import dadm.practica3.R;
import dadm.practica3.engine.GameEngine;
import dadm.practica3.engine.ScreenGameObject;
import dadm.practica3.engine.Sprite;

public class montana3 extends Sprite {

    private double speedFactor;
    private int posicion;

    public montana3(GameEngine gameEngine, String color, int pos) {
        super(gameEngine,new int[]{R.drawable.montana3},new int[]{R.drawable.montana3});
        posicion=pos;
        speedFactor = -pixelFactor * 100d / 100d;
        this.setColor(color);
    }

    @Override
    public void startGame() {
        positionX = posicion*this.width;
        Log.i("Pos", ""+positionX);
        Log.i("Width", ""+this.width);
        positionY = 900;
    }


    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        this.setCurrentTime(elapsedMillis);
        positionX += speedFactor * elapsedMillis;
        //positionY += speedY * elapsedMillis;
        if(positionX<-this.width){
            positionX=this.width;
        }
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {

    }
}
