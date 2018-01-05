package bikesonoma.org.scbc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("ViewConstructor")
public class NewsPostView extends LinearLayout {
    private ImageView mImageView;
    private TextView mTitle;
    private TextView mBodyPreview;
    private TextView mUploadDate;

    public NewsPostView(Context context, Post post) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        inflater.inflate(R.layout.view_news_post, this, true);

        mImageView = findViewById(R.id.post_image);
        mTitle = findViewById(R.id.post_title);
        mBodyPreview = findViewById(R.id.post_body_preview);
        mUploadDate = findViewById(R.id.post_time);

        setPost(post);
    }

    //Set an existing post to one passed through the parameter
    public void setPost(Post post) {
        mTitle.setText(post.getTitle());
        mBodyPreview.setText(post.getBodyPreview());
        mImageView.setImageBitmap(post.getBitmap());
        mUploadDate.setText(post.getUploadDate());

        requestLayout();
    }
}
