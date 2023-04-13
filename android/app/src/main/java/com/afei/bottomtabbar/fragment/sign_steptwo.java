package com.afei.bottomtabbar.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.afei.bottomtabbar.DBHelper.DBHelper;
import com.afei.bottomtabbar.HttpTools.Session;
import com.afei.bottomtabbar.R;
import com.afei.bottomtabbar.Utils.ProjectUtils;
import  com.afei.bottomtabbar.Utils.newFragment;
import com.afei.bottomtabbar.Utils.nextFragment;
import com.afei.bottomtabbar.fragment.component.signFragment;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;

public class sign_steptwo extends signFragment implements  newFragment{

    private Session session = new Session();
    private DBHelper database;
    private TextView name;
    private  TextView content;
    private TextView contractID;
    private  TextView tip;
    private  TextView number;
    private Button button;
    private QMUITipDialog tipDialog;
    private JSONObject savedJson;
    private JSONObject json;

    private Handler handler=new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        try {
            name.setText(json.getString("title"));
            content.setText(json.getString("content"));
            tip.setText(json.getString("tip"));
            number.setText(""+json.getInt("num"));
            System.out.println(json);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        //pdfView.fromFile(new File(pdfPath)).load();
    }};

    private String id;

    public Fragment newInstance()
    {
        return  new sign_steptwo();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_contract_2, container, false);
        name=view.findViewById(R.id.contact_name_3);
        contractID=view.findViewById(R.id.contact_id_3);
        content=view.findViewById(R.id.contact_content_3);
        number=view.findViewById(R.id.count_3);
        tip=view.findViewById(R.id.contact_tip_3);
        button=view.findViewById(R.id.next_tap_3);
        database=new DBHelper(view.getContext());
        try {
            savedJson=new JSONObject(new JSONTokener(getArguments().getString("data"))).getJSONObject("data");
            contractID.setText(savedJson.getString("contractID"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        tipDialog = new QMUITipDialog.Builder(view.getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("查询中...")
                .create();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    json=new JSONObject();
                    json.put("contractID",savedJson.getString("contractID"));
                    json.put("address",database.select().get("address"));
                    json.put("currentStatus",1);
                    json=session.postJSON(ProjectUtils.apiURL("/api/contract/perform"),json);
                    handler.sendMessage(new Message());

                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                tipDialog.dismiss();
            }
        }).start();
        tipDialog.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData(savedJson);
            }
        });
        return view;
    }
}
