package com.afei.bottomtabbar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.afei.bottomtabbar.DBHelper.DBHelper;
import com.afei.bottomtabbar.HttpTools.Session;
import com.afei.bottomtabbar.MainActivity;
import com.afei.bottomtabbar.R;
import com.afei.bottomtabbar.Utils.ProjectUtils;
import  com.afei.bottomtabbar.Utils.newFragment;
import com.afei.bottomtabbar.Utils.nextFragment;
import  com.afei.bottomtabbar.fragment.component.signFragment;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Map;

public class sign_stepone extends signFragment implements  newFragment{

    private EditText contratID;
    private Button button;
    private  QMUITipDialog tipDialog;

    private Session session = new Session();
    private DBHelper database;
    private Toast errorMessage;

    public Fragment newInstance()
    {
        return  new sign_stepone();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_contract_1, container, false);
        contratID=view.findViewById(R.id.contract_id_1);
        button=view.findViewById(R.id.next_tap_1);
        tipDialog = new QMUITipDialog.Builder(view.getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("查询中...")
                .create();
        errorMessage = Toast.makeText(view.getContext(),"合同不存在或者非法用户！",Toast.LENGTH_LONG);
        database = new DBHelper(view.getContext());
        try {
            JSONObject json=new JSONObject(new JSONTokener(getArguments().getString("data")));
            String id=json.getJSONObject("data").getString("data");
            contratID.setText(id);
        }
        catch (Exception e)
        {
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String address=database.select().get("address");
                            String id=contratID.getText().toString();
                            JSONObject json=new JSONObject();
                            json.put("address",address);
                            json.put("contractID",id);
                            System.out.println(json);
                            json=session.postJSON(ProjectUtils.apiURL("/api/contract/query"),json);
                            int currentStatus=json.getInt("currentStatus");
                            if (currentStatus==0)
                            {
                                JSONObject json1=new JSONObject();
                                json1.put("contractID",id);
                                json1.put("address",address);
                                json1.put("currentStatus",currentStatus);
                                json1.put("privateKey","hide");
                                System.out.println("result:"+json1);
                                session.postJSON(ProjectUtils.apiURL("/api/contract/perform"),json1);
                                currentStatus=1;
                            }
                            json.put("contractID",id);
                            if (currentStatus<0)
                            {
                                throw new RuntimeException();
                            }
                            switchToFragment(json,currentStatus);
                        }
                        catch (Exception e)
                        {
                            errorMessage.show();
                            e.printStackTrace();
                        }
                        finally {
                            tipDialog.dismiss();
                        }
                    }
                }).start();
            }
        });
        return view;
    }
}
