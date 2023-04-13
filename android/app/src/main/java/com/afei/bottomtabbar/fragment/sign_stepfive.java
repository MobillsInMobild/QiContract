package com.afei.bottomtabbar.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.afei.bottomtabbar.HttpTools.Session;
import com.afei.bottomtabbar.R;
import com.afei.bottomtabbar.Utils.ProjectUtils;
import  com.afei.bottomtabbar.Utils.newFragment;
import com.afei.bottomtabbar.Utils.nextFragment;
import com.afei.bottomtabbar.fragment.component.signFragment;
import com.joanzapata.pdfview.PDFView;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;

public class sign_stepfive extends signFragment implements  newFragment{

    private JSONObject savedJson;
    private String contractID;
    private Session session=new Session();

    private QMUITipDialog tipDialog;
    private Button button;
    private Toast successMessage;
    private TextView filepath;
    private PDFView pdfView;
    private Button openLocal;
    private Button nextTap;

    private String pdfPath;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            System.out.println(pdfPath);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(pdfPath)), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            try {
                startActivity(intent);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            //pdfView.fromFile(new File(pdfPath)).load();
        }};

    public Fragment newInstance()
    {
        return  new sign_stepfive();
    }

    private void downloadFile(final String url){
        //下载路径，如果路径无效了，可换成你的下载路径
        final long startTime = System.currentTimeMillis();

        Request request = new Request.Builder().url(url).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Sink sink = null;
                BufferedSink bufferedSink = null;
                try {
                    String mSDCardPath= Environment.getExternalStorageDirectory().getAbsolutePath();
                    File dest = new File(mSDCardPath+"/download",   url.substring(url.lastIndexOf("/") + 1));
                    sink = Okio.sink(dest);
                    bufferedSink = Okio.buffer(sink);
                    bufferedSink.writeAll(response.body().source());

                    bufferedSink.close();
                    pdfPath= dest.getAbsolutePath();
                    successMessage.show();
                    handler.sendMessage(new Message());

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if(bufferedSink != null){
                        bufferedSink.close();
                    }

                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_contract_5, container, false);
        tipDialog = new QMUITipDialog.Builder(view.getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("下载中...")
                .create();
        try {
            savedJson=new JSONObject(new JSONTokener(getArguments().getString("data"))).getJSONObject("data");
            contractID=savedJson.getString("contractID");
            System.out.println(contractID);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        successMessage = Toast.makeText(view.getContext(),"下载成功",Toast.LENGTH_LONG);
        button=view.findViewById(R.id.getPDF);
        filepath=view.findViewById(R.id.filePath);
        //pdfView=view.findViewById(R.id.pdfview);
        nextTap=view.findViewById(R.id.sign_success);
        openLocal=view.findViewById(R.id.getPDFOffline);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //  downloadPDF();

            }
        });
        nextTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData(new JSONObject());
            }
        });
        openLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadPDF();
                }
        });
        return view;
    }
    private void downloadPDF()
    {
        tipDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject json=new JSONObject();
                    json.put("contractID",contractID);
                    json=session.postJSON(ProjectUtils.apiURL("/api/contract/get"),json);
                    System.out.println(ProjectUtils.apiURL(json.getString("data")));
                    downloadFile(ProjectUtils.apiURL(json.getString("data")));

                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                tipDialog.dismiss();
            }
        }).start();
    }
}
