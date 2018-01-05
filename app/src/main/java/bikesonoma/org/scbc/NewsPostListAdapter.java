package bikesonoma.org.scbc;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.List;

public class NewsPostListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Post> mPostList;

    NewsPostListAdapter(Context context, List<Post> posts) {
        mPostList = new ArrayList<>();
        mPostList = posts;
        mContext = context;
    }

    //Required methods for implementing BaseAdapter
    @Override
    public int getCount() {
        return mPostList.size();
    }

    @Override
    public Object getItem(int i) {
        return mPostList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return new NewsPostView(mContext, mPostList.get(i));
    }
}
