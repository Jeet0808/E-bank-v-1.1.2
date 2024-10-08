package ebank;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Banks {
    String nameofbank;
    String Curancyofbank;
    int funds;
    String nameofowner;
    int specialbno;
    String manager;
    public int id; // Moved up for clarity


    Conn conn;

    public Banks(String nameofbank, String curancyofbank, int funds, String nameofowner, int specialbno ,String manager) {
        this.nameofbank = nameofbank;
        this.Curancyofbank = curancyofbank;
        this.funds = funds;
        this.nameofowner = nameofowner;
        this.specialbno = specialbno;
        this.conn = new Conn();  // Ensure the connection instance is initialized
        this.manager = manager;

        insertbanksToDB();
    }

    public void bankinfo() {
        // Classic header for bank information
        System.out.println("===============================================");
        System.out.println("                Bank Information                ");
        System.out.println("===============================================\n");

        // Displaying bank details with clear formatting
        System.out.println("Bank Name: "+ nameofbank);
        System.out.println("Currency of Bank:"+ Curancyofbank);
        System.out.println("Funds in Bank: "+ funds);
        System.out.println("Name of Bank Owner:"+ nameofowner);
        System.out.println("Name of Bank Manager: "+ manager);

        // Classic footer
        System.out.println("\n===============================================");
    }

    public void getfunds(){
        System.out.println(funds);

    }

    // Method to insert bank details into the database
    public void insertbanksToDB() {
        try {
            // Ensure the connection is not null
            if (this.conn.c != null) {
                String sql = "INSERT INTO banks (nameofbank, Curancyofbank, funds, nameofowner, specialbno) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pstmt = this.conn.c.prepareStatement(sql);

                // Correct data types for each placeholder
                pstmt.setString(1, this.nameofbank);        // Name of the bank (String)
                pstmt.setString(2, this.Curancyofbank);     // Currency of the bank (String)
                pstmt.setInt(3, this.funds);                // Funds (int)
                pstmt.setString(4, this.nameofowner);       // Name of the owner (String)
                pstmt.setInt(5, this.specialbno);           // Special number (int)

                pstmt.executeUpdate();
                System.out.println("Bank inserted successfully!");
            } else {
                System.out.println("Connection is null!");
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("Table 'storemydata.banks' doesn't exist")) {
                System.out.println("Table 'registors' does not exist. Proceeding without inserting phone number and OTP...");
                // Optionally, you can log this or handle it as needed
            } else {
                // Handle other SQL exceptions
                System.out.println("SQL Exception: " + e.getMessage());
                e.printStackTrace();
            }


        }
    }

    // Method to update data in the database
    public void updatebanksFromDB() {
        String query = "UPDATE bank SET name = ?, fund = ? WHERE id = ?";

        try (PreparedStatement pstmt = this.conn.c.prepareStatement(query)) {
            // Set the parameters for the prepared statement
            pstmt.setString(1, this.nameofbank);
            pstmt.setDouble(2, this.funds);
            pstmt.setInt(3, this.id);

            // Execute the update query
            pstmt.executeUpdate();
            System.out.println("Bank details updated successfully!");

        } catch (SQLException e) {
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



}
