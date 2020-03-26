package com.example.ocr.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.ocr.DB.DataBaseOpenHelper;
import com.example.ocr.MainActivity;
import com.example.ocr.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ViewPagerAdapter1 extends PagerAdapter {
    private List<View> mList;
    private Context context;
    private DataBaseOpenHelper db;


    public ViewPagerAdapter1(List<View> list, Context context) {
        this.mList = list;
        this.context = context;

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        switch (position){
            case 0:
                db = DataBaseOpenHelper.getInstance(context, "ocrSql", 1, new ArrayList<String>());
                Cursor cursor = db.query("money","money");
                ArrayList<String> list = new ArrayList<>();
                //获取帐户名并添加到Spinner
                while (cursor.moveToNext()){
                    String s = cursor.getString(0);
                    list.add(s);

                }

                Button btnKnowledge = (Button)mList.get(position).findViewById(R.id.btnInput);
                final Spinner spinner = (Spinner) mList.get(position).findViewById(R.id.spiner_user);
                final EditText edtMoney = (EditText) mList.get(position).findViewById(R.id.edt_money);

                ArrayAdapter adapter =new ArrayAdapter(context,R.layout.spinner_items_1,list);
                spinner.setAdapter(adapter);

//                String User = spinner.getSelectedItem().toString();
//
//                String money = edtMoney.getText().toString().trim();


//                String createWater = "create table water(username text ,moneyCategory text,money text,wtime text,foreign key(username) references money(username))";
                btnKnowledge.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String money = edtMoney.getText().toString().trim();
                        if (money.equals("")){
                            Toast.makeText(context, "请输入收入金额", Toast.LENGTH_SHORT).show();

                        }else {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd-yy");
                            DecimalFormat decimalFormat =new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
                            String distanceString = decimalFormat.format(Float.valueOf(money));//format 返回的是字符串

                            String User = spinner.getSelectedItem().toString();
                            Date date = new Date(System.currentTimeMillis());
                            String nowtime = simpleDateFormat.format(date);//时间

                            List<String> sql = new ArrayList<String>(){};
                            Cursor cursor1 = db.query("userphone","userphone");
                            Cursor cursor2 = db.query("money","money");
                            String Total = "";
                            while (cursor1.moveToNext()){
                                Total = cursor1.getString(2);
                            }
                            float moneytal = Float.valueOf(Total) + Float.valueOf(money);
                            String UserTotal = "";
                            while (cursor2.moveToNext()){
                                String user = cursor2.getString(0);
                                if (user.equals(User)){
                                    UserTotal = cursor2.getString(1);
                                }
                            }
                            float userMoneyTotal = Float.valueOf(UserTotal) + Float.valueOf(money);

                            String s = String.format("INSERT INTO water VALUES ('%s','%s','%s','%s','%s','%s')",User,moneytal,userMoneyTotal,"支出",distanceString,nowtime);
                            String alter = String.format("UPDATE userphone SET total = total+'%s' where i = 1",distanceString);

                            String moneySql2 = String.format("UPDATE money SET total = total+'%s' where username = '%s'",distanceString,User);
                            sql.add(s);
                            sql.add(alter);
                            sql.add(moneySql2);
                            db.execSQL(sql);

                            db.clear();
                            Toast.makeText(context, "记录成功", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(v.getContext(), MainActivity.class);
                            v.getContext().startActivity(intent);
                        }
                    }
                });
                break;

            case 1:
                db = DataBaseOpenHelper.getInstance(context, "ocrSql", 1, new ArrayList<String>());
                Cursor cursor2 = db.query("money","money");
                ArrayList<String> list2 = new ArrayList<>();

                while (cursor2.moveToNext()){
                    String s = cursor2.getString(0);
                    list2.add(s);

                }

                Button btnKnowledge2 = (Button)mList.get(position).findViewById(R.id.btnInput);
                final Spinner spinner2 = (Spinner) mList.get(position).findViewById(R.id.spiner_user);
                final EditText edtMoney2 = (EditText) mList.get(position).findViewById(R.id.edt_money);

                ArrayAdapter adapter2 =new ArrayAdapter(context,R.layout.spinner_items_1,list2);
                spinner2.setAdapter(adapter2);

                btnKnowledge2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String money = edtMoney2.getText().toString().trim();
                        if (money.equals("")){
                            Toast.makeText(context, "请输入收入金额", Toast.LENGTH_SHORT).show();

                        }else {
//                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd-yy");
                            Date date = new Date(System.currentTimeMillis());
                            String nowtime = simpleDateFormat.format(date);

                            DecimalFormat decimalFormat =new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
                            String distanceString2 = decimalFormat.format(Float.valueOf(money));//format 返回的是字符串

                            String User2 = spinner2.getSelectedItem().toString();


                            List<String> sql = new ArrayList<String>(){};

                            Cursor cursor1 = db.query("userphone","userphone");
                            Cursor cursor2 = db.query("money","money");
                            String Total = "";
                            while (cursor1.moveToNext()){
                                Total = cursor1.getString(2);
                            }
                            float moneytal = Float.valueOf(Total) - Float.valueOf(money);
                            String UserTotal = "";
                            while (cursor2.moveToNext()){
                                String user = cursor2.getString(0);
                                if (user.equals(User2)){
                                    UserTotal = cursor2.getString(1);
                                }
                            }
                            float userMoneyTotal = Float.valueOf(UserTotal) - Float.valueOf(money);

                            String s = String.format("INSERT INTO water VALUES ('%s','%s','%s','%s','%s','%s')",User2,moneytal,userMoneyTotal,"收入",distanceString2,nowtime);



                            String alter2 = String.format("UPDATE userphone SET total = total-'%s' where i = 1",distanceString2);
                            String moneySql = String.format("UPDATE money SET total = total-'%s' where username = '%s'",distanceString2,User2);

                            sql.add(s);
                            sql.add(alter2);
                            sql.add(moneySql);
                            db.execSQL(sql);
                            db.clear();
                            Toast.makeText(context, "记录成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(v.getContext(), MainActivity.class);
                            v.getContext().startActivity(intent);
                        }
                    }
                });
                break;
        }

        ((ViewPager) container).addView(mList.get(position));
        return mList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView(mList.get(position));
        //super.destroyItem(container, position, object);
    }

}
