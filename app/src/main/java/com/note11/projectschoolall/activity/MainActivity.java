package com.note11.projectschoolall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import com.note11.projectschoolall.R;
import com.note11.projectschoolall.activity.fragment.ContactFragment;
import com.note11.projectschoolall.activity.fragment.HomeFragment;
import com.note11.projectschoolall.activity.fragment.ToDoFragment;
import com.note11.projectschoolall.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.bottomMain.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.action_1:
                    switchFragment(HomeFragment.newInstance());
                    break;
                case R.id.action_2:
                    switchFragment(ToDoFragment.newInstance());
                    break;
                case R.id.action_3:
                    switchFragment(ContactFragment.newInstance());
                    break;
            }
            return true;
        });
        switchFragment(HomeFragment.newInstance());
    }

    private void switchFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_main, fragment);
        transaction.commit();
    }
}