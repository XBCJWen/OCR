package com.example.ocr.Adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.ocr.frame.BaseFrame;

import java.util.List;

/**
 * Fragment 滑动适配器
 * BaseFragment 为自定义的 Fragment 基类。
 */
public class PagerAdapter_ji extends FragmentPagerAdapter {

    private List<BaseFrame> mFragmentList;

    public PagerAdapter_ji(FragmentManager fm, List<BaseFrame> fragmentList) {
        super(fm);
        this.mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }

}