package com.example.climate;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {
EditText et;
TextView tv;
TextView tv2;
TextView textView;
ImageView iv;
TextView tv4;
TextView tv5;
TextView tv8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        et = findViewById(R.id.et);
        tv = findViewById(R.id.tv);
        tv2 = findViewById(R.id.tv2);
        textView = findViewById(R.id.textView);
        iv = findViewById(R.id.iv);
        tv4 = findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);
        tv8 = findViewById(R.id.tv8);
    }


    public void get(View view) {
        String city = et.getText().toString();
        String url = "https://api.weatherbit.io/v2.0/current?city="+city+"&key=6119b5a789c84a9ba10bd1f6acc20597";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override

            public void onResponse(JSONObject response) {
                try {

                    JSONArray data = response.getJSONArray("data");
                    JSONObject obj1 = data.getJSONObject(0);
                    JSONObject weather = obj1.getJSONObject("weather");
                    String desc = weather.getString("description");
                    String  icid = weather.getString("icon");
                    String temp = obj1.getString("temp");
                    String pod = obj1.getString("pod");
                    String wind = obj1.getString("wind_spd");
                    String dire = obj1.getString("wind_cdir");
                    String url2 = "https://www.weatherbit.io/static/img/icons/"+icid+".png";

                    Glide.with(MainActivity.this)
                            .load(url2)
                            .apply(new RequestOptions().override(500,500))
                            .into(iv);

                    tv8.setText("Temp");
                   tv.setText(temp+" °c");
                    tv2.setText(desc);
                    if(pod.equals("d"))
                    {
                        tv5.setText("DAY");
                    }
                    if(pod.equals("n"))
                    {
                        tv5.setText("NIGHT");
                    }
                    float ws = Float.parseFloat(wind);
                    DecimalFormat df = new DecimalFormat("#.0");
                    float wsp = Float.valueOf(df.format(ws));

                    tv4.setText("Wind : "+ wsp +" m/s - "+ dire );

                }

                catch (JSONException e)
                {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        },new Response.ErrorListener() {
            @Override

            public void onErrorResponse(VolleyError error) {
                Glide.with(MainActivity.this)
                        .load(R.drawable.saddy)
                        .apply(new RequestOptions().override(300,300))
                        .into(iv);
                tv2.setText(null);
                tv4.setText(null);
                tv5.setText(null);
                tv.setText("??");
                Toast.makeText(MainActivity.this, "enter valid location", Toast.LENGTH_SHORT).show();
            }


        });
        queue.add(request);

    }
}