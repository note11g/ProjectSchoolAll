package com.note11.projectschoolall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.note11.projectschoolall.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
                goToLogin();
                finish();
            }, 2000);
    }
    private void goToLogin(){
        startActivity(new Intent(this, LoginActivity.class));
    }
}