package iband.grademojo;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentListActivity extends AppCompatActivity {

    RecyclerView studentRV;
    List<StudentListProvider> list;
    StudentListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        PrefManager.initialise(this);
        studentRV = (RecyclerView) findViewById(R.id.studentRecycler);
        studentRV.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new StudentListAdapter(this, list);


        getStudentList();


    }

    private void getStudentList() {
        StringRequest strreq = new StringRequest(Request.Method.POST,
                URL_HUB.student_list_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String Response) {
                        // get response
                        Log.v("Result", Response);


                        try {
                            JSONObject object = new JSONObject(Response).getJSONObject("data");

                            JSONArray resultarray = object.getJSONArray("students");
                            for (int i = 0; i < resultarray.length();i++){
                                JSONObject result= resultarray.getJSONObject(i);
                                String name= result.getString("name");
                                String roll= result.getString("roll");
                                String id=result.getString("student_id");
                                String gender= result.getString("gender");
                                list.add(new StudentListProvider(id,roll,name,gender));
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        studentRV.setAdapter(adapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
                Snackbar.make(studentRV, e.getMessage(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("access_token", PrefManager.getAuthToken());

                return params;
            }
        };

        Singleton.getInstance(getApplicationContext()).addToRequestQueue(strreq, "Studentlist");

    }
}
