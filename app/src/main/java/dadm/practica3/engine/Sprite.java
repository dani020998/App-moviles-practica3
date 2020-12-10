package dadm.practica3.engine;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

public abstract class Sprite extends ScreenGameObject {

    protected double rotation;

    protected double pixelFactor;

    private final Bitmap[] bitmapYellow;

    private final Bitmap[] bitmapGreen;

    private int currentStep = 0;

    private long currentTime=0;

    private final Matrix matrix = new Matrix();

    protected Sprite (GameEngine gameEngine, int[] drawableResYellow, int[] drawableResGreen) {

        Resources r = gameEngine.getContext().getResources();
        this.bitmapYellow = new Bitmap[drawableResYellow.length];

        this.bitmapGreen = new Bitmap[drawableResGreen.length];
        this.pixelFactor = gameEngine.pixelFactor;

        Drawable spriteDrawableYellow = r.getDrawable(drawableResYellow[0]);
        Drawable spriteDrawableGreen = r.getDrawable(drawableResGreen[0]);

        this.greenHeight = (int) (spriteDrawableGreen.getIntrinsicHeight() * this.pixelFactor);
        this.greenWidth= (int) (spriteDrawableGreen.getMinimumWidth() * this.pixelFactor);

        this.yellowHeight = (int) (spriteDrawableYellow.getIntrinsicHeight() * this.pixelFactor);
        this.yellowWidth= (int) (spriteDrawableYellow.getIntrinsicWidth() * this.pixelFactor);

        for(int i=0;i<drawableResYellow.length;i++){
            spriteDrawableYellow = r.getDrawable(drawableResYellow[i]);
            spriteDrawableGreen = r.getDrawable(drawableResGreen[i]);

            this.bitmapYellow[i] = ((BitmapDrawable) spriteDrawableYellow).getBitmap();
            this.bitmapGreen[i] = ((BitmapDrawable) spriteDrawableGreen).getBitmap();
        }
    }

    public void setColor(String color){
        if(color.equals("green")){
            this.color="green";
            this.width=this.greenWidth;
            this.height=this.greenHeight;
        }else{
            this.color="yellow";
            this.width=this.yellowWidth;
            this.height=this.yellowHeight;
        }
    }

    public String getColor(){
        return this.color;
    }

    public void setCurrentTime(long time){
        this.currentTime+=time;
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (positionX > canvas.getWidth()
                || positionY > canvas.getHeight()
                || positionX < - width
                || positionY < - height) {
            return;
        }

        /*Paint mPaint = new Paint();
        mPaint.setColor(Color.CYAN);
        canvas.drawCircle((float)this.positionX,(float)this.positionY,(float)this.radius,mPaint);*/
        //canvas.drawRect((float)positionX,(float)positionY,(float)positionX+width,(float)positionY+height,mPaint);
        //canvas.drawCircle((float)this.positionX,(float)this.positionY,(float)this.radius,mPaint);


        matrix.reset();
        matrix.postScale((float) pixelFactor, (float) pixelFactor);
        matrix.postTranslate((float) positionX, (float) positionY);
        matrix.postRotate((float) rotation, (float) (positionX + width/2), (float) (positionY + height/2));
        if(this.color.equals("green")){
            canvas.drawBitmap(bitmapGreen[currentStep], matrix, null);
        }else{
            canvas.drawBitmap(bitmapYellow[currentStep], matrix, null);
        }
        if(this.currentTime>250){
            currentStep++;
            if(currentStep==bitmapGreen.length){
                currentStep=0;
            }
            this.currentTime=0;
        }
    }
}
