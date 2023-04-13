package com.afei.bottomtabbar.style2;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.afei.bottomtabbar.BlankFragment;
import com.afei.bottomtabbar.DBHelper.DBHelper;
import com.afei.bottomtabbar.R;
import com.afei.bottomtabbar.Utils.BaseFragmentPagerAdapter;
import com.afei.bottomtabbar.Utils.FragmentChanger;
import com.afei.bottomtabbar.fragment.create;
import com.afei.bottomtabbar.fragment.welcome;
import com.afei.bottomtabbar.fragment.sign_stepone;
import com.afei.bottomtabbar.fragment.sign_steptwo;
import com.afei.bottomtabbar.fragment.sign_stepthree;
import com.afei.bottomtabbar.fragment.sign_stepfour;
import com.afei.bottomtabbar.fragment.sign_stepfive;
import com.afei.bottomtabbar.fragment.createNotify;
import com.afei.bottomtabbar.fragment.showSettings;
import com.afei.bottomtabbar.fragment.setInformation;
import com.afei.bottomtabbar.Utils.newFragment;
import com.afei.bottomtabbar.Utils.nextFragment;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Style2Activity extends AppCompatActivity implements  nextFragment{

    private ViewPager mViewPager;
    private RadioGroup mTabRadioGroup;

    private DBHelper database=new DBHelper(Style2Activity.this);
    private List<Fragment> mFragments;
    private BaseFragmentPagerAdapter mAdapter;
    private List<FragmentChanger> pageManager=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        builder.detectFileUriExposure();
        StrictMode.setVmPolicy(builder.build());
        setContentView(R.layout.activity_style2);
        initView();

    }

    private void initView() {
        // find view
        mViewPager = findViewById(R.id.fragment_vp);
        mTabRadioGroup = findViewById(R.id.tabs_rg);

        List<newFragment> pageOneFragments=new ArrayList<>();
        pageOneFragments.add((newFragment)new welcome());
        pageManager.add(new FragmentChanger(pageOneFragments));

        List<newFragment> pageTwoFraments=new ArrayList<>();
        pageTwoFraments.add((newFragment)new create());
        pageTwoFraments.add((newFragment)new createNotify());
        pageManager.add(new FragmentChanger(pageTwoFraments));

        List<newFragment> pageThreeFragments=new ArrayList<>();
        pageThreeFragments.add((newFragment)new sign_stepone());
        pageThreeFragments.add((newFragment)new sign_steptwo());
        pageThreeFragments.add((newFragment)new sign_stepthree());
        pageThreeFragments.add((newFragment)new sign_stepfour());
        pageThreeFragments.add((newFragment)new sign_stepfive());
        pageManager.add(new FragmentChanger(pageThreeFragments));

        List<newFragment> pageFourFragments=new ArrayList<>();
        pageFourFragments.add((newFragment)new showSettings());
        pageFourFragments.add((newFragment)new setInformation());
        pageManager.add(new FragmentChanger(pageFourFragments));

        // init fragment
        mFragments = new ArrayList<>(4);
        mFragments.add(pageManager.get(0).getDefaultFragment());
        mFragments.add(pageManager.get(1).getDefaultFragment());
        mFragments.add(pageManager.get(2).getDefaultFragment());
        mFragments.add(pageManager.get(3).getDefaultFragment());
        // init view pager
        mAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mAdapter);
        // register listener
        mViewPager.addOnPageChangeListener(mPageChangeListener);
        mTabRadioGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);


        final Map<String,String> result=database.select();
        if (result.get("privateKey")==null)
        {
            final EditText inputServer = new EditText(Style2Activity.this);
            AlertDialog.Builder builder = new AlertDialog.Builder(Style2Activity.this);
            builder.setTitle("请输入以太坊私钥").setView(inputServer);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String text = inputServer.getText().toString();
                    database.updatePrivateKey(text,result.get("address"));
                }});
                builder.show();
        }
    }

    @Override
    public void sendData(JSONObject data) {
        Handler handler=new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    int type=data.getInt("type");
                    if (type==0)
                    {
                        int now=data.getInt("index");
                        pageManager.get(now).switchToHead(mAdapter,now,data);
                        mViewPager.setCurrentItem(now);
                    }
                    else if (type==1){
                        int now = data.getInt("index");
                        pageManager.get(now).switchToNext(mAdapter, now, data);
                    }
                    else{
                        int now = data.getInt("index");;
                        pageManager.get(now).switchTo(mAdapter, now, data,data.getInt("fragment"));
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewPager.removeOnPageChangeListener(mPageChangeListener);
    }

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            RadioButton radioButton = (RadioButton) mTabRadioGroup.getChildAt(position);
            radioButton.setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            for (int i = 0; i < group.getChildCount(); i++) {
                if (group.getChildAt(i).getId() == checkedId) {
                    mViewPager.setCurrentItem(i);
                    return;
                }
            }
        }
    };

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mList;

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.mList = list;
        }

        @Override
        public Fragment getItem(int position) {
            return this.mList == null ? null : this.mList.get(position);
        }

        @Override
        public int getCount() {
            return this.mList == null ? 0 : this.mList.size();
        }
    }

}
