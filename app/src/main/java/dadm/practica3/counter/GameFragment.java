package dadm.practica3.counter;

import android.content.Context;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;

import dadm.practica3.BaseFragment;
import dadm.practica3.R;
import dadm.practica3.ScaffoldActivity;
import dadm.practica3.engine.FramesPerSecondCounter;
import dadm.practica3.engine.GameEngine;
import dadm.practica3.engine.GameView;
import dadm.practica3.input.JoystickInputController;
import dadm.practica3.space.GameController;
import dadm.practica3.space.SpaceShipPlayer;
import dadm.practica3.space.estela;
import dadm.practica3.space.montana1;
import dadm.practica3.space.montana2;
import dadm.practica3.space.montana3;
import dadm.practica3.space.nube;


public class GameFragment extends BaseFragment implements View.OnClickListener {
    private GameEngine theGameEngine;
    private static final long TIME_BETWEEN_TORPEDOS = 5000;
    private static final long TIME_BETWEEN_CHANGE_COLOR = 1000;
    public TextView textPutuacion;
    public ImageView img_vida3, img_vida2, img_vida1;
    public ImageButton btn_pause, btn_changeColor, btn_shoot;
    private static GameFragment frag;

    public GameFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        frag=this;
        textPutuacion= (TextView)view.findViewById(R.id.Txt_Puntuacion);
        img_vida1= (ImageView) view.findViewById(R.id.img_vida1);
        img_vida2= (ImageView) view.findViewById(R.id.img_vida2);
        img_vida3= (ImageView) view.findViewById(R.id.img_vida3);
        super.onViewCreated(view, savedInstanceState);
        btn_pause=view.findViewById(R.id.btn_play_pause);
        btn_shoot=view.findViewById(R.id.btn_shoot);
        btn_changeColor=view.findViewById(R.id.btn_changeColor);
        btn_pause.setOnClickListener(this);
        btn_shoot.setOnClickListener(this);
        btn_changeColor.setOnClickListener(this);
        final ViewTreeObserver observer = view.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){
            @Override
            public void onGlobalLayout(){
                //Para evitar que sea llamado múltiples veces,
                //se elimina el listener en cuanto es llamado
                observer.removeOnGlobalLayoutListener(this);
                GameView gameView = (GameView) getView().findViewById(R.id.gameView);
                theGameEngine = new GameEngine(getActivity(), gameView);
                theGameEngine.setSoundManager(getScaffoldActivity().getSoundManager());
                theGameEngine.setTheInputController(new JoystickInputController(getView()));
                theGameEngine.addGameObject(new GameController(theGameEngine,frag));
                theGameEngine.addGameObject(new nube(theGameEngine,"yellow",0));
                theGameEngine.addGameObject(new nube(theGameEngine,"yellow",1));
                theGameEngine.addGameObject(new montana1(theGameEngine,"yellow",0));
                theGameEngine.addGameObject(new montana1(theGameEngine,"yellow",1));
                theGameEngine.addGameObject(new montana2(theGameEngine,"yellow",0));
                theGameEngine.addGameObject(new montana2(theGameEngine,"yellow",1));
                theGameEngine.addGameObject(new montana3(theGameEngine,"yellow",0));
                theGameEngine.addGameObject(new montana3(theGameEngine,"yellow",1));
                theGameEngine.addGameObject(new estela(theGameEngine,"yellow",0));
                theGameEngine.addGameObject(new estela(theGameEngine,"yellow",1));
                theGameEngine.addSpaceShip(new SpaceShipPlayer(theGameEngine, SeleccionNaveFragment.getNave_yellow(), SeleccionNaveFragment.getNave_green()));
                theGameEngine.startGame();
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_play_pause:
                pauseGameAndShowPauseDialog();
                break;
            case R.id.btn_changeColor:
                theGameEngine.setSpaceShipPlayerColor();
                btn_changeColor.setEnabled(false);
                new CountDownTimer(TIME_BETWEEN_CHANGE_COLOR, 1000){
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        btn_changeColor.setEnabled(true);
                    }
                }.start();
                break;
            case R.id.btn_shoot:
                SpaceShipPlayer spaceShipPlayer = theGameEngine.getSpaceShipPlayer();
                spaceShipPlayer.shootTorpedo();
                btn_shoot.setEnabled(false);
                new CountDownTimer(TIME_BETWEEN_TORPEDOS, 1000){
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        btn_shoot.setEnabled(true);
                    }
                }.start();
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (theGameEngine.isRunning()){
            pauseGameAndShowPauseDialog();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        theGameEngine.stopGame();
    }

    @Override
    public boolean onBackPressed() {
        if (theGameEngine.isRunning()) {
            pauseGameAndShowPauseDialog();
            return true;
        }
        return false;
    }

    private void pauseGameAndShowPauseDialog() {
        theGameEngine.pauseGame();
        new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                .setTitle(R.string.pause_dialog_title)
                .setMessage(R.string.pause_dialog_message)
                .setPositiveButton(R.string.resume, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        theGameEngine.resumeGame();
                    }
                })
                .setNegativeButton(R.string.stop, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        theGameEngine.stopGame();
                        ((ScaffoldActivity)getActivity()).navigateBack();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        theGameEngine.resumeGame();
                    }
                })
                .create()
                .show();

    }


    private void playOrPause() {
        ImageButton button = (ImageButton) getView().findViewById(R.id.btn_play_pause);
        if (theGameEngine.isPaused()) {
            theGameEngine.resumeGame();
        }
        else {
            theGameEngine.pauseGame();
        }
    }
    public void CambioPuntuacion(String newText){
        textPutuacion.setText(newText);
    }
    public void Actualizar_vida(int n_vidas){
        switch (n_vidas){
            case 2:
                img_vida3.setVisibility(getView().INVISIBLE);
                break;
            case 1:
                img_vida2.setVisibility(getView().INVISIBLE);
                break;
            case 0:
                img_vida1.setVisibility(getView().INVISIBLE);
                break;
        }
    }

}
