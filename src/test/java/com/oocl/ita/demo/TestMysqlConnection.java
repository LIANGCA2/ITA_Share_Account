package com.oocl.ita.demo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;

public class TestMysqlConnection {

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
         conn = DriverManager.getConnection("jdbc:mysql://localhost/account?"
                    + "user=root&password=root");
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from user");

            while (rs.next()) {
                System.out.println("id:" + rs.getString("id"));
            }

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            ex.printStackTrace();
        } finally {
            try {
                if(rs != null) {
                    rs.close();
                    rs = null;
                }
                if(stmt != null) {
                    stmt.close();
                    stmt = null;
                }
                if(conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

}