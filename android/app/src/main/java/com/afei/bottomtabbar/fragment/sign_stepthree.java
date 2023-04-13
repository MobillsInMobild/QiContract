package com.afei.bottomtabbar.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
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
import com.afei.bottomtabbar.Utils.bitmapHelper;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.json.JSONObject;
import org.json.JSONTokener;

public class sign_stepthree extends signFragment implements  newFragment{

    private SignaturePad signature;
    private Button button;
    private QMUITipDialog tipDialog;
    private TextView contractID;

    private Session session=new Session();
    private DBHelper database;
    private JSONObject savedJson;

    public Fragment newInstance()
    {
        return  new sign_stepthree();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_contract_3, container, false);
        database=new DBHelper(view.getContext());
        tipDialog = new QMUITipDialog.Builder(view.getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("签署中...")
                .create();
        signature=view.findViewById(R.id.signature_pad);
        button=view.findViewById(R.id.next_tap_4);
        contractID=view.findViewById(R.id.contact_id_4);
        try {
            savedJson=new JSONObject(new JSONTokener(getArguments().getString("data"))).getJSONObject("data");
            contractID.setText(savedJson.getString("contractID"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Bitmap bitmap=signature.getSignatureBitmap();
                            bitmap=Bitmap.createScaledBitmap(bitmap,800,160,true);
                            JSONObject json=new JSONObject();
                            json.put("address",database.select().get("address"));
                            json.put("contractID",savedJson.getString("contractID"));
                            json.put("image","0123456789012345678901"+bitmapHelper.bitmapToBase64(bitmap).replaceAll("[\\s*\t\n\r]", ""));
                            session.postJSON(ProjectUtils.apiURL("/api/contract/upload"),json);
                            json=new JSONObject();
                            json.put("contractID",savedJson.getString("contractID"));
                            json.put("address",database.select().get("address"));
                            json.put("currentStatus",2);
                            json=session.postJSON(ProjectUtils.apiURL("/api/contract/perform"),json);
                            System.out.println(json);
                            sendData(savedJson);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        tipDialog.dismiss();
                    }
                }).start();
            }
        });
        return view;
    }
}
