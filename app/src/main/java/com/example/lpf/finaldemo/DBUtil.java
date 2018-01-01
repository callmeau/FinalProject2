package com.example.lpf.finaldemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by JingrongFeng on 2017/12/30.
 */

public class DBUtil
{
    private static Connection getSQLConnection()
    {
        String userName = "learn_sql";  //默认用户名
        String userPwd = "learn_sql2017";   //密码
        String url="jdbc:jtds:sqlserver://rm-learnsql2017.sqlserver.rds.aliyuncs.com:3433;DatabaseName=AndroidProj";
        Connection dbConn = null;
        try
        {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");   //加载驱动
            dbConn = DriverManager.getConnection(url, userName, userPwd);
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return dbConn;
    }

//    public static String QueryUsers(String sql)     //模板
//    {
//        String result = "";
//        try
//        {
//            Connection conn = getSQLConnection();
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery(sql);
//            while (rs.next())
//            {
//                String s1 = rs.getString("UserName");
//                String s2 = rs.getString("Password");
//                result += s1 + "  -  " + s2 + "\n";
//                System.out.println(s1 + "  -  " + s2);
//            }
//            rs.close();
//            stmt.close();
//            conn.close();
//        } catch (SQLException e)
//        {
//            e.printStackTrace();
//            result += "查询数据异常!" + e.getMessage();
//        }
//        return result;
//    }

    public static String QueryDorm(String sql)
    {
        String result = "";
//        try
//        {
//            Connection conn = getSQLConnection();
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery(sql);
//            while (rs.next())
//            {
//                String dorm_id = rs.getString("D_id");
//                String dorm_no = rs.getString("D_no");
//                result += s1 + "  -  " + s2 + "\n";
//            }
//            rs.close();
//            stmt.close();
//            conn.close();
//        } catch (SQLException e)
//        {
//            e.printStackTrace();
//            result += "查询数据异常!" + e.getMessage();
//        }
        return result;
    }

}
