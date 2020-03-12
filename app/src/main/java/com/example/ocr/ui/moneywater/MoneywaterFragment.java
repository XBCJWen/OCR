package com.example.ocr.ui.moneywater;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ocr.DB.MySQLSeries;
import com.example.ocr.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoneywaterFragment extends Fragment {


    private View root;
    private TextView tvDisplay;
    private Button btnDb;
    private ListView lsMain;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_moneywater, container, false);
        init();


        event();

        return root;
    }

    private void event() {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String d = simpleDateFormat.format(date);

        List<Map<String, Object>> lists = new ArrayList<>();
        int[] imageViews = {R.drawable.output,R.drawable.input};
        Map<String, Object> map = new HashMap<>();
        map.put("user2","微信余额");
        map.put("image",imageViews[0]);
        map.put("usertotal", "0.00");
        map.put("total", "0.00");
        map.put("user", "微信");
        map.put("money", "0.00");
        map.put("date",d);
        lists.add(map);

        Map<String, Object> map1 = new HashMap<>();
        map1.put("user2","支付宝余额");
        map1.put("image",imageViews[1]);
        map1.put("usertotal", "0.00");
        map1.put("total", "0.00");
        map1.put("user", "支付宝");
        map1.put("money", "0.00");
        map1.put("date",d);
        lists.add(map1);


        SimpleAdapter adapter = new SimpleAdapter(getContext(), lists, R.layout.moneywater_item, new String[]{"user2","image","usertotal","total","user", "money","date"}, new int[]{R.id.tv_user2,R.id.im_in_or_out,R.id.tv_user2_money,R.id.tv_money2_money,R.id.tv_user, R.id.tv_money,R.id.tv_date});
        lsMain.setAdapter(adapter);


    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x11:
                    String s = (String) msg.obj;
                    tvDisplay.setText(s);
                    break;
                case 0x12:
                    String ss = (String) msg.obj;
                    tvDisplay.setText(ss);
                    break;
            }
        }
    };

    private void init() {
        lsMain = root.findViewById(R.id.ls_main);

    }

    private void getphone() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 调用数据库工具类DBUtils的getInfoByName方法获取数据库表中数据
                MySQLSeries mySQLSeries = MySQLSeries.getDbService();
                String n = mySQLSeries.getUserData("15819285449");
                String name = n;
//                HashMap<String, Object> map = DBUtils.getPhoneUser();
                Message message = handler.obtainMessage();
//                if (map != null) {
//                    String s = "";
//                    for (String key : map.keySet()) {
//                        s += key + ":" + map.get(key) + "\n";
//                    }
                message.what = 0x12;
                message.obj = name;
//                } else {
//                    message.what = 0x11;
//                    message.obj = "查询结果为空";
//                }
                // 发消息通知主线程更新UI
                handler.sendMessage(message);
            }
        }).start();
    }

}