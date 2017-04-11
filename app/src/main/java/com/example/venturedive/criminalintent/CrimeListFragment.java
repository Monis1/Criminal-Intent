package com.example.venturedive.criminalintent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Venturedive on 4/5/2017.
 */

public class CrimeListFragment extends Fragment {

    private ArrayList<Crime> mCrimes;
    private ListView mListView;
    private static CrimeListActivity ActivityList;
    private CallBacks mCallBacks;

    public interface CallBacks{
        void OnCrimeSelected(Crime crime);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallBacks=(CallBacks)context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallBacks=null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.crimes_title);
        mCrimes=CrimeLab.Get(getActivity()).getCrimes();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mListView=(ListView)view.findViewById(R.id.crime_listview);
        CrimeAdapter adapter = new CrimeAdapter(mCrimes);
        mListView.setAdapter(adapter);
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.HONEYCOMB)
            registerForContextMenu(mListView);
        else
            mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        mListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater=mode.getMenuInflater();
                inflater.inflate(R.menu.menu_crime_list_item_context,menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_item_delete_crime:
                        CrimeAdapter adapter=(CrimeAdapter)mListView.getAdapter();
                        CrimeLab crime_lab=CrimeLab.Get(getActivity());
                        for(int i=adapter.getCount()-1;i>=0;i--){
                            if(mListView.isItemChecked(i))
                                crime_lab.DeleteCrime(adapter.getItem(i));
                        }
                        mode.finish();
                        adapter.notifyDataSetChanged();
                        crime_lab.SaveCrimes();
                        return true;
                default:
                return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Crime c=((CrimeAdapter)mListView.getAdapter()).getItem(position);
                mCallBacks.OnCrimeSelected(c);
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_crime_list,menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.menu_crime_list_item_context,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int position=info.position;
        CrimeAdapter adapter=(CrimeAdapter) mListView.getAdapter();
        Crime crime=adapter.getItem(position);
        switch (item.getItemId())
        {
            case R.id.menu_item_delete_crime:
                CrimeLab.Get(getActivity()).DeleteCrime(crime);
                adapter.notifyDataSetChanged();
                CrimeLab.Get(getActivity()).SaveCrimes();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((CrimeAdapter)mListView.getAdapter()).notifyDataSetChanged();
    }


    public void updateUI() {
        ((CrimeAdapter)mListView.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_item_new_crime:
                Crime crime=new Crime();
                CrimeLab.Get(getActivity()).AddCrime(crime);
                ((CrimeAdapter)mListView.getAdapter()).notifyDataSetChanged();
                mCallBacks.OnCrimeSelected(crime);
                return true;
            case R.id.menu_item_settings:
                Intent i1=new Intent(getActivity(),CrimeSettingsActivity.class);
                startActivity(i1);
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }




    private class CrimeAdapter extends ArrayAdapter<Crime>{

        public CrimeAdapter(ArrayList<Crime> crimes){
            super(getActivity(),0,crimes);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if(convertView==null)
                convertView=getActivity().getLayoutInflater().inflate(R.layout.list_item_crime,null);
            Crime c=getItem(position);
            TextView titleTextView=(TextView)convertView.findViewById(R.id.crime_title_label);
            TextView dateTextView=(TextView)convertView.findViewById(R.id.crime_date_label);
            CheckBox solvedCheckBox=(CheckBox)convertView.findViewById(R.id.crime_solved);

            titleTextView.setText(c.getTitle());
            dateTextView.setText(c.getDate().toString());
            solvedCheckBox.setChecked(c.isSolved());

            return convertView;
        }
    }
}