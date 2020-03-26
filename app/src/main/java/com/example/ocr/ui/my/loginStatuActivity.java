package com.example.ocr.ui.my;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ocr.Beam.UserTable;
import com.example.ocr.DB.DataBaseOpenHelper;
import com.example.ocr.MainActivity;
import com.example.ocr.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class loginStatuActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLoging;
    private  DataBaseOpenHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_statu);
        initView();
        initdata();
        acBar();
    }
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            List<UserTable> list=new ArrayList<>();
            switch (msg.what) {
                case 200:
                    String char2 = (String) msg.obj;
                    String user1 = char2.replace("\"", "");
                    btnLoging.setText("返回首页");
                    List<String> listSql = new ArrayList<>();
                    String initInsert = String.format("INSERT INTO userphone VALUES (%s,'记','0.00',1)", user1);
                    String wexin = String.format("INSERT INTO money VALUES ('%s','0.00')","微信");
                    String zhifu = String.format("INSERT INTO money VALUES ('%s','0.00')","支付宝");
                    listSql.add(initInsert);
                    listSql.add(wexin);
                    listSql.add(zhifu);

                    db.execSQL(listSql);
                    db.clear();

            }
        }
    };

    private void initdata() {
        String token = getIntent().getStringExtra("token");
        requestsIntent(token);

        //初始化，db
        List<String> sql = new ArrayList<>();
        String createSql = "create table user(user int primary key,total int,weixi int,zhifu int,ntime text)";
        sql.add(createSql);
        db = DataBaseOpenHelper.getInstance(this,"ocrSql",1,sql);
    }

    private void acBar() {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bottomback));
        actionBar.setTitle("登录");
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }
    private void initView() {

        btnLoging = (Button) findViewById(R.id.btn_loging);
        btnLoging.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_loging:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void  requestsIntent(final String token) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = handler.obtainMessage();
                URL url = null;
                try {
                    url = new URL("http://101.132.128.168:5000/get_modify");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setConnectTimeout(10000);
                    httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");//json格式
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setUseCaches(false);
                    JSONObject body = new JSONObject();

                    String s = token;

                    body.put("loginToken", s);
                    DataOutputStream stream = new DataOutputStream(httpURLConnection.getOutputStream());
                    String sss = String.valueOf(body);
                    stream.writeBytes(sss);
                    stream.flush();
                    stream.close();
                    if (httpURLConnection.getResponseCode() == 201) {
                        InputStreamReader inputStream = new InputStreamReader(httpURLConnection.getInputStream());
                        BufferedReader buffer = new BufferedReader(inputStream);
                        String r = null;
                        String i = "";
                        while ((r = buffer.readLine()) != null) {
                            i = r;
                        }
                        inputStream.close();
                        message.what = 200;
                        message.obj = i;
                        httpURLConnection.disconnect();
                        handler.sendMessage(message);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();
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
