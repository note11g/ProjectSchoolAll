package com.note11.projectschoolall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.PhoneAuthProvider;
import com.note11.projectschoolall.R;

import java.util.concurrent.TimeUnit;

public class CreateAccountActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);



    }

    private void phoneAuth(long phoneNumber){
//        PhoneAuthProvider.verifyPhoneNumber(
//                phoneNumber,
//                300,
//                TimeUnit.SECONDS,
//                this,
//
//        );
    }
}