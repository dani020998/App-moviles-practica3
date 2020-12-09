package dadm.practica3.counter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dadm.practica3.BaseFragment;
import dadm.practica3.R;
import dadm.practica3.ScaffoldActivity;
import dadm.practica3.space.GameController;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PuntuacionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PuntuacionFragment extends BaseFragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PuntuacionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PuntuacionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PuntuacionFragment newInstance(String param1, String param2) {
        PuntuacionFragment fragment = new PuntuacionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_puntuacion, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.Btn_Volver).setOnClickListener(this);
        ((TextView) view.findViewById(R.id.Txt_PuntuacionFinal)).setText(""+GameController.get_GameController().getPuntuacion());

    }

    @Override
    public void onClick(View view) {
        ((ScaffoldActivity)getActivity()).startMenuInicio();
    }
}