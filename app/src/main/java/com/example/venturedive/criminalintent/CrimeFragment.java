package com.example.venturedive.criminalintent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Venturedive on 4/5/2017.
 */

public class CrimeFragment extends Fragment{



    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;
    private Button mSendReportButton;
    private Button mSuspectButton;
    public static final String EXTRA_CRIME_ID="com.example.venturedive.criminalintent.crime_id";
    public static final String DIALOG_DATE="date";
    public static final int REQUEST_DATE=0;
    public static final String TAG="CrimeFragment";
    private static final int REQUEST_CONTACT=2;


    public static CrimeFragment NewInstance(UUID crime_id){
        Bundle args=new Bundle();
        args.putSerializable(EXTRA_CRIME_ID,crime_id);
        CrimeFragment fragment=new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!= Activity.RESULT_OK)
            return;
        if(requestCode==REQUEST_DATE)
        {
            Date date=(Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            mDateButton.setText(mCrime.getDate().toString());
        }
        else if(requestCode==REQUEST_CONTACT)
        {
            Uri contact_uri=data.getData();
            String[] query_fields=new String[]{ContactsContract.Contacts.DISPLAY_NAME};
            Cursor c=getActivity().getContentResolver().query(contact_uri,query_fields,null,null,null);
            if(c.getCount()==0){
                c.close();
                return;
            }
            c.moveToFirst();
            mSuspectButton.setText("");
            c.close();
        }
    }

    public String GetCrimeReport(){
        String solved_string=null;
        if(mCrime.isSolved())
            solved_string=getString(R.string.crime_report_solved);
        else
            solved_string=getString(R.string.crime_report_unsolved);

        String date_format="EEE, MMM dd";
        String date_string= DateFormat.format(date_format,mCrime.getDate()).toString();
        String suspect=null;
        if(suspect==null)
            suspect=getString(R.string.crime_report_no_suspect);
        else
            suspect=getString(R.string.crime_report_suspect,suspect);
        String report=getString(R.string.crime_report,mCrime.getTitle(),date_string,solved_string,suspect);
        return report;
    }


    @Override
    public void onPause() {
        super.onPause();
        CrimeLab.Get(getActivity()).SaveCrimes();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle("Crime Details");
        UUID crime_id=(UUID)getArguments().getSerializable(EXTRA_CRIME_ID);
        mCrime=CrimeLab.Get(getActivity()).getCrime(crime_id);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_crime,container,false);
        mSuspectButton=(Button)view.findViewById(R.id.choose_suspect);
        mSuspectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,
                        ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(i, REQUEST_CONTACT);
            }
        });
        mSendReportButton=(Button)view.findViewById(R.id.send_crime_report);
        mSendReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, GetCrimeReport());
                i.putExtra(Intent.EXTRA_SUBJECT,
                        getString(R.string.crime_report_subject));
                startActivity(i);
            }
        });
        mTitleField=(EditText)view.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDateButton=(Button)view.findViewById(R.id.crime_date);
        mDateButton.setText(mCrime.getDate().toString());
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragment_manager=getActivity().getSupportFragmentManager();
                DatePickerFragment dialog=DatePickerFragment.NewInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this,REQUEST_DATE);
                dialog.show(fragment_manager,DIALOG_DATE);
            }
        });


        mSolvedCheckBox=(CheckBox)view.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });
        return view;
    }


}
