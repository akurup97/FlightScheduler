/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightscheduleradithyankurupaqk5645;

import java.sql.*;
import java.util.*;

/**
 *
 * @author adith
 */
public class Flight {
    
    public static ArrayList<String> getFlights(){
        ArrayList<String> flights = new ArrayList();
        Connection c = Database.getConnection();
        try {
            PreparedStatement request = c.prepareStatement("SELECT * FROM FLIGHT");
            ResultSet flightResults = request.executeQuery();
            while(flightResults.next()){
                flights.add(flightResults.getString("FLIGHT_ID"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return flights;
    }
    
    public static int getSeats(String flight){
        int seats = 0;
        Connection c = Database.getConnection();

        try {
            PreparedStatement request = c.prepareStatement("SELECT * FROM FLIGHT WHERE FLIGHT_ID = (?)");
            request.setString(1,flight);
            ResultSet statusResults = request.executeQuery();
            if(statusResults.next())
                seats = statusResults.getInt("SEATS");
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return seats;
    }
    
    public static void addFlight(String flight, int seats){
        if(!getFlights().contains(flight) && flight.length() > 0 && seats > 0){
            Connection c = Database.getConnection();
            try {
                PreparedStatement request = c.prepareStatement("INSERT INTO FLIGHT VALUES (?, ?)");
                request.setString(1, flight);
                request.setInt(2, seats);
                request.executeUpdate();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
    public static void dropFlight(String flight){
        int seats = getSeats(flight);
        if(getFlights().contains(flight) && flight.length() > 0){
            Connection c = Database.getConnection();
            try {
                PreparedStatement request = c.prepareStatement("DELETE FROM FLIGHT WHERE FLIGHT_ID = (?)");
                request.setString(1, flight);
                request.executeUpdate();
                request = c.prepareStatement("SELECT * FROM BOOKINGS WHERE FLIGHT = (?)");
                request.setString(1, flight);
                ResultSet statusResults = request.executeQuery();
                while(statusResults.next()){
                    for(int i = 0; i < seats; i++){
                        String name = statusResults.getString("NAME");
                        String day = statusResults.getString("DATE");
                        String newFlight = null;
                        PreparedStatement request1 = c.prepareStatement("SELECT * FROM FLIGHT");
                        ResultSet statusResults1 = request1.executeQuery();
                        while(statusResults1.next()){//goes through the new flight table
                            PreparedStatement request2 = c.prepareStatement("SELECT * FROM BOOKINGS WHERE FLIGHT = (?) AND DATE = (?)");
                            request2.setString(1, statusResults1.getString("FLIGHT_ID"));
                            request2.setString(2, day);
                            ResultSet statusResults2 = request2.executeQuery();
                            int spot = 1;
                            int seats1 = getSeats(statusResults1.getString("FLIGHT_ID"));
                            while(statusResults2.next()){//goes through potential new flight
                                if(spot > seats1){
                                    break;
                                }
                                spot++;
                            }
                            if(spot <= seats1){
                                newFlight = statusResults1.getString("FLIGHT_ID");
                                PreparedStatement request3 = c.prepareStatement("INSERT INTO BOOKINGS VALUES (?, ?, ?, ?)");
                                request3.setString(1, name);
                                request3.setString(2, day);
                                request3.setString(3, newFlight);
                                request3.setTimestamp(4,new Timestamp(System.currentTimeMillis()));
                                request3.executeUpdate();
                                break;
                            }
                        }
                        PreparedStatement deleteRequest = c.prepareStatement("DELETE FROM BOOKINGS WHERE NAME = (?) AND DATE = (?) AND FLIGHT = (?)");
                        deleteRequest.setString(1, name);
                        deleteRequest.setString(2, day);
                        deleteRequest.setString(3, flight);
                        deleteRequest.executeUpdate();
                    }
                }
                request = c.prepareStatement("SELECT * FROM BOOKINGS WHERE FLIGHT = (?)");
                request.setString(1, flight);
                statusResults = request.executeQuery();
                while(statusResults.next()){
                    PreparedStatement deleteRequest = c.prepareStatement("DELETE FROM BOOKINGS WHERE NAME = (?) AND DATE = (?) AND FLIGHT = (?)");
                    deleteRequest.setString(1, statusResults.getString("NAME"));
                    deleteRequest.setString(2, statusResults.getString("DATE"));
                    deleteRequest.setString(3, flight);
                    deleteRequest.executeUpdate();
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}