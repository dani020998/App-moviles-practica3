package dadm.practica3;

import android.support.v4.app.Fragment;


public class BaseFragment extends Fragment {
    public boolean onBackPressed() {
        return false;
    }

    protected ScaffoldActivity getScaffoldActivity () {
        return (ScaffoldActivity) getActivity();
    }
}
