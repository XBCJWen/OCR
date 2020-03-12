package com.example.ocr.DB;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLUntils {
        private static String driver = "com.mysql.jdbc.Driver";//MySQL 驱动
        private static String url = "jdbc:mysql://101.132.128.168:3306/androidOCR";//MYSQL数据库连接Url
        private static String user = "root";//用户名
        private static String password = "123456";//密码

        /**
         * 连接数据库
         * */

        public static Connection getConn(){
            Connection conn = null;
            try {
                Class.forName(driver);//获取MYSQL驱动
                conn = (Connection) DriverManager.getConnection(url, user, password);//获取连接
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return conn;
        }

        /**
         * 关闭数据库
         * */

        public static void closeAll(Connection conn, PreparedStatement ps){
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }

        /**
         * 关闭数据库
         * */

        public static void closeAll(Connection conn, PreparedStatement ps, ResultSet rs){
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


}
