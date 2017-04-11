package com.example.venturedive.criminalintent;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

/**
 * A placeholder fragment containing a simple view.
 */
public class CrimeSettingsFragment extends Fragment {

    ToggleButton mDbToggleButton;
    public static boolean mFromDb;
    SharedPreferences mSharedPreferences;
    public static final String PREFERENCES = "MyPreferences" ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_crime_settings, container, false);
        getActivity().setTitle(R.string.settings);
        mDbToggleButton=(ToggleButton)view.findViewById(R.id.use_db_toggle_button);
        mSharedPreferences=getActivity().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        mDbToggleButton.setChecked(mSharedPreferences.getBoolean("from_db",false));
        mFromDb=mDbToggleButton.isChecked();
        mDbToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mFromDb=isChecked;
                SharedPreferences.Editor editor=mSharedPreferences.edit();
                editor.putBoolean("from_db",isChecked);
                editor.commit();
            }
        });
        return view;
    }
}
