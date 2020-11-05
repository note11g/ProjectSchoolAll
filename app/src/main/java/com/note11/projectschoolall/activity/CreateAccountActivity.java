package com.note11.projectschoolall.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.note11.projectschoolall.R;
import com.note11.projectschoolall.databinding.ActivityCreateAccountBinding;

import java.util.concurrent.TimeUnit;

public class CreateAccountActivity extends AppCompatActivity {

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private ActivityCreateAccountBinding binding;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String vId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_account);



        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
//                Log.d(TAG, "onVerificationCompleted:" + credential);
                Toast.makeText(CreateAccountActivity.this, "성공!", Toast.LENGTH_SHORT).show();
                CreateAccountActivity.this.finish();
                //signInWithPhoneAuthCredential(credential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(CreateAccountActivity.this, "잘못된 전화번호입니다.", Toast.LENGTH_LONG).show();
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Toast.makeText(CreateAccountActivity.this, "sms를 너무 많이 보냈습니다. 잠시 후 다시 실행해주세요.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                Intent intent = new Intent(CreateAccountActivity.this, PhoneAuth2Activity.class);
                startActivityForResult(intent, 1);

                vId = verificationId;
            }
        };

        binding.setPhoneNum("");
        binding.btnAuthPhone1.setOnClickListener(v-> startPhoneAuth(binding.getPhoneNum()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String code = data.getStringExtra("authInput");

                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(vId, code);
                signInWithPhoneAuthCredential(credential);
            }
        }
    }

    private void startPhoneAuth(String phoneInputNumber){

        String phoneNumber = "+82" + phoneInputNumber;

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

        Toast.makeText(this, "캡챠 인증을 진행해주세요.", Toast.LENGTH_SHORT).show();
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(CreateAccountActivity.this, "성공!", Toast.LENGTH_SHORT).show();

                            //FirebaseUser user = task.getResult().getUser();

                        } else {
                            // Sign in failed, display a message and update the UI
                            Toast.makeText(CreateAccountActivity.this, "실패", Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                invalidCodeReStartActivity();
                            }
                        }
                    }
                });
    }

    private void invalidCodeReStartActivity(){
        Toast.makeText(this, "인증 코드가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
        startActivityForResult( new Intent(this, PhoneAuth2Activity.class), 1 );
    }
}