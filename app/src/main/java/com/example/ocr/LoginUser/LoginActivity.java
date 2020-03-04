package com.example.ocr.LoginUser;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ocr.R;

import cn.jiguang.verifysdk.api.AuthPageEventListener;
import cn.jiguang.verifysdk.api.JVerificationInterface;
import cn.jiguang.verifysdk.api.LoginSettings;
import cn.jiguang.verifysdk.api.RequestCallback;
import cn.jiguang.verifysdk.api.VerifyListener;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class LoginActivity extends AppCompatActivity {
    private Button btn_login;
    private Button btn_login2;
//    private SimCardInfo number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        acbar();
        event();
    }

    private void event() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean verifyEnable = JVerificationInterface.checkVerifyEnable(getApplicationContext());
                if(verifyEnable){
                    Toast.makeText(LoginActivity.this, "当前网络环境支持认证", Toast.LENGTH_SHORT).show();

                    return;
                }
//                getPermission();
//
//                number = new SimCardInfo(getApplicationContext());
//                String s = number.getNativePhoneNumber();
////                String ss = number.getProvidersName();
//                Toast.makeText(LoginActivity.this,s , Toast.LENGTH_SHORT).show();
            }
        });
        btn_login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginSettings settings = new LoginSettings();
                settings.setAutoFinish(true);//设置登录完成后是否自动关闭授权页
                settings.setTimeout(15 * 1000);//设置超时时间，单位毫秒。 合法范围（0，30000],范围以外默认设置为10000
                settings.setAuthPageEventListener(new AuthPageEventListener() {
                    @Override
                    public void onEvent(int cmd, String msg) {
                        //do something...
                    }
                });//设置授权页事件监听
                JVerificationInterface.loginAuth(getApplicationContext(), settings, new VerifyListener() {
                    @Override
                    public void onResult(int code, String content, String operator) {
                        if (code == 6000){
                            Toast.makeText(LoginActivity.this, "code=" + code + ", token=" + content+" ,operator="+operator, Toast.LENGTH_SHORT).show();
                        }else{
//                            Log.d(TAG, "code=" + code + ", message=" + content);
                            Toast.makeText(LoginActivity.this, "code=" + code + ", message=" + content, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    private void init() {
        btn_login = findViewById(R.id.btn_login);
        btn_login2 = findViewById(R.id.btn_login2);
        JVerificationInterface.init(getApplicationContext(), 5000, new RequestCallback<String>() {
            @Override
            public void onResult(int code, String msg) {
                Toast.makeText(LoginActivity.this, "tag"+"code = " + code + " msg = " + msg, Toast.LENGTH_SHORT).show();
            }
        });



    }
//    public void getPermission() {
//        //判断是否已经赋予权限
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.READ_PHONE_STATE)
//                != PERMISSION_GRANTED) {
//            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.READ_PHONE_STATE)) {//这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限
//            } else {
//                //申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.READ_PHONE_STATE,}, 1);
//            }
//        }
//    }

    private void acbar() {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("用户登录");
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //actionbar navigation up 按钮
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PERMISSION_GRANTED) {
                    Toast.makeText(this, "" + "权限" + permissions[i] + "申请成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "" + "权限" + permissions[i] + "申请失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}

