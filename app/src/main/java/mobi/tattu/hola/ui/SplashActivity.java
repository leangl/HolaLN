package mobi.tattu.hola.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import mobi.tattu.hola.R;
import mobi.tattu.hola.data.DataStore;
import mobi.tattu.hola.model.Category;

/**
 * Created by cristian on 25/09/15.
 */
public class SplashActivity extends BaseActivity {

    private ProgressBar mProgressBar;
    private Timer mTimer;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
        mTimer.purge();
        mTimer = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        createListCategory();
        this.mProgressBar = (ProgressBar) this.findViewById(R.id.progressBar);
        mTimer = new Timer();

        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {


                startActivity(new Intent(SplashActivity.this, CategoryActivity.class));
            }
        }, TimeUnit.SECONDS.toMillis(2));
    }
    private void hideProgressbar(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                mProgressBar.setVisibility(View.GONE);
            }
        });
    }
    private void createListCategory() {

        createCategory(getString(R.string.category_politica), false, Category.Side.LEFT);
        createCategory(getString(R.string.category_economia), false, Category.Side.LEFT);
        createCategory(getString(R.string.category_el_mundo), false, Category.Side.LEFT);
        createCategory(getString(R.string.category_opinion), false, Category.Side.LEFT);
        createCategory(getString(R.string.category_sociedad), false, Category.Side.LEFT);
        createCategory(getString(R.string.category_buenos_aires), false, Category.Side.RIGHT);
        createCategory(getString(R.string.category_espectaculos), false, Category.Side.RIGHT);
        createCategory(getString(R.string.category_tecnologia), false, Category.Side.RIGHT);
        createCategory(getString(R.string.category_deportes), false, Category.Side.RIGHT);
        createCategory(getString(R.string.category_seguridad), false, Category.Side.RIGHT);
    }

    /**
     * @param name
     * @param checked
     * @param side    indica de que lado se tiene que mostrar el checkbox (los valores son )
     * @return
     */
    private Category createCategory(String name, boolean checked, Category.Side side) {
        Category category = new Category();
        category.name = name;
        category.checked = checked;
        category.layoutSide = side;
        DataStore.getInstance().putObject(category.name, category);
        return category;
    }


}
