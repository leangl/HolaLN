package mobi.tattu.hola.service;

import android.content.Context;
import android.media.AudioManager;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import mobi.tattu.hola.model.Category;
import mobi.tattu.hola.model.News;
import mobi.tattu.utils.Tattu;

/**
 * Created by cristian on 26/09/15.
 */
public class NewsReader {
    private static NewsReader ourInstance = new NewsReader();
    private ArrayList<News> mNewsArrayList;
    private TextToSpeech mTextToSpeech;
    private Context mContext;
    private Locale mLocale = new Locale("es_ar", "ES_AR");

    public static NewsReader getInstance() {
        return ourInstance;
    }

    private NewsReader() {
    }

    public void start(Context context) {
        this.mContext = context;
        this.mNewsArrayList = new ArrayList<>();
        this.mNewsArrayList.add(createNews("River gano 2 a 0 a Liga de Quito.", "",
                "En su cancha, River se hizo fuerte y derrotó con contundencia a un tibio equipo peruano.", Category.DEPORTES));
        this.mNewsArrayList.add(createNews("Hoy se estrena la película truman.", "",
                "Otro estreno Argentino con Darín a la cabeza, se encamina a ser otro record de taquilla..", Category.ESPECTACULOS));
        this.mNewsArrayList.add(createNews("Scioli se niega a debatir.", "",
                "Scioli se niega a debatir en el primer debate presidencial de la historia argentina..", Category.POLITICA));
        this.mNewsArrayList.add(createNews("El Papa Francisco en el congreso de Estados Unidos.", "",
                "Es la primera vez en la histora que un Papa habla en el congreso de los Estados Unidos.", Category.EL_MUNDO));

        this.mTextToSpeech = new TextToSpeech(this.mContext, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                mTextToSpeech.setLanguage(mLocale);

                mTextToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                    @Override
                    public void onStart(String s) {
                        if (s.equals("null")) {
                            Tattu.post(new SpeakStarted());
                        } else {
                            Tattu.post(new NewsStarted());
                        }
                    }
                    @Override
                    public void onDone(String s) {
                        if (s.equals("null")) {
                            Tattu.post(new SpeakEnded());
                        } else {
                            Tattu.post(new NewsEnded());
                        }
                    }

                    @Override
                    public void onError(String s) {
                    }
                });
            }
        });
    }

    public ArrayList<News> getNews() {
        return mNewsArrayList;
    }

    /**
     * @return true si paro el servicio , sino devuevel false
     */
    public boolean stopSpeech() {
        if (mTextToSpeech.isSpeaking()) {
            mTextToSpeech.stop();
            return true;
        } else {
            return false;
        }
    }

    public void read(News news) {
        read(news, false);
    }

    public void read(News news, boolean title) {
        speak(title ? news.title : news.content, news.title);
    }

    public void speak(String text, String unique) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(TextToSpeech.Engine.KEY_PARAM_STREAM, String.valueOf(AudioManager.STREAM_ALARM));
        hashMap.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, unique != null ? unique : "null");
        mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, hashMap);
    }

    private News createNews(String title, String subtitle, String contenido, Category category) {
        News news = new News();
        news.title = title;
        news.subTitle = subtitle;
        news.category = category;
        news.content = contenido;
        news.imageUri = "http://cdn9.staztic.com/app/a/900/900030/la-nacion-110-l-280x280.png";
        return news;
    }

    public class NewsStarted {
    }

    public class NewsEnded {
    }

    public class SpeakStarted {

    }

    public class SpeakEnded {

    }

}
