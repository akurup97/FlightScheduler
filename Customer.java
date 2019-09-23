/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightscheduleradithyankurupaqk5645;

import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author adith
 */
public class Customer {
    
    public static ArrayList<String> getCustomer(){
        ArrayList<String> customers = new ArrayList();
        Connection c = Database.getConnection();
        try {
            PreparedStatement request = c.prepareStatement("SELECT * FROM CUSTOMERS");
            ResultSet customersResults = request.executeQuery();
            while(customersResults.next()){
                customers.add(customersResults.getString("CUSTOMER"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }  
    
    public static void addCustomer(String name){
        if(!getCustomer().contains(name) && name.length() > 0){
            Connection c = Database.getConnection();
            try {
                PreparedStatement request = c.prepareStatement("INSERT INTO CUSTOMERS VALUES (?)");
                request.setString(1,name);
                request.executeUpdate();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }   
}

