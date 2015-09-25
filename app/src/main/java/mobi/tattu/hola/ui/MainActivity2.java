package mobi.tattu.hola.ui;

import android.os.Bundle;

import mobi.tattu.hola.ui.fragments.MainFragment;

/**
 * Created by cristian on 25/09/15.
 */
public class MainActivity2 extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showFragment(MainFragment.newInstance(),false);
    }
}
