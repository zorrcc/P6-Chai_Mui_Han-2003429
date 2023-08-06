package com.utar.individualassignment1;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class EqualBreak extends AppCompatActivity {
    String sendResult = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equal_break);


        Button equalBreakSubmit = findViewById(R.id.equalbreaksubmit);
        Button sendWhatsapp = findViewById(R.id.EqualWS);


        equalBreakSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText eqBreakTotalAmount = findViewById(R.id.equalbreakinputamount); //total amount
                EditText eqBreakPeopleNumber = findViewById(R.id.eqpeoplenumber); //no. people

                //if total amount or no. people not been input, it will notify user to key in
                if(eqBreakTotalAmount.getText().toString().isEmpty() || eqBreakPeopleNumber.getText().toString().isEmpty()){
                    TextView resultText = findViewById(R.id.EqualBreakResult);
                    resultText.setText("There have no enough information given to show the result. Please key in all information.");
                }else {
                    Double TotalAmount = Double.parseDouble(eqBreakTotalAmount.getText().toString());
                    int PeopleNumber = Integer.parseInt(eqBreakPeopleNumber.getText().toString());
                    Double result = TotalAmount / PeopleNumber;
                    TextView resultText = findViewById(R.id.EqualBreakResult);
                    resultText.setText("Total bill " + TotalAmount + ", number of people " + PeopleNumber + ", each person to pay RM" + result);
                    sendResult = resultText.getText().toString();
                }
            }
        });
        //when click on the whatsapp button, it will redirect the result to whatsapp
        sendWhatsapp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        sendMessage(sendResult);
                    }
                });



    }

    public void sendMessage(String message)
    {

        PackageManager pm = EqualBreak.this.getPackageManager();
        try {
            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            String text = message;

            // Get the correct package name dynamically
            String packageName = "com.whatsapp";
            List<ResolveInfo> matches = pm.queryIntentActivities(waIntent, 0);
            for (ResolveInfo info : matches) {
                if (info.activityInfo.packageName.toLowerCase().startsWith(packageName)) {
                    packageName = info.activityInfo.packageName;
                    break;
                }
            }

            waIntent.setPackage(packageName);
            waIntent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(waIntent, "Share with"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(EqualBreak.this, "WhatsApp not Installed", Toast.LENGTH_SHORT).show();
        }
    }



}