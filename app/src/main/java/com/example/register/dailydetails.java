package com.example.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class dailydetails extends AppCompatActivity {

    ArrayList<daily> arrayList;
    RecyclerView recyclerView;
    dailyadapter orderadapter;
    List<daily> filteredList;
    DatabaseReference ref;
    EditText orderno;
    String value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dailydetails);
        recyclerView = findViewById(R.id.orders);
        orderno=findViewById(R.id.orderno);
//        value=getIntent().getStringExtra("value");
        ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://calculate-app-d46ec-default-rtdb.asia-southeast1.firebasedatabase.app/");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<>();
        filteredList=new ArrayList<>();
        orderadapter = new dailyadapter(this,arrayList);
        recyclerView.setAdapter(orderadapter);
        orderno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filteredList.clear();
                String ods=orderno.getText().toString();
                if(ods.isEmpty()){
                    recyclerView.setAdapter(orderadapter);
                    orderadapter.notifyDataSetChanged();
                }
                else{
                    Filter(ods.toString());
                }
            }
        });

        getall();



    }
    private void Filter(String text) {
        for (daily item : arrayList) {
            if (item.getDate().equals(text)) {
                filteredList.add(item);
            }
        }
        recyclerView.setAdapter(new dailyadapter(getApplicationContext(),(ArrayList<daily>) filteredList));
        orderadapter.notifyDataSetChanged();
    }

    private void getall() {

        ref.child("Daily_Orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                   String value=dataSnapshot.getKey();
                   ref.child("Daily_Orders").child(value).addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                           for(DataSnapshot dt:snapshot.getChildren()){
                          String Order=dt.child("Rorder").getValue(String.class);
                          String Details=dt.child("Details").getValue(String.class);
                               String Date=dt.child("Date").getValue(String.class);
                               daily od = new daily(Order, Details,Date);
                               arrayList.add(od);
                               orderadapter.notifyDataSetChanged();
                           }
                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {

                       }
                   });



//                    Log.e("Order",value);
//                   String Order=dataSnapshot.child(value).getValue(String.class);
//                    String details=dataSnapshot.child(value).child("Details").getValue(String.class);
//                    Toast.makeText(dailydetails.this, Order, Toast.LENGTH_SHORT).show();
//                    daily od = new daily(Order,details);
//                    arrayList.add(od);
//                    orderadapter.notifyDataSetChanged();
//
                }

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
