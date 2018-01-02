package bikesonoma.org.scbc;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class Post {

    //Properties of a post
    private Bitmap mBitmap;
    private String mTitle;
    private String mBodyPreview;
    private String mDateUploaded;

    public Post() {
        mBitmap = null;
        mTitle = "";
        mBodyPreview = "";
    }

    public Post(Bitmap bmp, String title, String bodyPreview, String dateUploaded) {
        mBitmap = bmp;
        mTitle = title;
        mBodyPreview = bodyPreview;
        mDateUploaded = dateUploaded;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String str) {
        mTitle = str;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public String getBodyPreview() {
        return mBodyPreview;
    }

    public void setBodyPreview(String str) {
        mBodyPreview = str;
    }

    public String getUploadDate() { return mDateUploaded; }

    public void setUploadDate (String days) { mDateUploaded = days; }
}
