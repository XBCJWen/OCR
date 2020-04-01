package com.example.ocr.ui.my;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ocr.DB.DataBaseOpenHelper;
import com.example.ocr.MainActivity;
import com.example.ocr.R;

import java.util.ArrayList;
import java.util.List;

public class Info2Activity extends AppCompatActivity implements View.OnClickListener {

    private EditText tv_name;
    private TextView tv_user;
    private Button btn_sure;
    private DataBaseOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info2);
        acBar();
        initView();
        initData();
        db.clear();
    }

    private void initView() {
        tv_name = (EditText) findViewById(R.id.tv_name);
        tv_user = (TextView) findViewById(R.id.tv_user);
        btn_sure = (Button) findViewById(R.id.btn_sure);

        btn_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sure:
                String name = tv_name.getText().toString().trim();
                if(name.equals("")){

                }else {
                    List<String> list = new ArrayList<>();
                    String alter = String.format("UPDATE userphone SET name = '%s' where i = 1",name);
                    list.add(alter);
                    db.execSQL(list);
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }

    private void initData() {
        db = DataBaseOpenHelper.getInstance(Info2Activity.this, "ocrSql", 1, new ArrayList<String>());
        Cursor cursor = db.query("userphone","userphone");
        while (cursor.moveToNext()){
            String i = cursor.getString(3);
            if(i.equals("1")){
                String name = cursor.getString(0);
                String number = cursor.getString(1);
                tv_name.setText(number);
                tv_user.setText(name);

            }else {
                Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void acBar() {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bottomback));
        actionBar.setTitle("修改资料");
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }
    //标题栏返回按钮监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //登录成功后给予返回Main
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}