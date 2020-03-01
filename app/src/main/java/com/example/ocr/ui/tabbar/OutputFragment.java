package com.example.ocr.ui.tabbar;


import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.ocr.R;
import com.example.ocr.frame.BaseFrame;

/**
 * A simple {@link Fragment} subclass.
 */
public class OutputFragment extends BaseFrame {

    @Override
    public View initView() {
        Log.e("TAG", "Fragment1 --> initView");
        View view = View.inflate(mContext, R.layout.fragment_output, null);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        // ......加载数据
        Log.e("TAG", "Fragment1 --> initData");
    }
}
