package mobi.tattu.hola.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import mobi.tattu.hola.R;
import mobi.tattu.hola.ui.fragments.CategoryFragment;

/**
 * Created by cristian on 25/09/15.
 */
public class CategoryActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideToolbar();
        showFragment(CategoryFragment.newInstance(), false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_category:
                break;


        }
        return super.onOptionsItemSelected(item);
    }
}
