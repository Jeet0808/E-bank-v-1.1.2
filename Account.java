//package ebank;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//
//public class Account {
//    String name;
//    int age;
//    private int accNo;
//    private int balance;
//    private ArrayList<Transaction> transactions;
//
//    public Account(String name, int age, int accNo, int balance) {
//        this.name = name;
//        this.age = age;
//        this.accNo = accNo;
//        this.balance = balance;
//        this.transactions = new ArrayList<>();
//    }
//
//    public void printInfo() {
//        System.out.println("Account Holder: " + name);
//        System.out.println("Age: " + age);
//        System.out.println("Account Number: " + accNo);
//        System.out.println("Balance: " + balance);
//    }
//
//    public int getAccNo() {
//        return accNo;
//    }
//
//    public int getBalance() {
//        return balance;
//    }
//
//    public void credit(int amount) {
//        balance += amount;
//        Transaction creditTransaction = new Transaction(LocalDateTime.now(), "Credited", amount);
//        transactions.add(creditTransaction);
//
//    }
//
//    public void debit(int amount) {
//        if (amount <= balance) {
//            balance -= amount;
//            Transaction debitTransaction = new Transaction(LocalDateTime.now(), "Debited", amount);
//            transactions.add(debitTransaction);
//            System.out.println("Amount debited: " + amount);
//        } else {
//            System.out.println("Insufficient funds.");
//        }
//    }
//
//    public void transfer(Account targetAccount, int amount) {
//        if (amount <= balance) {
//            debit(amount);
//            targetAccount.credit(amount);
//            System.out.println("Transferred " + amount + " to Account No: " + targetAccount.getAccNo());
//        } else {
//            System.out.println("Insufficient funds.");
//        }
//    }
//
//    public void printTransactions() {
//        System.out.println("Transaction History for Account Number: " + accNo);
//        for (Transaction transaction : transactions) {
//            System.out.println(transaction);
//
//        }
//    }
//
//    public static class Transaction {
//        private LocalDateTime timestamp;
//        private String transactionType;
//        private int amount;
//
//        public Transaction(LocalDateTime timestamp, String transactionType, int amount) {
//            this.timestamp = timestamp;
//            this.transactionType = transactionType;
//            this.amount = amount;
//        }
//
//        @Override
//        public String toString() {
//            return timestamp + " - " + transactionType + ": " + amount;
//        }
//    }
//}

package ebank;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Account {
    String name;
    int age;
    private int accNo;
    private int balance;
    private ArrayList<Transaction> transactions;
    private ArrayList<Messages> accmessages;
    Banks banks;
    public int id;
    Scanner sc = new Scanner(System.in);
    // Create a connection object to interact with the database
    Conn conn;

    public Account(String name, int age, int accNo, int balance , Banks banks) {
        this.name = name;
        this.age = age;
        this.accNo = accNo;
        this.balance = balance;
        this.transactions = new ArrayList<>();
        this.accmessages = new ArrayList<>();
        this.banks = banks;

        // Initialize the connection object
        this.conn = new Conn();

        // Insert account details into the database when an account is created
        insertAccountToDB();
//        insertinfo();
    }

    public void userchatbox(String mesage,String sendername, String sendertype) {
        String messageinput  = mesage;
        String sender = sendername;
        Messages addmessages = new Messages(LocalDateTime.now(), messageinput,sender , sendertype);
        accmessages.add(addmessages);


    }

//    public void chatboxresponse() {
//        System.out.println("Send message to manager :");
//        String mesage = sc.nextLine();
//
//
//    }

    public void printmessages(String accname) {
        System.out.println("chating of Account Number: " + accname);
        for (Messages messages1 : accmessages) {
            System.out.println(messages1);
        }
    }

//    private void insertinfo() {
//        try {
//            // Ensure the connection is not null
//            if (this.conn.c != null) {
//                String sql = "INSERT INTO info (name, age, accNo, balance) VALUES (name, age, accNo, balance)";
//                PreparedStatement pstmt = this.conn.c.prepareStatement(sql);
//                pstmt.setString(1, this.name);
//                pstmt.setInt(2, this.age);
//                pstmt.setInt(3, this.accNo);
//                pstmt.setInt(4, this.balance);
//                pstmt.executeUpdate();
//                System.out.println("Account inserted successfully!");
//            } else {
//                System.out.println("Connection is null!");
//            }
//        } catch (SQLException e) {
//            System.out.println("SQL Exception: " + e.getMessage());
//            e.printStackTrace();
//        }
//
//    }

    // Method to insert account details into the database
    public void insertAccountToDB() {
        try {
            // Ensure the connection is not null
            if (this.conn.c != null) {
                String sql = "INSERT INTO accounts (name, age, accNo, balance) VALUES (?, ?, ?, ?)";
                PreparedStatement pstmt = this.conn.c.prepareStatement(sql);
                pstmt.setString(1, this.name);
                pstmt.setInt(2, this.age);
                pstmt.setInt(3, this.accNo);
                pstmt.setInt(4, this.balance);
                pstmt.executeUpdate();
                System.out.println("Account inserted successfully!");
            } else {
                System.out.println("Connection is null!");
            }
        }  catch (SQLException e) {
            // Check if the error is due to a duplicate entry
            if (e.getErrorCode() == 1062) {  // Error code for duplicate key
                System.out.println("Error: Duplicate entry. The email '" + this.age + "' is already registered.");
            } else {
                // Print any other SQL error
                System.out.println("SQL Exception: " + e.getMessage());
            }
            if (e.getMessage().contains("Table 'storemydata.accounts' doesn't exist")) {
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
    public void updateAccountFromDB() {
        String query = "UPDATE profiles SET name = ?, age = ?, accNo = ?, balance = ? WHERE id = ?";
        try (PreparedStatement pstmt = this.conn.c.prepareStatement(query)) {
            pstmt.setString(1, this.name);
            pstmt.setInt(2, this.age);
            pstmt.setInt(3, this.accNo);
            pstmt.setInt(4, this.balance);
            pstmt.setInt(5, this.id);
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

    public void transfer(Account targetAccount, int amount) {
        if (amount <= balance) {
            debit(amount);
            targetAccount.credit(amount);
            System.out.println("Transferred " + amount + " to Account No: " + targetAccount.getAccNo());
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    public void printInfo() {
        // Stylish header for account information
        System.out.println("===============================================");
        System.out.println("             Account Information               ");
        System.out.println("===============================================\n");

        // Displaying account details with stylish formatting
        System.out.println("👤 Account Holder            "+ name);
        System.out.println("🎂 Age:                      "+ age);
        System.out.println("🔢 Account Number:           "+ accNo);
        System.out.println("💰 Balance:                  "+ balance);

        // Stylish footer
        System.out.println("\n===============================================");
    }

    public int getAccNo() {
        return accNo;
    }

    public int getBalance() {
        return balance;
    }

    public void credit(int amount) {
        balance += amount;
        banks.funds +=amount;
        Transaction creditTransaction = new Transaction(LocalDateTime.now(), "Credited", amount);
        transactions.add(creditTransaction);

        // Insert transaction into the database
        insertTransactionToDB(creditTransaction);
    }

    public void debit(int amount) {
        if (amount <= balance) {
            balance -= amount;
            banks.funds -= amount;
            Transaction debitTransaction = new Transaction(LocalDateTime.now(), "Debited", amount);
            transactions.add(debitTransaction);


            // Insert transaction into the database
            insertTransactionToDB(debitTransaction);
            System.out.println("Amount debited: " + amount);
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    // Method to insert transaction details into the database
    private void insertTransactionToDB(Transaction transaction) {
        try {
            if (this.conn.c != null) {
                // SQL query to insert transaction details
                String query = "INSERT INTO transactions (accNo, transactionType, amount, timestamp) VALUES (?, ?, ?, ?)";
                PreparedStatement pstmt = conn.c.prepareStatement(query);
                pstmt.setInt(1, accNo);
                pstmt.setString(2, transaction.getTransactionType());
                pstmt.setInt(3, transaction.getAmount());
                pstmt.setTimestamp(4, java.sql.Timestamp.valueOf(transaction.getTimestamp()));

                // Execute the insert query
                pstmt.executeUpdate();

            }


        } catch (SQLException e) {
            // Check if the error is due to a duplicate entry
            if (e.getErrorCode() == 1062) {  // Error code for duplicate key
                System.out.println("Error: Duplicate entry. The email '" + this.age + "' is already registered.");
            } else {
                // Print any other SQL error
                System.out.println("SQL Exception: " + e.getMessage());
            }
            if (e.getMessage().contains("Table 'storemydata.transactions' doesn't exist")) {
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

    public void printTransactions() {
        System.out.println("Transaction History for Account Number: " + accNo);
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }



    public static class Transaction {
        private LocalDateTime timestamp;
        private String transactionType;
        private int amount;

        public Transaction(LocalDateTime timestamp, String transactionType, int amount) {
            this.timestamp = timestamp;
            this.transactionType = transactionType;
            this.amount = amount;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public String getTransactionType() {
            return transactionType;
        }

        public int getAmount() {
            return amount;
        }

        @Override
        public String toString() {
            return timestamp + " - " + transactionType + ": " + amount;
        }
    }
    public static class Messages {
        private LocalDateTime timestamp;

        String message;
        String sendername;
        String sendertype;


        public Messages(LocalDateTime timestamp, String message, String sendername,String sendertype) {
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
