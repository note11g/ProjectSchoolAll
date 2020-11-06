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
        //검색버튼을 누르면 이 함수가 호출됨.
        //그때, 검색단어가 schoolName으로 들어오는데, 그걸 Api에 집어넣어서 반환 받고
        //여러개중 일치하는 애들 리사이클러뷰에 표시 (없으면 검색결과가 없다고 토스트 띄우기)
        //그 중에 하나 누르면 그걸로 학교코드 반환함수를 실행.
        //반환함수 : selectSucceed("학교코드", "학교 이름");
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