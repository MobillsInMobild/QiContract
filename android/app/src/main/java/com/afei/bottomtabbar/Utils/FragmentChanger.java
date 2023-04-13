package com.afei.bottomtabbar.Utils;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import org.json.JSONObject;

import java.util.List;

public class FragmentChanger {
    private List<newFragment> fragments;
    private int pointer;
    private int size;
    public  FragmentChanger(List<newFragment> fragments)
    {
        this.fragments=fragments;
        pointer=0;
        size=fragments.size();
    }
    public Fragment getDefaultFragment()
    {
        Fragment result=fragments.get(pointer).newInstance();
        pointer+=1;
        pointer%=size;
        return result;
    }
    public void switchTo(BaseFragmentPagerAdapter mAdapter,int position,JSONObject data,int ptr)
    {
        Fragment newPage=fragments.get(ptr).newInstance();
        Bundle bundle=new Bundle();
        bundle.putString("data",data.toString());
        newPage.setArguments(bundle);
        mAdapter.replaceFragment(position, newPage);
        pointer=ptr+1;
        pointer%=size;
    }
    public void switchToNext(BaseFragmentPagerAdapter mAdapter,int position, JSONObject data)
    {
        switchTo(mAdapter,position,data,pointer);

    }
    public void switchToHead(BaseFragmentPagerAdapter mAdapter,int position, JSONObject data)
    {
        switchTo(mAdapter,position,data,0);
    }
}
