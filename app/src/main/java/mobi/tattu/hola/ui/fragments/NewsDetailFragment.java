package mobi.tattu.hola.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import mobi.tattu.hola.R;
import mobi.tattu.hola.model.News;
import mobi.tattu.hola.utils.Utils;

/**
 * Created by cristian on 25/09/15.
 */
public class NewsDetailFragment extends BaseFragment {

    private static String BUNDLE_NEWS = "bundle_news";
    private News mNews;

    public static NewsDetailFragment newInstance(News news) {

        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_NEWS, news);
        NewsDetailFragment fragment = new NewsDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mNews = (News) getArguments().get(BUNDLE_NEWS);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_detail, container, false);
        TextView titleTextView = (TextView) view.findViewById(R.id.textview_title);
        TextView subtitleTextView = (TextView) view.findViewById(R.id.textview_subtitle);
        TextView contentTextView = (TextView) view.findViewById(R.id.textview_content);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview_news);
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar2);

        Utils.loadImage(mNews,progressBar,imageView);

        titleTextView.setText(mNews.title);
        subtitleTextView.setText(mNews.subTitle);
        contentTextView.setText(mNews.content);
        return view;
    }
}
