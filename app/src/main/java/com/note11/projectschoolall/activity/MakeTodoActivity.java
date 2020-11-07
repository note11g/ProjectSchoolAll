package com.note11.projectschoolall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.note11.projectschoolall.R;
import com.note11.projectschoolall.databinding.ActivityMakeTodoBinding;
import com.note11.projectschoolall.model.TodoModel;
import com.note11.projectschoolall.model.UserModel;
import com.note11.projectschoolall.util.UserCache;

public class MakeTodoActivity extends AppCompatActivity {

    private ActivityMakeTodoBinding binding;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_todo);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_make_todo);

        binding.button.setOnClickListener(v-> upload());
    }

    public static String getClassNumber(Context c){
        UserModel u = UserCache.getUser(c);
        return u.getSchoolCode()+"0"+u.getGrade()+u.getClassNum();
    }
    private void upload(){
        if(binding.getContentData().isEmpty()||binding.getEndDate().isEmpty()||binding.getSubject().isEmpty()||binding.getTitle().isEmpty()){
            Toast.makeText(this, "입력되지 않은 항목이 존재합니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseFirestore
                .collection("todo")
                .document()
                .set(new TodoModel(
                        getClassNumber(MakeTodoActivity.this),
                        binding.getEndDate(),
                        binding.getTitle(),
                        binding.getContentData(),
                        binding.getSubject()
                )).addOnSuccessListener(runnable -> {
            Toast.makeText(this, "등록이 완료되었습니다!", Toast.LENGTH_SHORT).show();
            finish();
        }).addOnFailureListener(e-> Toast.makeText(this, "네트워크 오류로 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show());
    }
}