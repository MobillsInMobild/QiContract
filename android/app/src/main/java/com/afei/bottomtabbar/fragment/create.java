package com.afei.bottomtabbar.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.afei.bottomtabbar.DBHelper.DBHelper;
import com.afei.bottomtabbar.HttpTools.Session;
import com.afei.bottomtabbar.R;
import com.afei.bottomtabbar.Utils.ProjectUtils;
import com.afei.bottomtabbar.Utils.nextFragment;
import com.afei.bottomtabbar.contract.ShareSecret;
import com.afei.bottomtabbar.Utils.newFragment;
import com.afei.bottomtabbar.fragment.component.createFragment;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.Map;

public class create extends createFragment implements  newFragment{
    private EditText count;
    private EditText name;
    private EditText content;
    private EditText tip;
    private EditText userGroup;
    private Button button;
    private DBHelper database;
    private Session session = new Session();
    QMUITipDialog tipDialog;
    Toast errorMessage;

    AlertDialog.Builder builder;
    public Fragment newInstance()
    {
        return  new create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_contract, container, false);
        count = view.findViewById(R.id.count);
        name = view.findViewById(R.id.contact_name);
        content = view.findViewById(R.id.contact_content);
        tip = view.findViewById(R.id.contact_tip);
        button = view.findViewById(R.id.create_contract);
        userGroup=view.findViewById(R.id.userGroup);
        database = new DBHelper(view.getContext());
        userGroup.setText(String.format("[\"%s\"]",database.select().get("address")));
        tipDialog = new QMUITipDialog.Builder(view.getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("提交中...")
                .create();
        errorMessage = Toast.makeText(view.getContext(),"创建失败",Toast.LENGTH_LONG);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONArray temp=new JSONArray(new JSONTokener(userGroup.getText().toString()));
                            Map<String, String> result = database.select();
                            Web3j web3j = Web3j.build(new HttpService(ProjectUtils.web3Address));
                            ContractGasProvider contractGasProvider = new DefaultGasProvider();
                            Credentials credentials = Credentials.create(result.get("privateKey"));
                            ShareSecret contract = ShareSecret.deploy(web3j, credentials, contractGasProvider, new BigInteger(count.getText().toString())).send();
                            String contractAddress = contract.getContractAddress();
                            String hash = contract.getTransactionReceipt().get().getTransactionHash();
                            System.out.println(contractAddress);
                            System.out.println(hash);
                            JSONObject json = new JSONObject();
                            json.put("address", result.get("address"));
                            json.put("title", name.getText());
                            json.put("content", content.getText());
                            json.put("userGroup",userGroup.getText());
                            json.put("tip", tip.getText());
                            json.put("contractAddress", contractAddress);
                            json.put("num", Integer.parseInt(count.getText().toString()));
                            json = session.postJSON(ProjectUtils.apiURL("/api/contract/new"), json);
                            json.put("contractAddress",contractAddress);
                            json.put("hash",hash);
                            sendData(json);
                        } catch (Exception e) {
                            e.printStackTrace();
                            errorMessage.show();
                        } finally {
                            tipDialog.dismiss();
                        }
                    }
                }).start();
            }
        });
        return view;
    }

}