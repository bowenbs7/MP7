package edu.illinois.cs.cs125.mp7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MP7:Main";
    static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity();
    }

    //Used volley to get the JSON data from pricecharting.com as jsonResult
    String test;
    public void startAPI(final String gameInput) {
        requestQueue = Volley.newRequestQueue(this);
        if (gameInput == null) {
            Log.d(TAG, "null input error");
        }
        String url = "https://www.pricecharting.com/api/product?t=e858b303b42db5af194bd8dc53bdfcf5e098ff42&q=" + gameInput;
        JsonObjectRequest jsonResult = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        test = response.toString();
                        //mTextView.setText("Response: " + response.toString());
                        Log.d(TAG, test);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "An error occured " + error.toString());
                        // TODO: Handle error
                    }
                });
        requestQueue.add(jsonResult);
    }

    //Sets up the activity_main.xml page
    public void mainActivity() {
        setContentView(R.layout.activity_main);

        final EditText productName = findViewById(R.id.productName);
        final String input = productName.getText().toString();

        final Button seeResults = findViewById(R.id.seeResults);
        seeResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.d(TAG, "See results button clicked");
                secondaryActivity();
                startAPI(input);
            }
        });

        final Button letsGo = findViewById(R.id.letsGo);
        letsGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.d(TAG, "Let's go button clicked");
                setContentView(R.layout.activity_tertiary);
                tertiaryActivity();
            }
        });
    }
    //Sets up the activity_secondary.xml page
    public void secondaryActivity() {
        setContentView(R.layout.activity_secondary);

        final Button searchAgain = findViewById(R.id.seeResults);
        searchAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.d(TAG, "Search again button clicked");
            }
        });

        final Button mainPage = findViewById(R.id.mainPage);
        mainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.d(TAG, "Main page button clicked");
                mainActivity();
            }
        });
    }
    //Sets up the activity_tertiary.xml page
    public void tertiaryActivity() {
        setContentView(R.layout.activity_tertiary);
        final Button calculate = findViewById(R.id.calculate);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.d(TAG, "Calculate button clicked");
            }
        });

        final Button mainPage = findViewById(R.id.mainPage);
        mainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.d(TAG, "Main page button clicked");
                mainActivity();
            }
        });
    }
}
