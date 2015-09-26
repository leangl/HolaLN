package mobi.tattu.hola.ui;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import mobi.tattu.hola.service.SpeechRecognizerService;

/**
 * Created by cristian on 25/09/15.
 */
public class HolaLaNacionApplication extends Application {

    private int mBindFlag;
    private Messenger mServiceMessenger;

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);

        Intent service = new Intent(this, SpeechRecognizerService.class);
        startService(service);
        mBindFlag = Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH ? 0 : Context.BIND_ABOVE_CLIENT;

        bindService(new Intent(this, SpeechRecognizerService.class), mServiceConnection, mBindFlag);
    }

    public static Context getContext() {
        return mContext;
    }

    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("VOICE", "onServiceConnected");

            mServiceMessenger = new Messenger(service);
            Message msg = new Message();
            msg.what = SpeechRecognizerService.MSG_RECOGNIZER_START_LISTENING;

            try {
                mServiceMessenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("MainActivity", "onServiceDisconnected");
            mServiceMessenger = null;
        }

    };
}
