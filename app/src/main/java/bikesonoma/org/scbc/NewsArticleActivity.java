package bikesonoma.org.scbc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

public class NewsArticleActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    ImageLoader imageLoader;
    private String fullBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_complete);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
        imageLoader = ImageLoader.getInstance();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();
        new LoadNews(this, intent).execute();

    }

    private static class LoadNews extends AsyncTask<Void, Void, Void> {
        private Intent mIntent;
        private Bundle extras;
        private WeakReference<NewsArticleActivity> activityReference;

        LoadNews(NewsArticleActivity context, Intent intent) {
            activityReference = new WeakReference<>(context);
            mIntent = intent;
            extras = mIntent.getExtras();
        }

        @Override
        protected void onPreExecute() {
            //Loading animation when app is scraping data from the website
            super.onPreExecute();
            NewsArticleActivity activity = activityReference.get();
            if (activity == null) return;
            activity.mProgressBar = activity.findViewById(R.id.progressBarNewsArticle);
            activity.mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void result) {
            NewsArticleActivity activity = activityReference.get();
            if (activity == null) return;
            InputStream inputStream = null;
            try {
                inputStream = new java.net.URL(mIntent.getStringExtra("ImageURL")).openStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ImageView mImageView = activity.findViewById(R.id.post_image_full);
            TextView mTitle = activity.findViewById(R.id.post_title_full);
            TextView mUploadDate = activity.findViewById(R.id.post_time_full);
            TextView mBodyText = activity.findViewById(R.id.post_body_full);
            mImageView.setImageBitmap(bitmap);
            mTitle.setText(mIntent.getStringExtra("Title"));
            mUploadDate.setText(mIntent.getStringExtra("UploadDate"));
            mBodyText.setText(activity.fullBody);

            activity.mProgressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                NewsArticleActivity activity = activityReference.get();
                // Connect to the web site
                String url = extras.getString("ArticleURL");
                Document mBlogDocument = Jsoup.connect(url).get();
                //Creates a set of all images, headers, body previews, and upload dates, and links to full articles found in all article previews
                Elements mBodyParagraphs = mBlogDocument.select("div[class=entry-content clearfix]").select("p");
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < mBodyParagraphs.size(); i++) {
                    String mBodyParagraph = mBodyParagraphs.get(i).text();
                    builder.append(mBodyParagraph).append("\n \n");
                }
                activity.fullBody = builder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
