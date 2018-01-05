package bikesonoma.org.scbc;

import android.graphics.Bitmap;

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

    Post(Bitmap bmp, String title, String bodyPreview, String dateUploaded) {
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

    Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    String getBodyPreview() {
        return mBodyPreview;
    }

    public void setBodyPreview(String str) {
        mBodyPreview = str;
    }

    String getUploadDate() {
        return mDateUploaded;
    }

    public void setUploadDate(String days) {
        mDateUploaded = days;
    }
}
