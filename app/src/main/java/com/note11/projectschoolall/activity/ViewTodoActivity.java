package com.note11.projectschoolall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.note11.projectschoolall.R;
import com.note11.projectschoolall.databinding.ActivityViewTodoBinding;
import com.note11.projectschoolall.model.TodoModel;

public class ViewTodoActivity extends AppCompatActivity {

    private ActivityViewTodoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_todo);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_todo);

        Intent g = getIntent();
        binding.setSendItem(new TodoModel("",
                g.getStringExtra("date").substring(0, 4)+"년 "+g.getStringExtra("date").substring(4,6)+"월 "+g.getStringExtra("date").substring(6,8)+"일 까지",
                g.getStringExtra("title"),
                g.getStringExtra("contentData"),
                g.getStringExtra("subject")
        ));

    }
}