package mobi.tattu.hola.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import mobi.tattu.hola.R;
import mobi.tattu.hola.model.News;
import mobi.tattu.hola.ui.fragments.NewsDetailFragment;

/**
 * Created by cristian on 25/09/15.
 */
public class NewsDetailActivity extends BaseActivity {

    public static final String EXTRA_NEWS = "extra_news";
    private News mNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNews = (News) getIntent().getSerializableExtra(EXTRA_NEWS);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        showFragment(NewsDetailFragment.newInstance(mNews), false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                shareTextUrl();
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    private void shareTextUrl() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.putExtra(Intent.EXTRA_TEXT, mNews.urlNews);
        share.setType("text/plain");
        startActivity(Intent.createChooser(share, getString(R.string.share_news_label)));
    }
}
