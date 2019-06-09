package com.latnok.atmapp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
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

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Transaction extends AppCompatActivity implements LocationListener {

    private Button btn500, btn1000, btn2000, btn3000, btn5000, btn10000, btn20000;
    private String pin, amount;
    public static boolean invalid = false;
    LocationManager locationManager;
    Location location;
    int counter = 3;
    SharedPreferences shref;
    SharedPreferences.Editor shrefs;
    int balance, mamount;
    String machinelocation;
    int finalbalance;


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

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            requestMultiplePermissions();
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);

        findViewById(R.id.myvalue).setOnClickListener(
                v -> {
                    showinsertamount();
                }
        );
    }

    @Override
    public void onLocationChanged(Location location){
        machinelocation = String.valueOf(location.getLongitude()+location.getLatitude());
        Log.d("LOcation",machinelocation);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

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
                yes.setError("Valid Amount is required");
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
                yes.setError("Valid Pin is required");
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
                         finalbalance = balance - mamount;
                        shrefs.putInt("balance",finalbalance);
                        shrefs.commit();
                        shrefs.apply();

                        showreceipt();
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
        } else if((balance - mamount) <= 1000){
            Toast.makeText(getApplicationContext(), "NOT ENOUGH MONEY IN ACCOUNT TO WITHDRAW",
                    Toast.LENGTH_LONG).show();
        }
            else{
            confirm();
        }
    }

    public void showreceipt(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Transaction.this);
        View dialog_view = getLayoutInflater().inflate(R.layout.receipt,null);
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c);
        String time = String.valueOf(c.getHours())+":"+String.valueOf(c.getMinutes());
        String account ="224****5678";
        String transaction = "Withdrawal";
        String endbalance = String.valueOf(balance);
        String availablebalance = String.valueOf(finalbalance);
        TextView mdate = dialog_view.findViewById(R.id.date);
        TextView mtime = dialog_view.findViewById(R.id.time);
        TextView mlocation = dialog_view.findViewById(R.id.location);
        TextView mtransaction = dialog_view.findViewById(R.id.transaction);
        TextView maccount = dialog_view.findViewById(R.id.account);
        TextView mamount = dialog_view.findViewById(R.id.amount);
        TextView mendbal = dialog_view.findViewById(R.id.endingbalance);
        TextView mavalbal = dialog_view.findViewById(R.id.availablebalance);
        mdate.setText(formattedDate);
        mtime.setText(time);
        if (machinelocation == null){
            machinelocation = "WE CANT ACCESS YOUR LOCATION";
        }

        mlocation.setText(machinelocation);

        mtransaction.setText(transaction);
        maccount.setText(account);
        mamount.setText(amount);
        mendbal.setText(endbalance);
        mavalbal.setText(availablebalance);
        builder.setView(dialog_view);
        Button no = dialog_view.findViewById(R.id.btn);
        AlertDialog alertDialog = builder.create();


        no.setOnClickListener((View v) -> {



                    alertDialog.dismiss();
                }
        );


        alertDialog.show();
    }

    private void  requestMultiplePermissions(){
        Dexter.withActivity(this)
                .withPermissions(

                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }


                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }


}

