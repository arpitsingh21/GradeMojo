package iband.grademojo;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by dell on 22-11-2017.
 */

public class Singleton {
    public static final String TAG = Singleton.class
            .getSimpleName();
    private static Singleton AppSingleton;
    private RequestQueue mRequestQueue;
    private static Context mContext;

    private Singleton(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();

    }

    public static synchronized Singleton getInstance(Context context) {
        if (AppSingleton == null) {
            AppSingleton = new Singleton(context);
        }
        return AppSingleton;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
