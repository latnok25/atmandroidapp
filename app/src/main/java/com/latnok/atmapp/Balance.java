package com.latnok.atmapp;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Balance extends AppCompatActivity {

    private Button btn;
    private String pin,balance;
    public static boolean invalid = false;
    int counter = 3;
    int isbalance;
    SharedPreferences shref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        btn = findViewById(R.id.m500);
        shref = getSharedPreferences("gokada", MODE_PRIVATE);


        balance = String.valueOf(shref.getInt("balance",0));

    }

    public void go(View v){
//        Button button = (Button)v;
//        amount = button.getText().toString();
        showalert();
    }

    public void showalert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Balance.this);
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
                confirm();
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
        builder.setMessage("Your balance is NGN"+balance+ "on this account")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        // finish();
                    }
                });
        android.app.AlertDialog alert = builder.create();
        alert.show();


    }

}
