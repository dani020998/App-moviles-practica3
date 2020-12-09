package dadm.practica3.counter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dadm.practica3.BaseFragment;
import dadm.practica3.R;
import dadm.practica3.ScaffoldActivity;


public class MainMenuFragment extends BaseFragment implements View.OnClickListener {
    public MainMenuFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_menu, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_start).setOnClickListener(this);
        view.findViewById(R.id.btn_seleccionNave).setOnClickListener(this);
        SeleccionNaveFragment.Inicializar_aviones();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btn_start:
                ((ScaffoldActivity)getActivity()).startGame();
                break;
            case R.id.btn_seleccionNave:
                ((ScaffoldActivity)getActivity()).startSeleccionNave();
                break;
        }
    }
}
