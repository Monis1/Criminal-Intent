package com.example.venturedive.criminalintent;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Venturedive on 4/5/2017.
 */

public class CrimeListActivity extends SingleFragmentActivity implements CrimeListFragment.CallBacks,CrimeFragment.CallBacks {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

    @Override
    protected int GetLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    public void OnCrimeSelected(Crime crime) {
        if(findViewById(R.id.details_fragment_container)==null){
            Intent i=new Intent(this,CrimePagerActivity.class);
            i.putExtra(CrimeFragment.EXTRA_CRIME_ID,crime.getId());
            startActivity(i);
        }
        else{
            FragmentManager fm=getSupportFragmentManager();
            FragmentTransaction ft=fm.beginTransaction();
            Fragment old_detail=fm.findFragmentById(R.id.details_fragment_container);
            Fragment new_detail=CrimeFragment.NewInstance(crime.getId());
            if(old_detail!=null)
                ft.remove(old_detail);
            ft.add(R.id.details_fragment_container,new_detail);
            ft.commit();
        }
    }

    @Override
    public void onCrimeUpdated(Crime crime) {
        FragmentManager fm = getSupportFragmentManager();
        CrimeListFragment listFragment = (CrimeListFragment)fm.findFragmentById(R.id.details_fragment_container);
        listFragment.updateUI();
    }
}
