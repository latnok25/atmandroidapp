package com.latnok.atmapp

import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Balance : AppCompatActivity() {

    private var btn: Button? = null
    private var pin: String? = null
    private var balance: String? = null
    internal var counter = 3
    internal lateinit var shref: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_balance)

        btn = findViewById(R.id.m500)
        shref = getSharedPreferences("gokada", Context.MODE_PRIVATE)


        balance = shref.getInt("balance", 0).toString()

    }

    fun go(v: View) {
        //        Button button = (Button)v;
        //        amount = button.getText().toString();
        showalert()
    }

    fun showalert() {
        val builder = AlertDialog.Builder(this@Balance)
        val dialog_view = layoutInflater.inflate(R.layout.alert, null)
        val yes = dialog_view.findViewById<EditText>(R.id.tt)
        yes.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(4))
        pin = yes.text.toString().trim { it <= ' ' }
        builder.setView(dialog_view)
        val no = dialog_view.findViewById<Button>(R.id.cc)
        val no2 = dialog_view.findViewById<Button>(R.id.dd)
        val alertDialog = builder.create()


        no.setOnClickListener { v: View ->
            pin = yes.text.toString().trim { it <= ' ' }
            if (pin!!.isEmpty()) {
                yes.error = "Valid Pin is required"
                yes.requestFocus()
            } else {
                sendAndRequesttobank()
                //sendAndRequestResponsefinal();
                alertDialog.dismiss()
            }
        }

        no2.setOnClickListener { v -> alertDialog.dismiss() }


        alertDialog.show()
    }

    fun sendAndRequesttobank() {

        invalid = isvalid(pin)


        if (invalid == true) {

            val piny = shref.getString("pin", "")
            if (piny == pin) {
                /*Toast.makeText(getApplicationContext(), "correct Password",
                    Toast.LENGTH_SHORT).show();*/
                confirm()
            } else {
                if (counter == 0) {
                    wrongpin()
                } else {
                    counter -= 1
                    Toast.makeText(applicationContext, "incorrect Password",
                            Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    fun wrongpin() {

        val builder = android.app.AlertDialog.Builder(this)
        builder.setMessage("You have entered an incorrect pin three times.\nYour card has been destroyed.\nYou will need to contact the bank to get a new one ")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    dialog.cancel()
                    finish()
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.cancel()
                    finish()
                }
        val alert = builder.create()
        alert.show()

    }

    fun confirm() {

        val builder = android.app.AlertDialog.Builder(this)
        builder.setMessage("Your balance is NGN" + balance + "on this account")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id -> dialog.cancel() }
                .setNegativeButton("No") { dialog, id ->
                    dialog.cancel()
                    // finish();
                }
        val alert = builder.create()
        alert.show()


    }

    companion object {
        var invalid = false

        fun isvalid(pin: CharSequence?): Boolean {
            return pin != null
        }
    }

}
