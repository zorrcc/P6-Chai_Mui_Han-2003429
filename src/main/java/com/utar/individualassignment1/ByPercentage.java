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


public class ByPercentage extends AppCompatActivity {
    Double[] amount = new Double[20];
    int totalSum = 0;
    int count = 0;
    String sendResult="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_percentage);

        Button PercentageSubmit = findViewById(R.id.percentageSubmit);
        Button PercentageWhatsapp = findViewById(R.id.percentageWhatsapp);


        PercentageSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                EditText PercentageTotalAmount = findViewById(R.id.PercentageTotalAmount); //total amount
                EditText PercentagePeopleNumber = findViewById(R.id.PercentagePeopleNumber); //no. people

                //if total amount or no. people not been input, it will notify user to key in
                if (PercentageTotalAmount.getText().toString().isEmpty() || PercentagePeopleNumber.getText().toString().isEmpty()) {
                    TextView PercentageResult = findViewById(R.id.PercentageResult);
                    PercentageResult.setText("There have no enough information given to show the result. Please key in all information.");
                } else {
                    Double TotalAmount = Double.parseDouble(PercentageTotalAmount.getText().toString());
                    int PeopleNumber = Integer.parseInt(PercentagePeopleNumber.getText().toString());

                    //loop for get the percentage
                    for (int i = 0; i < PeopleNumber; i++) {
                        // Create an AlertDialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(ByPercentage.this);
                        builder.setTitle("Enter percentage " + (i + 1));

                        // Create an EditText field
                        final EditText input = new EditText(ByPercentage.this);
                        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                        builder.setView(input);

                        // Set positive button action
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String enteredNumber = input.getText().toString();
                                if (!enteredNumber.isEmpty()) {
                                    Double number = Double.parseDouble(enteredNumber);
                                    amount[count] = number;
                                    count++;
                                    totalSum += number; // Add the entered number to the total sum

                                    if(count == PeopleNumber){
                                        //if the sum of percentage is not 100, it will show error message
                                        if(totalSum != 100){
                                            TextView PercentageResult = findViewById(R.id.PercentageResult);
                                            PercentageResult.setText("The percentage that given is not 100%");
                                        }else{
                                            Double[] resultNumber = new Double[count];
                                            for(int k=0;k < count; k++){

                                                resultNumber[k] = ((amount[k]/100) * TotalAmount);

                                            }
                                            //output correct result
                                            String resultstr = "Total bill RM" + TotalAmount + ", number of people " + PeopleNumber + ", each person to pay by percentage (";
                                            for(int j=0; j < count; j++){
                                                if(j!=count-1){
                                                    resultstr = resultstr + amount[j] + "%, ";
                                                }else {
                                                    resultstr = resultstr + amount[j] + "%) the amount RM";
                                                }
                                            }
                                            for(int l=0; l < count; l++){
                                                if(l!=count-1){
                                                    resultstr = resultstr + resultNumber[l] + ", RM";
                                                }else {
                                                    resultstr = resultstr + resultNumber[l] + ".";
                                                }
                                            }
                                            TextView PercentageResult = findViewById(R.id.PercentageResult);
                                            PercentageResult.setText(resultstr);
                                            sendResult = resultstr;
                                            for(int q=0; q< count; q++){
                                                resultNumber[q]=0.0;
                                            }
                                        }

                                        //set back to initial value
                                        totalSum=0;
                                        count = 0;
                                        for(int n = 0;n <20;n++){
                                            amount[n] = 0.0;
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
        PercentageWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(sendResult);
            }
        });


    }

    public void sendMessage(String message)
    {

        PackageManager pm = ByPercentage.this.getPackageManager();
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
            Toast.makeText(ByPercentage.this, "WhatsApp not Installed", Toast.LENGTH_SHORT).show();
        }
    }




}