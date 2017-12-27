package bikesonoma.org.scbc;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class NewsPostView extends LinearLayout {
    private ImageView mImageView;
    private TextView mTitle;
    private TextView mBodyPreview;
    private TextView mTimeSincePosted;
    private Post mPost;
    private Context mContext;

    public NewsPostView(Context context, Post post) {
        super(context);
        mContext = context;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_news_post, this, true);

        mImageView = findViewById(R.id.post_image);
        mTitle = findViewById(R.id.post_title);
        mBodyPreview = findViewById(R.id.post_body_preview);
        mTimeSincePosted = findViewById(R.id.post_time);

        setPost(post);
    }
//Set an existing post to one passed through the parameter
    public void setPost(Post post) {
        mPost = post;
        mTitle.setText(post.getTitle());
        mBodyPreview.setText(post.getBodyPreview());
        mTimeSincePosted.setText(String.format(getResources().getString(R.string.time_since_posted), post.getDaysSincePosted()));

        requestLayout();
    }
}
