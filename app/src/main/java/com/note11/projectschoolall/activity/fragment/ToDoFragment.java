package com.note11.projectschoolall.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.note11.projectschoolall.R;
import com.note11.projectschoolall.databinding.FragmentHomeBinding;
import com.note11.projectschoolall.databinding.FragmentTodoBinding;

public class ToDoFragment extends Fragment {

    public static ToDoFragment newInstance() {
        return new ToDoFragment();
    }

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


        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        //업데이트
    }

}
