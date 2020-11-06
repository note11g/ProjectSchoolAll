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
import com.note11.projectschoolall.databinding.FragmentContactBinding;
import com.note11.projectschoolall.databinding.FragmentHomeBinding;

public class ContactFragment extends Fragment {

    public static ContactFragment newInstance() {
        return new ContactFragment();
    }

    private Context mContext;
    private FragmentContactBinding binding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact, container, false);


        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        //업데이트
    }

}
