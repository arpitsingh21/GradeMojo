package iband.grademojo;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StudentDetail extends AppCompatActivity {
     String  id,stname;
    TextView name,attendance,performance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);
        name=(TextView)findViewById(R.id.student_Name);
        attendance=(TextView)findViewById(R.id.student_Attandance);
        performance=(TextView)findViewById(R.id.student_Performance);
        id=getIntent().getStringExtra("id");
        stname=getIntent().getStringExtra("name");
        if (id!=null&&stname!=null)
        getStudentDetails();
    }

    private void getStudentDetails() {
        StringRequest strreq = new StringRequest(Request.Method.POST,
                URL_HUB.student_info,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String Response) {
                        // get response
                        Log.v("Result", Response);


                        try {
                            JSONObject object = new JSONObject(Response).getJSONObject("data");
                            name.setText(stname);
                            attendance.setText(object.getJSONObject("attendance").getString("avg"));
                            performance.setText(object.getJSONObject("performance").getString("avg"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
                Snackbar.make(getWindow().getDecorView().getRootView(), "Error Fetching Details", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("access_token", PrefManager.getAuthToken());
                params.put("student_id", id);
                return params;
            }
        };

        Singleton.getInstance(getApplicationContext()).addToRequestQueue(strreq, "Studentlist");

    }
}
