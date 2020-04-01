package com.example.ocr.ui.my;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ocr.AutoActivity;
import com.example.ocr.DB.DataBaseOpenHelper;
import com.example.ocr.R;

import java.util.ArrayList;
import java.util.List;

import cn.jiguang.verifysdk.api.AuthPageEventListener;
import cn.jiguang.verifysdk.api.JVerificationInterface;
import cn.jiguang.verifysdk.api.JVerifyUIConfig;
import cn.jiguang.verifysdk.api.LoginSettings;
import cn.jiguang.verifysdk.api.PreLoginListener;
import cn.jiguang.verifysdk.api.VerifyListener;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MyFragment extends Fragment {
    private LinearLayout la_user;
    private View root;
    private TextView tvName;
    private TextView tvMunber;
    private DataBaseOpenHelper db;
    private LinearLayout la;
    private LinearLayout laInfo;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_my, container, false);
        init();
        event();
        return root;
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 200:
                    String char2 = (String) msg.obj;
                    Intent intent = new Intent(getContext(), loginStatuActivity.class);
                    intent.putExtra("token", char2);
                    startActivity(intent);
            }
        }
    };

    private void event() {
        laInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),Info2Activity.class);
                startActivity(intent);
            }
        });

        la_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvName.getText().equals("未登录")) {
                    setUI();
                    LoginSettings settings = new LoginSettings();
                    settings.setAutoFinish(true);//设置登录完成后是否自动关闭授权页
                    settings.setTimeout(15 * 1000);//设置超时时间，单位毫秒。 合法范围（0，30000],范围以外默认设置为10000
                    settings.setAuthPageEventListener(new AuthPageEventListener() {
                        @Override
                        public void onEvent(int cmd, String msg) {
                            //do something...
                        }
                    });//设置授权页事件监听
                    JVerificationInterface.loginAuth(getContext(), settings, new VerifyListener() {
                        @Override
                        public void onResult(int i, String s, String s1) {
                            if (i == 6000) {
                                //获取token成功后发送消息给主线程
                                Message message = handler.obtainMessage();
                                message.what = 200;
                                message.obj = s;
                                handler.sendMessage(message);

                            } else {
                                if (i == 6001) {
                                    Toast.makeText(getContext(), "登录超时，请稍后重试", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "登录错误，请稍后重试", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "已登录，请勿重复登录", Toast.LENGTH_SHORT).show();
                }
            }
        });
        la.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AutoActivity.class);
                startActivity(intent);
            }
        });
    }


    private void init() {
        la_user = root.findViewById(R.id.la_my);
        tvMunber = root.findViewById(R.id.tv_munber);
        tvName = root.findViewById(R.id.tv_name);
        la = root.findViewById(R.id.la_auto);
        laInfo = root.findViewById(R.id.la_info);
        initData();

        //一键登录的预取号与初始化
        JVerificationInterface.setDebugMode(false);
        JVerificationInterface.init(getContext());
        JVerificationInterface.preLogin(getContext(), 5000, new PreLoginListener() {
            @Override
            public void onResult(final int code, final String content) {
            }
        });

    }

    //登录成功后设置昵称与帐号
    private void initData() {
        //登录成功后设置昵称与帐号
        List<String> sql = new ArrayList<>();
//        String createUser = "create table userphone(user real primary key,name text,total real)";
        String sqll = "userphone";
        db = DataBaseOpenHelper.getInstance(getContext(), "ocrSql", 1, sql);
        Cursor cursor = db.query("userphone", sqll);
        String name = "";
        String number = "";
        String i = "";
        while (cursor.moveToNext()) {
            number = cursor.getString(0);
            name = cursor.getString(1);
            i = cursor.getString(3);
        }
        db.clear();
        if (i.equals("1")) {
            tvName.setText(name);
            tvMunber.setText(number);
        }
    }

    //一键登录的UI设置
    private void setUI() {
        JVerifyUIConfig uiConfig = new JVerifyUIConfig.Builder()
                .setAuthBGImgPath("main_bg")
                .setNavColor(0xff0086d0)
                .setNavText("登录")
                .setNumberSize(24)
                .setNavReturnBtnWidth(23)
                .setNavReturnBtnHeight(23)
                .setNumberFieldWidth(300)
                .setNumberFieldHeight(30)
                .setNavTextColor(0xffffffff)
                .setNavReturnImgPath("umcsdk_return_bg")
                .setLogoWidth(70)
                .setLogoHeight(70)
                .setLogoHidden(false)
                .setNumberColor(0xff333333)
                .setLogBtnText("本机号码一键登录")
                .setLogBtnTextColor(0xffffffff)
                .setLogBtnImgPath("umcsdk_login_btn_bg")
                .setAppPrivacyColor(0xff666666, 0xff0085d0)
                .setUncheckedImgPath("umcsdk_uncheck_image")
                .setCheckedImgPath("umcsdk_check_image")
                .setSloganTextColor(0xff999999)
                .setLogoImgPath("@mipmap/ic_launcher_round")
                .setLogoOffsetY(100)
                .setNumFieldOffsetY(250)
                .setSloganOffsetY(230)
                .setLogBtnOffsetY(350)
                .setPrivacyState(false)
                .setNavTransparent(false)
                .setPrivacyOffsetY(30).build();
        JVerificationInterface.setCustomUIWithConfig(uiConfig);
    }

    //申请权限的回调接口
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), "" + "权限" + permissions[i] + "申请成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "" + "权限" + permissions[i] + "申请失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
