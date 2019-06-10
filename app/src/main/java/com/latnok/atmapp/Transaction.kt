package com.latnok.atmapp

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.DexterError
import com.karumi.dexter.listener.PermissionRequestErrorListener
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Enumeration

 class Transaction : AppCompatActivity(), LocationListener {

    private var btn500: Button? = null
    private var btn1000: Button? = null
    private var btn2000: Button? = null
    private var btn3000: Button? = null
    private var btn5000: Button? = null
    private var btn10000: Button? = null
    private var btn20000: Button? = null
    private var pin: String? = null
    private var amount: String? = null
    internal lateinit var locationManager: LocationManager
    internal var location: Location? = null
    internal var counter = 3
    internal lateinit var shref: SharedPreferences
    internal lateinit  var shrefs: SharedPreferences.Editor
    internal var balance: Int = 0
    internal var mamount: Int = 0
    internal var machinelocation: String? = null
    internal var deviceip: String? = null
    internal var finalbalance: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

        getipAddress()

        btn500 = findViewById(R.id.m500)
        btn1000 = findViewById(R.id.m1000)
        btn2000 = findViewById(R.id.m2000)
        btn3000 = findViewById(R.id.m3000)
        btn5000 = findViewById(R.id.m5000)
        btn10000 = findViewById(R.id.m10000)
        btn20000 = findViewById(R.id.m20000)
        shref = getSharedPreferences("gokada", Context.MODE_PRIVATE)
        shrefs = getSharedPreferences("gokada", Context.MODE_PRIVATE).edit()

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            requestMultiplePermissions()
            return
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this as LocationListener)

        findViewById<View>(R.id.myvalue).setOnClickListener { v -> showinsertamount() }
    }

    private fun getipAddress() {
        try {
            val en = NetworkInterface.getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val intf = en.nextElement() as NetworkInterface
                val enumIpAddr = intf.inetAddresses
                while (enumIpAddr.hasMoreElements()) {
                    val inetAddress = enumIpAddr.nextElement() as InetAddress
                    if (!inetAddress.isLoopbackAddress) {
                        deviceip = inetAddress.hostAddress.toString()
                        Log.d("ip address", "" + deviceip!!)

                    }
                }
            }
        } catch (ex: SocketException) {
            Log.d("Socket exception", ex.toString())
        }

    }

    override fun onLocationChanged(location: Location) {
        machinelocation = (location.longitude + location.latitude).toString()
        Log.d("LOcation", machinelocation)
    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {

    }

    override fun onProviderEnabled(provider: String) {

    }

    override fun onProviderDisabled(provider: String) {

    }


    fun go(v: View) {
        val button = v as Button
        amount = button.text.toString()
        showalert()
    }

    fun showinsertamount() {
        val builder = AlertDialog.Builder(this@Transaction)
        val dialog_view = layoutInflater.inflate(R.layout.alert, null)
        val yes = dialog_view.findViewById<EditText>(R.id.tt)
        yes.inputType = InputType.TYPE_CLASS_NUMBER
        yes.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(5))
        val heading = dialog_view.findViewById<TextView>(R.id.heading)
        heading.text = "Please enter your withdrawal amount"
        amount = yes.text.toString()
        builder.setView(dialog_view)
        val no = dialog_view.findViewById<Button>(R.id.cc)
        val no2 = dialog_view.findViewById<Button>(R.id.dd)
        val alertDialog = builder.create()


        no.setOnClickListener { v: View ->
            amount = yes.text.toString()
            if (amount!!.isEmpty()) {
                yes.error = "Valid Amount is required"
                yes.requestFocus()
            } else {
                if (Integer.parseInt(amount) % 500 != 0) {
                    Toast.makeText(applicationContext, "please enter the amount in multiples of 500 or 1000.\nThank you.",
                            Toast.LENGTH_LONG).show()
                } else {
                    amount = "NGN" + amount!!
                    showalert()
                    //sendAndRequestResponsefinal();
                    alertDialog.dismiss()
                }
            }
        }

        no2.setOnClickListener { v -> alertDialog.dismiss() }


        alertDialog.show()
    }

    fun showalert() {
        val builder = AlertDialog.Builder(this@Transaction)
        val dialog_view = layoutInflater.inflate(R.layout.alert, null)
        val yes = dialog_view.findViewById<EditText>(R.id.tt)
        yes.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(4))
        pin = yes.text.toString()
        builder.setView(dialog_view)
        val no = dialog_view.findViewById<Button>(R.id.cc)
        val no2 = dialog_view.findViewById<Button>(R.id.dd)
        val alertDialog = builder.create()


        no.setOnClickListener { v: View ->
            pin = yes.text.toString()
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
                checkifthereismoney()
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
        builder.setMessage("Do You Confirm and agree to carry out the withdrawal of" + amount + "on this account ?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    Toast.makeText(applicationContext, "Transaction succesfull",
                            Toast.LENGTH_LONG).show()
                    finalbalance = balance - mamount
                    shrefs.putInt("balance", finalbalance)
                    shrefs.commit()
                    shrefs.apply()

                    showreceipt()
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.cancel()
                    Toast.makeText(applicationContext, "Transaction aborted",
                            Toast.LENGTH_LONG).show()
                }
        val alert = builder.create()
        alert.show()

    }

    fun checkifthereismoney() {
        balance = shref.getInt("balance", 0)
        val newamount = amount!!.replace("NGN", "")
        mamount = Integer.parseInt(newamount)
        if (mamount >= balance) {
            Toast.makeText(applicationContext, "NOT ENOUGH MONEY IN ACCOUNT TO WITHDRAW",
                    Toast.LENGTH_LONG).show()
        } else if (balance - mamount <= 1000) {
            Toast.makeText(applicationContext, "NOT ENOUGH MONEY IN ACCOUNT TO WITHDRAW",
                    Toast.LENGTH_LONG).show()
        } else {
            confirm()
        }
    }

    fun showreceipt() {
        val builder = AlertDialog.Builder(this@Transaction)
        val dialog_view = layoutInflater.inflate(R.layout.receipt, null)
        val c = Calendar.getInstance().time
        println("Current time => $c")

        val df = SimpleDateFormat("dd/MM/yyyy")
        val formattedDate = df.format(c)
        val time = c.hours.toString() + ":" + c.minutes.toString()
        val account = "224****5678"
        val transaction = "Withdrawal"
        val endbalance = balance.toString()
        val availablebalance = finalbalance.toString()
        val mdate = dialog_view.findViewById<TextView>(R.id.date)
        val mtime = dialog_view.findViewById<TextView>(R.id.time)
        val mlocation = dialog_view.findViewById<TextView>(R.id.location)
        val mtransaction = dialog_view.findViewById<TextView>(R.id.transaction)
        val maccount = dialog_view.findViewById<TextView>(R.id.account)
        val mamount = dialog_view.findViewById<TextView>(R.id.amount)
        val mendbal = dialog_view.findViewById<TextView>(R.id.endingbalance)
        val mavalbal = dialog_view.findViewById<TextView>(R.id.availablebalance)
        mdate.text = formattedDate
        mtime.text = time
        if (machinelocation == null) {
            if(deviceip == null){
                mlocation.text = "CANT LOCATE YOUR LOCATION"
            }
            mlocation.text = deviceip
        }

        mlocation.text = machinelocation

        mtransaction.text = transaction
        maccount.text = account
        mamount.text = amount
        mendbal.text = endbalance
        mavalbal.text = availablebalance
        builder.setView(dialog_view)
        val no = dialog_view.findViewById<Button>(R.id.btn)
        val alertDialog = builder.create()


        no.setOnClickListener { v: View ->


            alertDialog.dismiss()
        }


        alertDialog.show()
    }

    private fun requestMultiplePermissions() {
        Dexter.withActivity(this)
                .withPermissions(

                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(applicationContext, "All permissions are granted by user!", Toast.LENGTH_SHORT).show()
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied) {
                            // show alert dialog navigating to Settings

                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(permissions: List<com.karumi.dexter.listener.PermissionRequest>, token: PermissionToken) {
                        token.continuePermissionRequest()
                    }


                }).withErrorListener { Toast.makeText(applicationContext, "Some Error! ", Toast.LENGTH_SHORT).show() }
                .onSameThread()
                .check()
    }

    companion object {
        var invalid = false


        fun isvalid(pin: CharSequence?): Boolean {
            return pin != null
        }
    }


}

