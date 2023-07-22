package com.example.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void book(View view) {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

    public void check(View view) {
        startActivity(new Intent(getApplicationContext(),check.class));
    }

    public void Date(View view) {
        startActivity(new Intent(getApplicationContext(),dates_order.class));
    }

    public void All_order(View view) {
        startActivity(new Intent(getApplicationContext(),orders.class));
    }

    public void Daily_Entry(View view) {
        startActivity(new Intent(getApplicationContext(),DailyEntry.class));
    }

    public void Daily_details(View view) {
        startActivity(new Intent(getApplicationContext(),dailydetails.class));
    }
}