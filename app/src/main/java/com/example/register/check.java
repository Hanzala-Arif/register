package com.example.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.snapshot.StringNode;

public class check extends AppCompatActivity {
EditText order,details;
CheckBox checkBox,checkBox2;
Button rebook,delievery;
    DatabaseReference ref= FirebaseDatabase.getInstance().getReferenceFromUrl("https://calculate-app-d46ec-default-rtdb.asia-southeast1.firebasedatabase.app/");
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        order=(EditText) findViewById(R.id.order);
        details=(EditText) findViewById(R.id.details);
        checkBox2=(CheckBox) findViewById(R.id.checkBox2);
        checkBox=(CheckBox) findViewById(R.id.checkBox);
        rebook=(Button) findViewById(R.id.starts);
        delievery=(Button)findViewById(R.id.startss);
details.setEnabled(false);
    }

    public void check(View view) {
        String Order=order.getText().toString();
        String Details=details.getText().toString();
        if(Order.isEmpty()){
            Toast.makeText(this, "Enter Order Number", Toast.LENGTH_SHORT).show();
            order.setError("Empty");
        }
        else{
            ref.child("Order").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                        if(snapshot1.getKey().equals(Order)){
                           String Name=snapshot1.child("Name").getValue(String.class).toString();
                            String Number=snapshot1.child("Number").getValue(String.class);
                            String Date=snapshot1.child("Date").getValue(String.class);
                            String Total=snapshot1.child("Total").getValue(String.class);
                            String Advance=snapshot1.child("Advance").getValue(String.class);
                            String Balance=snapshot1.child("Balance").getValue(String.class);
                            String Details=snapshot1.child("Details").getValue(String.class);


                            StringBuilder messageBuilder = new StringBuilder();
                            messageBuilder.append("Name: ").append(Name).append("\n");
                            messageBuilder.append("Number: ").append(Number).append("\n");
                            messageBuilder.append("Date: ").append(Date).append("\n");
                            messageBuilder.append("Total: ").append(Total).append("\n");
                            messageBuilder.append("Advance: ").append(Advance).append("\n");
                            messageBuilder.append("Balance: ").append(Balance).append("\n");
                            messageBuilder.append("Details: ").append(Details).append("\n");

                            String message=messageBuilder.toString();
                            details.setText(message);
                            details.setEnabled(false);
                            Toast.makeText(check.this, "Exsist", Toast.LENGTH_SHORT).show();

                        }
                        else{
                            Toast.makeText(check.this, "Not Exsist", Toast.LENGTH_SHORT).show();
                        }


                    }






                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }



    public void deliever(View view) {
        String Order=order.getText().toString();
        boolean isChecked= checkBox.isChecked();

        if(isChecked){
ref.child("Order").addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if(snapshot.hasChild(Order)){
            ref.child("Order").child(Order).child("Delievered").setValue(isChecked);
            Toast.makeText(check.this, "Order is Delievered", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});
        }
        else{
            ref.child("Order").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(Order)){
                        ref.child("Order").child(Order).child("Delievered").setValue(false);
                        Toast.makeText(check.this, "Order is Not Delievered", Toast.LENGTH_SHORT).show();
                    }


                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
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


    public void OK(View view) {
        String Order=order.getText().toString();
        boolean isChecked= checkBox2.isChecked();

        if(isChecked){
            ref.child("Order").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(Order)){
                        ref.child("Order").child(Order).child("OK").setValue(isChecked);
                        Toast.makeText(check.this, "Order is OK", Toast.LENGTH_SHORT).show();
                    }


                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        else{
            ref.child("Order").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(Order)){
                        ref.child("Order").child(Order).child("OK").setValue(false);
                        Toast.makeText(check.this, "Order is Not OK", Toast.LENGTH_SHORT).show();
                    }


                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public void account(MenuItem item) {
        startActivity(new Intent(getApplicationContext(),DailyEntry.class));
    }

    public void accounts(MenuItem item) {
        startActivity(new Intent(getApplicationContext(),dailydetails.class));
    }
}