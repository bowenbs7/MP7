package edu.illinois.cs.cs125.mp7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ActionBar.LayoutParams;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MP7:Main";
    static RequestQueue requestQueue;
    int count;
    double totalPrice;
    static String json;
    static String newPrice;
    static String retailBuy;
    static String retailSell;
    static String gameName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity();
    }

    //Used volley to get the JSON data from pricecharting.com as jsonResult
    public void startAPI(final String gameInput) {
        Log.d(TAG, "starting API call");
        requestQueue = Volley.newRequestQueue(this);
        if (gameInput == null) {
            Log.d(TAG, "null input error");
        }
        String url = "https://www.pricecharting.com/api/product?t=e858b303b42db5af194bd8dc53bdfcf5e098ff42&q=" + gameInput;
        JsonObjectRequest jsonResult = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        json = response.toString();
                        newPrice = String.valueOf(priceGetter(json,"new-price"));
                        retailBuy = String.valueOf(priceGetter(json,"retail-new-buy"));
                        retailSell = String.valueOf(priceGetter(json,"retail-new-sell"));
                        gameName = titleGetter(json);
                        Log.d(TAG, json + "//" + newPrice + "//" + retailBuy + "//" + retailSell + "//" + gameName);
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

        final Button seeResults = findViewById(R.id.seeResults);
        seeResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.d(TAG, "See results button clicked");
                String input = productName.getText().toString();
                Log.d(TAG, newPrice + "?");
                if (count == 0) {
                    startAPI(input);
                    count++;
                    final Button seeResults = findViewById(R.id.seeResults);
                    seeResults.setText("See Results!");
                } else {
                    count = 0;
                    secondaryActivity();
                }
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

        final EditText productName = findViewById(R.id.productName);

        final TextView offeredPrice = findViewById(R.id.offeredPrice);
        offeredPrice.setText("$ " + newPrice);

        final TextView retailedPrice = findViewById(R.id.retailedPrice);
        retailedPrice.setText("$ " + retailSell);

        final TextView sellPrice = findViewById(R.id.sellPrice);
        sellPrice.setText("$ " + retailBuy);

        final TextView gameTitle = findViewById(R.id.gameTitle);
        gameTitle.setText(gameName);

        final Button searchAgain = findViewById(R.id.seeResults);
        searchAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.d(TAG, "Search again button clicked");
                String input = productName.getText().toString();
                if (count == 0) {
                    startAPI(input);
                    count++;
                    final Button seeResults = findViewById(R.id.seeResults);
                    seeResults.setText("See Results!");
                } else {
                    count = 0;
                    secondaryActivity();
                }
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

        final LinearLayout boxLayout = findViewById(R.id.checkBox);
        final EditText addGame = findViewById(R.id.editText);
        final TextView cartTotal = findViewById(R.id.wishlist);

        final Button addCheckBox = findViewById(R.id.addGame);
        addCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.d(TAG, "Add check box button clicked");
                final LayoutParams lparams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                CheckBox newCheck = new CheckBox(MainActivity.this);
                newCheck.setText(addGame.getText().toString());
                newCheck.setLayoutParams(lparams);
                boxLayout.addView(newCheck);
            }
        });

        final Button calculate = findViewById(R.id.calculate);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.d(TAG, "Calculate button clicked");
                Log.d(TAG, String.valueOf(boxLayout.getChildCount()));
                calculateCart();
                if (count == 0) {
                    count++;
                    final Button calculateWishList = findViewById(R.id.calculate);
                    calculateWishList.setText("Let's Go!");
                } else {
                    count = 0;
                    double value = Double.parseDouble(retailSell);
                    totalPrice += value;
                    Log.d(TAG, String.valueOf(totalPrice));
                    cartTotal.setText("$ " + String.valueOf(totalPrice));
                    totalPrice = 0.0;
                }
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
    public double priceGetter(final String json, final String cat) {
        JsonParser parser = new JsonParser();
        JsonObject result = parser.parse(json).getAsJsonObject();
        double price = result.get(cat).getAsDouble();
        return price/100;
    }
    public String titleGetter(final String json) {
        JsonParser parser = new JsonParser();
        JsonObject result = parser.parse(json).getAsJsonObject();
        String title = result.get("product-name").getAsString();
        return title;
    }

    public void calculateCart() {
        final LinearLayout boxLayout = findViewById(R.id.checkBox);
        for (int i = 0; i < boxLayout.getChildCount(); i++) {
            View v = boxLayout.getChildAt(i);
            if (v instanceof CheckBox) {
                if (((CheckBox) v).isChecked()) {
                    startAPI(((CheckBox) v).getText().toString());
                    if (count == 0) {
                        Log.d(TAG, "first time");
                        count++;
                    } else {
                        Log.d(TAG, "next times");
                        double value = Double.parseDouble(retailSell);
                        totalPrice += value;
                    }
                } else {
                    Log.d(TAG, "not checked");
                }
            }
        }
        count = 0;
        Log.d(TAG, String.valueOf(totalPrice));
    }
}
