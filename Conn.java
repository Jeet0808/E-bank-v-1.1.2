package ebank;

import java.sql.*;

public class Conn {
    public Connection c;
    public Statement s;

    public Conn() {
        try {
            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection to the database
            c = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/storemydata",
                    "root", "jeet2006"
            );

            // Create a statement object to execute SQL queries
            s = c.createStatement();

            System.out.println("Connection successful!");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver Class Not Found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    // Close the connection if necessary
    public void close() {
        try {
            if (s != null) s.close();
            if (c != null) c.close();
            System.out.println("Connection closed.");
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }

    // Method to prepare SQL statements
    public PreparedStatement prepareStatement(String query) {
        PreparedStatement pstmt = null;
        try {
            if (c != null) {
                pstmt = c.prepareStatement(query);
            } else {
                System.out.println("Connection is not established.");
            }
        } catch (SQLException e) {
            System.out.println("Error preparing statement: " + e.getMessage());
        }
        return pstmt; // Return the prepared statement or null if failed
    }
}

//// Method to retrieve data from the database
//public void retrieveData() {
//    String query = "SELECT * FROM profiles";
//
//    try (ResultSet rs = s.executeQuery(query)) {
//        while (rs.next()) {
//            // Assuming your table has the columns: id, name, email, nickname, links
//            int id = rs.getInt("id");
//            String name = rs.getString("name");
//            String email = rs.getString("email");
//            String nickname = rs.getString("nickname");
//            String links = rs.getString("links");
//
//            // Print the retrieved data
//            System.out.println("ID: " + id + ", Name: " + name + ", Email: " + email +
//                    ", Nickname: " + nickname + ", Links: " + links);
//        }
//    } catch (SQLException e) {
//        System.out.println("Error retrieving data: " + e.getMessage());
//    }
//}
