package com.example.ocr.ui.moeny;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ocr.R;
import com.example.ocr.Funtion.AdduserActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoneyFragment extends Fragment {

    private View root;
    private ListView lsMain;
    private Button btnAdd;
    private SimpleAdapter adapter;
    private List<Map<String, Object>> lists;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_money, container, false);
        init();
        event();

        return root;
    }

    private void init() {
        lsMain = root.findViewById(R.id.ls_main);
        btnAdd = root.findViewById(R.id.btn_add);
    }

    private void event() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AdduserActivity.class);
                startActivityForResult(intent,1);
            }
        });

        lists = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();
        map.put("user2", "微信");
        map.put("money2", "0.00");
        map.put("user", "微信");
        map.put("money", "0.00");
        lists.add(map);

        Map<String, Object> map1 = new HashMap<>();
        map1.put("user2", "支付宝");
        map1.put("money2", "0.00");
        map1.put("user", "支付宝");
        map1.put("money", "0.00");
        lists.add(map1);

        adapter = new SimpleAdapter(getContext(), lists, R.layout.money_item, new String[]{"user2", "money2", "user", "money"}, new int[]{R.id.tv_user2, R.id.tv_money2, R.id.tv_user, R.id.tv_money});
        lsMain.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         //当otherActivity中返回数据的时候，会响应此方法
         //requestCode和resultCode必须与请求startActivityForResult()和返回setResult()的时候传入的值一致。
         if(requestCode==1)
                {
                    String user=data.getStringExtra("user");
                    String money=data.getStringExtra("money");
                    Map<String, Object> map1 = new HashMap<>();
                    map1.put("user2", user);
                    map1.put("money2", money);
                    map1.put("user", user);
                    map1.put("money", money);
                    lists.add(map1);

                    adapter.notifyDataSetChanged();
                }
        }

}