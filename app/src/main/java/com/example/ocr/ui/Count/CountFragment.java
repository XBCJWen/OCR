package com.example.ocr.ui.Count;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ocr.R;

public class CountFragment extends Fragment implements View.OnClickListener{


    private WebView ecarts;
    private View root;
    private TextView tvLine;
    private TextView tvPie;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_count, container, false);
        initData();
        showPieChart();
        event();
        return root;
    }

    private void event() {

    }

    private void initData() {
        ecarts = root.findViewById(R.id.wv_main);
        tvLine = root.findViewById(R.id.tv_line);
        tvLine.setOnClickListener(this);
        tvPie = root.findViewById(R.id.tv_pie);
        tvPie.setOnClickListener(this);
        setinit();


    }

    private void showPieChart() {
//        ecarts.getSettings().setAllowFileAccess(true);
        //开启脚本支持
//        ecarts.setInitialScale(100);
        ecarts.getSettings().setJavaScriptEnabled(true);
//        ecarts.loadUrl("file:////android_asset/echarts.min.js");
        ecarts.loadUrl("file:///android_asset/main.html");
        ecarts.loadUrl("javascript:initLine();");

//        ecarts.loadUrl("javascript:createChart();");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_line:
                tvLine.setBackground(getResources().getDrawable(R.drawable.line_back));
                tvPie.setBackground(getResources().getDrawable(R.drawable.pieback));
                ecarts.loadUrl("file:///android_asset/main.html");
                ecarts.loadUrl("javascript:initLine();");

                break;
            case R.id.tv_pie:
                tvPie.setBackground(getResources().getDrawable(R.drawable.line_back));
                tvLine.setBackground(getResources().getDrawable(R.drawable.pieback));
                ecarts.loadUrl("file:///android_asset/mainPie.html");
                ecarts.loadUrl("javascript:initPie();");


                break;
        }
    }

    private void setinit() {
        tvLine.setBackground(getResources().getDrawable(R.drawable.line_back));
        tvPie.setBackground(getResources().getDrawable(R.drawable.pieback));
    }
}