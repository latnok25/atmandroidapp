package com.latnok.atmapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Transaction extends AppCompatActivity {

    private Button btn500,btn1000,btn2000,btn3000,btn5000,btn10000,btn20000;
    private String pin,amount;
    public static boolean invalid = false;
    int counter = 3;
    SharedPreferences shref;
    SharedPreferences.Editor shrefs;
    int balance,mamount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        btn500 = findViewById(R.id.m500);
        btn1000 = findViewById(R.id.m1000);
        btn2000 = findViewById(R.id.m2000);
        btn3000 = findViewById(R.id.m3000);
        btn5000 = findViewById(R.id.m5000);
        btn10000 = findViewById(R.id.m10000);
        btn20000 = findViewById(R.id.m20000);
        shref = getSharedPreferences("gokada", MODE_PRIVATE);
        shrefs = getSharedPreferences("gokada", MODE_PRIVATE).edit();

        findViewById(R.id.myvalue).setOnClickListener(
                v -> {
                    showinsertamount();
                }
        );
    }

    public void go(View v){
        Button button = (Button)v;
        amount = button.getText().toString();
        showalert();
    }

    public void showinsertamount(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Transaction.this);
        View dialog_view = getLayoutInflater().inflate(R.layout.alert,null);
        EditText yes = dialog_view.findViewById(R.id.tt);
        yes.setInputType(InputType.TYPE_CLASS_NUMBER);
        TextView heading = dialog_view.findViewById(R.id.heading);
        heading.setText("Please enter your withdrawal amount");
        amount = yes.getText().toString().trim();
        builder.setView(dialog_view);
        Button no = dialog_view.findViewById(R.id.cc);
        Button no2 = dialog_view.findViewById(R.id.dd);
        AlertDialog alertDialog = builder.create();


        no.setOnClickListener((View v) -> {
            amount = yes.getText().toString().trim();
            if (amount.isEmpty()) {
                yes.setError("Valid Name is required");
                yes.requestFocus();
            }else {
                if (Integer.parseInt(amount) %500!=0){
                    Toast.makeText(getApplicationContext(), "please enter the amount in multiples of 500 or 1000.\nThank you.",
                            Toast.LENGTH_LONG).show();
                }else {
                    amount = "NGN" + amount;
                    showalert();
                    //sendAndRequestResponsefinal();
                    alertDialog.dismiss();
                }
            }
        });

        no2.setOnClickListener(
                v -> {
                    alertDialog.dismiss();
                }
        );


        alertDialog.show();
    }

    public void showalert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Transaction.this);
        View dialog_view = getLayoutInflater().inflate(R.layout.alert,null);
        EditText yes = dialog_view.findViewById(R.id.tt);
        yes.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        pin = yes.getText().toString().trim();
        builder.setView(dialog_view);
        Button no = dialog_view.findViewById(R.id.cc);
        Button no2 = dialog_view.findViewById(R.id.dd);
        AlertDialog alertDialog = builder.create();


        no.setOnClickListener((View v) -> {
            pin = yes.getText().toString().trim();
            if (pin.isEmpty()) {
                yes.setError("Valid Name is required");
                yes.requestFocus();
            }else {
                sendAndRequesttobank();
                //sendAndRequestResponsefinal();
                alertDialog.dismiss();
            }
        });

        no2.setOnClickListener(
                v -> {
                    alertDialog.dismiss();
                }
        );


        alertDialog.show();
    }


    public static boolean isvalid(CharSequence pin){
        return pin!=null;
    }

    public void sendAndRequesttobank(){

        invalid = isvalid(pin);


        if(invalid == true){

            String piny = shref.getString("pin","");
            if (piny.equals(pin)){
                /*Toast.makeText(getApplicationContext(), "correct Password",
                    Toast.LENGTH_SHORT).show();*/
                checkifthereismoney();
            } else{
                if (counter==0){
                    wrongpin();
                }
                else {
                    counter -= 1;
                    Toast.makeText(getApplicationContext(), "incorrect Password",
                            Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    public void wrongpin(){

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage("You have entered an incorrect pin three times.\nYour card has been destroyed.\nYou will need to contact the bank to get a new one ")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });
        android.app.AlertDialog alert = builder.create();
        alert.show();

    }

    public void confirm(){

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage("Do You Confirm and agree to carry out the withdrawal of"+amount+"on this account ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(), "Transaction succesfull",
                                Toast.LENGTH_LONG).show();
                        int finalbalance = balance - mamount;
                        shrefs.putInt("balance",finalbalance);
                        shrefs.commit();
                        shrefs.apply();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(), "Transaction aborted",
                                Toast.LENGTH_LONG).show();
                    }
                });
        android.app.AlertDialog alert = builder.create();
        alert.show();

    }

    public void checkifthereismoney(){
        balance = shref.getInt("balance",0);
        String replace = amount.replace("NGN","");
        mamount = Integer.parseInt(replace);
        if (mamount>=balance){
            Toast.makeText(getApplicationContext(), "NOT ENOUGH MONEY IN ACCOUNT TO WITHDRAW",
                    Toast.LENGTH_LONG).show();
        } else if(balance - mamount <= 1000){
            Toast.makeText(getApplicationContext(), "NOT ENOUGH MONEY IN ACCOUNT TO WITHDRAW",
                    Toast.LENGTH_LONG).show();
        }
            else{
            confirm();
        }
    }


}

