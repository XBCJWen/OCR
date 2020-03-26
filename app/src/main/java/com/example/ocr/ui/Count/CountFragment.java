package com.example.ocr.ui.Count;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ocr.DB.DataBaseOpenHelper;
import com.example.ocr.R;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class CountFragment extends Fragment implements View.OnClickListener {


    private WebView ecarts;
    private View root;
    private TextView tvLine;
    private TextView tvPie;
    private TextView tvBar;

    private DataBaseOpenHelper db;
    private WebSettings webSettings;
    private Spinner spinnerEcharts;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_count, container, false);
        initData();
        showPieChart();
        event();
        return root;
    }

    private void event() {
        spinnerEcharts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch ((int) id){
                    case 0:
                        String createUser = "create table userphone(user integer primary key,name text,total text,i integer)";
//                String createWater = "create table water(username text ,moneyTotal text,UserTotal text,moneyCategory text,money text,jitime text,foreign key(username) references money(username))";
//                String createMoney = "create table money(username text primary key,total text)";

                        //设置切换后的图形
                        tvLine.setBackground(getResources().getDrawable(R.drawable.line_back));
                        tvPie.setBackground(getResources().getDrawable(R.drawable.pieback));
                        tvBar.setBackground(getResources().getDrawable(R.drawable.pieback));

                        ecarts.loadUrl("file:///android_asset/main.html");

                        //读取数据
                        ecarts.setWebViewClient(new WebViewClient() {
                            @Override
                            public void onPageFinished(WebView view, String url) {
                                List<String> xData = new ArrayList<>();
                                List<String> inData = new ArrayList<>();
                                List<String> outData = new ArrayList<>();

                                String sqlQuery = "SELECT moneyCategory,sum(money) as toatalmoney,jitime FROM water  group by jitime,moneyCategory";

                                db = DataBaseOpenHelper.getInstance(getContext(), "ocrSql", 1, new ArrayList<String>());
                                Cursor cursor = db.query(sqlQuery);
                                String starttime = "";
                                while (cursor.moveToNext()) {
                                    String category = cursor.getString(0);
                                    String money = cursor.getString(1);
                                    String time = cursor.getString(2);
                                    if (time.equals(starttime)) {

                                    } else {
                                        xData.add(String.format("'%s'", time));
                                    }
                                    starttime = time;
                                    if (category.equals("收入")) {
                                        inData.add(money);
                                    } else {
                                        outData.add(money);
                                    }

                                }
                                //调用脚本并传参
                                ecarts.loadUrl("javascript:initLine(" + inData + "," + outData + "," + xData + ")");
                                super.onPageFinished(view, url);
                            }
                        });
                        break;
                    case 1:
                        tvBar.setBackground(getResources().getDrawable(R.drawable.line_back));
                        tvLine.setBackground(getResources().getDrawable(R.drawable.pieback));
                        tvPie.setBackground(getResources().getDrawable(R.drawable.pieback));

                        ecarts.loadUrl("file:///android_asset/mainBar.html");
                        ecarts.setWebViewClient(new WebViewClient() {
                            @Override
                            public void onPageFinished(WebView view, String url) {
                                List<String> xData = new ArrayList<>();
                                List<String> inData = new ArrayList<>();
                                List<String> outData = new ArrayList<>();

                                String sqlQuery = "SELECT moneyCategory,sum(money) as toatalmoney,jitime FROM water  group by jitime,moneyCategory";

                                db = DataBaseOpenHelper.getInstance(getContext(), "ocrSql", 1, new ArrayList<String>());
                                Cursor cursor = db.query(sqlQuery);
                                String starttime = "";
                                while (cursor.moveToNext()) {
                                    String category = cursor.getString(0);
                                    String money = cursor.getString(1);
                                    String time = cursor.getString(2);
                                    if (time.equals(starttime)) {

                                    } else {
                                        xData.add(String.format("'%s'", time));
                                    }
                                    starttime = time;
                                    if (category.equals("收入")) {
                                        inData.add(money);
                                    } else {
                                        outData.add(money);
                                    }

                                }
                                //调用脚本并传参
                                ecarts.loadUrl("javascript:initLine(" + inData + "," + outData + "," + xData + ")");
                                super.onPageFinished(view, url);
                            }
                        });
                        break;
                    case 2:
                        tvPie.setBackground(getResources().getDrawable(R.drawable.line_back));
                        tvLine.setBackground(getResources().getDrawable(R.drawable.pieback));
                        tvBar.setBackground(getResources().getDrawable(R.drawable.pieback));

                        ecarts.loadUrl("file:///android_asset/mainPie.html");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initData() {
        ecarts = root.findViewById(R.id.wv_main);
        tvLine = root.findViewById(R.id.tv_line);
        tvLine.setOnClickListener(this);
        tvPie = root.findViewById(R.id.tv_pie);
        tvPie.setOnClickListener(this);
        tvBar = root.findViewById(R.id.tv_bar);
        tvBar.setOnClickListener(this);
        spinnerEcharts = root.findViewById(R.id.spinnerEcharts);
        webSettings = ecarts.getSettings();
        ArrayList<String> list = new ArrayList<>();
        list.add("线性图");
        list.add("柱状图");
        list.add("饼状图");

        ArrayAdapter adapter =new ArrayAdapter(getContext(),R.layout.spinner_items_ecaharts,list);
        spinnerEcharts.setAdapter(adapter);

        setinit();


    }

    private void showPieChart() {
        //开启脚本支持
        webSettings.setJavaScriptEnabled(true);
        //读取JS节点
        webSettings.setDomStorageEnabled(true);
        //允许网页页面弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        ecarts.loadUrl("file:///android_asset/main.html");
        ecarts.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                List<String> xData = new ArrayList<>();
                List<String> inData = new ArrayList<>();
                List<String> outData = new ArrayList<>();

                String sqlQuery = "SELECT moneyCategory,sum(money) as toatalmoney,jitime FROM water  group by jitime,moneyCategory";

                db = DataBaseOpenHelper.getInstance(getContext(), "ocrSql", 1, new ArrayList<String>());
                Cursor cursor = db.query(sqlQuery);
                String starttime = "";
                while (cursor.moveToNext()) {
                    String category = cursor.getString(0);
                    String money = cursor.getString(1);
                    String time = cursor.getString(2);
                    if (time.equals(starttime)) {

                    } else {
                        xData.add(String.format("'%s'", time));
                    }
                    starttime = time;
                    if (category.equals("收入")) {
                        inData.add(money);
                    } else {
                        outData.add(money);
                    }

                }
                //调用脚本并传参
                ecarts.loadUrl("javascript:initLine(" + inData + "," + outData + "," + xData + ")");
                super.onPageFinished(view, url);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_line:
//                String createUser = "create table userphone(user integer primary key,name text,total text,i integer)";
//                String createWater = "create table water(username text ,moneyTotal text,UserTotal text,moneyCategory text,money text,jitime text,foreign key(username) references money(username))";
//                String createMoney = "create table money(username text primary key,total text)";

                //设置切换后的图形
                tvLine.setBackground(getResources().getDrawable(R.drawable.line_back));
                tvPie.setBackground(getResources().getDrawable(R.drawable.pieback));
                tvBar.setBackground(getResources().getDrawable(R.drawable.pieback));

                ecarts.loadUrl("file:///android_asset/main.html");

                //读取数据
                ecarts.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        List<String> xData = new ArrayList<>();
                        List<String> inData = new ArrayList<>();
                        List<String> outData = new ArrayList<>();

                        String sqlQuery = "SELECT moneyCategory,sum(money) as toatalmoney,jitime FROM water  group by jitime,moneyCategory";

                        db = DataBaseOpenHelper.getInstance(getContext(), "ocrSql", 1, new ArrayList<String>());
                        Cursor cursor = db.query(sqlQuery);
                        String starttime = "";
                        while (cursor.moveToNext()) {
                            String category = cursor.getString(0);
                            String money = cursor.getString(1);
                            String time = cursor.getString(2);
                            if (time.equals(starttime)) {

                            } else {
                                xData.add(String.format("'%s'", time));
                            }
                            starttime = time;
                            if (category.equals("收入")) {
                                inData.add(money);
                            } else {
                                outData.add(money);
                            }

                        }
                        //调用脚本并传参
                        ecarts.loadUrl("javascript:initLine(" + inData + "," + outData + "," + xData + ")");
                        super.onPageFinished(view, url);
                    }
                });
                break;

            case R.id.tv_bar:
                tvBar.setBackground(getResources().getDrawable(R.drawable.line_back));
                tvLine.setBackground(getResources().getDrawable(R.drawable.pieback));
                tvPie.setBackground(getResources().getDrawable(R.drawable.pieback));

                ecarts.loadUrl("file:///android_asset/mainBar.html");
                ecarts.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        List<String> xData = new ArrayList<>();
                        List<String> inData = new ArrayList<>();
                        List<String> outData = new ArrayList<>();

                        String sqlQuery = "SELECT moneyCategory,sum(money) as toatalmoney,jitime FROM water  group by jitime,moneyCategory";

                        db = DataBaseOpenHelper.getInstance(getContext(), "ocrSql", 1, new ArrayList<String>());
                        Cursor cursor = db.query(sqlQuery);
                        String starttime = "";
                        while (cursor.moveToNext()) {
                            String category = cursor.getString(0);
                            String money = cursor.getString(1);
                            String time = cursor.getString(2);
                            if (time.equals(starttime)) {

                            } else {
                                xData.add(String.format("'%s'", time));
                            }
                            starttime = time;
                            if (category.equals("收入")) {
                                inData.add(money);
                            } else {
                                outData.add(money);
                            }

                        }
                        //调用脚本并传参
                        ecarts.loadUrl("javascript:initLine(" + inData + "," + outData + "," + xData + ")");
                        super.onPageFinished(view, url);
                    }
                });
                break;

            case R.id.tv_pie:
                tvPie.setBackground(getResources().getDrawable(R.drawable.line_back));
                tvLine.setBackground(getResources().getDrawable(R.drawable.pieback));
                tvBar.setBackground(getResources().getDrawable(R.drawable.pieback));

                ecarts.loadUrl("file:///android_asset/mainPie.html");
                ecarts.setWebViewClient(new WebViewClient(){
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        List<JsonObject> list = new ArrayList<>();
                        Cursor cursor = db.query("money","money");
                        while (cursor.moveToNext()){
                            JsonObject json = new JsonObject();

                            String userName = cursor.getString(0);
                            String money = cursor.getString(1);
                            json.addProperty("value",money);
                            json.addProperty("name",userName);

                            list.add(json);
//                            initPie([{value:34,name:'er'},{value:4,name:'ab'},{value:374,name:'cd'}]);

                        }

                        ecarts.loadUrl("javascript:initPie(" + list + ")");

                        super.onPageFinished(view, url);
                    }
                });
//                ecarts.loadUrl("javascript:initPie();");
                break;


        }
    }

    private void setinit() {
        tvLine.setBackground(getResources().getDrawable(R.drawable.line_back));
        tvPie.setBackground(getResources().getDrawable(R.drawable.pieback));
        tvBar.setBackground(getResources().getDrawable(R.drawable.pieback));
    }
}