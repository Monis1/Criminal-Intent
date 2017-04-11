package com.example.venturedive.criminalintent;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by Venturedive on 4/10/2017.
 */

public class CriminalIntentJsonSerializer implements DataStorage {

    private Context mContext;
    private String mFileName;

    public CriminalIntentJsonSerializer(Context mContext, String mFileName) {
        this.mContext = mContext;
        this.mFileName = mFileName;
    }

    public ArrayList<Crime> Load(){
        ArrayList<Crime> crimes=new ArrayList<>();
        BufferedReader reader=null;
        try{
            InputStream in=mContext.openFileInput(mFileName);
            reader=new BufferedReader(new InputStreamReader(in));
            StringBuilder json_string=new StringBuilder();
            String line;
            while ((line=reader.readLine())!=null)
                json_string.append(line);
            JSONArray array=(JSONArray)new JSONTokener(json_string.toString()).nextValue();
            for (int i=0;i<array.length();i++)
                crimes.add(new Crime(array.getJSONObject(i)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        finally {
            if(reader!=null)
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
   return crimes;
    }

    public void Save(ArrayList<Crime> crimes){
        JSONArray array=new JSONArray();
        for (Crime c:crimes)
            array.put(c.toJSON());
        Writer writer=null;
        try {
            OutputStream out=mContext.openFileOutput(mFileName,Context.MODE_PRIVATE);
            writer=new OutputStreamWriter(out);
            writer.write(array.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(writer!=null)
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
