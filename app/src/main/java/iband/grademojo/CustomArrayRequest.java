package iband.grademojo;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Arpit on 30-08-2017.
 * Custom request to make JSONArray as input..
 */

public class CustomArrayRequest extends Request<JSONArray> {

    private Response.Listener<JSONArray> listener;
    private int versionCode = BuildConfig.VERSION_CODE;
    private String versionName = BuildConfig.VERSION_NAME;
    private Map<String, String> params;
    public CustomArrayRequest(String url, Map<String, String> params,
                              Response.Listener<JSONArray> reponseListener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.listener = reponseListener;
        this.params = params;

    }


    @Override
    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONArray(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }
    protected Map<String, String> getParams()
            throws com.android.volley.AuthFailureError {

        return params;
    }

    @Override
    protected void deliverResponse(JSONArray response) {
        listener.onResponse(response);
    }

    @Override
    public Map<String, String> getHeaders()
            throws com.android.volley.AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        headers.put("app", "android");
        headers.put("version", String.valueOf(versionCode));
        headers.put("Authorization", PrefManager.getAuthToken());
        return headers;
    }
}
