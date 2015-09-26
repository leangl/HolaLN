package mobi.tattu.hola.utils;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import mobi.tattu.hola.model.News;

/**
 * Created by cristian on 26/09/15.
 */
public class Utils {

    public static void loadImage(News news, final ProgressBar progressBar, final ImageView imageNews) {
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
