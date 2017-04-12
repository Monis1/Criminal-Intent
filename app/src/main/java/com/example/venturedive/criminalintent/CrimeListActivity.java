package com.example.venturedive.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by Venturedive on 4/5/2017.
 */

public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

    @Override
    protected int GetLayoutResId() {
        return R.layout.activity_masterdetail;
    }
}
