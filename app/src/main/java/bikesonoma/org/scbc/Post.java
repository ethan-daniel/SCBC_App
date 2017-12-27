package bikesonoma.org.scbc;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class Post{

    //Properties of a post
    private ImageView mImageView;
    private String mTitle;
    private String mBodyPreview;
    private int mDaysSincePosted;

    public Post() {
        mImageView = null;
        mTitle = "";
        mBodyPreview = "";
    }

    public Post(ImageView image, String title, String bodyPreview, int daysSince) {
        mImageView = image;
        mTitle = title;
        mBodyPreview = bodyPreview;
        mDaysSincePosted = daysSince;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String str) {
        mTitle = str;
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public void setImageView(ImageView imageView) {
        mImageView = imageView;
    }

    public String getBodyPreview() {
        return mBodyPreview;
    }

    public void setBodyPreview(String str) {
        mBodyPreview = str;
    }

    public int getDaysSincePosted() {
        return mDaysSincePosted;
    }

    public void setDaysSincePosted(int days) {
        mDaysSincePosted = days;
    }
}
