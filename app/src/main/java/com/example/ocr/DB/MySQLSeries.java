package com.example.ocr.DB;

import android.util.Log;

import com.example.ocr.Beam.UserTable;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MySQLSeries {
        private Connection conn=null; //打开数据库对象
        private PreparedStatement ps=null;//操作整合sql语句的对象
        private ResultSet rs=null;//查询结果的集合

        //DBService 对象
        public static MySQLSeries dbService=null;

        /**
         * 构造方法 私有化
         * */

        private MySQLSeries(){

        }

        /**
         * 获取MySQL数据库单例类对象
         * */

        public static MySQLSeries getDbService(){
            if(dbService==null){
                dbService=new MySQLSeries();
            }
            return dbService;
        }


        /**
         * 获取要发送短信的患者信息    查
         * */

        public String getUserData(String phone){
            //结果存放集合
//            List<UserTable> list=new ArrayList<UserTable>();
            JSONObject json = new JSONObject();
            String data="";
            //MySQL 语句
            String sql="select * from UserTable where phoneNumber =?";
            //获取链接数据库对象
            conn= MySQLUntils.getConn();
            try {
                if(conn!=null&&(!conn.isClosed())){
                    ps= (PreparedStatement) conn.prepareStatement(sql);
                    ps.setString(1,phone);
                    if(ps!=null){
                        rs= ps.executeQuery();
                        if(rs!=null){
                            while(rs.next()){
                                String user = rs.getString(1);
                                String name =rs.getString(2);
                                Log.d("name",name+user);
                                json.put("name",name);
                                json.put("user",user);
                                data = String.valueOf(name);
//                                list.add(u);
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            MySQLUntils.closeAll(conn,ps,rs);//关闭相关操作
            return data;
        }

        /**
         * 修改数据库中某个对象的状态   改
         * */

        public int updateUserData(String phone){
            int result=-1;
            if(phone!=null){
                //获取链接数据库对象
                conn= MySQLUntils.getConn();
                //MySQL 语句
                String sql="update UserTable set state=? where phone=?";
                try {
                    boolean closed=conn.isClosed();
                    if(conn!=null&&(!closed)){
                        ps= (PreparedStatement) conn.prepareStatement(sql);
                        ps.setString(1,"1");//第一个参数state 一定要和上面SQL语句字段顺序一致
                        ps.setString(2,phone);//第二个参数 phone 一定要和上面SQL语句字段顺序一致
                        result=ps.executeUpdate();//返回1 执行成功
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            MySQLUntils.closeAll(conn,ps);//关闭相关操作
            return result;
        }

        /**
         * 批量向数据库插入数据   增
         * */

        public int insertUserData(List<UserTable> list){
            int result=-1;
            if((list!=null)&&(list.size()>0)){
                //获取链接数据库对象
                conn= MySQLUntils.getConn();
                //MySQL 语句
                String sql="INSERT INTO UserTable  VALUES (?,?,?)";
                try {
                    boolean closed=conn.isClosed();
                    if((conn!=null)&&(!closed)){
                        for(UserTable user:list){
                            ps= (PreparedStatement) conn.prepareStatement(sql);
                            String name = user.getName();
                            String phone=String.valueOf(user.getUser());
                            String passwd=String.valueOf(user.getUser());

                            ps.setString(1,phone);//第一个参数 name 规则同上
                            ps.setString(2,name);//第二个参数 phone 规则同上
                            ps.setString(3,passwd);//第二个参数 phone 规则同上

                            result=ps.executeUpdate();//返回1 执行成功
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            MySQLUntils.closeAll(conn,ps);//关闭相关操作
            return result;
        }


        /**
         * 删除数据  删
         * */

        public int delUserData(String phone){
            int result=-1;
            if(phone != null) {
                //获取链接数据库对象
                conn= MySQLUntils.getConn();
                //MySQL 语句
                String sql="delete from UserTable where phone=?";
                try {
                    boolean closed=conn.isClosed();
                    if((conn!=null)&&(!closed)){
                        ps= (PreparedStatement) conn.prepareStatement(sql);
                        ps.setString(1, phone);
                        result=ps.executeUpdate();//返回1 执行成功
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            MySQLUntils.closeAll(conn,ps);//关闭相关操作
            return result;
        }

}
