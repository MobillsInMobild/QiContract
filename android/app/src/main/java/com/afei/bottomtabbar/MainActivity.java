package com.afei.bottomtabbar;

import android.Manifest;
import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afei.bottomtabbar.DBHelper.DBHelper;
import com.afei.bottomtabbar.HttpTools.Session;
import com.afei.bottomtabbar.Utils.ProjectUtils;
import com.afei.bottomtabbar.style2.Style2Activity;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.json.JSONObject;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private  Button clickButton;
    private EditText userName;
    private EditText password;
    private Session session=new Session();
    private QMUITipDialog tipDialog;
    private QMUITipDialog failedInfoDialog;
    Toast errorMessage;

    private DBHelper database=new DBHelper(MainActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        clickButton=findViewById(R.id.login_btn);
        userName=findViewById(R.id.userName);
        password=findViewById(R.id.password);
        Map<String,String> result=database.select();
        System.out.println(database.select());
        errorMessage = Toast.makeText(MainActivity.this,"登录失败",Toast.LENGTH_LONG);
        if (result!=null)
        {
            userName.setText(result.get("address"));
            password.setText(result.get("password"));
        }
        clickButton.setOnClickListener(this);
        tipDialog= new QMUITipDialog.Builder(MainActivity.this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("登录中")
                .create();
        failedInfoDialog = new QMUITipDialog.Builder(MainActivity.this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                .setTipWord("登录失败")
                .create();
    }

   @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn: {
                tipDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject j = new JSONObject();
                            j.put("address", userName.getText());
                            j.put("privateKey", password.getText());
                            j = session.postJSON(ProjectUtils.apiURL("/api/login"), j);
                            tipDialog.dismiss();
                            if (j.getBoolean("login")) {
                                Map<String, String> result = database.select();
                                if (result == null || !result.get("address").equals(userName.getText().toString())) {
                                    database.delete();
                                    database.insert(userName.getText().toString(), password.getText().toString(), null);
                                }
                                startActivity(new Intent(MainActivity.this, Style2Activity.class));
                            } else {
                                database.delete();
                                showFailDialog();
                            }

                        } catch (Exception e) {
                            if (tipDialog.isShowing())
                                tipDialog.dismiss();
                            showFailDialog();
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
                break;
            default:
                break;
        }
    }
    private void showFailDialog()
    {
        errorMessage.show();
    }
}
