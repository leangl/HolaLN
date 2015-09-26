package mobi.tattu.hola.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import mobi.tattu.hola.ui.BaseActivity;

/**
 * Created by cristian on 25/09/15.
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public BaseActivity getBaseActivity(){
        return (BaseActivity) getActivity();
    }

}
