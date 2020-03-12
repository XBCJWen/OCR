package com.example.ocr.DB;


import android.util.Log;

import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 数据库工具类：连接数据库用、获取数据库数据用
 * 相关操作数据库的方法均可写在该类
 */
public class DBUtils {

    private static String driver = "com.mysql.jdbc.Driver";// MySql驱动

//    private static String url = "jdbc:mysql://localhost:3306/map_designer_test_db";

    private static String user = "root";// 用户名

    private static String password = "123456";// 密码

    private static Connection getConn(String dbName){

        Connection connection = null;
        try{
            Class.forName(driver);// 动态加载类
            String ip = "101.132.128.168";// 写成本机地址，不能写成localhost，同时手机和电脑连接的网络必须是同一个

            // 尝试建立到给定数据库URL的连接
            connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":3306/" + dbName,
                    user, password);
        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }

    public static HashMap<String, Object> getPhoneMoney(){

        HashMap<String, Object> map = new HashMap<>();
        // 根据数据库名称，建立连接
        Connection connection = getConn("androidOCR");

        try {
            // mysql简单的查询语句。这里是根据MD_CHARGER表的NAME字段来查询某条记录
            String sql = "select * from MoneyTable";
//            String sql = "select * from MD_CHARGER";
            if (connection != null){// connection不为null表示与数据库建立了连接
                PreparedStatement ps = connection.prepareStatement(sql);
                if (ps != null){

                    ResultSet rs = ps.executeQuery();
                    if (rs != null){
                        int count = rs.getMetaData().getColumnCount();
                        Log.e("DBUtils","列总数：" + count+rs);
                        while (rs.next()){
                            ArrayList<String> list= new ArrayList<String>(){};
                            JSONObject jsonObject = new JSONObject();
                            String phoneNumber = rs.getString("phoneNumber");
                            String total = rs.getString("total");
                            String wechatUser = rs.getString("wechatUser");
                            String zhifuUser = rs.getString("zhifuUser");
                            jsonObject.put("总资金",total);
                            jsonObject.put("微信资金",wechatUser);
                            jsonObject.put("支付宝资金",zhifuUser);

//                            list.add(total);
//                            list.add(wechatUser);
//                            list.add(zhifuUser);
                            map.put(phoneNumber, jsonObject);
//                            map.put("总资金", total);
//                            map.put("微信", wechatUser);
//                            map.put("支付宝", zhifuUser);

                        }
                        connection.close();
                        ps.close();
                        return  map;
                    }else {
                        return null;
                    }
                }else {
                    return  null;
                }
            }else {
                return  null;
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("DBUtils","异常：" + e.getMessage());
            return null;
        }

    }


    public static HashMap<String, Object> getPhoneUser(){

        HashMap<String, Object> map = new HashMap<>();
        // 根据数据库名称，建立连接
        Connection connection = getConn("androidOCR");

        try {
            // mysql简单的查询语句。这里是根据MD_CHARGER表的NAME字段来查询某条记录
            String sql = "SELECT * FROM `UserTable` where phoneNumber=";
//            String sql = "select * from MD_CHARGER";
            if (connection != null){// connection不为null表示与数据库建立了连接
                PreparedStatement ps = connection.prepareStatement(sql);
                if (ps != null){
                    ResultSet rs = ps.executeQuery();
                    if (rs != null){
                        int count = rs.getMetaData().getColumnCount();
                        Log.e("DBUtils","列总数：" + count+rs);
                        while (rs.next()){
                            JSONObject jsonObject = new JSONObject();
                            String phoneNumber = rs.getString("phoneNumber");
                            String name = rs.getString("name");
                            jsonObject.put("昵称",name);
//                            jsonObject.put("帐号",name);
                            map.put(phoneNumber, jsonObject);
                        }
                        connection.close();
                        ps.close();
                        return  map;
                    }else {
                        return null;
                    }
                }else {
                    return  null;
                }
            }else {
                return  null;
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("DBUtils","异常：" + e.getMessage());
            return null;
        }

    }
    public String insertData(String phone){

        HashMap<String, Object> map = new HashMap<>();
        // 根据数据库名称，建立连接
        Connection connection = getConn("androidOCR");

        try {
            // mysql简单的查询语句。这里是根据MD_CHARGER表的NAME字段来查询某条记录
            String sql = "insert into UserTable values (%d,user);";
            String ss = String.format(sql, Integer.valueOf(phone));
            if (connection != null){// connection不为null表示与数据库建立了连接
                PreparedStatement ps = connection.prepareStatement(ss);
                if (ps != null){
                    ResultSet rs = ps.executeQuery();
                    return "sucess";
                }else {
                    return  null;
                }
            }else {
                return  null;
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("DBUtils","异常：" + e.getMessage());
            return null;
        }

    }

    public static HashMap<String, Object> selectData(String phone){

        HashMap<String, Object> map = new HashMap<>();
        // 根据数据库名称，建立连接
        Connection connection = getConn("androidOCR");
        try {
            // mysql简单的查询语句。这里是根据MD_CHARGER表的NAME字段来查询某条记录
            String initSql = "select * from UserTable where = %s";
            String sql =  String.format(initSql,phone);
            if (connection != null){// connection不为null表示与数据库建立了连接
                PreparedStatement ps = connection.prepareStatement(sql);
                if (ps != null){

                    ResultSet rs = ps.executeQuery();
                    if (rs != null){
                        int count = rs.getMetaData().getColumnCount();
                        Log.e("DBUtils","列总数：" + count+rs);
                        while (rs.next()){
                            JSONObject jsonObject = new JSONObject();
                            String phoneNumber = rs.getString("phoneNumber");
                            String name = rs.getString("name");
                            jsonObject.put("昵称",name);

                            map.put(phoneNumber, jsonObject);
//                            map.put("name", name);
                        }
                        connection.close();
                        ps.close();
                        return  map;
                    }else {
                        return null;
                    }
                }else {
                    return  null;
                }
            }else {
                return  null;
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("DBUtils","异常：" + e.getMessage());
            return null;
        }

    }
}

