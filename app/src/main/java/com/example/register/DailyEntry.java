package com.example.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DailyEntry extends AppCompatActivity {
    Spinner spinner;
    EditText rorder,details,date;
    String value;
    String [] country={"Select Option","Daily","Outside","Lahore"};
    DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://calculate-app-d46ec-default-rtdb.asia-southeast1.firebasedatabase.app/");
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_entry);
        rorder=(EditText)findViewById(R.id.rorder);
        details=(EditText) findViewById(R.id.details);
        date=(EditText) findViewById(R.id.date);
        spinner=(Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(DailyEntry.this, android.R.layout.simple_spinner_item,country);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                value=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void book(View view) {
        String Rorder=rorder.getText().toString();
        String Details=details.getText().toString();
        String Date=date.getText().toString();
        if(Rorder.isEmpty()||Details.isEmpty()||Date.isEmpty()){
            Toast.makeText(this, "Fill this Completely", Toast.LENGTH_SHORT).show();
        }

        ref.child("Daily_Orders").child(value).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ref.child("Daily_Orders").child(value).child(Rorder).child("Rorder").setValue(Rorder);
                ref.child("Daily_Orders").child(value).child(Rorder).child("Details").setValue(Details);
                ref.child("Daily_Orders").child(value).child(Rorder).child("Date").setValue(Date);
                Toast.makeText(DailyEntry.this, "Successfully Enter", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),dailydetails.class).putExtra("value",value));
                rorder.setText("");
                details.setText("");
                date.setText("");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void CheckOrder(MenuItem item) {
        startActivity(new Intent(getApplicationContext(),check.class));
    }

    public void AllOrders(MenuItem item) {
        startActivity(new Intent(getApplicationContext(),orders.class));
    }

    public void DailyOrders(MenuItem item) {
        startActivity(new Intent(getApplicationContext(),dates_order.class));
    }

    public void ReBook(MenuItem item) {
        startActivity(new Intent(getApplicationContext(),update.class));
    }
    public void account(MenuItem item) {
        startActivity(new Intent(getApplicationContext(),DailyEntry.class));
    }

    public void accounts(MenuItem item) {
        startActivity(new Intent(getApplicationContext(),dailydetails.class));
    }

}