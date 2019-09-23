/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightscheduleradithyankurupaqk5645;

import java.sql.*;

/**
 *
 * @author adith
 */
public class Booking {

    public static void addBooking(String flight, String date, String name){
        Connection c = Database.getConnection();
        try {
            PreparedStatement request = c.prepareStatement("INSERT INTO BOOKINGS VALUES (?, ?, ?, ?)");
            request.setString(1,name);
            request.setString(2,date);
            request.setString(3,flight);
            request.setTimestamp(4,new Timestamp(System.currentTimeMillis()));
            request.executeUpdate();     
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void cancelBooking(String name, String date){
        Connection c = Database.getConnection();
        try {
            PreparedStatement request = c.prepareStatement("DELETE FROM BOOKINGS WHERE NAME = (?) AND DATE = (?)");
            request.setString(1,name);
            request.setString(2,date);
            request.executeUpdate();     
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
