package mobi.tattu.hola.ui;

import android.app.Application;
import android.content.Context;

/**
 * Created by cristian on 25/09/15.
 */
public class HolaLaNacionApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}
