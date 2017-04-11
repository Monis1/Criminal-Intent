package com.example.venturedive.criminalintent;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Venturedive on 4/11/2017.
 */

public class StorageFactory {

    private Context mContext;
    private String mFileName;

    public StorageFactory(Context mContext, String mFileName) {
        this.mContext = mContext;
        this.mFileName = mFileName;
    }

    ArrayList<Crime> LoadData(boolean from_db){
        DataStorage DS=from_db?new SQLliteHelper(mContext,mFileName):new CriminalIntentJsonSerializer(mContext,mFileName);
        return DS.Load();
    }

    void SaveData(boolean from_db,ArrayList<Crime> crime){
        DataStorage DS=from_db?new SQLliteHelper(mContext,mFileName):new CriminalIntentJsonSerializer(mContext,mFileName);
        DS.Save(crime);
    }

}
