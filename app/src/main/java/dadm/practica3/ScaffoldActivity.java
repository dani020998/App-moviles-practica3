package dadm.practica3;

import android.media.AudioManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import dadm.practica3.counter.GameFragment;
import dadm.practica3.counter.PuntuacionFragment;
import dadm.practica3.counter.MainMenuFragment;
import dadm.practica3.counter.SeleccionNaveFragment;
import dadm.practica3.sound.GameEvent;
import dadm.practica3.sound.SoundManager;

public class ScaffoldActivity extends AppCompatActivity {

    private static final String TAG_FRAGMENT = "content";
    private static ScaffoldActivity myGameActivity = null;

    private SoundManager soundManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scaffold);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainMenuFragment(), TAG_FRAGMENT)
                    .commit();
        }
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundManager = new SoundManager(getApplicationContext());
        soundManager.loadMusic("sfx/menu_theme.mp3");
    }

    @Override
    protected void onStop() {
        super.onStop();
        soundManager.stopMusic();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        soundManager.resumeMusic();
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public void startGame() {
        // Navigate the the game fragment, which makes the start automatically
        navigateToFragment( new GameFragment());
        soundManager.loadMusic("sfx/game_theme.wav");
    }

    public void startMenuInicio() {
        // Navigate the the game fragment, which makes the start automatically
        navigateToFragment( new MainMenuFragment());
        soundManager.loadMusic("sfx/menu_theme.mp3");
    }

    public void startSeleccionNave() {
        // Navigate the the game fragment, which makes the start automatically
        navigateToFragment( new SeleccionNaveFragment());
    }

    public void startPuntuaciones() {
        // Navigate the the game fragment, which makes the start automatically
        navigateToFragment( new PuntuacionFragment());
        soundManager.loadMusic("sfx/menu_theme.mp3");
    }

    private void navigateToFragment(BaseFragment dst) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, dst, TAG_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        final BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);
        if (fragment == null || !fragment.onBackPressed()) {
            super.onBackPressed();
        }
    }

    public void navigateBack() {
        // Do a push on the navigation history
        super.onBackPressed();
    }

    public static ScaffoldActivity GetActivity()
    {
        return myGameActivity;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            View decorView = getWindow().getDecorView();
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LOW_PROFILE);
            }
            else {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
        }
    }
}
