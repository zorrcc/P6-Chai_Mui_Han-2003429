package com.utar.individualassignment1;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class ByRatio extends AppCompatActivity {
    Double[] RatioAmount = new Double[20];
    Double RatioTotalSum = 0.0;
    int RatioCount = 0;
    String sendResult="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_ratio);


        Button RatioBreakDownSubmit = findViewById(R.id.ratioSubmit);
        Button RatioWhatsapp = findViewById(R.id.ratioWhatsapp);



        RatioBreakDownSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText RatioBreakTotalAmount = findViewById(R.id.RatioTotalAmount); //total amount
                EditText RatioBreakPeopleNumber = findViewById(R.id.RatioPeopleNumber); //no.people

                //if total amount or no. people not been input, it will notify user to key in
                if (RatioBreakTotalAmount.getText().toString().isEmpty() || RatioBreakPeopleNumber.getText().toString().isEmpty()) {
                    TextView RatioResult = findViewById(R.id.RatioResult);
                    RatioResult.setText("There have no enough information given to show the result. Please key in all information.");
                } else {
                    Double TotalAmount = Double.parseDouble(RatioBreakTotalAmount.getText().toString());
                    int PeopleNumber = Integer.parseInt(RatioBreakPeopleNumber.getText().toString());

                    for (int i = 0; i < PeopleNumber; i++) {
                        // Create an AlertDialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(ByRatio.this);
                        builder.setTitle("Enter Ratio " + (i + 1));

                        // Create an EditText field
                        final EditText input = new EditText(ByRatio.this);
                        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                        builder.setView(input);

                        // Set positive button action
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String enteredNumber = input.getText().toString();
                                if (!enteredNumber.isEmpty()) {
                                    Double number = Double.parseDouble(enteredNumber);
                                    RatioAmount[RatioCount] = number;
                                    RatioCount++;
                                    RatioTotalSum += number; // Add the entered number to the total sum

                                    //count ratio
                                    if(RatioCount == PeopleNumber){
                                        Double[] resultNumber = new Double[RatioCount];
                                        for(int k=0;k < RatioCount; k++){
                                            resultNumber[k] = ((RatioAmount[k]/RatioTotalSum) * TotalAmount);
                                        }
                                        String resultstr = "Total bill RM" + TotalAmount + ", number of people " + PeopleNumber + ", each person to pay by ratio (";
                                        for(int j=0; j < RatioCount; j++){
                                            if(j!=RatioCount-1){
                                                resultstr = resultstr + RatioAmount[j] + ", ";
                                            }else {
                                                resultstr = resultstr + RatioAmount[j] + ") the amount RM";
                                            }
                                        }
                                        for(int l=0; l < RatioCount; l++){
                                            if(l!=RatioCount-1){
                                                resultstr = resultstr + String.format("%.2f",resultNumber[l]) + ", RM";
                                            }else {
                                                resultstr = resultstr + String.format("%.2f",resultNumber[l]) + ".";
                                            }
                                        }
                                        TextView RatioResult = findViewById(R.id.RatioResult);
                                        RatioResult.setText(resultstr);
                                        sendResult = resultstr;

                                        //set back the initial value
                                        for(int q=0; q< RatioCount; q++){
                                            resultNumber[q]=0.0;
                                        }
                                        RatioTotalSum=0.0;
                                        RatioCount = 0;
                                        for(int n = 0;n <20;n++){
                                            RatioAmount[n] = 0.0;
                                        }
                                    }

                                }
                            }

                        });

                        // Set negative button action
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        // Show the AlertDialog
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }

                }


            }

        });
        //when click on the whatsapp button, it will redirect the result to whatsapp
        RatioWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(sendResult);
            }
        });

    }

    public void sendMessage(String message)
    {

        PackageManager pm = ByRatio.this.getPackageManager();
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
            Toast.makeText(ByRatio.this, "WhatsApp not Installed", Toast.LENGTH_SHORT).show();
        }
    }


}