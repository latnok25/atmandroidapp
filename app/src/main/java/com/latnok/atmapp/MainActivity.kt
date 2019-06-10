package com.latnok.atmapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView


 class MainActivity : AppCompatActivity() {

    private var headline: TextView? = null
    private var button: Button? = null
    private var button1: Button? = null
    private var editText: EditText? = null
    private var editText1: EditText? = null
    private var cardno: String? = null
    private var pinno: String? = null
    private val balance = "30000"
    var invalid = false
    internal var isbalance: Int = 0
    lateinit var shref: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.select)
        headline = findViewById(R.id.headline)
        editText = findViewById(R.id.edtextnumber)
        shref = getSharedPreferences("gokada", Context.MODE_PRIVATE).edit()
        button1 = findViewById(R.id.movetonext)
        editText1 = findViewById(R.id.edtextpin)

        button!!.setOnClickListener { validate() }

        button1!!.setOnClickListener { validatepin() }

    }

    private fun validate() {

        cardno = editText!!.text.toString().trim { it <= ' ' }

        if (cardno!!.isEmpty()) {
            editText!!.error = "Card number Required"
            editText!!.requestFocus()
        } else {
            showpin()
        }
    }

    private fun validatepin() {

        pinno = editText1!!.text.toString().trim { it <= ' ' }

        invalid = isvalid(pinno)


        if (invalid == true) {


            if (pinno!!.isEmpty()) {
                editText1!!.error = "pin Required"
                editText1!!.requestFocus()
            } else {
                isbalance = Integer.parseInt(balance)
                shref.putInt("balance", isbalance)
                shref.putString("pin", pinno)
                shref.commit()
                shref.apply()
                movetonextactivity()
            }
        }
    }

    private fun movetonextactivity() {
        val intent = Intent(this@MainActivity, dashboard::class.java)
        startActivity(intent)
    }

    private fun showpin() {
        button!!.visibility = View.GONE
        editText!!.visibility = View.GONE
        button1!!.visibility = View.VISIBLE
        editText1!!.visibility = View.VISIBLE
        headline!!.text = "Please input your pin!"
    }

    companion object {

        fun isvalid(pin: CharSequence?): Boolean {
            return pin != null
        }
    }


}
