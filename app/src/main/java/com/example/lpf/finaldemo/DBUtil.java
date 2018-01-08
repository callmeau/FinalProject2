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


    //调用存储过程更新dormlist调整宿舍
    public static void UpdateExchange(String stu1,String stu2,String stu3,String stu4,String stu5,String stu6,String stu7,String stu8,int room1,int room2){
        try{
            Connection conn = getSQLConnection();
            CallableStatement stmt = conn.prepareCall("{call PROC_EXECHANGE_DORM(?,?,?,?,?,?,?,?,?,?)}");
            stmt.setString(1,stu1);
            stmt.setString(2,stu2);
            stmt.setString(3,stu3);
            stmt.setString(4,stu4);
            stmt.setString(5,stu5);
            stmt.setString(6,stu6);
            stmt.setString(7,stu7);
            stmt.setString(8,stu8);
            stmt.setInt(9,room1);
            stmt.setInt(10,room2);
            stmt.execute();
            stmt.close();
            conn.close();
            System.out.println("修改成功");
            System.out.println("-----------------------");
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("change：");
            System.out.println("修改失败");
            System.out.println("-----------------------");
        }
    }

    public static String QueryName(String s_account){
        String sname="";
        try {
            Connection connection = getSQLConnection();
            CallableStatement statement = connection.prepareCall("{call PROC_STUDENTS_NAME(?)}");
            statement.setString(1,s_account);
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                sname = rs.getString("S_name");
            }
            statement.close();
            connection.close();
            System.out.println("查询成功");
            System.out.println("-----------------------");
            System.out.println("查询结果：\n"+sname);
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("getname：");
            System.out.println("查询失败");
        }
        return sname;
    }
  
  /*
    // 根据管理员账号获取所管理楼栋的宿舍
    public static String QueryAllDorm2(String a_account)
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
    */


    // 根据学生账号获取学生信息
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
                 hobby2 = rs.getString("S_hobby2");
                 hobby3 = rs.getString("S_hobby3");
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
                String stu1_name = " ";
                String stu2_name = " ";
                String stu3_name = " ";
                String stu4_name = " ";
                if (stu1 != null){
                    String sql1 = "select * from student where S_account = '"+stu1+"'";
                    try {
                        Connection conn1 = getSQLConnection();
                        Statement stmt1 = conn1.createStatement();
                        ResultSet rs1 = stmt1.executeQuery(sql1);
                        while (rs1.next()) {
                            stu1_name = rs1.getString("S_name");
                        }
                        rs1.close();
                        stmt1.close();
                        conn1.close();
                    } catch (SQLException e)
                    {
                        e.printStackTrace();
                        System.out.println("查询失败");
                        System.out.println("-----------------------");
                    }
                }
                if (stu2 != null) {
                    String sql2 = "select * from student where S_account = '"+stu2+"'";
                    try {
                        Connection conn2 = getSQLConnection();
                        Statement stmt2 = conn2.createStatement();
                        ResultSet rs2 = stmt2.executeQuery(sql2);
                        while (rs2.next()) {
                            stu2_name = rs2.getString("S_name");
                        }
                        rs2.close();
                        stmt2.close();
                        conn2.close();
                    } catch (SQLException e)
                    {
                        e.printStackTrace();
                        System.out.println("查询失败");
                        System.out.println("-----------------------");
                    }
                }
                if (stu3 != null) {
                    String sql3 = "select * from student where S_account = '"+stu3+"'";
                    try {
                        Connection conn3 = getSQLConnection();
                        Statement stmt3 = conn3.createStatement();
                        ResultSet rs3 = stmt3.executeQuery(sql3);
                        while (rs3.next()) {
                            stu3_name = rs3.getString("S_name");
                        }
                        rs3.close();
                        stmt3.close();
                        conn3.close();
                    } catch (SQLException e)
                    {
                        e.printStackTrace();
                        System.out.println("查询失败");
                        System.out.println("-----------------------");
                    }
                }
                if (stu4 != null) {
                    String sql4 = "select * from student where S_account = '"+stu4+"'";
                    try {
                        Connection conn4 = getSQLConnection();
                        Statement stmt4 = conn4.createStatement();
                        ResultSet rs4 = stmt4.executeQuery(sql4);
                        while (rs4.next()) {
                            stu4_name = rs4.getString("S_name");
                        }
                        rs4.close();
                        stmt4.close();
                        conn4.close();
                    } catch (SQLException e)
                    {
                        e.printStackTrace();
                        System.out.println("查询失败");
                        System.out.println("-----------------------");
                    }
                }

                result += dorm_id+","+building_id+","+dorm_no+","+stu1_name+","+stu2_name+","+stu3_name+","+stu4_name
                  +","+stu1+","+stu2+","+stu3+","+stu4+"\n";
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

    //根据宿舍ID查询宿舍成员
    public static String QueryDormInfo(String D_id)
    {
        String result = "";
        try
        {
            String sql = "select * from dormitory where D_id = '"+D_id+"'";
            Connection conn = getSQLConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String building_id = rs.getString("D_buildingId");
                String dorm_no = rs.getString("D_no");
                String stu1 = rs.getString("D_stu1");
                String stu2 = rs.getString("D_stu2");
                String stu3 = rs.getString("D_stu3");
                String stu4 = rs.getString("D_stu4");
                String stu1_name = " ";
                String stu2_name = " ";
                String stu3_name = " ";
                String stu4_name = " ";
                String stu1_no = " ";
                String stu2_no = " ";
                String stu3_no = " ";
                String stu4_no = " ";
                String stu1_school = " ";
                String stu2_school = " ";
                String stu3_school = " ";
                String stu4_school = " ";
                String building_name = " ";
                String sql0 = "select * from building where B_id = '"+building_id+"'";
                try {
                    Connection conn0 = getSQLConnection();
                    Statement stmt0 = conn0.createStatement();
                    ResultSet rs0 = stmt0.executeQuery(sql0);
                    while (rs0.next()) {
                        building_name = rs0.getString("B_name");
                    }
                    rs0.close();
                    stmt0.close();
                    conn0.close();
                } catch (SQLException e)
                {
                    e.printStackTrace();
                    System.out.println("查询失败");
                    System.out.println("-----------------------");
                }
                if(stu1 != null){
                    String sql1 = "select * from student where S_account = '"+stu1+"'";
                    try {
                        Connection conn1 = getSQLConnection();
                        Statement stmt1 = conn1.createStatement();
                        ResultSet rs1 = stmt1.executeQuery(sql1);
                        while (rs1.next()) {
                            stu1_name = rs1.getString("S_name");
                            stu1_no = rs1.getString("S_no");
                            stu1_school = rs1.getString("S_academy");
                        }
                        rs1.close();
                        stmt1.close();
                        conn1.close();
                    } catch (SQLException e)
                    {
                        e.printStackTrace();
                        System.out.println("查询失败");
                        System.out.println("-----------------------");
                    }
                }
                if (stu2 != null){
                    String sql2 = "select * from student where S_account = '"+stu2+"'";
                    try {
                        Connection conn2 = getSQLConnection();
                        Statement stmt2 = conn2.createStatement();
                        ResultSet rs2 = stmt2.executeQuery(sql2);
                        while (rs2.next()) {
                            stu2_name = rs2.getString("S_name");
                            stu2_no = rs2.getString("S_no");
                            stu2_school = rs2.getString("S_academy");
                        }
                        rs2.close();
                        stmt2.close();
                        conn2.close();
                    } catch (SQLException e)
                    {
                        e.printStackTrace();
                        System.out.println("查询失败");
                        System.out.println("-----------------------");
                    }
                }
                if (stu3 != null){
                    String sql3 = "select * from student where S_account = '"+stu3+"'";
                    try {
                        Connection conn3 = getSQLConnection();
                        Statement stmt3 = conn3.createStatement();
                        ResultSet rs3 = stmt3.executeQuery(sql3);
                        while (rs3.next()) {
                            stu3_name = rs3.getString("S_name");
                            stu3_no = rs3.getString("S_no");
                            stu3_school = rs3.getString("S_academy");
                        }
                        rs3.close();
                        stmt3.close();
                        conn3.close();
                    } catch (SQLException e)
                    {
                        e.printStackTrace();
                        System.out.println("查询失败");
                        System.out.println("-----------------------");
                    }
                }
                if (stu4 != null) {
                    String sql4 = "select * from student where S_account = '"+stu4+"'";
                    try {
                        Connection conn4 = getSQLConnection();
                        Statement stmt4 = conn4.createStatement();
                        ResultSet rs4 = stmt4.executeQuery(sql4);
                        while (rs4.next()) {
                            stu4_name = rs4.getString("S_name");
                            stu4_no = rs4.getString("S_no");
                            stu4_school = rs4.getString("S_academy");
                        }
                        rs4.close();
                        stmt4.close();
                        conn4.close();
                    } catch (SQLException e)
                    {
                        e.printStackTrace();
                        System.out.println("查询失败");
                        System.out.println("-----------------------");
                    }
                }

                result += building_name+","+dorm_no+","+stu1_name+","+stu1_no+","+stu1_school+","+stu2_name+","+stu2_no+","+stu2_school+
                        ","+stu3_name+","+stu3_no+","+stu3_school+","+stu4_name+","+stu4_no+","+stu4_school+"\n";
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


    //根据宿舍id查询维修表
    public static String QueryRepair(String D_id)
    {
        String result = "";
        try
        {
            String sql = "select * from repair_query where R_dormId = '"+D_id+"'";
            Connection conn = getSQLConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String R_id = rs.getString("R_id");
                String R_stime = rs.getString("R_starttime");
                String R_detail = rs.getString("R_detail");
                String R_check = rs.getString("R_ifrepair");
                if (R_check == null || R_check.equals("")){
                    R_check = "0";
                }
                if (R_stime.equals("")){
                    R_stime = " ";
                }
                if (R_detail.equals("")){
                    R_detail = " ";
                }
                result += R_id+","+R_stime+","+R_detail+","+R_check+"\n";
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

    //根据宿舍id查询订水表
    public static String QueryWater(String D_id)
    {
        String result = "";
        try
        {
            String sql = "select * from order_query where W_dormId = '"+D_id+"'";
            Connection conn = getSQLConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String W_id = rs.getString("W_id");
                String W_date = rs.getString("W_date");
                String W_num = rs.getString("W_num");
                String W_check = rs.getString("W_iffinish");
                if (W_check == null || W_check.equals("")){
                    W_check = "0";
                }
                if (W_date.equals("")){
                    W_date = " ";
                }
                if (W_num.equals("")){
                    W_num = " ";
                }
                result += W_id+","+W_date+","+W_num+","+W_check+"\n";
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

    //根据报修订单id删除条目
    public static void DeleteRepair(String R_id) {
        try
        {
            String sql = "delete from RepairInfo where R_id = '"+R_id+"'";
            Connection conn = getSQLConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.close();
            stmt.close();
            conn.close();
            System.out.println("删除成功");
            System.out.println("-----------------------");
        } catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println("删除失败");
            System.out.println("-----------------------");
        }
    }

    //根据订水订单id删除条目
    public static void DeleteWater(String W_id) {
        try
        {
            String sql = "delete from WaterOrder where W_id = '"+W_id+"'";
            Connection conn = getSQLConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.close();
            stmt.close();
            conn.close();
            System.out.println("删除成功");
            System.out.println("-----------------------");
        } catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println("删除失败");
            System.out.println("-----------------------");
        }
    }

    //根据订单id更新订单状态
    public static void UpdateRepair(String R_id) {
        try {
            String sql = String.format("UPDATE RepairInfo SET R_ifrepair = '%s' where R_id = '%s';","1", R_id);
            Connection conn = getSQLConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
            System.out.println("更新成功");
            System.out.println("-----------------------");
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("e");
            System.out.println("更新失败");
            System.out.println("-----------------------");
        }
    }

    //根据订单id更新订单状态
    public static void UpdateWater(String W_id) {
        try {
            String sql = String.format("UPDATE WaterOrder SET W_iffinish = '%s' where W_id = '%s';","1", W_id);
            Connection conn = getSQLConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
            System.out.println("更新成功");
            System.out.println("-----------------------");
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("e");
            System.out.println("更新失败");
            System.out.println("-----------------------");
        }
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
                    num,"0",Integer.toString(id));
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
            String sql = String.format("INSERT INTO RepairInfo (R_detail,R_starttime,R_ifrepair,W_dormId)" +
                            "Values('%s',GETDATE(),'%s','%s')",
                    title+": "+content,"0",Integer.toString(id));
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

    public static void notification(String content,int bu_id){
        try {
            //String s = "SET IDENTITY_INSERT Notification ON";
            //String sq = "SET IDENTITY_INSERT Notification OFF";
            String sql = String.format("INSERT INTO Notification (N_date,N_detail,N_obj)" +
                                                "Values(GETDATE(),'%s','%s')",
                    content,Integer.toString(bu_id));
            Connection connection = getSQLConnection();
            Statement stmt = connection.createStatement();
            //stmt.execute(s);
            stmt.executeUpdate(sql);
           // stmt.execute(sq);
            stmt.close();
            connection.close();
           // Connection conn = getSQLConnection();
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("e");
            System.out.println("发布失败");
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
