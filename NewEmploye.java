package ebank;

import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class NewEmploye {
    String name;
    String email;
    String password;
    String regNo;
    private int salary;
    private Account account;
    public int id; // Moved up for clarity
    Scanner sc = new Scanner(System.in);


    private ArrayList<SalaryTransaction> salaryTransactions = new ArrayList<>();
    private Timer salaryTimer;
    Conn conn;
    private ArrayList<Messagesemp> messagesemp;

    public NewEmploye(String name, String email, String password, String regNo, int salary, Account account) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.regNo = regNo;
        this.salary = salary;
        this.account = account;
        this.conn = new Conn();
        this.messagesemp = new ArrayList<>(); // Initialize empmessages in the constructor

        insertempintodb();
    }

    public void startSalaryIncrement() {
        salaryTimer = new Timer();
        salaryTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                increaseSalary();
            }
        }, 0, 10000);
    }

    private void increaseSalary() {
        salary += 100;
        account.credit(100);
        addSalaryTransaction(100);
    }

    public void stopSalaryIncrement() {
        if (salaryTimer != null) {
            salaryTimer.cancel();
        }
    }

    public void printEmpInfo(String bankname) {
        System.out.println("==================================");
        System.out.println("Your name is: " + name);
        System.out.println("Your email is: " + email);
        System.out.println("Your registered ID is: " + regNo);
        System.out.println("Working at " + bankname);
        System.out.println("==================================");
    }

    public void printSalaryDetails() {
        System.out.println("Your salary is: " + salary);
    }

    public void printSalaryTransactions() {
        System.out.println("Salary Transactions for Registration Number: " + regNo);
        for (SalaryTransaction transaction : salaryTransactions) {
            System.out.println("Date: " + transaction.dateTime + " -- Salary added: " + transaction.amount);
        }
    }

    public void addSalaryTransaction(int amount) {
        SalaryTransaction transaction = new SalaryTransaction(amount, LocalDateTime.now());
        salaryTransactions.add(transaction);
    }

    public String getName() {
        return name;
    }

    public String getemailid() {
        return email;
    }

    public int getSalary() {
        return salary;
    }

    public String getRegNo() {
        return regNo;
    }

    public void empchatbox(String mesage,String sendername ,String sendertype) {
        String messageinput  = mesage;
        String sender = sendername;
        Messagesemp addmessages = new Messagesemp(LocalDateTime.now(), messageinput,sender,sendertype);
        messagesemp.add(addmessages);


    }

//    public void chatboxresponse() {
//        System.out.println("Send message to manager :");
//        String mesage = sc.nextLine();
//
//
//    }

    public void printEmpmessages(String empname) {
        System.out.println("chating of Account Number: " + empname);
        for (Messagesemp messages1 : messagesemp) {
            System.out.println(messages1);
        }
    }
//    public String empchatboxresponse() {
//        System.out.println("Send message to Boss ->");
//        String message = sc.nextLine();
//        return message;
//    }



    public class SalaryTransaction {
        private int amount;
        private LocalDateTime dateTime;

        public SalaryTransaction(int amount, LocalDateTime dateTime) {
            this.amount = amount;
            this.dateTime = dateTime;
        }

        @Override
        public String toString() {
            return "Amount: " + amount + ", Date: " + dateTime;
        }
    }

    // Method to insert account details into the database
    public void insertempintodb() {
        try {
            // Ensure the connection is not null
            if (this.conn.c != null) {
                String sql = "INSERT INTO employe (name, email, password, regNo,salary) VALUES (?, ?, ?, ?,?)";
                PreparedStatement pstmt = this.conn.c.prepareStatement(sql);
                pstmt.setString(1, this.name);
                pstmt.setString(2, this.email);
                pstmt.setString(3, this.password);
                pstmt.setString(4, this.regNo);
                pstmt.setInt(5, this.salary);

                pstmt.executeUpdate();
                System.out.println("Employee inserted successfully!");
            } else {
                System.out.println("Connection is null!");
            }
        } catch (SQLException e) {
            // Check if the error is due to a duplicate entry
            if (e.getErrorCode() == 1062) {  // Error code for duplicate key
                System.out.println("Error: Duplicate entry. The email '" + this.email + "' is already registered.");
            } else {
                // Print any other SQL error
                System.out.println("SQL Exception: " + e.getMessage());
            }
            if (e.getMessage().contains("Table 'storemydata.employe' doesn't exist")) {
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
    public void updateemployeFromDB() {
        String query = "UPDATE profiles SET name = ?, email = ?, password = ?, regNo = ?,salary = ? WHERE id = ?";
        try (PreparedStatement pstmt = this.conn.c.prepareStatement(query)) {
            pstmt.setString(1, this.name);
            pstmt.setString(2, this.email);
            pstmt.setString(3, this.password);
            pstmt.setString(4, this.regNo);
            pstmt.setInt(5, this.salary);
            pstmt.setInt(6, this.id);
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
    public static class Messagesemp {
        private LocalDateTime timestamp;

        String message;
        String sendername;
        String sendertype;

        public Messagesemp(LocalDateTime timestamp, String message, String sendername , String sendertype) {
            this.timestamp = timestamp;
            this.message = message;
            this.sendername = sendername;
            this.sendertype = sendertype;

        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }



        public String getsendername() {
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
