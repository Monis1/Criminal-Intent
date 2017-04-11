package com.example.venturedive.criminalintent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Created by Venturedive on 4/5/2017.
 */

public abstract class SingleFragmentActivity extends FragmentActivity {

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(GetLayoutResId());
        FragmentManager fragment_manager=getSupportFragmentManager();
        Fragment fragment=fragment_manager.findFragmentById(R.id.fragment_container);
        if(fragment==null)
        {
            fragment=createFragment();
            fragment_manager.beginTransaction().add(R.id.fragment_container,fragment).commit();
        }
    }


    protected int GetLayoutResId(){
        return R.layout.activity_fragment;
    }
}
