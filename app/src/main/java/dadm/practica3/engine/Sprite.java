package dadm.practica3.engine;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public abstract class Sprite extends ScreenGameObject {

    protected double rotation;

    protected double pixelFactor;

    private final Bitmap bitmap;

    private final Matrix matrix = new Matrix();

    protected Sprite (GameEngine gameEngine, int drawableRes) {
        Resources r = gameEngine.getContext().getResources();
        Drawable spriteDrawable = r.getDrawable(drawableRes);

        this.pixelFactor = gameEngine.pixelFactor;

        this.width = (int) (spriteDrawable.getIntrinsicWidth() * this.pixelFactor);
        this.height = (int) (spriteDrawable.getIntrinsicHeight() * this.pixelFactor);

        this.bitmap = ((BitmapDrawable) spriteDrawable).getBitmap();

        radius = Math.max(height, width)/2;
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (positionX > canvas.getWidth()
                || positionY > canvas.getHeight()
                || positionX < - width
                || positionY < - height) {
            return;
        }

        Paint mPaint = new Paint();
        mPaint.setColor(Color.CYAN);
        //canvas.drawRect((float)positionX,(float)positionY,(float)positionX+width,(float)positionY+height,mPaint);
        canvas.drawCircle((float)this.positionX,(float)this.positionY,(float)this.radius,mPaint);

        matrix.reset();
        matrix.postScale((float) pixelFactor, (float) pixelFactor);
        matrix.postTranslate((float) positionX, (float) positionY);
        matrix.postRotate((float) rotation, (float) (positionX + width/2), (float) (positionY + height/2));
        canvas.drawBitmap(bitmap, matrix, null);
    }
}
