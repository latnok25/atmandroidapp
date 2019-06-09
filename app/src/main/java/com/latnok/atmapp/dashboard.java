package com.latnok.atmapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class dashboard extends AppCompatActivity {

    private Button withdrawal,balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        withdrawal = findViewById(R.id.withdrawal);
        balance = findViewById(R.id.balance);

        withdrawal.setOnClickListener(
                v -> {
                    Intent intent = new Intent(dashboard.this,Transaction.class);
                    startActivity(intent);
                }
        );
    }
}
