package edu.illinois.cs.cs125.mp7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MP7:Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public int test() {
        return 0;
    }

    /**
     * Comparing prices to see discounts.
     * Calculate price of cart.
     */
}
