package com.example.venturedive.criminalintent;

import java.util.ArrayList;

/**
 * Created by Venturedive on 4/11/2017.
 */

public interface DataStorage {

    ArrayList<Crime> Load();
    void Save(ArrayList<Crime> Data);

}
