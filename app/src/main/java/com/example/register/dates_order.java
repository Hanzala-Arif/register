package com.example.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class dates_order extends AppCompatActivity {
    EditText date;
    String Date;
    ArrayList<date> arrayList;
    RecyclerView recyclerView;
    dateadapter orderadapter;
    DatabaseReference ref;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dates_order);
        date = (EditText) findViewById(R.id.date);
        recyclerView = findViewById(R.id.display);
        ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://calculate-app-d46ec-default-rtdb.asia-southeast1.firebasedatabase.app/");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<>();
        orderadapter = new dateadapter(this, arrayList);
        recyclerView.setAdapter(orderadapter);

    }

    private void getall() {

//Toast.makeText(dates_order.this,Date,Toast.LENGTH_SHORT).show();
        ref.child("Order").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Map<String, Object> mp = (Map<String, Object>) dataSnapshot.getValue();
                    Object date = mp.get("Date");
                    Object Orders = mp.get("Order-No");
                    String ods = Orders.toString();
                    String dates = date.toString();
                    if (dates.equals(Date)) {
                        String fdate = Date.toString();
                        Query query = ref.child("Order").child(ods).orderByChild("Date").equalTo(Date);

                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String data = dataSnapshot.getKey();
                                ref.child("Order").child(data).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                                        String Order = snapshot.child("Order-No").getValue(String.class);
                                        String details = snapshot.child("Details").getValue(String.class);
                                        date od = new date(Order, details);
                                        arrayList.add(od);
                                        orderadapter.notifyDataSetChanged();

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // Handle any errors that may occur
                            }
                        });


                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//                        String Order = dataSnapshot.child("Order-No").getValue(String.class);
//                        String details = dataSnapshot.child("Details").getValue(String.class);
//                        date od = new date(Order, details);
//                        arrayList.add(od);
//                        orderadapter.notifyDataSetChanged();


    }


    public void date(View view) {
        Date = date.getText().toString();
        if (Date.isEmpty()) {
            Toast.makeText(this, "Fill it", Toast.LENGTH_SHORT).show();
        } else {
            getall();
        }
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


