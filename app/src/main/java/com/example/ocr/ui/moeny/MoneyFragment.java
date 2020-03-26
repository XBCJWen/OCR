package com.example.ocr.ui.moeny;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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
import com.example.ocr.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class MoneyFragment extends Fragment {

    private View root;
    private ListView lsMain;
    private SimpleAdapter adapter;
    private List<Map<String, Object>> lists;
    private DataBaseOpenHelper db;
    private TextView tvTotalMoney;
    private Button btnAdd;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_money, container, false);
        init();
        event();
        db.clear();
        return root;
    }

    private void init() {
        lsMain = root.findViewById(R.id.ls_main);
        tvTotalMoney = root.findViewById(R.id.tv_total_money);
        btnAdd = root.findViewById(R.id.btn_add);
        initData();
    }

    private void event() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AdduserActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        lsMain.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//                menu.setHeaderTitle("选择操作");
                menu.add(0, 0, 0, "添加帐户");
                menu.add(0, 1, 0, "删除帐户");
            }
        });

        lsMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //修改帐户数据
                //获取帐户信息并Inten到Activity
                Map<String, Object> map = lists.get(position);
                String user = (String) map.get("user");
                String money = (String) map.get("money");

                Intent intent = new Intent(getContext(), AlterUserActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("money", money);
                startActivityForResult(intent, 2);

            }
        });

    }


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        //获取点击的item的id
        int id = (int) info.id;
        switch (item.getItemId()) {
            case 0:
                //添加帐户

                Intent intent = new Intent(getContext(), AdduserActivity.class);
                startActivityForResult(intent, 1);

                return true;
            case 1:
                //删除帐户数据
                /*移除list的某项数据，注意remove()里的数据只能是int，这里用了强制转换，将long转换成int*/
                Map<String, Object> map = lists.get(id);
                String user = (String) map.get("user");
                String money = (String) map.get("money");
                lists.remove(id);
                adapter.notifyDataSetChanged();

                //删除了数据库的userphone
                List<String> sqlist = new ArrayList<>();
                String sql = String.format("delete from money where username = '%s'", user);
                sqlist.add(sql);
                db.execSQL(sqlist);

                //更新总资产
                Cursor c = db.query("money", "money");
                float total = 0;

                while ((c.moveToNext())) {
                    float money_total = Float.valueOf(c.getString(1));
                    total += money_total;
                }
                DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
                String distanceString = decimalFormat.format(total);//format 返回的是字符串
                tvTotalMoney.setText(distanceString);
                //更新listview的数据
                return true;
        }


        return super.onContextItemSelected(item);
    }

    //监听Intent的返回方法
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //当otherActivity中返回数据的时候，会响应此方法
        //requestCode和resultCode必须与请求startActivityForResult()和返回setResult()的时候传入的值一致。
        if (requestCode == 1 && resultCode == RESULT_OK) {
            //添加帐户

            String user = data.getStringExtra("user");
            String money = data.getStringExtra("money");
            DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
            String distanceString = decimalFormat.format(Float.valueOf(money));//format 返回的是字符串
            Map<String, Object> map1 = new HashMap<>();
            map1.put("user2", user);
            map1.put("money2", distanceString);
            map1.put("user", user);
            map1.put("money", distanceString);
            lists.add(map1);
            adapter.notifyDataSetChanged();

            Cursor c = db.query("money", "money");
            float moneytotal = 0;
            while ((c.moveToNext())) {
                float moneyt = Float.valueOf(c.getString(1));
                moneytotal += moneyt;
            }
            //设置总资产
            String distanceString1 = decimalFormat.format(moneytotal);//format 返回的是字符串
            tvTotalMoney.setText(distanceString1);
        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            //修改帐户数据
            String user = data.getStringExtra("user");
            String money = data.getStringExtra("money");
            String yuser = data.getStringExtra("yuser");
            DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.

            //更新item
            for (int i = 0; i < lists.size(); i++) {
                Map map = lists.get(i);
                String userm = (String) map.get("user");
                if (userm.equals(yuser)) {
                    String up_money = decimalFormat.format(Float.valueOf(money));
                    lists.get(i).put("user2", user);
                    lists.get(i).put("money2", up_money);
                    lists.get(i).put("user", user);
                    lists.get(i).put("money", up_money);

                }
            }
            adapter.notifyDataSetChanged();

            float moneytotal = 0;
            Cursor cursor = db.query("money", "money");
            while (cursor.moveToNext()) {
                float total = Float.valueOf(cursor.getString(1));
                moneytotal += total;
            }
            String distanceString = decimalFormat.format(moneytotal);//format 返回的是字符串
            tvTotalMoney.setText(distanceString);
        }
//

    }

    private void initData() {
        List<String> sql = new ArrayList<>();

        //查询userphone表
        String sqll = "userphone";
        db = DataBaseOpenHelper.getInstance(getContext(), "ocrSql", 1, sql);
        Cursor cursor = db.query("userphone", sqll);

        String i = "";
//        String total = "";
        float moneytotal = 0; //存储计算得出的总资金
        //遍历userphone表
        while (cursor.moveToNext()) {

//            total = cursor.getString(2);
            i = cursor.getString(3);
        }

        if (i.equals("1")) {

//            tvTotalMoney.setText(total);

            Cursor cursor1 = db.query("money", "money");
            lists = new ArrayList<>();

            while ((cursor1.moveToNext())) {
                String user = cursor1.getString(0);
                String money = cursor1.getString(1);
                Map<String, Object> map1 = new HashMap<>();
                moneytotal += Float.valueOf(money);
                DecimalFormat decimalFormat =new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
                String distanceString2 = decimalFormat.format(Float.valueOf(money));//format 返回的是字符串

                map1.put("user2", user);
                map1.put("money2", distanceString2);
                map1.put("user", user);
                map1.put("money", distanceString2);

                lists.add(map1);

            }
            adapter = new SimpleAdapter(getContext(), lists, R.layout.money_item, new String[]{"user2", "money2", "user", "money"}, new int[]{R.id.tv_user2, R.id.tv_money2, R.id.tv_user, R.id.tv_money});
            lsMain.setAdapter(adapter);
        }

        //设置总资产
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String distanceString = decimalFormat.format(moneytotal);//format 返回的是字符串
        tvTotalMoney.setText(distanceString);

        //更新userphone total字段
        List<String> lisql = new ArrayList<>();
        String sqlt = String.format("update userphone set total = %s where  i = 1 ", moneytotal);
        lisql.add(sqlt);
        db.execSQL(lisql);
    }

}