package com.note11.projectschoolall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.note11.projectschoolall.R;
import com.note11.projectschoolall.databinding.ActivityPhoneAuth2Binding;

public class PhoneAuth2Activity extends AppCompatActivity {

    private ActivityPhoneAuth2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth2);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_phone_auth2);

        binding.setAuthKey("");
        binding.btnAuthPhone2.setOnClickListener(v->{
            authCheck();
        });
    }

    private void authCheck(){
        String authKeyInput = binding.getAuthKey();
        Intent intent = new Intent();
        intent.putExtra("authInput", authKeyInput);
        setResult(RESULT_OK, intent);
        finish();
    }


}