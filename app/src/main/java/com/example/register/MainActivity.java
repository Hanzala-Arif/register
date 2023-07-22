package com.example.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Pattern;

public class MainActivity<name> extends AppCompatActivity {
    EditText order, name, number, date, email, total, advance, balance, details;
    CheckBox checkBox,checkBox2;
    String dt, tt, ad, bl, dts,content;
    Button starts;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://calculate-app-d46ec-default-rtdb.asia-southeast1.firebasedatabase.app/");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        order = (EditText) findViewById(R.id.order);
        name = (EditText) findViewById(R.id.name);
        number = (EditText) findViewById(R.id.cnum);
        date = (EditText) findViewById(R.id.date);

        total = (EditText) findViewById(R.id.total);
        advance = (EditText) findViewById(R.id.advance);
        balance = (EditText) findViewById(R.id.balance);
        details = (EditText) findViewById(R.id.details);
checkBox= (CheckBox) findViewById(R.id.checkBox);
checkBox2=(CheckBox)findViewById(R.id.checkBox2);
checkBox.setVisibility(View.GONE);
checkBox2.setVisibility(View.GONE);
        Dexter.withContext(this)
                .withPermission(Manifest.permission.SEND_SMS)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {/* ... */}
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}

                    @Override
                    public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permissionRequest, PermissionToken permissionToken) {

                    }

                     public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken qtoken) {/* ... */}
                }).check();



    }

    public void book(View view) {
        String Order = order.getText().toString();
        String Name = name.getText().toString();
        String Number = number.getText().toString();
        String Date = date.getText().toString();
      ;
        String Total = total.getText().toString();
        String Advance = advance.getText().toString();
        String Balance = balance.getText().toString();
        String Details = details.getText().toString();
        Boolean checkbox=checkBox.isChecked();
        Boolean checkbox2=checkBox2.isChecked();
        if (Order.isEmpty() || Name.isEmpty() || Number.isEmpty() || Date.isEmpty() ||  Total.isEmpty() || Advance.isEmpty() || Balance.isEmpty() || Details.isEmpty()) {
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
                        Toast.makeText(MainActivity.this, "Already exsist", Toast.LENGTH_SHORT).show();
                    } else {
                        ref.child("Order").child(Order).child("Order-No").setValue(Order);
                        ref.child("Order").child(Order).child("Name").setValue(Name);
                        ref.child("Order").child(Order).child("Number").setValue(Number);
                        ref.child("Order").child(Order).child("Date").setValue(Date);

                        ref.child("Order").child(Order).child("Total").setValue(Total);
                        ref.child("Order").child(Order).child("Advance").setValue(Advance);
                        ref.child("Order").child(Order).child("Balance").setValue(Balance);
                        ref.child("Order").child(Order).child("Details").setValue(Details);
                        ref.child("Order").child(Order).child("Delievered").setValue(checkbox);
                        ref.child("Order").child(Order).child("OK").setValue(checkbox2);
                        Toast.makeText(MainActivity.this, "Register", Toast.LENGTH_SHORT).show();


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
                        messageBuilder.append("Order-No: ").append(Order).append("\n");
                        messageBuilder.append("Date: ").append(Date).append("\n");
                        messageBuilder.append("Total: ").append(Total).append("\n");
                        messageBuilder.append("Advance: ").append(Advance).append("\n");
                        messageBuilder.append("Balance: ").append(Balance).append("\n");
                        messageBuilder.append("Details: ").append(Details).append("\n");

                         content = messageBuilder.toString();
                        for(int i=0;i<=1;i++) {
                            try {
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(Number, null, content, null, null);
                                 Toast.makeText(MainActivity.this, "Send", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Toast.makeText(MainActivity.this, "Not Send", Toast.LENGTH_SHORT).show();
                            }
                        }

//                            }
                        openWhatsAppWithPhoneNumber(Number);




                       // sendEmail(Order, content, Email);

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
    private void openWhatsAppWithPhoneNumber(String phoneNumber) {
        try {

            String url = "https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + URLEncoder.encode(content, "UTF-8");
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any exceptions, e.g., if WhatsApp is not installed on the device
        }
    }


    private boolean whatsappinstall(){
        PackageManager packageManager=getPackageManager();
        boolean whatsappinstall;
        try{
            packageManager.getPackageInfo("com.whatsapp",PackageManager.GET_ACTIVITIES);
            whatsappinstall=true;

        }catch(PackageManager.NameNotFoundException e){
            whatsappinstall=false;
        }
        return whatsappinstall;
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


