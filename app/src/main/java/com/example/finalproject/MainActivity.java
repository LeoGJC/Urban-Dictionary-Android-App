package com.example.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;
import com.example.finalproject.R;
import com.example.lib.JsonConverter;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "cs 125";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button myButton = (Button)findViewById(R.id.search);
        EditText myInput = (EditText)findViewById(R.id.input);
        String input = myInput.getText().toString();

        myButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditText myInput = (EditText)findViewById(R.id.input);
                String input = myInput.getText().toString();
                Log.i(TAG, "onClick: " + input);
                String toUrl =
                        "https://mashape-community-urban-dictionary.p.rapidapi.com/define?term="
                                + input;
                JsonGetter(toUrl);
                final TextView define = findViewById(R.id.result);
                JsonConverter newJson = new JsonConverter();
                Log.i("cs`125", define.getText().toString());
//                String[] toSet = newJson.converter(define.getText().toString());
  //              String toReturn = "";
    //            for (int i = 0; i < toSet.length; i ++) {
      //              toReturn += toSet[i];
        //        }
          //      define.setText(toReturn);
            }
        });
    }
    public void JsonGetter(String url) {
// ...
        final TextView textView = findViewById(R.id.result);
// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

// Request a string response from the provided URL.
        Log.i("cs 125", "JsonGetter");
        Log.i("cs 125", url);
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        textView.setText(response);
                        Log.i("cs 125", "successful");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("cs 125", "Does not work");
                        Log.i(TAG, "onErrorResponse: " + error.getMessage());
                        textView.setText("Something is not right");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-RapidAPI-Host"
                        , "mashape-community-urban-dictionary.p.rapidapi.com");
                params.put("X-RapidAPI-Key"
                        , "d1aac54addmsh57dfeffbe602fdep1aefcfjsn6297c2f6f681");
                return params;
            };
            @Override
            public Map<String, String> getHeaders() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-RapidAPI-Host"
                        , "mashape-community-urban-dictionary.p.rapidapi.com");
                params.put("X-RapidAPI-Key"
                        , "d1aac54addmsh57dfeffbe602fdep1aefcfjsn6297c2f6f681");
                return params;
            };
        };
        queue.add(stringRequest);
    }
}
