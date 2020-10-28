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
import android.text.method.ScrollingMovementMethod;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.example.finalproject.R;
import com.example.lib.JsonConverter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "cs 125";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button myButton = (Button)findViewById(R.id.search);
        TextView resultText = findViewById(R.id.search);
        resultText.setMovementMethod(new ScrollingMovementMethod());
        //EditText myInput = (EditText)findViewById(R.id.input);
        //String input = myInput.getText().toString();

        myButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditText myInput = (EditText)findViewById(R.id.input);
                String input = myInput.getText().toString();
                Log.i(TAG, "onClick: " + input);
                String toUrl =
                        "https://mashape-community-urban-dictionary.p.rapidapi.com/define?term="
                                + input;
                JsonGetter(toUrl);
            }
        });
    }
    public void JsonGetter(String url) {
// ...
        final TextView resultText = findViewById(R.id.result);
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
                        TextView resultText = findViewById(R.id.result);
                        apiCallDone(response);
                        Log.i("cs 125", "successful");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("cs 125", "Does not work");
                        Log.i(TAG, "onErrorResponse: " + error.getMessage());
                        resultText.setText("Something is not right");
                    }
                }) {
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
    void apiCallDone(final String response) {
        try {
            TextView resultText = findViewById(R.id.result);

            JsonParser parser = new JsonParser();
            JsonObject jsonObj = parser.parse(response).getAsJsonObject();

            JsonArray result = jsonObj.getAsJsonArray("list");

            //final ArrayList lists = new ArrayList<>();

            int size = result.size();
            String[] toReturn = new String[size];
            for (int i = 0; i < size; i++) {
                toReturn[i] = result.get(i).getAsJsonObject().get("definition").getAsString();
            }

            String toReturn2 = "";
            for (int i = 0; i < toReturn.length; i ++) {
                toReturn2 += toReturn[i];
            }

            resultText.setText(toReturn2);
        } catch (final Exception e) {
            Log.e(TAG, "Couldn't get json from server.");
        }
    }
}
