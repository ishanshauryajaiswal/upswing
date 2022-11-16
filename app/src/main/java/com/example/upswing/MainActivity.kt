package com.example.upswing

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import com.google.android.material.snackbar.Snackbar
import android.content.DialogInterface
import android.telephony.SmsManager
import android.telephony.SubscriptionInfo
import androidx.appcompat.app.AlertDialog
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED


/*
read and send permission. check and ask.
show pop up for sim selection
hardcoded body. destination number
send sms and try to get the status
 */
class MainActivity : AppCompatActivity() {

    private lateinit var simInfo: List<SubscriptionInfo>
    companion object{
        private const val DESTINATION_NUM = "9839060055"
        private const val SMS_BODY = "Hello World"
    }

    private fun startSendSms() {
        val simCount = SimInfoProvider.getActiveSimCount(applicationContext)
        if(simCount < 2) {
            Toast.makeText(applicationContext, "Dual sim device needed", Toast.LENGTH_SHORT).show()
            return
        }
        simInfo = SimInfoProvider.getSimInfo(applicationContext)!!
        createDialog()
    }

    private fun sendTextMessage(simInfo: SubscriptionInfo){
        val smsManager = SmsManager.getSmsManagerForSubscriptionId(simInfo.subscriptionId)
        smsManager.sendTextMessage(DESTINATION_NUM, null, SMS_BODY, null, null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkAndAskForPermissions()
    }

    private fun checkAndAskForPermissions() {
        if(!PermissionManager.checkAllPermissions(listOf(Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_PHONE_STATE
            ), applicationContext)){
            requestPermissions(arrayOf(Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE), 100)
        }
        else{
            createDialog()
        }
    }

    private fun createDialog(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("Please select sim")
            .setCancelable(false)
            .setPositiveButton("Sim 1",
                DialogInterface.OnClickListener { dialog, id ->
                    sendTextMessage(simInfo[0])
                    dialog.dismiss()
                })
            .setNegativeButton("Sim 2",
                DialogInterface.OnClickListener { dialog, id ->
                    sendTextMessage(simInfo[1])
                    dialog.dismiss()
                })
        val alert: AlertDialog = builder.create()
        alert.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        grantResults.forEach {
            if(it != PERMISSION_GRANTED){
                return
            }
        }
        startSendSms()
    }
}