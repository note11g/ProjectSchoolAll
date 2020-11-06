package com.note11.projectschoolall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.note11.projectschoolall.R;
import com.note11.projectschoolall.databinding.ActivityWriteInfoBinding;
import com.note11.projectschoolall.model.UserModel;
import com.note11.projectschoolall.util.UserCache;

public class writeInfoActivity extends AppCompatActivity {

    private ActivityWriteInfoBinding binding;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_info);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_write_info);

        binding.setName("");
        binding.setSchoolName("");
        binding.setClass1("");
        binding.setClass2("");
        binding.setClass3("");
        binding.setPhone(getIntent().getStringExtra("phone"));
        binding.btnRegSubmit.setOnClickListener(v->submit(
                binding.getName(),
                binding.getSchoolName(),
                "학교코드",
                binding.getClass1(),
                binding.getClass2(),
                binding.getClass3()
        ));
        binding.btnFindSchool.setOnClickListener(v->{
            Intent intent = new Intent(this, FindSchoolActivity.class);
            intent.putExtra("schoolName", binding.getSchoolName());
            startActivityForResult(intent, 3);
        });
    }

    private void submit(String name, String SchoolName, String SchoolCode, String class1, String class2, String class3 ){

        if(name.isEmpty()||SchoolName.isEmpty()||class1.isEmpty()||class2.isEmpty()||class3.isEmpty()){
            Toast.makeText(this, "입력하지 않은 항목이 존재합니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(SchoolCode.isEmpty()){
            Toast.makeText(this, "학교검색을 진행해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        UserModel rModel = new UserModel(
                name,
                SchoolName,
                SchoolCode,
                binding.getPhone(),
                class1,
                class2,
                class3
                );

        firebaseFirestore
                .collection("users")
                .document(binding.getPhone())
                .set(rModel)
                .addOnSuccessListener(runnable -> {
                    UserCache.setUser(this, rModel);
                    Toast.makeText(this, "회원가입이 정상적으로 진행되었습니다.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }).addOnFailureListener(e -> Toast.makeText(this, "가입하는데 문제가 발생하였습니다. 잠시 후 다시 시도하세요.", Toast.LENGTH_SHORT).show());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==3&&resultCode==RESULT_OK){
            writeInfoActivity.this.binding.setSchoolCode(data.getStringExtra("schoolCodeCallback"));
            writeInfoActivity.this.binding.setSchoolName(data.getStringExtra("schoolNameCallback"));
        }
    }
}