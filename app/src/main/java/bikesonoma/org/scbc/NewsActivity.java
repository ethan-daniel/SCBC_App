package bikesonoma.org.scbc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {

    protected ArrayList<Post> mNewsPosts = new ArrayList<>();
    protected NewsPostListAdapter mAdapter;
    protected ListView mPostLayout;

    private String url = "http://www.bikesonoma.org/";
    private ArrayList<String> mImageURLList = new ArrayList<>();
    private ArrayList<String> mTitleList = new ArrayList<>();
    private ArrayList<String> mBodyPreviewList = new ArrayList<>();
    private ArrayList<String> mUploadDateList = new ArrayList<>();
    private ArrayList<String> mArticleURLList = new ArrayList<>();

    private ProgressBar mProgressBar;
    private ImageLoader imageLoader;


    private static int numElements = 8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        mNewsPosts = new ArrayList<>();
        mAdapter = new NewsPostListAdapter(this, mNewsPosts);
        mPostLayout = findViewById(R.id.list_posts);
        mPostLayout.setAdapter(mAdapter);

        //Set onClickListener for the ListView of news articles
        mPostLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(view.getContext(), NewsArticleActivity.class);
                intent.putExtra("ImageURL", mImageURLList.get(position));
                intent.putExtra("Title", mTitleList.get(position));
                intent.putExtra("UploadDate", mUploadDateList.get(position));
                intent.putExtra("ArticleURL", mArticleURLList.get(position));
                startActivity(intent);
            }
        });
        // Create global configuration and initialize ImageLoader with this config
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
        imageLoader = ImageLoader.getInstance();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        new LoadNews(this).execute();
    }

    private static class LoadNews extends AsyncTask<Void, Void, Void> {

        private WeakReference<NewsActivity> activityReference;

        LoadNews(NewsActivity context) {
            activityReference = new WeakReference<>(context);
        }
        @Override
        protected void onPreExecute() {
        //Loading animation when app is scraping data from the website
            super.onPreExecute();
            NewsActivity activity = activityReference.get();
            if (activity == null) return;
            activity.mProgressBar = activity.findViewById(R.id.progressBarNewsList);
            activity.mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void result) {
            NewsActivity activity = activityReference.get();
            if (activity == null) return;
            activity.mPostLayout = activity.findViewById(R.id.list_posts);

            for (int i = 0; i < numElements; i++) {
                InputStream inputStream = null;
                try {
                    inputStream = new java.net.URL(activity.mImageURLList.get(i)).openStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                //Add eight posts, passing the bitmap of each image scraped from the website
                activity.addPost(new Post(bitmap, activity.mTitleList.get(i), activity.mBodyPreviewList.get(i), activity.mUploadDateList.get(i)));
            }

            activity.mProgressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                NewsActivity activity = activityReference.get();
                // Connect to the web site
                Document mBlogDocument = Jsoup.connect(activity.url).get();
                //Creates a set of all images, headers, body previews, and upload dates, and links to full articles found in all article previews
                Elements mElementImages = mBlogDocument.select("header[class=entry-header]").select("img");
                Elements mElementHeaders = mBlogDocument.select("h2[class=entry-title]").select("a");
                Elements mElementBodyPreviews = mBlogDocument.select("div[class=entry-content clearfix]").select("p");
                Elements mElementDates = mBlogDocument.select("time[class=entry-date published updated]");
                Elements mElementURLs = mBlogDocument.select("div[class=entry-content clearfix]").select("a");

                for (int i = 0; i < numElements; i++) {
                    String mImageURL = mElementImages.get(i).absUrl("abs:src");
                    String mHeader = mElementHeaders.get(i).text();
                    String bodyPrevText = mElementBodyPreviews.get(i).text();
                    //Replaces [...] with ...
                    String mBodyPreview = bodyPrevText.substring(0, bodyPrevText.length() - 3) + ("...");
                    String mDate = mElementDates.get(i).text();
                    String mArticleURL = mElementURLs.get(i).absUrl("abs:href");

                    activity.mImageURLList.add(mImageURL);
                    activity.mTitleList.add(mHeader);
                    activity.mBodyPreviewList.add(mBodyPreview);
                    activity.mUploadDateList.add(mDate);
                    activity.mArticleURLList.add(mArticleURL);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void addPost(Post post) {
        mNewsPosts.add(post);
        mAdapter.notifyDataSetChanged();
    }
}
