package com.example.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class update extends AppCompatActivity {
    EditText order, name, number, date, email, total, advance, balance, details;
    Button update,rebook;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://calculate-app-d46ec-default-rtdb.asia-southeast1.firebasedatabase.app/");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        order = (EditText) findViewById(R.id.order);
        name = (EditText) findViewById(R.id.name);
        number = (EditText) findViewById(R.id.cnum);
        date = (EditText) findViewById(R.id.date);
        email = (EditText) findViewById(R.id.email);
        total = (EditText) findViewById(R.id.total);
        advance = (EditText) findViewById(R.id.advance);
        balance = (EditText) findViewById(R.id.balance);
        details = (EditText) findViewById(R.id.details);
        update=(Button)findViewById(R.id.starts);
        rebook=(Button)findViewById(R.id.start);
        update.setEnabled(false);
      name.setEnabled(false);
      number.setEnabled(false);
      date.setEnabled(false);
      email.setEnabled(false);
      total.setEnabled(false);
      advance.setEnabled(false);
      balance.setEnabled(false);
      details.setEnabled(false);
    }

    public void update(View view) {
        String Order = order.getText().toString();
        String Name = name.getText().toString();
        String Number = number.getText().toString();
        String Date = date.getText().toString();
        String Email = email.getText().toString();
        String Total = total.getText().toString();
        String Advance = advance.getText().toString();
        String Balance = balance.getText().toString();
        String Details = details.getText().toString();
        if (Order.isEmpty() || Name.isEmpty() || Number.isEmpty() || Date.isEmpty() || Email.isEmpty() || Total.isEmpty() || Advance.isEmpty() || Balance.isEmpty() || Details.isEmpty()) {
            Toast.makeText(this, "Fill it Completely", Toast.LENGTH_SHORT).show();
            order.setError("Empty");
            name.setError("Empty");
            number.setError("Empty");
            date.setError("Empty");
            total.setError("Empty");
            advance.setError("Empty");
            balance.setError("Empty");
            details.setError("Empty");

        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
            email.setError("Invalid email");
        } else {
            ref.child("Order").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(Order)) {


                        ref.child("Order").child(Order).child("Name").setValue(Name);
                        ref.child("Order").child(Order).child("Number").setValue(Number);
                        ref.child("Order").child(Order).child("Date").setValue(Date);
                        ref.child("Order").child(Order).child("Email").setValue(Email);
                        ref.child("Order").child(Order).child("Total").setValue(Total);
                        ref.child("Order").child(Order).child("Advance").setValue(Advance);
                        ref.child("Order").child(Order).child("Balance").setValue(Balance);
                        ref.child("Order").child(Order).child("Details").setValue(Details);
                        Toast.makeText(update.this, "Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), dates_order.class));
                        finish();
                        order.setText("");
                        name.setText("");
                        number.setText("");
                        date.setText("");
                        email.setText("");
                        total.setText("");
                        advance.setText("");
                        balance.setText("");
                        details.setText("");


                        StringBuilder messageBuilder = new StringBuilder();
                        messageBuilder.append("Date: ").append(Date).append("\n");
                        messageBuilder.append("Total: ").append(Total).append("\n");
                        messageBuilder.append("Advance: ").append(Advance).append("\n");
                        messageBuilder.append("Balance: ").append(Balance).append("\n");
                        messageBuilder.append("Details: ").append(Details).append("\n");
                        String content = messageBuilder.toString();
                        sendEmail(Order, content, Email);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


    }
    public void sendEmail(String subject, String dt, String email) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, dt);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose email:"));
    }

    public void Rebook(View view) {
        String Order = order.getText().toString();
        if (Order.isEmpty()) {
            Toast.makeText(this, "Enter Order Number", Toast.LENGTH_SHORT).show();
            order.setError("Empty");
        } else {
            ref.child("Order").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        if (snapshot1.getKey().equals(Order)) {
                            String Name = snapshot1.child("Name").getValue(String.class).toString();
                            String Number = snapshot1.child("Number").getValue(String.class);
                            String Date = snapshot1.child("Date").getValue(String.class);
                            String Email = snapshot1.child("Email").getValue(String.class);
                            String Total = snapshot1.child("Total").getValue(String.class);
                            String Advance = snapshot1.child("Advance").getValue(String.class);
                            String Balance = snapshot1.child("Balance").getValue(String.class);
                            String Details = snapshot1.child("Details").getValue(String.class);

                            name.setText(Name);
                            number.setText(Number);
                            date.setText(Date);
                            total.setText(Total);
                            email.setText(Email);
                            advance.setText(Advance);
                            balance.setText(Balance);
                            details.setText(Details);
                            name.setEnabled(true);
                            number.setEnabled(true);
                            date.setEnabled(true);
                            email.setEnabled(true);
                            total.setEnabled(true);
                            advance.setEnabled(true);
                            balance.setEnabled(true);
                            details.setEnabled(true);
                            update.setEnabled(true);
                            break;

                        } else {
                            Toast.makeText(update.this, "Not Exsist", Toast.LENGTH_SHORT).show();
                        }
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