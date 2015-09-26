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

        saveCategory(Category.POLITICA, false, Category.Side.LEFT);
        saveCategory(Category.ECONOMIA, false, Category.Side.LEFT);
        saveCategory(Category.EL_MUNDO, false, Category.Side.LEFT);
        saveCategory(Category.OPINION, false, Category.Side.LEFT);
        saveCategory(Category.SOCIEDAD, false, Category.Side.LEFT);
        saveCategory(Category.BUENOS_AIRES, false, Category.Side.RIGHT);
        saveCategory(Category.ESPECTACULOS, false, Category.Side.RIGHT);
        saveCategory(Category.TECNOLOGIA, false, Category.Side.RIGHT);
        saveCategory(Category.DEPORTES, false, Category.Side.RIGHT);
        saveCategory(Category.SEGURIDAD, false, Category.Side.RIGHT);
    }

    /**
     *
     * @param checked
     * @param side    indica de que lado se tiene que mostrar el checkbox (los valores son )
     * @return
     */
    private Category saveCategory(Category category, boolean checked, Category.Side side) {
        category.checked = checked;
        category.layoutSide = side;
        DataStore.getInstance().putObject(category.getNameCategory(this), category);
        return category;
    }


}
