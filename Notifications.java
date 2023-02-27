package com.example.iteminventoryapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

import androidx.core.content.ContextCompat;

import java.util.Timer;
import java.util.TimerTask;

public class Notifications {

    private Context context;
    private Timer timer;

    public Notifications(Context context)
    {
        this.timer = new Timer();
        this.context = context;
    }

    private class Work extends TimerTask
    {
        private Context context;
        InventoryDatabase mInvDb;

        public Work(Context context) {
            this.context = context;
            this.mInvDb = new InventoryDatabase(this.context);
        }

        public void run()
        {
                String phoneNumber = null;

                // Check Permissions
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(context.TELEPHONY_SERVICE);
                    phoneNumber = telephonyManager.getLine1Number();
                }
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    // Send the Message
                    smsManager.sendTextMessage(phoneNumber, null, "Item " + "is running low.", null, null);
                }
            }
        }


    // Service Handlers
    public void StartThread() {
        timer.scheduleAtFixedRate(new Work(this.context), 0, 60000);
    }

    public void StopThread() {
        timer.cancel();
    }
}
