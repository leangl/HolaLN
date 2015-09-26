package mobi.tattu.hola.ui.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import mobi.tattu.hola.R;
import mobi.tattu.hola.data.DataStore;
import mobi.tattu.hola.model.Category;
import mobi.tattu.hola.model.News;
import mobi.tattu.hola.ui.NewsDetailActivity;

/**
 * Created by cristian on 25/09/15.
 */
public class MainFragment extends BaseFragment {
    private ArrayList<News> mNewsArrayList;
    private TextToSpeech mTextToSpeech;

    private Locale mLocale = new Locale("es_ar","ES_AR");


    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mTextToSpeech != null){
            mTextToSpeech.shutdown();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null, false);
        LinearLayout containerCard = (LinearLayout) view.findViewById(R.id.cardview_container);

        mTextToSpeech = new TextToSpeech(this.getBaseActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(TextToSpeech.ERROR != status){
                    mTextToSpeech.setLanguage(mLocale);
                }
            }
        });
        createCardNews(containerCard);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsArrayList = new ArrayList<>();
        ArrayList aux = new ArrayList();
        aux.add(createNews("River gano 2 a 0 a Liga de Quito", "En su cancha river se hizo fuerte y derroto con contundencia a un tibio equipo peruano",
                "bbvbvbcbcvbcvbv bcvb cvb cv b ewr w rwe rw er we rwe rew r ewr ewr we r ew rew rew r ewr ew rwe rc vb vc b vcbcvb cvb vc bvc b vc bc vb vcb cv b vcb vc bcv b vc bvc bcv", Category.DEPORTES));
        aux.add(createNews("Hoy se estrena la película truman", "Otro estreno argentino con darin a la cabeza, se encamina a ser otro record de taquilla",
                "bbvbvbcbcvbcvbv bcvb cvb cv bc  werewr ewr we rwe r ew r ew rew rew r ew rew r wervb vc b vcbcvb cvb vc bvc b vc bc vb vcb cv b vcb vc bcv b vc bvc bcv", Category.ESPECTACULOS));
        aux.add(createNews("Scioli se niega a debatir", "Scioli se niega a debatir en el primer debate presidencial de la historia argentina",
                "bbvbvbcbcvbcvbv bcvb cvb cv bc v wer ew r ewr wer we r wer ew r ewrb vc b vcbcvb cvb vc bvc b vc bc vb vcb cv b vcb vc bcv b vc bvc bcv", Category.POLITICA));
        aux.add(createNews("El Papa Francisco en el congreso de Estados Unidos", "Es la primera vez en la histora que un papa habla en el congreso de los estados unidos",
                "bbvbvbcbcvbcvbv bcvb cvb cv bc vbwer we r ewr wer we rwe vc b vcbcvb cvb vc bvc b vc bc vb vcb cv b vcb vc bcv b vc bvc bcv", Category.EL_MUNDO));
        searchNewsCategory(aux);


    }

    private void searchNewsCategory(List<News> newsList) {
        final ArrayList<Category> categories = new ArrayList<>(DataStore.getInstance().getAll(Category.class));
        int size = categories.size();
        int sizeNew = newsList.size();
        for (int i = 0; i < size; i++) {
            Category category = categories.get(i);
            for (int it = 0; it < sizeNew; it++) {
                News news = newsList.get(it);
                if (category.equals(news.category)) {
                    mNewsArrayList.add(news);
                }
            }

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

    private void createCardNews(LinearLayout container) {
        int size = mNewsArrayList.size();
        for (int i = 0; i < size; i++) {
            final News news  = mNewsArrayList.get(i);
            View view = LayoutInflater.from(getBaseActivity()).inflate(R.layout.cardview_news, null, false);
            final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.cardview_progressbar);
            final ImageView imageNews = (ImageView) view.findViewById(R.id.cardview_image_news);
            TextView titleTextView = (TextView) view.findViewById(R.id.textview_title);
            TextView subTitleTextView = (TextView) view.findViewById(R.id.textview_subtitle);
            ImageView imageSpeech = (ImageView) view.findViewById(R.id.cardview_icon_speech);
            imageSpeech.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textToSpeech(news);
                }
            });

            loadImage(news, progressBar, imageNews);
            titleTextView.setText(news.title);
            subTitleTextView.setText(news.subTitle);
            view.setTag(news);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mTextToSpeech != null && mTextToSpeech.isSpeaking()) {
                        mTextToSpeech.stop();
                        stopEqualizerView();
                    }
                    Intent intent = new Intent(getBaseActivity(), NewsDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(NewsDetailActivity.EXTRA_NEWS, news);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
            });
            container.addView(view);
        }


    }

    private void textToSpeech(News news) {
        if(mTextToSpeech.isSpeaking()){
            mTextToSpeech.stop();
            stopEqualizerView();
        }else{
            startEqualizerView();
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                mTextToSpeech.speak(news.toString(), TextToSpeech.QUEUE_FLUSH,null);
            }else{
                mTextToSpeech.speak(news.toString(),TextToSpeech.QUEUE_FLUSH,null,news.title);
            }

        }
    }

    private void loadImage(News news, final ProgressBar progressBar, final ImageView imageNews) {
        // Load image, decode it to Bitmap and return Bitmap to callback
        ImageLoader.getInstance().displayImage(news.imageUri, imageNews, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                progressBar.setVisibility(View.GONE);
                imageNews.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
    }
}
