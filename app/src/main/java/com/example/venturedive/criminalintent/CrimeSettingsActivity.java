package com.example.venturedive.criminalintent;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;

public class CrimeSettingsActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return new CrimeSettingsFragment();
    }
}
