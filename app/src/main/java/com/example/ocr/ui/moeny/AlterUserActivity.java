package com.example.ocr.ui.moeny;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ocr.DB.DataBaseOpenHelper;
import com.example.ocr.MainActivity;
import com.example.ocr.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AlterUserActivity extends AppCompatActivity {

    private EditText edtUser;
    private EditText edtMoney;
    private Button btnSure;
    private DataBaseOpenHelper db;
    private String yuser;
    private String ymoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_user);
        acBar();
        initView();
        initData();
        event();

    }

    private void event() {
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edtUser.getText().toString().trim();
                String money = edtMoney.getText().toString().trim();


                if(user.equals("") | money.equals("")){
                    Toast.makeText(getApplicationContext(), "请输入正确的帐户名或余额", Toast.LENGTH_SHORT).show();
                }else {
                    List<String> sql=new ArrayList();

                    db = DataBaseOpenHelper.getInstance(getApplicationContext(),"ocrSql",1,sql);
                    DecimalFormat decimalFormat =new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
                    String distanceString = decimalFormat.format(Double.valueOf(money));//format 返回的是字符串
                    String alter = String.format("UPDATE money SET username = '%s',total = '%s' where username = '%s'",user,distanceString,yuser);

                    sql.add(alter);

                    db.execSQL(sql);
                    db.clear();
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                intent.putExtra("user",user);
                intent.putExtra("money",money);
                intent.putExtra("yuser",yuser);
                setResult(RESULT_OK,intent);
                finish();

            }
        });

    }
    private void initData() {
        //list item传递的原先的帐户
        yuser = getIntent().getStringExtra("user");
        ymoney = getIntent().getStringExtra("money");
        edtUser.setText(yuser);
        edtMoney.setText(ymoney);

    }

    //自定义标题栏
    private void acBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bottomback));
        actionBar.setTitle("帐户");
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initView() {
        edtUser = (EditText) findViewById(R.id.edt_user);
        edtMoney = (EditText) findViewById(R.id.edt_money);
        btnSure = (Button) findViewById(R.id.btn_sure);
    }

    //监听标题栏返回按钮
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
