package dadm.practica3.engine;

import android.graphics.Rect;

public abstract class ScreenGameObject extends GameObject {

    protected double positionX;
    protected double positionY;

    protected int yellowWidth;
    protected int yellowHeight;
    protected int greenWidth;
    protected int greenHeight;

    protected int width;
    protected int height;

    protected String color="green";

    public double radius;

    public abstract void onCollision(GameEngine gameEngine, ScreenGameObject otherObject);

    public Rect mBoundingRect = new Rect(-1, -1, -1, -1);

    public void onPostUpdate(GameEngine gameEngine) {
        if(this.color.equals("green")){
            mBoundingRect.set(
                    (int) positionX,
                    (int) positionY,
                    (int) positionX + greenWidth,
                    (int) positionY + greenHeight);
        }else{
            mBoundingRect.set(
                    (int) positionX,
                    (int) positionY,
                    (int) positionX + yellowWidth,
                    (int) positionY + yellowHeight);
        }

    }

    public boolean checkCollision(ScreenGameObject otherObject) {
         return checkRectangularCollision(otherObject);
    }

    private boolean checkCircularCollision(ScreenGameObject other) {
        double distanceX = (positionX + width /2) - (other.positionX + other.width /2);
        double distanceY = (positionY + height /2) - (other.positionY + other.height /2);
        double squareDistance = distanceX*distanceX + distanceY*distanceY;
        double collisionDistance = (radius + other.radius);
        return squareDistance <= collisionDistance*collisionDistance;
    }

    private boolean checkRectangularCollision(ScreenGameObject other){
        return Rect.intersects(mBoundingRect, other.mBoundingRect);
    }

}
