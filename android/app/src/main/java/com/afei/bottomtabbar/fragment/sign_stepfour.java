package com.afei.bottomtabbar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.afei.bottomtabbar.DBHelper.DBHelper;
import com.afei.bottomtabbar.HttpTools.Session;
import com.afei.bottomtabbar.R;
import com.afei.bottomtabbar.Utils.ProjectUtils;
import  com.afei.bottomtabbar.Utils.newFragment;
import com.afei.bottomtabbar.Utils.nextFragment;
import com.afei.bottomtabbar.contract.ShareSecret;
import com.afei.bottomtabbar.fragment.component.signFragment;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;

import java.math.BigInteger;

public class sign_stepfour extends signFragment implements  newFragment{

    private LinearLayout v1;
    private LinearLayout v2;
    private LinearLayout v3;
    private LinearLayout v4;
    private TextView t1;
    private TextView t2;
    private TextView t3;
    private TextView t4;
    private Button button;
    private TextView contractID;
    private  QMUITipDialog tipDialog;

    private Web3j web3j;
    private JSONObject savedJson;
    private DBHelper database;
    private Session session=new Session();

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1: {
                    v1.setVisibility(View.VISIBLE);
                    break;
                }
                case 2: {
                    v2.setVisibility(View.VISIBLE);
                    break;
                }
                case 3:{
                    v3.setVisibility(View.VISIBLE);
                    break;
                }
                case 4:{
                    v4.setVisibility(View.VISIBLE);
                    break;
                }
            }
        }
    };

    public Fragment newInstance()
    {
        return  new sign_stepfour();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_contract_4, container, false);
        v1=view.findViewById(R.id.signView);
        v2=view.findViewById(R.id.depositView);
        v3=view.findViewById(R.id.claimView);
        v4=view.findViewById(R.id.refundView);
        t1=view.findViewById(R.id.SignHash);
        t2=view.findViewById(R.id.DepositHash);
        t3=view.findViewById(R.id.ClaimHash);
        t4=view.findViewById(R.id.RefundHash);
        contractID=view.findViewById(R.id.contact_id_4);
        button=view.findViewById(R.id.next_tap_4);
        v1.setVisibility(View.GONE);
        v2.setVisibility(View.GONE);
        v3.setVisibility(View.GONE);
        v4.setVisibility(View.GONE);
        tipDialog = new QMUITipDialog.Builder(view.getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("验证中...")
                .create();
        database=new DBHelper(view.getContext());
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
                            web3j = Web3j.build(new HttpService(ProjectUtils.web3Address));
                            JSONObject json = new JSONObject();
                            json.put("contractID", savedJson.getString("contractID"));
                            json.put("address", database.select().get("address"));
                            json.put("currentStatus", 1);
                            JSONObject json1 = session.postJSON(ProjectUtils.apiURL("/api/contract/perform"), json);
                            String contractAddress=json1.getString("contractAddress");
                            ShareSecret secret=ShareSecret.load(contractAddress, web3j, Credentials.create(database.select().get("privateKey")), new DefaultGasProvider());
                            json.put("currentStatus",3);

                            try {
                            json.put("step",1);
                            session.postJSON(ProjectUtils.apiURL("/api/contract/perform"), json);
                            String hash1=secret.sign().send().getTransactionHash();
                            t1.setText(hash1);
                            }
                            catch (Exception e)
                            {
                                ;
                            }
                            Message message1=new Message();
                            message1.what=1;
                            handler.sendMessage(message1);

                            try {
                                json.put("step", 2);
                                session.postJSON(ProjectUtils.apiURL("/api/contract/perform"), json);
                                BigInteger value = Convert.toWei("" + (2), Convert.Unit.ETHER).toBigInteger();
                                String hash2 = secret.Deposit(value).send().getTransactionHash();
                                t2.setText(hash2);
                            }
                            catch (Exception e)
                            {

                            }
                            Message message2=new Message();
                            message2.what=2;
                            handler.sendMessage(message2);

                            try {
                                json.put("step", 3);
                                session.postJSON(ProjectUtils.apiURL("/api/contract/perform"), json);
                                String hash3=secret.Claim(Boolean.TRUE).send().getTransactionHash();
                                t3.setText(hash3);
                            }
                            catch (Exception e)
                            {

                            }
                            Message message3=new Message();
                            message3.what=3;
                            handler.sendMessage(message3);

                            try {
                                json.put("step", 4);
                                session.postJSON(ProjectUtils.apiURL("/api/contract/perform"), json);
                                String hash4=secret.refund().send().getTransactionHash();
                                t4.setText(hash4);
                            }catch (Exception e)
                            {

                            }
                            Message message4=new Message();
                            message4.what=4;
                            handler.sendMessage(message4);

                            json.put("step",5);
                            session.postJSON(ProjectUtils.apiURL("/api/contract/perform"), json);

                            sendData(savedJson);
                        }catch (Exception e)
                        {
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
