package com.note11.projectschoolall.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.note11.projectschoolall.R;
import com.note11.projectschoolall.databinding.ActivityLoginAndRegisterBinding;
import com.note11.projectschoolall.model.UserModel;
import com.note11.projectschoolall.util.UserCache;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class LoginAndRegisterActivity extends AppCompatActivity {

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private ActivityLoginAndRegisterBinding binding;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String vId, phoneNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_and_register);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_and_register);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                Toast.makeText(LoginAndRegisterActivity.this, "성공!", Toast.LENGTH_SHORT).show();
                LoginAndRegisterActivity.this.finish();
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                if (e instanceof FirebaseAuthInvalidCredentialsException)
                    Toast.makeText(LoginAndRegisterActivity.this, "잘못된 전화번호입니다.", Toast.LENGTH_LONG).show();
                else if (e instanceof FirebaseTooManyRequestsException)
                    Toast.makeText(LoginAndRegisterActivity.this, "sms를 너무 많이 보냈습니다. 잠시 후 다시 실행해주세요.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                Intent intent = new Intent(LoginAndRegisterActivity.this, PhoneAuth2Activity.class);
                startActivityForResult(intent, 1);
                vId = verificationId;
            }
        };

        binding.setPhoneNum("");
        binding.setPhoneNum(getPhoneNumber());
        binding.btnAuthPhone1.setOnClickListener(v -> {
            phoneNum = binding.getPhoneNum();
            startPhoneAuth(phoneNum);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String code = data.getStringExtra("authInput");
                try {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(vId, code);
                    signInWithPhoneAuthCredential(credential);
                } catch (Exception e) {
                    Toast.makeText(this, "네트워크 연결이 원활하지 않거나, 메모리가 부족합니다.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void startPhoneAuth(String phoneInputNumber) {
        if(phoneInputNumber.isEmpty()){
            Toast.makeText(this, "전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        String phoneNum = "+82" + phoneInputNumber;

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNum)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

        Toast.makeText(this, "캡챠 인증을 진행해주세요.", Toast.LENGTH_LONG).show();
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginAndRegisterActivity.this, "인증 성공!", Toast.LENGTH_SHORT).show();
                        isInDB();//기존회원인지 체크
                    } else {
                        Toast.makeText(LoginAndRegisterActivity.this, "인증 실패", Toast.LENGTH_SHORT).show();
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                            invalidCodeReStartActivity();
                    }
                });
    }

    private void goToNextStep() {
        //새 계정
        Intent intent = new Intent(this, writeInfoActivity.class);
        intent.putExtra("phone", phoneNum);
        startActivity(intent);
        this.finish();
    }

    private void goHome(UserModel userModel) {
        //기존 계정 존재
        UserCache.setUser(this, userModel);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void invalidCodeReStartActivity() {
        Toast.makeText(this, "인증 코드가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
        startActivityForResult(new Intent(this, PhoneAuth2Activity.class), 1);
    }

    private void isInDB() {
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").document(phoneNum);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    goHome(document.toObject(UserModel.class));//기존회원
                } else {
                    goToNextStep();
                }
            } else {
                Toast.makeText(this, "인터넷이 불안정합니다.", Toast.LENGTH_SHORT).show();
                goToNextStep();
            }
        });
    }

    @SuppressLint("MissingPermission")
    private String getPhoneNumber() {
        TelephonyManager telephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String phoneNumber = "";
        try {
            if (telephony.getLine1Number() != null) {
                phoneNumber = telephony.getLine1Number();
            } else {
                if (telephony.getSimSerialNumber() != null) {
                    phoneNumber = telephony.getSimSerialNumber();
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "전화번호를 자동으로 불러오지 못했습니다", Toast.LENGTH_SHORT).show();
        }

        if (phoneNumber.startsWith("+82"))
            phoneNumber = phoneNumber.replace("+82", "0"); // +8210xxxxyyyy 로 시작되는 번호

        phoneNumber = PhoneNumberUtils.formatNumber(phoneNumber, Locale.getDefault().getCountry());

        if (phoneNumber.contains("-")) {
            phoneNumber = phoneNumber.replace("-", "");
        }

        return phoneNumber;
    }
}