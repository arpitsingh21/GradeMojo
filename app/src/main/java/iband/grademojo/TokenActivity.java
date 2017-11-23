package iband.grademojo;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class TokenActivity extends AppCompatActivity {

    RelativeLayout fetch;
    TextView tokenText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token);
        tokenText = (TextView) findViewById(R.id.token_text);
        fetch = (RelativeLayout) findViewById(R.id.fetch_layout);

        PrefManager.initialise(this);

        String token = PrefManager.getAuthToken();

        if (token != null) {
            tokenText.setText(token);
        }

        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getToken();
            }
        });
    }

    private void getToken() {

//        JSONObject postJson = new JSONObject("");
        JsonObjectRequest tokenRequest = new JsonObjectRequest(Request.Method.POST, URL_HUB.tokenurl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    String token=response.getJSONObject("data").getString("access_token");

                    if(token!=null){

                        tokenText.setText(token);
                        PrefManager.setAuthToken(token);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error != null) {
                    Log.v("The error occured  ", error.toString());
                    Snackbar.make(getWindow().getDecorView().getRootView(), "Error Occured", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }else
                    Log.v("Error", "The error occured while following user is null ");
            }
        });

        Singleton.getInstance(getApplicationContext()).addToRequestQueue(tokenRequest, "Bundlelist");


    }
}
