package com.note11.projectschoolall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

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
        }else
            binding.setSchoolName("");

    }

    private void search(String schoolName){
        Toast.makeText(this, "검색 : "+schoolName, Toast.LENGTH_SHORT).show();
    }
}