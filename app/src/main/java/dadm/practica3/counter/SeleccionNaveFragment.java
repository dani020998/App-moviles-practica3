package dadm.practica3.counter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import dadm.practica3.BaseFragment;
import dadm.practica3.R;
import dadm.practica3.ScaffoldActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SeleccionNaveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SeleccionNaveFragment extends BaseFragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static int[] nave_seleccionada = new int[2];;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RadioButton ArrayRadioButton[] = new RadioButton[3];;

    public SeleccionNaveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SeleccionNaveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SeleccionNaveFragment newInstance(String param1, String param2) {
        SeleccionNaveFragment fragment = new SeleccionNaveFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_seleccion_nave, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayRadioButton[0]=view.findViewById(R.id.RBTN_Nave1);
        ArrayRadioButton[1]=view.findViewById(R.id.RBTN_Nave2);
        ArrayRadioButton[2]=view.findViewById(R.id.RBTN_Nave3);
        view.findViewById(R.id.Btn_SeleccionNaveVolver).setOnClickListener(this);

        switch (nave_seleccionada[0]){
            case R.drawable.plane_1_green:
                ((RadioButton) view.findViewById(R.id.RBTN_Nave1)).setChecked(true);
                break;
            case R.drawable.plane_2_green:
                ((RadioButton) view.findViewById(R.id.RBTN_Nave2)).setChecked(true);
                break;
            case R.drawable.plane_3_green:
                ((RadioButton) view.findViewById(R.id.RBTN_Nave3)).setChecked(true);
                break;
            default:
                ((RadioButton) view.findViewById(R.id.RBTN_Nave1)).setChecked(true);
                break;
        }
    }

    public static void Inicializar_aviones()
    {
        nave_seleccionada[0]=R.drawable.plane_1_green;
        nave_seleccionada[1]=R.drawable.plane_1_yellow;
    }

    @Override
    public void onClick(View view) {
        for (int i=0;i<3;i++){
            if(ArrayRadioButton[i].isChecked()){
                switch (i){
                    case 0:
                        nave_seleccionada[0]=R.drawable.plane_1_green;
                        nave_seleccionada[1]=R.drawable.plane_1_yellow;
                    break;
                    case 1:
                        nave_seleccionada[0]=R.drawable.plane_2_green;
                        nave_seleccionada[1]=R.drawable.plane_2_yellow;
                    break;
                    case 2:
                        nave_seleccionada[0]=R.drawable.plane_3_green;
                        nave_seleccionada[1]=R.drawable.plane_3_yellow;
                    break;
                }
                i=3;
            }
        }
        ((ScaffoldActivity)getActivity()).startMenuInicio();
    }

    public static int getNave_green(){
        return nave_seleccionada[0];
    }
    public static int getNave_yellow(){
        return nave_seleccionada[1];
    }
}