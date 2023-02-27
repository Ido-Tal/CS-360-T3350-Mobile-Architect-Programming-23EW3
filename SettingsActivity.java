package com.example.iteminventoryapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;

public class SettingsActivity extends AppCompatActivity
        implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int PERMISSION_REQUEST_SMS = 0;

    private View viewLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        viewLayout = findViewById(R.id.settingsLayout);
        findViewById(R.id.button_enable_sms).setOnClickListener(view -> showSmsPreview());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_SMS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(viewLayout, "Notifications are Enabled", Snackbar.LENGTH_SHORT).show();
                sendSmsThroughSmsManager();
            } else {
                Snackbar.make(viewLayout, "Notifications are Disabled", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private void showSmsPreview() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Snackbar.make(viewLayout, "Notifications are Available", Snackbar.LENGTH_SHORT).show();
            sendSmsThroughSmsManager();
        } else {
            requestSmsPermission();
        }
    }

    private void requestSmsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
            Snackbar.make(viewLayout, "SMS Access Required", Snackbar.LENGTH_INDEFINITE).setAction("Ok", view -> {
                        ActivityCompat.requestPermissions(SettingsActivity.this, new String[]{Manifest.permission.SEND_SMS},
                                PERMISSION_REQUEST_SMS);}).show();
        } else {
            Snackbar.make(viewLayout, "Notifications are Unavailable", Snackbar.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_SMS);
        }
    }

    public void sendSmsThroughSmsManager() {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage("+1234", null, "Item stock is running low", null, null);
    }
}