package com.bwd.chatbot.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DBProcessor {

    static final String JDBC_URL = "jdbc:mariadb://10.10.13.201:3306/";
    static final String USERNAME = "root";
    static final String PASSWORD = "OKWFw0Oe8sKKXC";


    public String[] getCustomers(String query) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            List<String> lst = new ArrayList<String>(){};
            while (rs.next()) {
                lst.add(rs.getString("name"));
            }
            String[] customers = lst.toArray(new String[0]);
            conn.close();
            return customers;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String[] getCustomerIntent(String query) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            List<String> lst = new ArrayList<String>(){};
            while (rs.next()) {
                lst.add(rs.getString("input_text"));
            }
            String[] customerIntent = lst.toArray(new String[0]);
            conn.close();
            return customerIntent;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String[] getGreetings(String query) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            List<String> lst = new ArrayList<String>(){};
            while (rs.next()) {
                lst.add(rs.getString("input_text"));
            }
            String[] greetings = lst.toArray(new String[0]);
//            System.out.println(Arrays.toString(greetings));
            conn.close();
            return greetings;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
