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

import com.google.firebase.auth.FirebaseAuth;
import com.note11.projectschoolall.R;
import com.note11.projectschoolall.util.UserCache;

public class SplashActivity extends AppCompatActivity {

    static final int READ_SMS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        if (isNew()) {
            if (getPermission())
                new Handler().postDelayed(this::goToLogin, 1500);
        } else {
            new Handler().postDelayed(this::goToMain, 1500);
        }
    }

    private void goToLogin() {
        startActivity(new Intent(this, LoginAndRegisterActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    private void goToMain() {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    private boolean isNew() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null
                && UserCache.getUser(this) != null)
            return false;
        else
            return true;
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
            goToLogin();
        }
    }
}