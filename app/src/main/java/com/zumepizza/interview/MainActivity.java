package com.zumepizza.interview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

/*
 Exercise instructions

 1) Fetch menu data using the URL given in API.java.
 2) Use fetched data to instantiate product model objects.
 2) Using product models, populate a list view using designs found in mocks/menu-mock-up.png
 3) Implement cart functionality, to allow users to add/remove items to their cart.
 4) Add cart activity to display items added to cart. Use designs found in mocks/cart-mock-up.png

 */


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
