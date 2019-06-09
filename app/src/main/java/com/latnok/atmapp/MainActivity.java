package com.latnok.atmapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private TextView headline;
    private Button button,button1;
    private EditText editText,editText1;
    private String cardno,pinno;
    private  String balance = "30000";
   public boolean invalid =false;
    int isbalance;
    SharedPreferences.Editor shref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button =findViewById(R.id.select);
        headline = findViewById(R.id.headline);
        editText = findViewById(R.id.edtextnumber);
        shref = getSharedPreferences("gokada", MODE_PRIVATE).edit();
        button1 =findViewById(R.id.movetonext);
        editText1 = findViewById(R.id.edtextpin);

        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        validate();
                    }
                }
        );

        button1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        validatepin();
                    }
                }
        );

    }

    private void validate(){

        cardno = editText.getText().toString().trim();

        if(cardno.isEmpty()){
            editText.setError("Card number Required");
            editText.requestFocus();
        } else{
            showpin();
        }
    }

    public static boolean isvalid(CharSequence pin){
        return pin!=null;
    }

    private void validatepin(){

        pinno = editText1.getText().toString().trim();

        invalid = isvalid(pinno);


        if(invalid == true) {



            if (pinno.isEmpty()) {
                editText1.setError("pin Required");
                editText1.requestFocus();
            } else {
                isbalance = Integer.parseInt(balance);
                shref.putInt("balance", isbalance);
                shref.putString("pin", pinno);
                shref.commit();
                shref.apply();
                movetonextactivity();
            }
        }
    }

    private void movetonextactivity(){
        Intent intent = new Intent(MainActivity.this,dashboard.class);
        startActivity(intent);
    }

    private void showpin(){
        button.setVisibility(View.GONE);
        editText.setVisibility(View.GONE);
        button1.setVisibility(View.VISIBLE);
        editText1.setVisibility(View.VISIBLE);
        headline.setText("Please input your pin!");
    }


}
