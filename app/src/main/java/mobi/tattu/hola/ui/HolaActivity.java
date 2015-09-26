package mobi.tattu.hola.ui;

import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.skyfishjy.library.RippleBackground;
import com.squareup.otto.Subscribe;

import es.claucookie.miniequalizerlibrary.EqualizerView;
import mobi.tattu.hola.R;
import mobi.tattu.hola.service.NewsReader;
import mobi.tattu.hola.service.SpeechRecognizerService;
import mobi.tattu.utils.Tattu;

/**
 * Created by Leandro on 26/9/2015.
 */
public class HolaActivity extends AppCompatActivity {

    private EqualizerView mEqualizerView;
    private RippleBackground rippleBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        setContentView(R.layout.hola);

        mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);

        rippleBackground = (RippleBackground) findViewById(R.id.content);
        //rippleBackground.startRippleAnimation();

        turnOnScreen();

        Tattu.register(this);

        mEqualizerView = (EqualizerView) findViewById(R.id.equalizer_view);
        mEqualizerView.setVisibility(View.VISIBLE);
        stopEqualizerView();
    }

    public void stopEqualizerView() {
        mEqualizerView.stopBars();
    }

    public void startEqualizerView() {
        mEqualizerView.animateBars();
    }

    public void hideEqualizarView() {
        mEqualizerView.setVisibility(View.GONE);
    }

    private PowerManager mPowerManager;
    private PowerManager.WakeLock mWakeLock;

    public void turnOnScreen() {
        // turn on screen
        Log.v("ProximityActivity", "ON!");
        mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "tag");
        mWakeLock.acquire();
        Tattu.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mWakeLock.release();
            }
        }, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Tattu.bus().unregister(this);
        Tattu.post(new SpeechRecognizerService.StopRecognition());
    }

    @Subscribe
    public void on(SpeechRecognizerService.Stop event) {
        finish();
    }

    @Subscribe
    public void on(NewsReader.NewsStarted event) {
        startEqualizerView();
    }

    @Subscribe
    public void on(NewsReader.SpeakStarted event) {
        startEqualizerView();
    }

    @Subscribe
    public void on(NewsReader.NewsEnded event) {
        stopEqualizerView();
    }

    @Subscribe
    public void on(NewsReader.SpeakEnded event) {
        stopEqualizerView();
    }

    @Subscribe
    public void on(SpeechRecognizerService.RecognitionStarted event) {
        rippleBackground.startRippleAnimation();
    }

    @Subscribe
    public void on(SpeechRecognizerService.RecognitionStopped event) {
        rippleBackground.stopRippleAnimation();
    }
}
