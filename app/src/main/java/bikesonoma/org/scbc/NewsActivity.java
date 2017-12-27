 package bikesonoma.org.scbc;

import android.content.res.Resources;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

 public class NewsActivity extends AppCompatActivity {

     protected ArrayList<Post> mNewsPosts = new ArrayList<Post>();
     protected NewsPostListAdapter mAdapter;
     protected ListView mPostLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources resources = getResources();
        mNewsPosts = new ArrayList<Post>();

        setContentView(R.layout.activity_news);
        mAdapter = new NewsPostListAdapter(this, mNewsPosts);
        mPostLayout = findViewById(R.id.list_posts);
        mPostLayout.setAdapter(mAdapter);

        //Create two posts to test the layout
        ImageView testImage = new ImageView(this);
        testImage.setImageResource(R.mipmap.ic_launcher);
        Post testPost1 = new Post(testImage, "This is the first test post!", "This is the body preview of the article", 1);
        addPost(testPost1);

        Post testPost2 = new Post(testImage, "This is the second test post!", "This is the body preview of the article", 2);
        addPost(testPost2);
    }

     public void addPost(Post post) {
        mNewsPosts.add(post);
        mAdapter.notifyDataSetChanged();
     }

 }
