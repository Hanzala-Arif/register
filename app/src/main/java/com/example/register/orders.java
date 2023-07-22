package com.example.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class orders extends AppCompatActivity {
    ArrayList<order> arrayList;
    RecyclerView recyclerView;
    orderadapter orderadapter;
    List<order> filteredList;
    DatabaseReference ref;
EditText orderno;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        recyclerView = findViewById(R.id.orders);
        orderno=findViewById(R.id.orderno);
        ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://calculate-app-d46ec-default-rtdb.asia-southeast1.firebasedatabase.app/");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<>();
        filteredList=new ArrayList<>();
        orderadapter = new orderadapter(this, arrayList);
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
        for (order item : arrayList) {
            if (item.getOrdernum().equals(text)) {
                filteredList.add(item);
            }
        }
        recyclerView.setAdapter(new orderadapter(getApplicationContext(), (ArrayList<order>) filteredList));
        orderadapter.notifyDataSetChanged();
    }

    private void getall() {

            ref.child("Order").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String Order = dataSnapshot.child("Order-No").getValue(String.class);
                        String Csname = dataSnapshot.child("Name").getValue(String.class);
                        String details = dataSnapshot.child("Details").getValue(String.class);
                        boolean checkBox = dataSnapshot.child("Delievered").getValue(Boolean.class);
                        boolean checkBox2 = dataSnapshot.child("OK").getValue(Boolean.class);
                        order od = new order(Order, details, Csname, checkBox, checkBox2);


                        arrayList.add(od);
                        orderadapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }




}

