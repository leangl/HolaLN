package mobi.tattu.hola.ui;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import mobi.tattu.hola.service.NewsReader;

/**
 * Created by cristian on 25/09/15.
 */
public class HolaLaNacionApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
        NewsReader.getInstance().start(this);
    }

    public static Context getContext(){
        return mContext;
    }
}
