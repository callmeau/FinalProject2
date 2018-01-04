package com.example.lpf.finaldemo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

    public static ArrayList<String> QueryStu(String account) {
        ArrayList<String> result = new ArrayList<>();
        String d_id = "";
        String d_name = "";
        String d_no = "";//宿舍号
        String name="";//名字
        String no="";//学号
        String gender="";
        String academy = "";
        String faculty = "";
        String classnum = "";
        String sleeptime = "";
        String waketime = "";
        String hobby1 = "";
        String hobby2 = "";
        String hobby3 = "";
        String plan = "";
        String sql = "select * from student where S_account = '"+account+"'";
        try {
            Connection conn = getSQLConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                name = rs.getString("S_name");
                 no=rs.getString("S_no");
                 gender=rs.getString("S_gender");
                 academy = rs.getString("S_academy");
                 faculty = rs.getString("S_faculty");
                 classnum = rs.getString("S_class");
                 sleeptime = rs.getString("S_sleeptime");
                 waketime = rs.getString("S_waketime");
                 hobby1 = rs.getString("S_hobby1");
                 hobby2 = rs.getString("S_hobby2");;
                 hobby3 = rs.getString("S_hobby3");;
                 plan = rs.getString("S_plan");
                 d_id = rs.getString("S_dormId");
                result.add(name);
                result.add(no);
                result.add(gender);
                result.add(academy);
                result.add(faculty);
                result.add(classnum);
                result.add(sleeptime);
                result.add(waketime);
                result.add(hobby1);
                result.add(hobby2);
                result.add(hobby3);
                result.add(plan);
            }

            rs.close();
            stmt.close();
            conn.close();
            sql="select * from Building where B_id = "+d_id;
            Connection conn1 = getSQLConnection();
            Statement stmt1 = conn1.createStatement();
            ResultSet rs1 = stmt1.executeQuery(sql);
            if(rs1.next()) d_name=rs1.getString("B_name");
            result.add(d_name);//宿舍名称
            rs1.close();
            stmt1.close();
            conn1.close();

            sql = "select * from stu_query where This_stu = '"+account+"'";
            Connection conn2 = getSQLConnection();
            Statement stmt2 = conn2.createStatement();
            ResultSet rs2 = stmt2.executeQuery(sql);
            if(rs2.next()) d_no=rs2.getString("D_no");
            result.add(d_no);//宿舍号
            rs2.close();
            stmt2.close();
            conn2.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
            System.out.println("-----------------------");
        }
        return result;
    }

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

    public static void Uploaddata(String academy,String faculty,String classnum, String sleep_time,String wake_time
    ,String hobby1,String hobby2,String hobby3,String plan,String account){
        try {
            String sql = String.format("UPDATE student SET S_academy = '%s',S_faculty = '%s',S_class = '%s' , " +
                            "S_sleeptime = '%s' ,S_waketime = '%s',S_hobby1 = '%s',S_hobby2 = '%s',S_hobby3 = '%s'," +
                            "S_plan = '%s' where S_account = '%s';",
                    academy,faculty,classnum,sleep_time,wake_time,hobby1,hobby2,hobby3,plan,account);  ;
            Connection conn = getSQLConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("e");
            System.out.println("上传失败");
            System.out.println("-----------------------");
        }
    }

    public static void Order(String num,int id){
        try {
            String sql = String.format("INSERT INTO WaterOrder (W_num,W_date,W_iffinish,W_dormId)" +
                            "Values('%s',GETDATE(),'%s','%s')",
                    num,"0",Integer.toString(id));  ;
            Connection conn = getSQLConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("e");
            System.out.println("上传失败");
            System.out.println("-----------------------");
        }
    }

    public static void repair(String title,String content,int id){
        try {
            String sql = String.format("INSERT INTO WaterOrder (R_detail,R_starttime,R_ifrepair,W_dormId)" +
                            "Values('%s',GETDATE(),'%s','%s')",
                    title+": "+content,"0",Integer.toString(id));  ;
            Connection conn = getSQLConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("e");
            System.out.println("上传失败");
            System.out.println("-----------------------");
        }
    }

    public static void uploadImage(byte[] img,String account){
        try {
            String sql = "UPDATE student SET S_profile = ?  where S_account = ?;";
            Connection conn = getSQLConnection();
            Statement stmt = conn.createStatement();
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setBytes(1, img);//设置第一个问号
            pStatement.setString(2, account);
            pStatement.execute();//执行

            pStatement.close();
            conn.close();
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println(e);
            System.out.println("上传失败");
            System.out.println("-----------------------");
        }

    }
    public static byte[] loadImage(String account){
        byte[] b = new byte[0];
        try {
            String sql = "SELECT * from student  where S_account = ?;";
            Connection conn = getSQLConnection();
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setString(1, account);
            ResultSet rs =  pStatement.executeQuery();//执行
            if (rs.next())
            b=rs.getBytes("S_profile");
            rs.close();
            pStatement.close();
            conn.close();
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println(e);
            System.out.println("加载失败");
            System.out.println("-----------------------");
        }
        return b;

    }



}
