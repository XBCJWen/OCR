package com.example.ocr.ui.moneywater;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ocr.DB.DataBaseOpenHelper;
import com.example.ocr.Funtion.JiActivity;
import com.example.ocr.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoneywaterFragment extends Fragment {


    private View root;
    private TextView tvDisplay;
    private Button btnDb;
    private ListView lsMain;
    private Button btnAdd;
    private DataBaseOpenHelper db;
    private SimpleAdapter adapter;
    private List<Map<String, Object>> lists;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_moneywater, container, false);
        init();


        event();

        return root;
    }

    private void event() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), JiActivity.class);
                startActivity(intent);
            }
        });
        lsMain.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//                menu.setHeaderTitle("选择操作");
                menu.add(0, 0, 0, "删除流水");
            }
        });
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");获得时间
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
//        Date date = new Date(System.currentTimeMillis());
//        String d = simpleDateFormat.format(date);



//        int[] imageViews = {R.drawable.output,R.drawable.input};
//        Map<String, Object> map = new HashMap<>();
//        map.put("user2","微信余额");
//        map.put("image",imageViews[0]);
//        map.put("usertotal", "0.00");
//        map.put("total", "0.00");
//        map.put("user", "微信");
//        map.put("money", "0.00");
//        map.put("date",d);
//        lists.add(map);


    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        //获取点击的item的id
        int id = (int) info.id;
        switch (item.getItemId()) {
            case 0:
                Map<String, Object> map = lists.get(id);
                lists.remove(id);
                adapter.notifyDataSetChanged();
                String nowtime = (String) map.get("date");
                Log.d("ttttttttttttttttttttime", "onContextItemSelected:"+map);
                List<String> sqlist = new ArrayList<>();
                String sql = String.format("delete from water where jitime = '%s'", nowtime);

                sqlist.add(sql);
                db.execSQL(sqlist);

                return true;
        }

        return super.onContextItemSelected(item);
    }
    private void init() {
        lsMain = root.findViewById(R.id.ls_main);
        btnAdd = root.findViewById(R.id.btn_add);



        //流水数据的读取
        lists = new ArrayList<>();
        db = DataBaseOpenHelper.getInstance(getContext(),"ocrSql",1,new ArrayList<String>());
        int[] imageViews = {R.drawable.output,R.drawable.input};

        Cursor waterCursor = db.query("water","water");
        while (waterCursor.moveToNext()){
            String category = waterCursor.getString(3);
            Map<String, Object> map = new HashMap<>();
            map.put("user2",waterCursor.getString(0)+"余额");
            if (category.equals("收入")){
                map.put("image",imageViews[0]);
            }else {
                map.put("image",imageViews[1]);
            }
            map.put("usertotal", waterCursor.getString(2));
            map.put("total", waterCursor.getString(1));
            map.put("user", waterCursor.getString(0));
            map.put("money", waterCursor.getString(4));
            map.put("date",waterCursor.getString(5));
            lists.add(map);
        }
        Collections.reverse(lists);
        adapter = new SimpleAdapter(getContext(), lists, R.layout.moneywater_item, new String[]{"user2","image","usertotal","total","user", "money","date"}, new int[]{R.id.tv_user2,R.id.im_in_or_out,R.id.tv_user2_money,R.id.tv_money2_money,R.id.tv_user, R.id.tv_money,R.id.tv_date});
        lsMain.setAdapter(adapter);

    }


}