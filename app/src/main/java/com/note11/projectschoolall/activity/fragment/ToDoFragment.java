package com.note11.projectschoolall.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.note11.projectschoolall.R;
import com.note11.projectschoolall.activity.MainActivity;
import com.note11.projectschoolall.activity.MakeTodoActivity;
import com.note11.projectschoolall.activity.ViewTodoActivity;
import com.note11.projectschoolall.databinding.FragmentHomeBinding;
import com.note11.projectschoolall.databinding.FragmentTodoBinding;
import com.note11.projectschoolall.model.TodoModel;
import com.note11.projectschoolall.util.TodoAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class ToDoFragment extends Fragment {

    public static ToDoFragment newInstance() {
        return new ToDoFragment();
    }

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private ObservableArrayList<TodoModel> items = new ObservableArrayList<>();


    private Context mContext;
    private FragmentTodoBinding binding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_todo, container, false);


        binding.recyclerTodo.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        TodoAdapter adapter = new TodoAdapter();
        binding.recyclerTodo.setAdapter(adapter);

        adapter.setOnItemClickListener((view, item) -> {
            Intent intent = new Intent(mContext, ViewTodoActivity.class);
            intent.putExtra("title", item.getTitle());
            intent.putExtra("date", item.getDate());
            intent.putExtra("contentData", item.getContentData());
            intent.putExtra("subject", item.getSubject());
            startActivity(intent);
        });

        adapter.setOnItemLongClickListener((view, item) -> true);

        binding.setItems(items);

        binding.imgBtnAddNewTodo.setOnClickListener(v->startActivity(new Intent(mContext, MakeTodoActivity.class)));

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        //업데이트
        getTodo();
    }

    private void getTodo() {
        int g = Integer.parseInt(new SimpleDateFormat("yyyyMMdd", Locale.KOREAN).format(new Date()));

        items.clear();
        firebaseFirestore
                .collection("todo")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot d : queryDocumentSnapshots) {
                        TodoModel t = d.toObject(TodoModel.class);
                        if (t.getClassNumber().equals(MakeTodoActivity.getClassNumber(mContext)))
                            if(Integer.parseInt(t.getDate())>= g) items.add(t);
                    }

                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.KOREAN);
                    items.sort((memoModel, t1) -> {
                        try {
                            return format.parse(memoModel.getDate()).compareTo(format.parse(t1.getDate()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                            return 0;
                        }
                    });
                })
                .addOnFailureListener(e -> Toast.makeText(mContext, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());
    }

}