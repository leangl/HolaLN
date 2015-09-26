package mobi.tattu.hola.ui.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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

import mobi.tattu.hola.R;
import mobi.tattu.hola.data.DataStore;
import mobi.tattu.hola.model.Category;
import mobi.tattu.hola.model.News;
import mobi.tattu.hola.service.NewsReader;
import mobi.tattu.hola.ui.NewsDetailActivity;
import mobi.tattu.utils.Tattu;

/**
 * Created by cristian on 25/09/15.
 */
public class MainFragment extends BaseFragment {
    private ArrayList<News> mNewsArrayList;


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
        Tattu.bus().unregister(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null, false);
        LinearLayout containerCard = (LinearLayout) view.findViewById(R.id.cardview_container);


        createCardNews(containerCard);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsArrayList = new ArrayList<>();
        searchNewsCategory(NewsReader.getInstance().getNews());
        Tattu.register(this);


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


    private void createCardNews(LinearLayout container) {
        int size = mNewsArrayList.size();
        for (int i = 0; i < size; i++) {
            final News news = mNewsArrayList.get(i);
            View view = LayoutInflater.from(getBaseActivity()).inflate(R.layout.cardview_news, null, false);
            final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.cardview_progressbar);
            final ImageView imageNews = (ImageView) view.findViewById(R.id.cardview_image_news);
            TextView titleTextView = (TextView) view.findViewById(R.id.textview_title);
            TextView subTitleTextView = (TextView) view.findViewById(R.id.textview_subtitle);

            loadImage(news, progressBar, imageNews);
            titleTextView.setText(news.title);
            subTitleTextView.setText(news.subTitle);
            view.setTag(news);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NewsReader.getInstance().stopSpeech();

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
