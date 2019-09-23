/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightscheduleradithyankurupaqk5645;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author adith
 */
public class Status {
    
    public static String statusFlight(String date, String flight){
        String status = "";
        Connection c = Database.getConnection();
        int seats = Flight.getSeats(flight);
        try {
            PreparedStatement request = c.prepareStatement("SELECT * FROM BOOKINGS WHERE FLIGHT = ? AND DATE = ?");
            request.setString(1,flight);
            request.setString(2,date);
            ResultSet statusResults = request.executeQuery();
            int spot = 1;
            while(statusResults.next()){    
                status += (" Spot " + spot + ": " + statusResults.getString("NAME"));
                if(spot > seats){
                    status += "\t--Waitlisted--\n";
                }
                else{
                    status += "\n";
                }
                spot++;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }
    
    public static String statusWaitlist(String date){
        String status = "";
        Connection c = Database.getConnection();
        ArrayList <String> flights = Flight.getFlights();
        for (String flight:flights){
            try {
                status += "Flight: " + flight + "\n";
                int seats = Flight.getSeats(flight);
                PreparedStatement request = c.prepareStatement("SELECT * FROM BOOKINGS WHERE FLIGHT = ? AND DATE = ?");
                request.setString(1,flight);
                request.setString(2,date);
                ResultSet statusResults = request.executeQuery();
                while(statusResults.next()){
                    if(seats <= 0)
                        status += (" Customer: " + statusResults.getString("NAME") + "\n");
                    seats--;
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return status;
    }
    public static String statusCustomer(String customer){
        String status = "";
        Connection c = Database.getConnection();
        try {
            PreparedStatement request = c.prepareStatement("SELECT * FROM BOOKINGS WHERE NAME = (?)");
            request.setString(1,customer);
            ResultSet statusResults = request.executeQuery();
            while(statusResults.next()){
                status += (" Flight: " + statusResults.getString("FLIGHT") + "\n  Date: " + statusResults.getString("DATE"));
                PreparedStatement request1 = c.prepareStatement("SELECT * FROM BOOKINGS WHERE FLIGHT = (?) AND DATE = (?)");
                int seats = Flight.getSeats(statusResults.getString("FLIGHT"));
                request1.setString(1,statusResults.getString("FLIGHT"));
                request1.setString(2,statusResults.getString("DATE"));
                ResultSet statusResults1 = request1.executeQuery();
                int spot = 1;
                while(statusResults1.next()){
                    if(spot > seats && customer.equals(statusResults1.getString("NAME"))){
                        status += "\t--Waitlisted--";
                        break;
                    }
                    spot++;
                }
                status += "\n";
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        } 
        return status;
    }
}
