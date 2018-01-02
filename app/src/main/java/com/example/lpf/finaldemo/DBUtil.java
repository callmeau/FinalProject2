package com.example.lpf.finaldemo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    // 根据管理员账号获取所管理楼栋的宿舍
    public static String QueryAllDorm(String a_account)
    {
        String result = "";
        try
        {
            Connection conn = getSQLConnection();
            CallableStatement stmt = conn.prepareCall("{call PROC_SELECT_STUDENTS(?)}");
            stmt.setString(1, a_account);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                String dorm_id = rs.getString("D_id");
                String building_id = rs.getString("D_buildingId");
                String dorm_no = rs.getString("D_no");
                String stu1 = rs.getString("D_stu1");
                String stu2 = rs.getString("D_stu2");
                String stu3 = rs.getString("D_stu3");
                String stu4 = rs.getString("D_stu4");
                result += dorm_id+","+building_id+","+dorm_no+","+stu1+","+stu2+","+stu3+","+stu4+"\n";
            }
            rs.close();
            stmt.close();
            conn.close();
            System.out.println("查询成功");
            System.out.println("-----------------------");
            System.out.println("查询结果：\n"+result);
        } catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println("查询失败");
            System.out.println("-----------------------");
        }
        return result;
    }

}
