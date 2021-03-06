package com.example.venturedive.criminalintent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Venturedive on 4/5/2017.
 */

public class Crime {

    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_SOLVED = "solved";
    private static final String JSON_DATE = "date";

    @Override
    public String toString() {
        return mTitle;
    }

    private UUID mId;
    private String mTitle;
    private Date mDate;


    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }

    private  boolean mSolved;

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Crime(){
        mId=UUID.randomUUID();
        mDate=new Date();
    }

    public Crime(String mTitle, Date mDate, boolean mSolved) {
        this.mId = UUID.randomUUID();
        this.mTitle = mTitle;
        this.mDate = mDate;
        this.mSolved = mSolved;
    }

    public Crime(JSONObject json) throws JSONException {
        mId=UUID.fromString(json.getString(JSON_ID));
        mTitle=json.getString(JSON_TITLE);
        mSolved=json.getBoolean(JSON_SOLVED);
        mDate=new Date(json.getLong(JSON_DATE));
    }


    public JSONObject toJSON() {
        JSONObject json=new JSONObject();
        try{
        json.put(JSON_ID,mId.toString());
        json.put(JSON_TITLE,mTitle);
        json.put(JSON_DATE,mDate.getTime());
        json.put(JSON_SOLVED,mSolved);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
