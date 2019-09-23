/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightscheduleradithyankurupaqk5645;

import static flightscheduleradithyankurupaqk5645.Customer.getCustomer;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author adith
 */
public class Day {
    
    public static ArrayList<String> getDays(){
        ArrayList<String> days = new ArrayList();
        Connection c = Database.getConnection();
        try {
            PreparedStatement request = c.prepareStatement("SELECT * FROM DATES");
            ResultSet dayResults = request.executeQuery();
            while(dayResults.next()){
                String newDate = dayResults.getString("DATE");
                if(!days.contains(newDate))
                    days.add(newDate);              
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return days;
    }
    public static void addDay(Date day){
        if(!getDays().contains(day)){
            Connection c = Database.getConnection();
            try {
                PreparedStatement request = c.prepareStatement("INSERT INTO DATES VALUES (?)");
                request.setDate(1, day);
                request.executeUpdate();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }   
}
