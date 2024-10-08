package ebank;


import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import java.sql.PreparedStatement;


public class Manager {
    String name;
    String degree;
    String mno;
    Scanner sc = new Scanner(System.in);
    Conn conn;
    private int id;
    String bankname;

    ArrayList<Messagesman> messagesmen;

    public Manager(String name, String degree, String mno ,String bankname) {
        this.name = name;
        this.degree = degree;
        this.mno = mno;
        this.bankname = bankname;
        this.conn = new Conn();
        this.messagesmen = new ArrayList<>();

        insertmanagerintodb();

    }

    private void insertmanagerintodb() {
        try {
            if (this.conn.c != null) {
                // writing query

                String query = "insert into managers (name, degree ,mno) values (?,?,?)";

                PreparedStatement pstatment = this.conn.c.prepareStatement(query);
                pstatment.setString(1, this.name);
                pstatment.setString(2, this.degree);
                pstatment.setString(3, this.mno);
                pstatment.executeUpdate();
                System.out.println("manager details inserted successfully!");

            } else {
                System.out.println("Connection is null!");
            }
        } catch (SQLException e) {
            // Check if the error is due to a duplicate entry
            if (e.getErrorCode() == 1062) {  // Error code for duplicate key
                System.out.println("Error: Duplicate entry. The email '" + this.name + "' is already registered.");
            } else {
                // Print any other SQL error
                System.out.println("SQL Exception: " + e.getMessage());
            }
            if (e.getMessage().contains("Table 'storemydata.managers' doesn't exist")) {
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

    public void getmaninfo() {
        System.out.println("name of manager is : " + name);
        System.out.println("degre is : " + degree);
        System.out.println("registered no  is : " + mno);
        System.out.println("bankname no  is : " + bankname);


    }

    public void managerchatbox(String mesage, String sendername, String sendertype) {
        String messageinput = mesage;
        String sender = sendername;
        Messagesman addmessages = new Messagesman(LocalDateTime.now(), messageinput, sender, sendertype);
        messagesmen.add(addmessages);


    }

//    public void chatboxresponse() {
//        System.out.println("Send message to manager :");
//        String mesage = sc.nextLine();
//
//
//    }

    public void printManmessages(String managername) {
        System.out.println("chatting of Manager: " + managername);
        for (Messagesman messagesman1 : messagesmen) {
            System.out.println(messagesman1);
        }
    }


    // Method to update data in the database
    public void updatemanagerFromDB() {
        String query = "UPDATE profiles SET name = ?, degree = ?, mno = ?, WHERE id = ?";
        try (PreparedStatement pstmt = this.conn.c.prepareStatement(query)) {
            pstmt.setString(1, this.name);
            pstmt.setString(2, this.degree);
            pstmt.setString(3, this.mno);
            pstmt.setInt(4, this.id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Profile updated successfully!");
            } else {
                System.out.println("No profile found with the given ID. Update failed.");
            }
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



    public static class Messagesman {
        private LocalDateTime timestamp;

        String message;
        String sendername;
        String sendertype;

        public Messagesman(LocalDateTime timestamp, String message, String sendername,String sendertype) {
            this.timestamp = timestamp;
            this.message = message;
            this.sendername = sendername;
            this.sendertype = sendertype;

        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }



        public String getMessage() {
            return message;
        }
        public String getSendertype() {
            return sendertype;
        }

        @Override
        public String toString() {
            return sendername+"("+sendertype+")"+" -> "+message+ "              D/T-" + timestamp ;
        }
    }


}
