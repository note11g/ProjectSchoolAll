package com.note11.projectschoolall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.note11.projectschoolall.R;

public class SplashActivity extends AppCompatActivity {

    static final int READ_SMS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (getPermission()) {
            new Handler().postDelayed(this::goToLogin, 2000);
        }


    }

    private void goToLogin() {

        startActivity(new Intent(this, LoginAndRegisterActivity.class));
        finish();
    }

    private boolean getPermission() {
        int permissonCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        if (permissonCheck == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, READ_SMS);
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == READ_SMS) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "권한 허용됨", Toast.LENGTH_SHORT).show();
            }
            goToLogin();
        }
    }
}