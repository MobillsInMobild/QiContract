package com.afei.bottomtabbar.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.afei.bottomtabbar.DBHelper.DBHelper;
import com.afei.bottomtabbar.HttpTools.Session;
import com.afei.bottomtabbar.R;
import com.afei.bottomtabbar.Utils.ProjectUtils;
import com.afei.bottomtabbar.Utils.newFragment;
import com.afei.bottomtabbar.fragment.component.settingFragment;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.json.JSONObject;

public class setInformation extends settingFragment implements newFragment {

    private TextView name;
    private TextView phone;
    private TextView email;
    private TextView tips;
    private Button button;
    private Toast successMessage;
    private QMUITipDialog tipDialog;

    private DBHelper database;
    private Session session=new Session();

    public Fragment newInstance()
    {
        return  new setInformation();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.individual_set, container, false);
        name=view.findViewById(R.id.set_name);
        phone=view.findViewById(R.id.set_number);
        email=view.findViewById(R.id.set_email);
        tips=view.findViewById(R.id.set_tip);
        button=view.findViewById(R.id.setButton);

        database=new DBHelper(view.getContext());
        successMessage = Toast.makeText(view.getContext(),"修改成功",Toast.LENGTH_LONG);
        tipDialog = new QMUITipDialog.Builder(view.getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("操作中...")
                .create();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    tipDialog.show();
                    JSONObject json=new JSONObject();
                    json.put("name",name.getText());
                    json.put("email",email.getText());
                    json.put("tips",tips.getText());
                    json.put("phone",phone.getText());
                    json.put("address",database.select().get("address"));
                    session.postJSON(ProjectUtils.apiURL("/api/user/update"),json);
                    successMessage.show();
                    tipDialog.dismiss();
                    sendData(new JSONObject());
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally {
                    if (tipDialog.isShowing())
                        tipDialog.dismiss();
                }
            }
        });
        return view;
    }
}