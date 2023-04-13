package com.afei.bottomtabbar.fragment;

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
import com.afei.bottomtabbar.Utils.newFragment;
import com.afei.bottomtabbar.fragment.component.welcomeFragment;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class  welcome extends welcomeFragment implements newFragment
{
    private DBHelper database;

    private View view;
    private  PieChart pie;
    private QMUITipDialog tipDialog;
    private QMUIGroupListView finishedList;
    private QMUIGroupListView unfinishedList;
    private TextView address;
    QMUIGroupListView.Section finishedSection;
    QMUIGroupListView.Section unfinishedSection;
    private List<QMUICommonListItemView> finishedView=new ArrayList<>();
    private List<QMUICommonListItemView> unfinishedView=new ArrayList<>();

    private Session session=new Session();

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v instanceof QMUICommonListItemView) {
                try {
                    String text = ((QMUICommonListItemView) v).getText().toString();
                    JSONObject json=new JSONObject();
                    json.put("data",text);
                    switchToPage(json,2);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            finishedSection=QMUIGroupListView.newSection(view.getContext());
            for (QMUICommonListItemView item:finishedView)
                finishedSection=finishedSection.addItemView(item,onClickListener);
            finishedSection.addTo(finishedList);
            unfinishedSection=QMUIGroupListView.newSection(view.getContext());
            for (QMUICommonListItemView item:unfinishedView)
                unfinishedSection=unfinishedSection.addItemView(item,onClickListener);
            unfinishedSection.addTo(unfinishedList);
        }
    };


    public Fragment newInstance()
    {
        return  new welcome();
    }

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_welcome,container,false);
        pie=view.findViewById(R.id.pie);
        finishedList=view.findViewById(R.id.finishedList);
        unfinishedList=view.findViewById(R.id.unfinishedList);
        database=new DBHelper(view.getContext());
        tipDialog = new QMUITipDialog.Builder(view.getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("初始化个人信息...")
                .create();
        address=view.findViewById(R.id.UserID);
        address.setText(database.select().get("address"));
        finishedView.clear();
        unfinishedView.clear();
        pie.getDescription().setText("合同概况");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject json = new JSONObject();
                    JSONObject tmp = new JSONObject();
                    tmp.put("account", database.select().get("address"));
                    json.put("address",tmp);
                    System.out.println(json);
                    JSONArray data=session.postJSONArtay(ProjectUtils.apiURL("/api/user/contractInfo"),json);
                    System.out.println(data);
                    int finished=0;
                    int unfinished=0;
                    for (int i=0;i<data.length();i++)
                        if (data.getJSONObject(i).getBoolean("status")) {
                            finished += 1;
                            QMUICommonListItemView item=finishedList.createItemView(data.getJSONObject(i).getString("contractID"));
                            item.setOrientation(QMUICommonListItemView.VERTICAL);
                            item.setDetailText(data.getJSONObject(i).getString("title"));
                            item.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
                            finishedView.add(item);
                        }
                        else {
                            unfinished += 1;
                            QMUICommonListItemView item=unfinishedList.createItemView(data.getJSONObject(i).getString("contractID"));
                            item.setOrientation(QMUICommonListItemView.VERTICAL);
                            item.setDetailText(data.getJSONObject(i).getString("title"));
                            item.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
                            unfinishedView.add(item);
                        }
                    List<PieEntry> strings = new ArrayList<>();
                    strings.add(new PieEntry(finished,"已完成"));
                    strings.add(new PieEntry(unfinished,"正在签署"));

                    PieDataSet dataSet = new PieDataSet(strings,"");

                    ArrayList<Integer> colors = new ArrayList<Integer>();
                    colors.add(getResources().getColor(R.color.qmui_config_color_gray_1));
                    colors.add(getResources().getColor(R.color.qmui_config_color_red));
                    dataSet.setColors(colors);
                    PieData pieData = new PieData(dataSet);
                    pieData.setDrawValues(true);

                    pie.setData(pieData);
                    pie.invalidate();
                    while (!tipDialog.isShowing());
                    tipDialog.dismiss();
                    handler.sendMessage(new Message());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
        tipDialog.show();
        return  view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            finishedSection.removeFrom(finishedList);
            finishedSection = null;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try {
            unfinishedSection.removeFrom(unfinishedList);
            unfinishedSection = null;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}