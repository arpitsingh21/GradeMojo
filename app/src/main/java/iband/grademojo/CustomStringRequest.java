package iband.grademojo;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Arpit on 30-08-2017.
 * Custom request to make String as input..
 */

public class CustomStringRequest extends Request<String> {

    private Response.Listener<String> listener;
    private int versionCode = BuildConfig.VERSION_CODE;
    private String versionName = BuildConfig.VERSION_NAME;

    public CustomStringRequest(String url, Response.Listener<String> responseListener,
                               Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.listener = responseListener;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(jsonString,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(String response) {
        listener.onResponse(response);
    }

    @Override
    public Map<String, String> getHeaders() throws com.android.volley.AuthFailureError {

        Map<String, String> headers = new HashMap<>();
        headers.put("app", "android");
        headers.put("version", String.valueOf(versionCode));
        headers.put("Authorization", PrefManager.getAuthToken());
        return headers;
    }
}
