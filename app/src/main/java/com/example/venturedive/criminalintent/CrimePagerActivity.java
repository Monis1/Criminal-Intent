package com.example.venturedive.criminalintent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Venturedive on 4/6/2017.
 */

public class CrimePagerActivity extends FragmentActivity {

    private ViewPager mViewPager;
    private ArrayList<Crime> mCrimes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager=new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);
        mCrimes=CrimeLab.Get(this).getCrimes();
        FragmentManager fragment_manager=getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragment_manager) {
            @Override
            public Fragment getItem(int position) {
              Crime c=mCrimes.get(position);
                return CrimeFragment.NewInstance(c.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
             Crime c=mCrimes.get(position);
                if(c.getTitle()!=null) {
                    setTitle(c.getTitle());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        UUID crime_id=(UUID)getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
        for(int i=0;i<mCrimes.size();i++)
        {
            if(mCrimes.get(i).getId().equals(crime_id))
            {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
