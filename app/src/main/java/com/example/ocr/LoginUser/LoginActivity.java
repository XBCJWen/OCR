package com.example.ocr.LoginUser;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ocr.R;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class LoginActivity extends AppCompatActivity {
    private Button btn_login;
    private EditText edtNameIn;
    private EditText edtPasswd;
    private EditText edtAgain;

    //    private SimCardInfo number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

        init();
        acbar();
        event();
    }

    private void event() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if (edtNameIn.getText().equals("") &&edtAgain.getText().equals("") && edtPasswd.getText().equals("")){
                    if (edtPasswd.getText() == edtAgain){
                        intent.putExtra("name",edtNameIn.getText());
                        intent.putExtra("passwd",edtAgain.getText());
                        setResult(RESULT_OK, intent);
                        finish();
                    }else {
                        Toast.makeText(LoginActivity.this, "两次密码不相同", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(LoginActivity.this, "昵称不可为空", Toast.LENGTH_SHORT).show();
                }



            }
        });

    }

    private void init() {
        btn_login = findViewById(R.id.btn_login);
    }

//    private void event() {
//        btn_login2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                SimCardInfo simCardInfo = new SimCardInfo(getBaseContext());
////                Toast.makeText(LoginActivity.this, simCardInfo.getNativePhoneNumber(), Toast.LENGTH_SHORT).show();
//                LoginSettings settings = new LoginSettings();
//                settings.setAutoFinish(true);//设置登录完成后是否自动关闭授权页
//                settings.setTimeout(15 * 1000);//设置超时时间，单位毫秒。 合法范围（0，30000],范围以外默认设置为10000
//                settings.setAuthPageEventListener(new AuthPageEventListener() {
//                    @Override
//                    public void onEvent(int cmd, String msg) {
//                        //do something...
//                    }
//                });//设置授权页事件监听
//
//                JVerificationInterface.loginAuth(getApplicationContext(), settings, new VerifyListener() {
//                    @Override
//                    public void onResult(int i, String s, String s1) {
//                        if (i == 6000){
//                            JVerificationInterface.clearPreLoginCache();
//
//
//
//
//                            finish();
//
//                        }else{
//                            if (i==6001){
//                                Toast.makeText(LoginActivity.this, "登录超时，请稍后重试", Toast.LENGTH_SHORT).show();
//                            }else{
//                                Toast.makeText(LoginActivity.this, "登录错误，请尝试其他登录方式", Toast.LENGTH_SHORT).show();
//                            }
//
//                        }
//                    }
//                });
//
//            }
//        });
//    }

    //    private void init() {
//        //判断是否支持手机号码认证
//        getPermission();
//        btn_login2 = findViewById(R.id.btn_login2);
//        //初始化接口
//        JVerificationInterface.setDebugMode(true);
//        JVerificationInterface.init(this);
//        JVerificationInterface.preLogin(getApplicationContext(), 5000,new PreLoginListener() {
//            @Override
//            public void onResult(final int code, final String content) {
//            }
//        });
//
//        JVerifyUIConfig uiConfig = new JVerifyUIConfig.Builder()
//                .setAuthBGImgPath("main_bg")
//                .setNavColor(0xff0086d0)
//                .setNavText("登录")
//                .setNumberSize(24)
//                .setNavReturnBtnWidth(23)
//                .setNavReturnBtnHeight(23)
//                .setNumberFieldWidth(300)
//                .setNumberFieldHeight(30)
//                .setNavTextColor(0xffffffff)
//                .setNavReturnImgPath("umcsdk_return_bg")
//                .setLogoWidth(70)
//                .setLogoHeight(70)
//                .setLogoHidden(false)
//                .setNumberColor(0xff333333)
//                .setLogBtnText("本机号码一键登录")
//                .setLogBtnTextColor(0xffffffff)
//                .setLogBtnImgPath("umcsdk_login_btn_bg")
//                .setAppPrivacyColor(0xff666666,0xff0085d0)
//                .setUncheckedImgPath("umcsdk_uncheck_image")
//                .setCheckedImgPath("umcsdk_check_image")
//                .setSloganTextColor(0xff999999)
//                .setLogoOffsetY(50)
//                .setLogoImgPath("logo_cm")
//                .setNumFieldOffsetY(170)
//                .setSloganOffsetY(230)
//                .setLogBtnOffsetY(254)
//                .setPrivacyState(false)
//                .setNavTransparent(false)
//                .setPrivacyOffsetY(30).build();
//        JVerificationInterface.setCustomUIWithConfig(uiConfig);
//
//
//    }
//    public void getPermission() {
//        //判断是否已经赋予权限
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.READ_PHONE_STATE)
//                != PERMISSION_GRANTED) {
//            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.READ_PHONE_STATE)) {//这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.READ_PHONE_STATE,}, 1);
//
//            } else {
//                //申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.READ_PHONE_STATE,}, 1);
//            }
//        }
//    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        if (edtNameIn.getText().equals("") &&edtAgain.getText().equals("") && edtPasswd.getText().equals("")){
            if (edtPasswd.getText() == edtAgain){
                intent.putExtra("name",edtNameIn.getText());
                intent.putExtra("passwd",edtAgain.getText());
                setResult(RESULT_OK, intent);
                finish();
            }else {
                Toast.makeText(LoginActivity.this, "两次密码不相同", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(LoginActivity.this, "昵称不可为空", Toast.LENGTH_SHORT).show();
        }
    }

    private void acbar() {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("设置密码");
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

    private void initView() {
        edtNameIn = (EditText) findViewById(R.id.edt_name_in);
        edtPasswd = (EditText) findViewById(R.id.edt_passwd);
        edtAgain = (EditText) findViewById(R.id.edt_again);
    }


}

