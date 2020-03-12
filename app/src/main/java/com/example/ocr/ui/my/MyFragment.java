package com.example.ocr.ui.my;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ocr.Beam.UserTable;
import com.example.ocr.LoginUser.loginStatuActivity;
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
    private ImageView imgHead;
    private TextView tvName;
    private TextView tvUserName;
    private TextView tvMunber;
    private String user="";
    private String name="";
    private String passwd="";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_my, container, false);
        Toast.makeText(getContext(), "aaa", Toast.LENGTH_SHORT).show();
        init();
        event();
        return root;
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            List<UserTable> list=new ArrayList<>();
            switch (msg.what) {
                case 200:
                    String char2 = (String) msg.obj;
//                    String user1 = char2.replace("\"", "");
//                    Log.d("user",user1);

                    Intent intent = new Intent(getContext(), loginStatuActivity.class);
                    intent.putExtra("token",char2);
                    startActivity(intent);
            }
        }
    };

        private void event() {
            la_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                Intent intent = new Intent(getContext(), LoginActivity.class);
//                startActivity(intent);

//                ((Activity)mContext).overridePendingTransition(R.anim.la_in,
//                        R.anim.la_out);
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
//                            ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
//                            ClipData mClipData = ClipData.newPlainText("token", s);
//                            cm.setPrimaryClip(mClipData);
                                Message message =handler.obtainMessage();
                                message.what =200;
                                message.obj =s;
                                handler.sendMessage(message);

//                                requestsIntent(s);
                              } else {
                                if (i == 6001) {
                                    Toast.makeText(getContext(), "登录超时，请稍后重试", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "登录错误，请尝试其他登录方式", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                    });
                }
            });
        }

        //        private void getDbData() {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    // 调用数据库工具类DBUtils的getInfoByName方法获取数据库表中数据
//                    HashMap<String, Object> map = DBUtils.getPhoneUser();
//                    Message message = handler.obtainMessage();
//                    if (map != null) {
//                        String s = "";
//                        for (String key : map.keySet()) {
//                            s += key + ":" + map.get(key) + "\n";
//                        }
//                        message.what = 0x12;
//                        message.obj = s;
//                    } else {
//                        message.what = 0x11;
//                        message.obj = "查询结果为空";
//                    }
//                    // 发消息通知主线程更新UI
//                    handler.sendMessage(message);
//                }
//            }).start();
//        }
//        private void  requestsIntent(final String token) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    Message message = handler.obtainMessage();
//                    URL url = null;
//                    try {
//                        url = new URL("http://101.132.128.168:5000/get_modify");
//                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                        httpURLConnection.setRequestMethod("POST");
//                        httpURLConnection.setConnectTimeout(10000);
//                        httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");//json格式
//                        httpURLConnection.setDoOutput(true);
//                        httpURLConnection.setDoInput(true);
//                        httpURLConnection.setUseCaches(false);
//                        JSONObject body = new JSONObject();
//
//                        String s = token;
//
//                        body.put("loginToken", s);
//
//                        DataOutputStream stream = new DataOutputStream(httpURLConnection.getOutputStream());
//                        String sss = String.valueOf(body);
//                        stream.writeBytes(sss);
//                        stream.flush();
//                        stream.close();
//                        if (httpURLConnection.getResponseCode() == 201) {
//                            InputStreamReader inputStream = new InputStreamReader(httpURLConnection.getInputStream());
//                            BufferedReader buffer = new BufferedReader(inputStream);
//                            String r = null;
//                            String i = "";
//                            while ((r = buffer.readLine()) != null) {
//                                i = r;
//                            }
//                            inputStream.close();
//                            message.what = 200;
//                            message.obj = i;
//                            httpURLConnection.disconnect();
//                            handler.sendMessage(message);
//                        }
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    } catch (ProtocolException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }).start();
//        }

        private void init() {
            la_user = root.findViewById(R.id.la_my);
            imgHead = root.findViewById(R.id.img_head);
            tvName = root.findViewById(R.id.tv_name);
            tvUserName = root.findViewById(R.id.tv_user_name);
            tvMunber = root.findViewById(R.id.tv_munber);

//            getPhonePermission();

            JVerificationInterface.setDebugMode(false);
            JVerificationInterface.init(getContext());
            JVerificationInterface.preLogin(getContext(), 5000, new PreLoginListener() {
                @Override
                public void onResult(final int code, final String content) {
                }
            });


        }

//        public void getPhonePermission() {
//            //判断是否已经赋予权限
//            if (ContextCompat.checkSelfPermission(getContext(),
//                    Manifest.permission.READ_PHONE_STATE)
//                    != PERMISSION_GRANTED) {
//                //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
//                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
//                        Manifest.permission.READ_PHONE_STATE)) {//这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限
//                    ActivityCompat.requestPermissions(getActivity(),
//                            new String[]{Manifest.permission.READ_PHONE_STATE,}, 1);
//
//                } else {
//                    //申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
//                    ActivityCompat.requestPermissions(getActivity(),
//                            new String[]{Manifest.permission.READ_PHONE_STATE,}, 1);
//                }
//            }
//        }

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
                    .setLogoOffsetY(50)
                    .setLogoImgPath("logo_cm")
                    .setNumFieldOffsetY(170)
                    .setSloganOffsetY(230)
                    .setLogBtnOffsetY(254)
                    .setPrivacyState(false)
                    .setNavTransparent(false)
                    .setPrivacyOffsetY(30).build();
            JVerificationInterface.setCustomUIWithConfig(uiConfig);
        }

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
