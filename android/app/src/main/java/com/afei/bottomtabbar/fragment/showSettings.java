package com.afei.bottomtabbar.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.afei.bottomtabbar.DBHelper.DBHelper;
import com.afei.bottomtabbar.HttpTools.Session;
import com.afei.bottomtabbar.MainActivity;
import com.afei.bottomtabbar.R;
import com.afei.bottomtabbar.Utils.ProjectUtils;
import com.afei.bottomtabbar.Utils.newFragment;
import com.afei.bottomtabbar.fragment.component.settingFragment;
import com.afei.bottomtabbar.style2.Style2Activity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class showSettings extends settingFragment implements newFragment {

    private Button button;
    private Button changePrivateKey;
    private TextView currentTime;
    private TextView name;
    private TextView tips;
    private TextView email;
    private TextView phone;
    private TextView address;
    private Toast successMessage;

    private Session session=new Session();
    private DBHelper database;
    private JSONObject json;

    public Fragment newInstance()
    {
        return  new showSettings();
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                tips.setText(json.getString("tips"));
                name.setText(json.getString("name"));
                email.setText(json.getString("email"));
                phone.setText(json.getString("phone"));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.individual, container, false);
        database=new DBHelper(view.getContext());
        button=view.findViewById(R.id.in_button);
        currentTime=view.findViewById(R.id.in_time);
        tips=view.findViewById(R.id.in_tip);
        name=view.findViewById(R.id.in_name);
        phone=view.findViewById(R.id.in_phone);
        email=view.findViewById(R.id.in_email);
        address=view.findViewById(R.id.in_address);
        changePrivateKey=view.findViewById(R.id.changePrivateKey);

        successMessage = Toast.makeText(view.getContext(),"修改成功",Toast.LENGTH_LONG);
        address.setText(database.select().get("address"));
        Date now=new Date();
        SimpleDateFormat f=new SimpleDateFormat("MM-dd");
        currentTime.setText(f.format(now));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String address=database.select().get("address");
                    json=new JSONObject();
                    json.put("address",address);
                    json=session.postJSON(ProjectUtils.apiURL("/api/user/details"),json);
                    handler.sendMessage(new Message());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData(new JSONObject());
            }
        });
        changePrivateKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Map<String,String> result=database.select();
                final EditText inputServer = new EditText(view.getContext());
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("请输入以太坊私钥").setView(inputServer);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String text = inputServer.getText().toString();
                        database.updatePrivateKey(text,result.get("address"));
                        successMessage.show();
                    }});
                builder.show();
            }
        });
        return view;
    }
}