package com.example.ocr;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.ocr.Adapter.PagerAdapter_ji;
import com.example.ocr.frame.BaseFrame;
import com.example.ocr.ui.tabbar.InputFragment;
import com.example.ocr.ui.tabbar.OutputFragment;

import java.util.ArrayList;
import java.util.List;

public class JiActivity extends AppCompatActivity implements View.OnClickListener {

    TextView text0;
    TextView text1;
    ImageView tab_line;
    ViewPager mViewPager;

    private int screenWidth;
    private List<BaseFrame> mFragmentList = new ArrayList<>();
    private  PagerAdapter_ji adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ji);
        init();
        acBar();
        initData(); // 初始化数据
        initWidth(); // 初始化滑动横条的宽度
        setListener(); // 设置监听器


    }

    private void init() {
        mViewPager = findViewById(R.id.main_pager);
        text0 = findViewById(R.id.tv_input);
        text1 = findViewById(R.id.tv_output);
        tab_line = findViewById(R.id.main_tab_line);

    }

    private void initData() {
        // 将我们自定义 Fragment 的对象添加到 List<BaseFragment> 中。
        mFragmentList.add(new InputFragment());
        mFragmentList.add(new OutputFragment());
        adapter = new PagerAdapter_ji(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);
        text0.setTextColor(Color.BLUE);
    }

    private void setListener() {

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /**
             * This method will be invoked when the current page is scrolled, either as part
             * of a programmatically initiated smooth scroll or a user initiated touch scroll.
             *
             * @param position Position index of the first page currently being displayed.
             *                 Page position+1 will be visible if positionOffset is nonzero.
             * @param positionOffset Value from [0, 1) indicating the offset from the page at position.
             * @param positionOffsetPixels Value in pixels indicating the offset from position.
             *                             这个参数的使用是为了在滑动页面时有文字下方横条的滑动效果
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tab_line.getLayoutParams();
                lp.leftMargin = screenWidth / 2 * position + positionOffsetPixels / 2;
                tab_line.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                // 在每次切换页面时重置 TextView 的颜色
                resetTextView();
                switch (position) {
                    case 0:
                        text0.setTextColor(Color.BLUE);
                        break;
                    case 1:
                        text1.setTextColor(Color.BLUE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        text0.setOnClickListener(this);
        text1.setOnClickListener(this);


    }

    private void resetTextView() {
        text0.setTextColor(Color.BLACK);
        text1.setTextColor(Color.BLACK);

    }

    // 初始化滑动横条的宽度
    private void initWidth() {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tab_line.getLayoutParams();
        lp.width = screenWidth / 2;
        tab_line.setLayoutParams(lp);
    }

    // 设置文字的点击事件，点击某个 TextView 就跳到相应页面
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_input:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.tv_output:
                mViewPager.setCurrentItem(1);
                break;

        }
    }

    private void acBar() {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("记一笔");
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}