package com.latnok.atmapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class dashboard : AppCompatActivity() {

    private var withdrawal: Button? = null
    private var balance: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        withdrawal = findViewById(R.id.withdrawal)
        balance = findViewById(R.id.balance)

        withdrawal!!.setOnClickListener { v ->
            val intent = Intent(this@dashboard, Transaction::class.java)
            startActivity(intent)
        }

        balance!!.setOnClickListener { v ->
            val intent = Intent(this@dashboard, Balance::class.java)
            startActivity(intent)
        }
    }


}
