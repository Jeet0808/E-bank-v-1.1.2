package ebank;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Profile {

    String name;
    String Email;
    String nickname;
    String links;
    Conn conn;
    public int id; // Moved up for clarity
    long registerdmobileno;

    // Constructor for creating new profiles
    public Profile(String name, String email, String nickname, String links, long registerdmobileno) {
        this.name = name;
        this.Email = email;
        this.nickname = nickname;
        this.links = links;
        this.conn = new Conn();
        this.registerdmobileno = registerdmobileno;

        // Insert account details into the database when a profile is created
        insertProfileToDB();
    }

//    // Constructor for loading existing profiles
//    public Profile(int id, String name, String email, String nickname, String links) {
//        this.id = id; // Assign the provided ID
//        this.name = name;
//        this.Email = email;
//        this.nickname = nickname;
//        this.links = links;
//        this.conn = new Conn(); // Use existing connection
//    }

    public void printpro() {
        System.out.println("===============================================");
        System.out.println("              Profile Information               ");
        System.out.println("===============================================\n");
        System.out.println("🌟 Name:             "+ name);
        System.out.println("📧 Email:            "+ Email);
        System.out.println("🧑 Nickname:         "+ nickname);
        System.out.println("🔗 Links:            "+ links);
        System.out.println("🔗 mobileno:          "+ registerdmobileno);
        System.out.println("\n===============================================");
    }

    // Method to insert profile details into the database
    public void insertProfileToDB() {
        try {
            // Ensure the connection is not null
            if (this.conn.c != null) {
                String sql = "INSERT INTO profiles (name, Email, nickname, links) VALUES (?, ?, ?, ?)";
                PreparedStatement pstmt = this.conn.c.prepareStatement(sql);

                // Set parameters
                pstmt.setString(1, this.name);
                pstmt.setString(2, this.Email);
                pstmt.setString(3, this.nickname);
                pstmt.setString(4, this.links);

                pstmt.executeUpdate();
                System.out.println("Profile inserted successfully!");

            } else {
                System.out.println("Connection is null!");
                conn.close();
            }
        } catch (SQLException e) {
            // Check if the error is due to a duplicate entry
            if (e.getErrorCode() == 1062) {  // Error code for duplicate key
                System.out.println("Error: Duplicate entry. The email '" + this.Email + "' is already registered.");
            } else {
                // Print any other SQL error
                System.out.println("SQL Exception: " + e.getMessage());
            }
            if (e.getMessage().contains("Table 'storemydata.profiles' doesn't exist")) {
                System.out.println("Table 'profiles' does not exist. Proceeding without inserting phone number and OTP...");
                // Optionally, you can log this or handle it as needed
            } else {
                // Handle other SQL exceptions
                System.out.println("SQL Exception: " + e.getMessage());
                e.printStackTrace();
            }
            // Continue program execution without interruption
        }
    }


    // Method to update data in the database
    public void updatprofileFromDB() {
        String query = "UPDATE profiles SET name = ?, Email = ?, nickname = ?, links = ? WHERE id = ?";
        try (PreparedStatement pstmt = this.conn.c.prepareStatement(query)) {
            pstmt.setString(1, this.name);
            pstmt.setString(2, this.Email);
            pstmt.setString(3, this.nickname);
            pstmt.setString(4, this.links);
            pstmt.setInt(5, this.id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Profile updated successfully!");
            } else {
                System.out.println("No profile found with the given ID. Update failed.");
            }
        }catch (SQLException e) {
            // Custom message for SQL error
            System.out.println("An error occurred while updating the bank details. Proceeding without interruption.");
            // Optionally log the error without printing the stack trace
            logSQLError(e); // You can create a logSQLError method to log errors for your own debugging
        } catch (Exception e) {
            // Handle any other unexpected exceptions
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    private void logSQLError(SQLException e) {
        // This method could write to a file or simply print to a hidden console
        System.out.println("Error logged: " + e.getMessage());
        // Optionally: write this to a file if needed
    }

//    // Optional: Method to fetch a profile by ID from the database
//    public static Profile fetchProfileById(Conn conn, int id) {
//        String query = "SELECT * FROM profiles WHERE id = ?";
//        try (PreparedStatement pstmt = conn.c.prepareStatement(query)) {
//            pstmt.setInt(1, id);
//            ResultSet rs = pstmt.executeQuery();
//            if (rs.next()) {
//                return new Profile(
//                        rs.getInt("id"),
//                        rs.getString("name"),
//                        rs.getString("Email"),
//                        rs.getString("nickname"),
//                        rs.getString("links")
//                );
//            } else {
//                System.out.println("No profile found with the given ID.");
//                return null;
//            }
//        } catch (SQLException e) {
//            System.out.println("SQL Exception: " + e.getMessage());
//            return null;
//        }
//    }

}
