package com.example.ocr.Funtion;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ocr.R;

public class AdduserActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvUser;
    private EditText edtUser;
    private TextView tvMoney;
    private EditText edtMoney;
    private Button btnAddUser;

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
                this.finish(); // back button
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
                if (user.equals("")|money.equals("")) {
                    Toast.makeText(this, "请输入帐户名或余额", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent();
                    intent.putExtra("user",user);
                    intent.putExtra("money",money);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
        }
    }
}
