package dadm.practica3.engine;

import android.content.Context;

import java.util.List;

public interface GameView {

    void draw();

    void setGameObjects(List<GameObject> gameObjects);

    int getWidth();

    int getHeight();

    int getPaddingLeft();

    int getPaddingRight();

    int getPaddingTop();

    int getPaddingBottom();

    Context getContext();
}
