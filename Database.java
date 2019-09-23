package flightscheduleradithyankurupaqk5645;


import java.sql.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author adith
 */
public class Database {
    
    private final static String url = "jdbc:derby://localhost:1527/FlighSchedulerDBAdithyanKurupaqk5645";
    private final static String user = "java";
    private final static String pass = "java";
    
    public static String getURL(){
        return url;
    }
    public static String getUser(){
        return user;
    }
    public static String getPassword(){
        return pass;
    }
    public static Connection getConnection(){
        Connection c = null;
        try {
            c = DriverManager.getConnection(url, user, pass);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return c;
    }
}
