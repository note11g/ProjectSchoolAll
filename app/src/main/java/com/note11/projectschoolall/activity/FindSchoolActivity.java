package com.note11.projectschoolall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.note11.projectschoolall.R;
import com.note11.projectschoolall.databinding.ActivityFindSchoolBinding;

public class FindSchoolActivity extends AppCompatActivity {

    private ActivityFindSchoolBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_school);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_find_school);

        if(!getIntent().getStringExtra("schoolName").isEmpty()){
            String scName = getIntent().getStringExtra("schoolName");
            binding.setSchoolName(scName);
            search(scName);
        }else {
            binding.setSchoolName("");
        }

        binding.btnSearch.setOnClickListener(v->{
            search(binding.getSchoolName());
        });
    }

    private void search(String schoolName){
        //이 함수에서, schoolName으로 학교를 찾아, 리사이클러뷰에 표시.
        //리사이클러뷰에 (학교이름)항목을 표시하고,
        // 클릭시 해당 학교로
        // selectSucceed(학교코드, 학교이름)실행

        Toast.makeText(this, "검색 : "+schoolName, Toast.LENGTH_SHORT).show();
        selectSucceed("", schoolName);
    }

    private void selectSucceed(String schoolCode, String schoolName){
        Intent intent = new Intent();
        intent.putExtra("schoolCodeCallback", schoolCode);
        intent.putExtra("schoolNameCallback", schoolName);
        setResult(RESULT_OK, intent);
        finish();
    }
}