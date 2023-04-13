package com.afei.bottomtabbar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.afei.bottomtabbar.R;
import com.afei.bottomtabbar.Utils.newFragment;
import com.afei.bottomtabbar.Utils.nextFragment;
import com.afei.bottomtabbar.fragment.component.createFragment;

import org.json.JSONObject;
import org.json.JSONTokener;

public class createNotify extends createFragment implements newFragment{

        private Button clickButton;
        private TextView contractID;
        private TextView contractAddress;
        private TextView hash;

        public Fragment newInstance()
        {
            return new createNotify();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view=inflater.inflate(R.layout.fragment_create_success,container,false);
            clickButton=view.findViewById(R.id.create_success_btn);
            contractID=view.findViewById(R.id.ID);
            contractAddress=view.findViewById(R.id.address);
            hash=view.findViewById(R.id.hash);
            String data=getArguments().getString("data");
            try {
                JSONObject json=new JSONObject(new JSONTokener(data)).getJSONObject("data");
                contractID.setText(json.getString("contractID"));
                contractAddress.setText(json.getString("contractAddress"));
                hash.setText(json.getString("hash"));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            clickButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendData(new JSONObject());
                }
            });
            return  view;
        }
}
