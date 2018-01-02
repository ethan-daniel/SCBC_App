 package bikesonoma.org.scbc;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

 public class NewsActivity extends AppCompatActivity {

     protected ArrayList<Post> mNewsPosts = new ArrayList<>();
     protected NewsPostListAdapter mAdapter;
     protected ListView mPostLayout;

     private String url = "http://www.bikesonoma.org/";
     private ArrayList<String> mImageURLList = new ArrayList<>();
     private ArrayList<String> mTitleList = new ArrayList<>();
     private ArrayList<String> mBodyPreviewList = new ArrayList<>();
     private ArrayList<String> mUploadDateList = new ArrayList<>();

     private ImageLoader imageLoader;

     private static int numElements = 8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources resources = getResources();
        mNewsPosts = new ArrayList<>();

        setContentView(R.layout.activity_news);
        mAdapter = new NewsPostListAdapter(this, mNewsPosts);
        mPostLayout = findViewById(R.id.list_posts);
        mPostLayout.setAdapter(mAdapter);
        // Create global configuration and initialize ImageLoader with this config
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
        imageLoader = ImageLoader.getInstance();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        new LoadNews().execute();
    }
    private class LoadNews extends AsyncTask<Void, Void, Void>{

        /*@Override
        protected void onPreExecute() {
        //Loading animation when app is scraping data from the website
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(NewsActivity.this);
            mProgressDialog.setTitle("SCBC");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }*/

        @Override
        protected void onPostExecute(Void result) {

            mPostLayout = findViewById(R.id.list_posts);

            for (int i = 0; i < numElements; i++) {
                InputStream inputStream = null;
                try {
                    inputStream = new java.net.URL(mImageURLList.get(i)).openStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                //Add eight posts, passing the bitmap of each image scraped from the website
                addPost(new Post(bitmap, mTitleList.get(i), mBodyPreviewList.get(i), mUploadDateList.get(i)));
            }

            //mProgressDialog.dismiss();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                // Connect to the web site
                Document mBlogDocument = Jsoup.connect(url).get();
                //Creates a set of all images, headers, body previews, and upload dates found in articles
                Elements mElementImage = mBlogDocument.select("header[class=entry-header]").select("img");
                Elements mElementHeader = mBlogDocument.select("h2[class=entry-title]").select("a");
                Elements mElementBodyPreview = mBlogDocument.select("div[class=entry-content clearfix]").select("p");
                Elements mElementDate = mBlogDocument.select("time[class=entry-date published updated]");

                for (int i = 0; i < numElements; i++) {
                    String mImageURL = mElementImage.get(i).absUrl("abs:src");
                    String mHeader = mElementHeader.get(i).text();
                    String bodyPrevText = mElementBodyPreview.get(i).text();
                    //Replaces [...] with ...
                    String mBodyPreview = bodyPrevText.substring(0, bodyPrevText.length() - 3) + ("...");
                    String mDate = mElementDate.get(i).text();

                    mImageURLList.add(mImageURL);
                    mTitleList.add(mHeader);
                    mBodyPreviewList.add(mBodyPreview);
                    mUploadDateList.add(mDate);
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
