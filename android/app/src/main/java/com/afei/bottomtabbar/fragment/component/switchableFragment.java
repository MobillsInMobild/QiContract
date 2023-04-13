package com.afei.bottomtabbar.fragment.component;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.afei.bottomtabbar.R;
import  com.afei.bottomtabbar.Utils.newFragment;
import com.afei.bottomtabbar.Utils.nextFragment;

import org.json.JSONObject;

public class switchableFragment extends Fragment{
    private int index;
    protected nextFragment sender;
    public switchableFragment(int index){
        super();
        this.index=index;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sender = (nextFragment) context;
    }
    public void sendData(JSONObject json)
    {
        try {
            JSONObject temp = new JSONObject();
            temp.put("data", json);
            temp.put("index",index);
            temp.put("type",1);
            sender.sendData(temp);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void switchToPage(JSONObject json,int pageNumber)
    {
        try {
            JSONObject temp = new JSONObject();
            temp.put("data", json);
            temp.put("index",pageNumber);
            temp.put("type",0);
            sender.sendData(temp);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void switchToFragment(JSONObject json,int fragmentNumber)
    {
        try {
            JSONObject temp = new JSONObject();
            temp.put("data", json);
            temp.put("index",index);
            temp.put("type",2);
            temp.put("fragment",fragmentNumber);
            sender.sendData(temp);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
