package com.example.venturedive.criminalintent;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Venturedive on 4/5/2017.
 */

public class CrimeLab {

    private static final String TAG = "CrimeLab";
    private static final String FILENAME = "crimes.json";
    private ArrayList<Crime> mCrimes;
    private static CrimeLab sCrimeLab;
    private Context mAppContext;
    private StorageFactory mFactory;

    private CrimeLab(Context appContext){
        mAppContext=appContext;
        mFactory=new StorageFactory(mAppContext,FILENAME);
        try {
            mCrimes = mFactory.LoadData(CrimeSettingsFragment.mFromDb);
        } catch (Exception e) {
            mCrimes = new ArrayList<>();
            Log.e(TAG, "Error loading crimes: ", e);
        }
    }

    public boolean SaveCrimes(){
        try{
            mFactory.SaveData(CrimeSettingsFragment.mFromDb,mCrimes);
            Log.d(TAG,"Crimes saved to file");
            return true;
        }
        catch (Exception e){
            Log.d(TAG,"Crimes failed to save "+e.getMessage());
            return false;
        }
    }

    public void AddCrime(Crime c){
        mCrimes.add(c);
    }

    public void DeleteCrime(Crime c) { mCrimes.remove(c); }

    public static CrimeLab Get(Context c){
        if(sCrimeLab==null)
            sCrimeLab=new CrimeLab(c.getApplicationContext());
        return sCrimeLab;
    }


    public ArrayList<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id){
        for(Crime c:mCrimes){
            if(c.getId().equals(id))
                return c;
        }
        return null;
    }

}