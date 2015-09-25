package mobi.tattu.hola.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import mobi.tattu.hola.R;

/**
 * Created by cristian on 25/09/15.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white, getTheme()));
            } else {
                mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
            }
            setSupportActionBar(mToolbar);

        }
    }

    public void showFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment);
        if (addToBackStack) {
            tx.addToBackStack(fragment.getClass().getSimpleName());
        }
        tx.commit();
    }

    public void setTitle(int title) {
        mToolbar.setTitle(title);
    }

    public void hideToolbar(){
        mToolbar.setVisibility(View.GONE);
    }

}
