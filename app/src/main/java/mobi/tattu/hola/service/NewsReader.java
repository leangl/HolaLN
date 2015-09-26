package mobi.tattu.hola.service;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;

import java.util.ArrayList;
import java.util.Locale;

import mobi.tattu.hola.model.Category;
import mobi.tattu.hola.model.News;

/**
 * Created by cristian on 26/09/15.
 */
public class NewsReader {
    private static NewsReader ourInstance = new NewsReader();
    private ArrayList<News> mNewsArrayList;
    private TextToSpeech mTextToSpeech;
    private Context mContext;
    private Locale mLocale = new Locale("es_ar", "ES_AR");
    private int mIndexNews = 0;

    public static NewsReader getInstance() {
        return ourInstance;
    }

    private NewsReader() {
    }

    public void start(Context context) {

        this.mContext = context;
        this.mNewsArrayList = new ArrayList<>();
        this.mNewsArrayList.add(createNews("River gano 2 a 0 a Liga de Quito", "En su cancha river se hizo fuerte y derroto con contundencia a un tibio equipo peruano",
                "bbvbvbcbcvbcvbv bcvb cvb cv b ewr w rwe rw er we rwe rew r ewr ewr we r ew rew rew r ewr ew rwe rc vb vc b vcbcvb cvb vc bvc b vc bc vb vcb cv b vcb vc bcv b vc bvc bcv", Category.DEPORTES));
        this.mNewsArrayList.add(createNews("Hoy se estrena la película truman", "Otro estreno argentino con darin a la cabeza, se encamina a ser otro record de taquilla",
                "bbvbvbcbcvbcvbv bcvb cvb cv bc  werewr ewr we rwe r ew r ew rew rew r ew rew r wervb vc b vcbcvb cvb vc bvc b vc bc vb vcb cv b vcb vc bcv b vc bvc bcv", Category.ESPECTACULOS));
        this.mNewsArrayList.add(createNews("Scioli se niega a debatir", "Scioli se niega a debatir en el primer debate presidencial de la historia argentina",
                "bbvbvbcbcvbcvbv bcvb cvb cv bc v wer ew r ewr wer we r wer ew r ewrb vc b vcbcvb cvb vc bvc b vc bc vb vcb cv b vcb vc bcv b vc bvc bcv", Category.POLITICA));
        this.mNewsArrayList.add(createNews("El Papa Francisco en el congreso de Estados Unidos", "Es la primera vez en la histora que un papa habla en el congreso de los estados unidos",
                "bbvbvbcbcvbcvbv bcvb cvb cv bc vbwer we r ewr wer we rwe vc b vcbcvb cvb vc bvc b vc bc vb vcb cv b vcb vc bcv b vc bvc bcv", Category.EL_MUNDO));
        this.mTextToSpeech = new TextToSpeech(this.mContext, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                mTextToSpeech.setLanguage(mLocale);
            }
        });
    }

    public ArrayList<News> getListNews() {
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

    public void readNewsSpeech(News news) {
        speech(news.toString(), news.title);

    }

    public void readResumeNewsSpeech() {
        News news = mNewsArrayList.get(mIndexNews);
        mIndexNews++;
        speech(news.getResumeNews(), news.title);
    }

    public void backNewsSpeech() {
        if (mIndexNews > 0) {
            News news = mNewsArrayList.get(--mIndexNews);
            speech(news.getResumeNews(), news.title);
        }
    }

    private void speech(String text, String unique) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        } else {
            mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, unique);
        }

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

    public interface NewsReaderListener {
        void onSuccessSpeech();
    }
}
