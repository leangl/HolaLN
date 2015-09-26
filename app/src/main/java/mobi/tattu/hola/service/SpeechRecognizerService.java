package mobi.tattu.hola.service;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import mobi.tattu.hola.R;

public class SpeechRecognizerService extends Service {

    private static final String HOLA = "hola";
    private static final String NACION = "nación";
    private static final String ATRAS = "atrás";
    private static final String LEER = "leer";
    private static final String PARAR = "parar";

    private static final String TAG = SpeechRecognizerService.class.getSimpleName();

    protected AudioManager mAudioManager;
    protected SpeechRecognizer mSpeechRecognizer;
    protected Intent mSpeechRecognizerIntent;
    protected final Messenger mServerMessenger = new Messenger(new IncomingHandler(this));

    protected boolean mIsListening;
    protected volatile boolean mIsCountDownOn;
    private boolean mIsStreamSolo;

    public static final int MSG_RECOGNIZER_START_LISTENING = 1;
    public static final int MSG_RECOGNIZER_CANCEL = 2;
    private SpeechRecognitionListener mListener;
    private Handler mHandler;

    private SoundPool mSoundPool;
    private int mBeepSound;

    @Override
    public void onCreate() {
        super.onCreate();
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mListener = new SpeechRecognitionListener();
        mHandler = new Handler(Looper.getMainLooper());
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechRecognizer.setRecognitionListener(mListener);
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        mSoundPool = new SoundPool(3, AudioManager.STREAM_ALARM, 0);
        mBeepSound = mSoundPool.load(this, R.raw.beep, 1);

        Notification n = new NotificationCompat.Builder(this)
                .setContentTitle("Hola La Nación")
                .setContentText("Activado")
                .setSmallIcon(R.drawable.icon_launcher)
                .build();

        startForeground(123, n);
    }

    protected static class IncomingHandler extends Handler {
        private WeakReference<SpeechRecognizerService> mtarget;

        IncomingHandler(SpeechRecognizerService target) {
            mtarget = new WeakReference<>(target);
        }

        @Override
        public void handleMessage(Message msg) {
            final SpeechRecognizerService target = mtarget.get();

            switch (msg.what) {
                case MSG_RECOGNIZER_START_LISTENING:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        // turn off beep sound
                        if (!target.mIsStreamSolo) {
                            target.mAudioManager.setStreamSolo(AudioManager.STREAM_VOICE_CALL, true);
                            target.mIsStreamSolo = true;
                        }
                    }
                    if (!target.mIsListening) {
                        target.mSpeechRecognizer.startListening(target.mSpeechRecognizerIntent);
                        target.mIsListening = true;
                        Log.d(TAG, "message start listening");
                    }
                    break;
                case MSG_RECOGNIZER_CANCEL:
                    if (target.mIsStreamSolo) {
                        target.mAudioManager.setStreamSolo(AudioManager.STREAM_VOICE_CALL, false);
                        target.mIsStreamSolo = false;
                    }
                    target.mSpeechRecognizer.cancel();
                    target.mIsListening = false;
                    Log.d(TAG, "message canceled recognizer");
                    break;
            }
        }
    }

    // Count down timer for Jelly Bean work around
    protected CountDownTimer mNoSpeechCountDown = new CountDownTimer(5000, 5000) {

        @Override
        public void onTick(long millisUntilFinished) {
        }

        @Override
        public void onFinish() {
            Log.d(TAG, "CountDownTimer.onFinish");
            mIsCountDownOn = false;
            restartListening();
        }
    };

    private void stopListening() {
        if (mIsCountDownOn) {
            mIsCountDownOn = false;
            mNoSpeechCountDown.cancel();
        }
        mIsListening = false;
        try {
            Message message = Message.obtain(null, MSG_RECOGNIZER_CANCEL);
            mServerMessenger.send(message);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private void startListening() {
        try {
            Message message = Message.obtain(null, MSG_RECOGNIZER_START_LISTENING);
            mServerMessenger.send(message);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mIsCountDownOn) {
            mNoSpeechCountDown.cancel();
        }
        if (mSpeechRecognizer != null) {
            mSpeechRecognizer.destroy();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");

        return mServerMessenger.getBinder();
    }

    protected class SpeechRecognitionListener implements RecognitionListener {

        @Override
        public void onBeginningOfSpeech() {
            // speech input will be processed, so there is no need for count down anymore
            if (mIsCountDownOn) {
                mIsCountDownOn = false;
                mNoSpeechCountDown.cancel();
            }
            Log.d(TAG, "onBeginingOfSpeech");
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
        }

        @Override
        public void onEndOfSpeech() {
            Log.d(TAG, "onEndOfSpeech");
        }

        @Override
        public void onError(int error) {
            restartListening();
            Log.d(TAG, "error = " + error);
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
            Log.d(TAG, params.toString());
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            Log.d(TAG, partialResults.toString());
        }

        @Override
        public void onReadyForSpeech(Bundle params) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mIsCountDownOn = true;
                mNoSpeechCountDown.start();

            }
            Log.d(TAG, "onReadyForSpeech");
        }

        @Override
        public void onResults(Bundle results) {
            if (results.containsKey("results_recognition")) {
                List<String> result = (ArrayList<String>) results.get("results_recognition");
                if (!result.isEmpty()) {
                    String phrase = result.get(0).toLowerCase();
                    Log.d(TAG, result.get(0));

                    if (contains(phrase, HOLA) || contains(phrase, NACION)) {
                        beep();
                        //
                    } else if (contains(phrase, ATRAS)) {
                        beep();
                        //
                    } else if (contains(phrase, LEER)) {
                        beep();
                        //
                    } else if (contains(phrase, PARAR)) {
                        beep();
                        //
                    }
                } else {
                    Log.d(TAG, "No results");
                }
            } else {
                Log.d(TAG, "No results");
            }
            restartListening();
        }

        @Override
        public void onRmsChanged(float rmsdB) {
        }

    }

    private boolean contains(String phrase, String key) {
        return phrase.contains(key) || key.contains(phrase);
    }

    private void beep() {
        Log.d(TAG, "beep!");
        mAudioManager.setStreamSolo(AudioManager.STREAM_VOICE_CALL, false);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSoundPool.play(mBeepSound, 1, 1, Integer.MAX_VALUE, 0, 1);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAudioManager.setStreamSolo(AudioManager.STREAM_VOICE_CALL, true);
                    }
                }, 500);
            }
        }, 200);
    }

    private void restartListening() {
        stopListening();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startListening();
            }
        }, 200);
    }
}