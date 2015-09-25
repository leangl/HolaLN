package mobi.tattu.hola.ui;

import android.os.Bundle;

import mobi.tattu.hola.ui.fragments.CategoryFragment;

/**
 * Created by cristian on 25/09/15.
 */
public class CategoryActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideToolbar();
        showFragment(CategoryFragment.newInstance(),false);

    }
}
