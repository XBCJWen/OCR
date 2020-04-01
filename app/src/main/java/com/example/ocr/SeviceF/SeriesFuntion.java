package com.example.ocr.SeviceF;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.alibaba.fastjson.JSON;
import com.example.ocr.DB.DataBaseOpenHelper;
import com.example.ocr.Funtion.ScreenShotListenManager;
import com.example.ocr.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class SeriesFuntion extends Service {
    private static final int K = 11201;
    private ScreenShotListenManager manager;
    private DataBaseOpenHelper db;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            db = DataBaseOpenHelper.getInstance(getBaseContext(),"ocrSql",1,new ArrayList<String>());

            switch (msg.what) {
                case 200:
                    String result = (String) msg.obj;
//                    Log.d("rrrrrrrrrrrrrresult", result);
                    Map<String,Object> josn = JSON.parseObject(result, Map.class);
                    List<Map<String, Object>> list = JSON.parseObject(String.valueOf(josn.get("words_result")), List.class);
//                    Log.d("lllllllllllist", String.valueOf(list));
                    float money = 0;
                    String category = "";
                    for (Map<String, Object> s:list){
                        String ocrResult= (String) s.get("words");
                        if (ocrResult.equals( "转帐成功") || ocrResult.equals("支付成功")){
                            category = "支出";
                        }
                        if (ocrResult.equals( "已收款") || ocrResult.equals("交易成功")){
                            category = "收入";
//                            Log.d("ooooooooooce", ocrResult);
                        }
                        try{
                            float i = Float.valueOf(ocrResult.substring(1));
                            if( i < 100000000 ){
                                money = i;
                            }
                        }catch (Exception e){

                        }
                    }
                    //识别成功后，写入数据库
                    if (category != null || money != 0){
                        final List<String> list1 = new ArrayList<>();
                        Cursor cursor = db.query("money","money");
                        while (cursor.moveToNext()){
                            list1.add(cursor.getString(0));
                        }

                        final String[] items = list1.toArray(new String[]{});
                        AlertDialog.Builder listDialog = new AlertDialog.Builder(getApplicationContext());
                        listDialog.setCancelable(false);
                        final String finalCategory = category;
                        final float finalMoney = money;
                        listDialog.setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String user = "";
                                user = list1.get(which);

                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd-yy");
                                DecimalFormat decimalFormat =new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
                                String distanceString = decimalFormat.format(Float.valueOf(finalMoney));//format 返回的是字符串
                                Date date = new Date(System.currentTimeMillis());
                                String nowtime = simpleDateFormat.format(date);//时间

                                List<String> sql = new ArrayList<String>(){};
                                Cursor cursor1 = db.query("userphone","userphone");
                                Cursor cursor2 = db.query("money","money");
                                String Total = "";
                                while (cursor1.moveToNext()){
                                    Total = cursor1.getString(2);
                                }
                                float moneytal = Float.valueOf(Total) + Float.valueOf(finalMoney);
                                String UserTotal = "";
                                //用于查询帐户是否配对
                                while (cursor2.moveToNext()){
                                    String user1 = cursor2.getString(0);
                                    if (user1.equals(user)){
                                        UserTotal = cursor2.getString(1);
                                    }
                                }
                                float userMoneyTotal = Float.valueOf(UserTotal) + Float.valueOf(finalMoney);

                                String s = String.format("INSERT INTO water VALUES ('%s','%s','%s','%s','%s','%s')",user,moneytal,userMoneyTotal,"收入",distanceString,nowtime);
                                String alter = String.format("UPDATE userphone SET total = total+'%s' where i = 1",distanceString);

                                String moneySql2 = String.format("UPDATE money SET total = total+'%s' where username = '%s'",distanceString,user);

                                sql.add(s);
                                sql.add(alter);
                                sql.add(moneySql2);
                                db.execSQL(sql);
                                Toast.makeText(SeriesFuntion.this, "记帐完成", Toast.LENGTH_SHORT).show();
                            }
                        });
                        Dialog dialog = listDialog.create();
                        //设置全局弹出
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            dialog.getWindow().setType((WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY));
                        }else {
                            dialog.getWindow().setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));
                        }
                        dialog.show();

                        db.clear();
                    }else {
                        Toast.makeText(SeriesFuntion.this, "小伙子识别失败了，是不是账单图片?", Toast.LENGTH_SHORT).show();
                    }

            }
        }
    };
    @Override
    public void onCreate() {
        Toast.makeText(this, "My Service created", Toast.LENGTH_LONG).show();
        manager = ScreenShotListenManager.newInstance(getApplicationContext());
        manager.setListener(
                new ScreenShotListenManager.OnScreenShotListener() {
                    public void onShot(final String imagePath) {
                        // do something
//                        Toast.makeText(SeriesFuntion.this, "监听成功", Toast.LENGTH_SHORT).show();

                        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                        builder.setMessage("OCR为您记帐");
                        builder.setCancelable(true);
                        builder.setNegativeButton("取消", null);
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 具体操作
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Message message = handler.obtainMessage();

                                        String clientId = "7Yu2jL4YPgAHUl73mNjbWs5N";
                                        // 官网获取的 Secret Key 更新为你注册的
                                        String clientSecret = "oGovvF6O0mfvy12pWLqXif3TRDbBse73";
                                        String toten = AuthService.getAuth(clientId,clientSecret);
                                        String result = AccurateBasic.accurateBasic(imagePath, toten);
                                        message.what = 200;
                                        message.obj = result;
                                        handler.sendMessage(message);
                                    }
                                }).start();


//                                Log.d("rrrrrrrrrrrrrrrrresult", result);
//                                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
//                                ByteArrayOutputStream output = new ByteArrayOutputStream();
//                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
//                                byte[] datas = output.toByteArray();
//                                String imageString = new String(Base64.encodeToString(datas, Base64.DEFAULT));
//                                requestsIntent(imageString);
                            }
                        });
                        final Dialog dialog = builder.create();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            dialog.getWindow().setType((WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY));
                        }else {
                            dialog.getWindow().setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));
                        }
                        dialog.show();
                    }
                }
        );
        manager.startListen();

    }
//    private void  requestsIntent(final String iamge) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Message message = handler.obtainMessage();
//                URL url = null;
//                try {
//                    url = new URL("http://101.132.128.168:5000/parse_image");
//                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                    httpURLConnection.setRequestMethod("POST");
//                    httpURLConnection.setConnectTimeout(100000);
//                    httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//json格式
//                    httpURLConnection.setDoOutput(true);
//                    httpURLConnection.setDoInput(true);
//                    httpURLConnection.setUseCaches(false);
//                    JSONObject body = new JSONObject();
//
//                    String s = iamge;
//
//                    body.put("image", s);
//                    DataOutputStream stream = new DataOutputStream(httpURLConnection.getOutputStream());
//                    String sss = String.valueOf(body);
//                    stream.writeBytes(sss);
//                    stream.flush();
//                    stream.close();
//                    if (httpURLConnection.getResponseCode() == 201) {
//                        InputStreamReader inputStream = new InputStreamReader(httpURLConnection.getInputStream());
//                        BufferedReader buffer = new BufferedReader(inputStream);
//                        String r = null;
//                        String i = "";
//                        while ((r = buffer.readLine()) != null) {
//                            i += r;
//                        }
//                        inputStream.close();
//                        message.what = 200;
//                        message.obj = i;
//                        httpURLConnection.disconnect();
//                        handler.sendMessage(message);
//                    }
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (ProtocolException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }).start();
//    }
    
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String CHANNEL_ONE_ID = "com.example.ocr.JP";
        String CHANNEL_ONE_NAME = "OCREvent";
        NotificationChannel notificationChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ONE_ID,
                    CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(notificationChannel);
        }
//--------------------------------------------------------新增
        Notification notification=null;
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        notification = new Notification.Builder(this).setChannelId(CHANNEL_ONE_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("自动记帐正在运行")
                .setContentIntent(pendingIntent)
                .getNotification();
        notification.flags |= Notification.FLAG_NO_CLEAR;
        startForeground(1, notification);
//        }
        return super.onStartCommand(intent,flags,startId);
    }

    public   class SecondeSevice extends Service{
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
        @Override
        public void onCreate() {
            Toast.makeText(this, "My Service created", Toast.LENGTH_LONG).show();
            Log.i("aaaaaaaaaaa", "onCreate");
            manager = ScreenShotListenManager.newInstance(getApplicationContext());
            manager.setListener(
                    new ScreenShotListenManager.OnScreenShotListener() {
                        public void onShot(String imagePath) {
                            // do something
                            Toast.makeText(SeriesFuntion.this, "监听成功", Toast.LENGTH_SHORT).show();
                        }
                    }
            );
            manager.startListen();

        }
        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            startForeground(K,new Notification());
            stopForeground(true);
            Toast.makeText(this, "My Service created", Toast.LENGTH_LONG).show();
            Log.i("aaaaaaaaaaa", "onCreate");
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "My Service Stoped", Toast.LENGTH_LONG).show();
        manager.stopListen();
        stopForeground(true);
    }

}}
