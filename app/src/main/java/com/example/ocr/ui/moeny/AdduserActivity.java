package com.example.ocr.ui.moeny;

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
import com.example.ocr.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AdduserActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvUser;
    private EditText edtUser;
    private TextView tvMoney;
    private EditText edtMoney;
    private Button btnAddUser;
    private DataBaseOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        initView();
        event();
        acBar();
    }

    private void event() {

    }

    private void acBar() {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bottomback));
        actionBar.setTitle("新建帐户");
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                finish(); // back buttonb
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        tvUser = (TextView) findViewById(R.id.tv_user);
        edtUser = (EditText) findViewById(R.id.edt_user);
        tvMoney = (TextView) findViewById(R.id.tv_money);
        edtMoney = (EditText) findViewById(R.id.edt_money);
        btnAddUser = (Button) findViewById(R.id.btn_add_user);
        btnAddUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_user:
                String user = edtUser.getText().toString().trim();
                String money = edtMoney.getText().toString().trim();
                db = DataBaseOpenHelper.getInstance(getApplicationContext(),"ocrSql",1,new ArrayList<String>());

                if (user.equals("")|money.equals("")) {
                    Toast.makeText(this, "请输入帐户名或余额", Toast.LENGTH_SHORT).show();
                }else {
                    //判断帐户是否存在
                    Cursor cursor = db.query("money","money");
                    int i =  0;
                    while (cursor.moveToNext()){
                        String username =cursor.getString(0);
                        if (username.equals(user)){
                            i = 1;
                            Toast.makeText(this, "已存在相同帐户", Toast.LENGTH_SHORT).show();
                        }

                        }
                    if(i == 0){
                        List<String> sql=new ArrayList<>();

                        DecimalFormat decimalFormat =new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
                        String distanceString = decimalFormat.format(Float.valueOf(money));//format 返回的是字符串

                        String insert = String.format("insert into money values ('%s','%s')", user,distanceString);
                        sql.add(insert);
                        db.execSQL(sql);
                        Intent intent = new Intent();
                        intent.putExtra("user",user);
                        intent.putExtra("money",money);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
                break;

        }
    }
}
