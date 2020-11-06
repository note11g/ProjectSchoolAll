package com.note11.projectschoolall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.note11.projectschoolall.R;
import com.note11.projectschoolall.databinding.ActivityWriteInfoBinding;

public class writeInfoActivity extends AppCompatActivity {

    private ActivityWriteInfoBinding binding;

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

    private void submit(String name, String SchoolName, String class1, String class2, String class3 ){
        if(name.isEmpty()||SchoolName.isEmpty()||class1.isEmpty()||class2.isEmpty()||class3.isEmpty()){
            Toast.makeText(this, "입력하지 않은 항목이 존재합니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, name+","+SchoolName+","+class1+","+class2+","+class3, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }
}