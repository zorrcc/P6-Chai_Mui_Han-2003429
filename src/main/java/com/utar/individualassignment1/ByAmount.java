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
import java.io.File;
import java.util.List;

public class ByAmount extends AppCompatActivity {
    Double[] AmountAmount = new Double[20];
    Double AmountTotalSum = 0.0;
    int AmountCount = 0;
    String sendResult="";
    private String filename = "Bill.txt";
    private String filepath = "MyBill";
    File myExternalFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_amount);

        Button amountSubmit = findViewById(R.id.amountSubmit);
        Button amountWhatsapp = findViewById(R.id.amountWhatsapp);



        amountSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText AmountTotalAmount = findViewById(R.id.AmountTotalAmount); //total amount
                EditText AmountPeopleNumber = findViewById(R.id.AmountPeopleNumber); //no. people

                //if total amount or no. people not been input, it will notify user to key in
                if (AmountTotalAmount.getText().toString().isEmpty() || AmountPeopleNumber.getText().toString().isEmpty()) {
                    TextView AmountResult = findViewById(R.id.AmountResult);
                    AmountResult.setText("There have no enough information given to show the result. Please key in all information.");
                } else {
                    Double TotalAmount = Double.parseDouble(AmountTotalAmount.getText().toString());
                    int PeopleNumber = Integer.parseInt(AmountPeopleNumber.getText().toString());

                    //loop to let user to enter amount
                    for (int i = 0; i < PeopleNumber; i++) {
                        // Create an AlertDialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(ByAmount.this);
                        builder.setTitle("Enter Amount " + (i + 1));

                        // Create an EditText field
                        final EditText input = new EditText(ByAmount.this);
                        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                        builder.setView(input);

                        // Set positive button action
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Double enteredNumber = Double.parseDouble(input.getText().toString());
                                if (!enteredNumber.toString().isEmpty()) {
                                    Double number = enteredNumber;
                                    AmountAmount[AmountCount] = number;
                                    AmountCount++;
                                    AmountTotalSum += number; // Add the entered number to the total sum

                                    if(AmountCount == PeopleNumber) {
                                        if (!TotalAmount.equals(AmountTotalSum)) { //check the sum is equal to total amount or not
                                            TextView AmountResult = findViewById(R.id.AmountResult);
                                            AmountResult.setText("Total amount that input are not same with the bill's total amount.");
                                        } else {
                                            String resultstr = "Total bill RM" + TotalAmount + ", number of people " + PeopleNumber + ", each person to pay by amount (";
                                            for (int j = 0; j < AmountCount; j++) {
                                                if (j != AmountCount - 1) {
                                                    resultstr = resultstr + AmountAmount[j] + ", ";
                                                } else {
                                                    resultstr = resultstr + AmountAmount[j] + ") the amount RM" + TotalAmount;
                                                }
                                            }

                                            TextView AmountResult = findViewById(R.id.AmountResult);
                                            AmountResult.setText(resultstr);
                                            sendResult = resultstr;

                                            //set back to initial value
                                            AmountTotalSum = 0.0;
                                            AmountCount = 0;
                                            for (int n = 0; n < 20; n++) {
                                                AmountAmount[n] = 0.0;
                                            }
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
        amountWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(sendResult);
            }
        });


    }

    public void sendMessage(String message)
    {

        PackageManager pm = ByAmount.this.getPackageManager();
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
            Toast.makeText(ByAmount.this, "WhatsApp not Installed", Toast.LENGTH_SHORT).show();
        }
    }


}