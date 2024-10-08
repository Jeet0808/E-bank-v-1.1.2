package ebank;



import java.time.LocalDateTime;
import java.util.*;
import java.util.HashMap;

public class Bank {
    private ArrayList<Account> userAccounts = new ArrayList<>();
    private ArrayList<NewEmploye> employees = new ArrayList<>();
    private ArrayList<Manager> managers = new ArrayList<>();
    private ArrayList<Register> registers = new ArrayList<>();
    private ArrayList<Profile> profiles = new ArrayList<>();
    private ArrayList<Banks> banks = new ArrayList<>();
    private ArrayList<Bankowner> owners = new ArrayList<>();


    private Scanner sc = new Scanner(System.in); // Single Scanner instance
    int specialbankno = 0;

        private Conn dbConnection; // Store your connection instance

        public Bank() {
            dbConnection = new Conn(); // Initialize the connection
        }

        // Your existing methods...



    public String Createnewbank() {
        // creating newbank
        System.out.println("Enter name of bank : ");
        String nm = sc.nextLine();
        System.out.println("Currancy of bank : ");
        String cur = sc.nextLine();


        // now we have to add a manue which shows all the bank no wise
        // and when user enter no thet that no of bank is going to open

        System.out.println("you seted the bank name and currancy now add bankowner detail -> ");

        // adding bank owner deatils
        System.out.println("'Add Bankowner details'->");
        System.out.println(" ");
        System.out.println("I. Enter bank owner name :");
        String nam = sc.nextLine();
        System.out.println("II. Enter age of bank owner :");
        int Age = errorhandle();
        System.out.println("III. Enter Phoneno of bank owner to varify :");
        long phoneno = errorhandle2();
        System.out.println("Iv. Add fund to the bank :");
        int fund = errorhandle();
        specialbankno++;
        String manager = null;

        Bankowner owner = new Bankowner(nam, Age, phoneno, fund,specialbankno);
        Banks banks1 = new Banks(nm, cur, fund, nam ,specialbankno,manager);


        banks.add(banks1);
        owners.add(owner);

        System.out.println("You all about seted just one more thing to add : ");
        System.out.println("That is manager of bank you can add him by main manue ");

        System.out.println("BUT FOR NOW TAKE A LOOK AT YOUR BANK DEAR " + nam);
        // displaying bank;
        bankststus(banks1.nameofbank ,owner.name);
        return nm;
    }

//    public void deletebank() {
//        System.out.println("enter bank name :");
//        String bname = sc.nextLine();
//        for (Banks banks1 : banks) {
//            if (Objects.equals(bname, banks1.nameofbank)) {
//                banks.remove(banks1);
//            } else {
//                System.out.println("bank not found!!");
//            }
//        }
//    }
//
//


    private void deletebank() {
        System.out.println("enter bank name :");
        String bname = sc.nextLine();
        boolean bankFound = false;
        for (int i = 0; i <banks.size(); i++) {  // Iterate using index
           Banks bank = banks.get(i);
            if (Objects.equals(bname,bank.nameofbank)) {
               banks.remove(i);  // Safe removal by index in ArrayList
                bankFound = true;
                bank.updatebanksFromDB();
                System.out.println("Bank removed successfully.");
                break;  // Exit loop after removal

            }
        }

        if (!bankFound) {
            System.out.println("Manager ID not found.");
        }
    }



    int sno = 0;

    private void openexistingbank() {
        if (banks.isEmpty()) {
            System.out.println("No bank available.");
        } else {
            System.out.println("To open a bank, select the bank by Serial No.");

            // Create a HashMap to store serial numbers and corresponding banks
            HashMap<Integer, Banks> map = new HashMap<>();

            // Iterate through the list of banks and add them to the HashMap
            for (int i = 0; i < banks.size(); i++) {
                Banks bank = banks.get(i);
                int m = i + 1; // Serial number (starting from 1)
                map.put(m, bank); // Add to HashMap

                // Display the serial number and bank name
                System.out.println(m + ". Bank: " + bank.nameofbank);
            }

            // Handle user input for selecting a bank by serial number
            sno = errorhandle(); // Assume this method returns a valid integer input

            // Check if the serial number exists in the HashMap
            if (map.containsKey(sno)) {
                Banks selectedBank = map.get(sno); // Get the bank associated with the serial number

                // Search for the bank owner
                for (Bankowner bon : owners) {
                    if (Objects.equals(selectedBank.specialbno, bon.specialno)) {
                        // Call bankststus method with bank name and owner name
                        bankststus(selectedBank.nameofbank, bon.name);
                    }
                }
            } else {
                System.out.println("Bank not found!");
            }
        }
    }

//    private void toprintnmaeofoner() {
//
//
//
//
//
//        for (Bankowner owner : owners) {
//            if (Objects.equals(sno, owner.specialno)) {
//
//                bankststus(owner.name);
//
//            }else {
//                System.out.println("bank not found!");
//            }
//        }
//    }
//

    public String crateManger(String nameofbank) {
        String managername = "";

        for (Banks bank1 : banks) {
            // Find the bank by name
            if (Objects.equals(nameofbank, bank1.nameofbank)) {
                // Add the manager and get the manager's name
                 managername = addmanager(nameofbank);

                // Set the manager's name to the current bank
                bank1.manager = managername;
                System.out.println("Manager assigned to the bank: " + bank1.nameofbank);
            }
        }
        return managername;
    }


    private String addmanager(String nameofbank) {
        System.out.println("Enter manager Name:");

        String name = sc.nextLine();
        System.out.println("Enter manager Degree ");
        String degree = sc.nextLine();
        System.out.println("Enter manager Salary ");
        int Salary = errorhandle();


        String manno = "MAN" + (int)Math.round(Math.random()*1000);
        Manager man = new Manager(name, degree, manno,nameofbank);
        managers.add(man);
        System.out.println("Manager Added ");
        System.out.println("manager id -> " + manno);
        return name;
    }


//    private void Removeman() {
//        System.out.println("enter Id of manager :");
//        String ID = sc.nextLine();
//        for (Manager man : managers) {
//            if (Objects.equals(ID, man.mno)) {
//                // NOTE ---> WE CAN'T USE DIRECT remove METHODE TO THE ARRALIST WHILE ITERATING OVER IT
//                //           USE Itarator to itarate over the Arraylist and remove an elsement
//
//              //  managers.remove(man);
//            } else {
//                System.out.println("manager id is not found");
//            }
//
//        }
//    }


    // removing by using Arrayist ----->

    private void Removeman(String nameofbank) {
        System.out.println("Enter ID of manager:");
        String ID = sc.nextLine();

        boolean managerFound = false;
        for (int i = 0; i < managers.size(); i++) {  // Iterate using index
            Manager man = managers.get(i);
            if (Objects.equals(ID, man.mno)) {
                managers.remove(i);  // Safe removal by index in ArrayList
                man.updatemanagerFromDB();
                for (Banks bank1 : banks) {

                    if (Objects.equals(nameofbank, bank1.nameofbank)) {

                        bank1.manager = null;

                    }
                }
                managerFound = true;
                System.out.println("Manager removed successfully.");
                break;  // Exit loop after removal
            }
        }

        if (!managerFound) {
            System.out.println("Manager ID not found.");
        }
    }

    // removing by useing Itarator ---->

//    private void Removeman() {
//        System.out.println("Enter ID of manager:");
//        String ID = sc.nextLine();
//
//        Iterator<Manager> iterator = managers.iterator();
//        boolean managerFound = false;
//
//        while (iterator.hasNext()) {
//            Manager man = iterator.next();
//            if (Objects.equals(ID, man.mno)) {
//                iterator.remove();  // Use Iterator's remove method
//                managerFound = true;
//                System.out.println("Manager removed successfully.");
//                break;  // Break after removing the manager to avoid further iteration
//            }
//        }
//
//        if (!managerFound) {
//            System.out.println("Manager ID not found.");
//        }
//    }


    public Manager managerinfo(String manno) {
        for (Manager man : managers) {
            if (man.mno.equals(manno)) {
                System.out.println("man info ->");
                man.getmaninfo();
            }
            else{
                System.out.println("registered no is not match :");
            }
        }
        return null;
    }
    public void getbankinfoandsettings(String ownername , String managername){
        for (Manager man : managers) {
            if(Objects.equals(managername,man.name)) {
              getbankinfoandsettings(ownername,managername);

            }
            else{
                System.out.println("manager  name  not found  :");
            }
        }



    }



    String nameofuser = "";
    // Method to create a new user account
    public String createUserAccount(String nameofbank) {

        for (Banks bank1 : banks){
            if (Objects.equals(nameofbank,bank1.nameofbank)){
                 nameofuser = createaccount(nameofbank);
            }
        }

        return nameofuser;
    }

    private String createaccount(String nameofbank) {
        System.out.println("Enter your name:");
        String name = sc.nextLine();
        System.out.println("Enter your age:");
        int age = errorhandle();
        System.out.println("Enter initial balance:");
        int balance = errorhandle();
        int accno = (int) (Math.random() * 1000000);

        // Get the Banks object
        Banks bank = fundsinbank2(nameofbank);

        // Check if the bank was found
        if (bank != null) {
            // Create the account with the bank's funds
            Account newAccount = new Account(name, age, accno, balance, bank);
            userAccounts.add(newAccount);

            // Print account details with boundaries around it
            System.out.println("==========================================");
            System.out.println("|         Account Creation Details       |");
            System.out.println("==========================================");
            System.out.println("| Name:          " + newAccount.name);
            System.out.println("| Age:           " + newAccount.age);
            System.out.println("| Account No:    " + newAccount.getAccNo());
            System.out.println("| Balance:       " + newAccount.getBalance());
            System.out.println("| Bank:          " + nameofbank);
            System.out.println("==========================================");
            System.out.println("Account created successfully!");
        } else {
            System.out.println("Could not create account. Bank not found.");
        }
        return name;
    }



    public Banks fundsinbank2(String nameofbank) {
        int fund = 0;
        for (Banks in : banks){
            if(Objects.equals(nameofbank , in.nameofbank)){

                return in;
            }
            else{
                System.out.println("name of bank not matches");
            }
        }

        return null;
    }

        String nameofemp = "";

    // Method to create a new employee
    public String createEmployee(String nameofbank) {
        System.out.println("Enter your name:");
        String name = sc.nextLine();
        System.out.println("Enter your email:");
        String email = sc.nextLine();
        System.out.println("Enter your password:");
        String password = sc.nextLine();


        int salary = controlsalary();
        System.out.println("Enter associated Account No:");
        int accNo = errorhandle();
        Account account = findAccountByNumber(accNo);
        if (account != null) {
            String regNo = nameofbank + (int) (Math.random()*1000);
            NewEmploye employee = new NewEmploye(name, email, password, regNo, salary, account);
            nameofemp = name;
            employees.add(employee);
            System.out.println("Employee registered successfully. Your Registration ID: " + regNo);
        } else {
            System.out.println("Account not found.");
        }

        return nameofemp;
    }


    // Method to handle input mismatch errors
    public int errorhandle() {
        int input = 0;
        while (true) {
            try {
                input = sc.nextInt();
                sc.nextLine(); // Consume newline
                break; // Exit loop once valid input is provided
            } catch (InputMismatchException exception) {
                System.out.println("Please enter a valid integer number:");
                sc.nextLine(); // Consume the invalid input
            }
        }
        return input; // Return the valid integer value
    }

    public int errorhandle2() {
        long input = 0;
        while (true) {
            try {
                input = sc.nextLong();
                sc.nextLine(); // Consume newline
                break; // Exit loop once valid input is provided
            } catch (InputMismatchException exception) {
                System.out.println("Please enter a valid long type no number:");
                sc.nextLine(); // Consume the invalid input
            }
        }
        return (int) input; // Return the valid integer value
    }


    // Find an account by account number
    public Account findAccountByNumber(int accNo) {
        for (Account acc : userAccounts) {
            if (acc.getAccNo() == accNo) {
                return acc;
            } else {
                System.out.println("acc no is wrong");
            }
        }
        return null;
    }

    public int calculatetotalmoney() {
        int totalmoney = 0;
        for (Account acc : userAccounts) {

            totalmoney += acc.getBalance();


        }
        return totalmoney;

    }

    // Find an employee by registration number
    public NewEmploye findEmployeeByRegNo(String regNo) {
        for (NewEmploye emp : employees) {
            if (emp.regNo.equals(regNo)) {
                return emp;
            }
        }
        return null;
    }

    public NewEmploye findEmployeeByRegNo2(String regNo) {
        for (NewEmploye emp : employees) {
            if (emp.regNo.equals(regNo)) {
                System.out.println("emp info ->");
                String bankname = "E-bank";
                emp.printEmpInfo(bankname);
            }
        }
        return null;
    }

    // Transfer money from one account to another
    public void transferMoney(int fromAccNo, int toAccNo, int amount) {
        Account fromAccount = findAccountByNumber(fromAccNo);
        Account toAccount = findAccountByNumber(toAccNo);
        if (fromAccount != null && toAccount != null) {
            fromAccount.transfer(toAccount, amount);
        } else {
            System.out.println("One or both account numbers are invalid.");
        }
    }


    // display manager manue

    public void displaymanager(String managername) {
        int op = 0;
        boolean exit = true;
        while (exit) {
            System.out.println("\n===============================");
            System.out.println("🏦✨ Welcome to the Manager Section ✨");
            System.out.println("=================================");
            System.out.println("💼 Please select an option:");
            System.out.println("---------------------------------");
            System.out.println("1️⃣  📊 View Bank Fund Status");
            System.out.println("2️⃣  💰 Check Total Loans Given by Bank");
            System.out.println("3️⃣  🧑‍💼 View Employee Details");
            System.out.println("4️⃣  👤 View User Account Details");
            System.out.println("5️⃣  🗑️ Remove an Employee");
            System.out.println("6️⃣  🗑️ Remove a User Account");
            System.out.println("7️⃣  🏢 View Investor Details");
            System.out.println("8️⃣  🚪 Exit Manager Section");
            System.out.println("=================================");
            System.out.print("🔑 Enter your choice (1-8): ");


            op = errorhandle();

            switch (op) {
                case 1:

                    managerMenu();
                    break;
                case 2:
                    System.out.println("Total loan given by bank is  "+loans);
                    break;
                case 3:
                        checkemploye(managername);
                    break;
                case 4:

                    checkuseraccount(managername);

                    break;
                case 5:
                    removeemp();

                    break;
                case 6:

                   deleteuseracc();

                    break;
                case 7:

                    investerMenu();

                    break;
                case 8:
                    System.out.println("Returning to the bank control...");
                    exit = false;
                    break;
                default:
                    System.out.println("Invalid option.");


            }

        }
    }
//#1.
    private void checkemploye(String managername) {
        int op;
        boolean exit = true;
        String nameofbank = "Notsetyet";
        while (exit) {
            // Welcome message
            System.out.println("\n===============================================");
            System.out.println("Good Morning, manager " + managername + " sir !");
            System.out.println("This is employee control section -->");
            System.out.println("===============================================");


            System.out.println("1. View all employee ");
            System.out.println("2. Control salary of employee");
            System.out.println("3. send message to an employee");
            System.out.println("4. View employee responses");
            System.out.println("5. Track employee performance");
            System.out.println("6. Add an employee");
            System.out.println("7. Return to manager mani main menue");
            System.out.println("===============================================");

            // Get user input for option selection
            op = errorhandle();
            switch (op) {
                case 1:
                   viewemployee(nameofbank);
                    break;
                case 2:
                   controlsalary();
                    break;
                case 3:
                    sendmasagetoempl(managername);
                    break;
                case 4:
                    seeresponses();
                    break;
                case 5:
                    empperformance();
                    break;
                case 6:
                    createEmployee(managername);
                    break;
                case 7:
                    System.out.println("Returning to main menu...");
                    exit = false;
                    break;
                default:
                    System.out.println("Invalid option.");


            }
        }
    }
//1.
    private void empperformance() {
        System.out.println("coming soon.....");

    }
//2.
//    private void seeresponses() {
//        System.out.println("Message from employees");
//        for (NewEmploye emp : employees){
//            String empname = emp.name;
//            emp.printEmpmessages(empname);
//
//        }
//
//
//
//    }
        private void seeresponses() {
    System.out.println("Messages from users");
    for (Manager manager : managers){
        String managername = manager.name;
        manager.printManmessages(managername);

    }
}
//3.
    private void sendmasagetoempl(String managernmae) {

        int op;
        boolean exit = true;
        String nameofbank = "Notsetyet";
        while (exit) {


            System.out.println("1. Send mesage to all employee ");
            System.out.println("2. Send mesage to individual employee");

            System.out.println("3. Return to manager mani main menue");
            System.out.println("===============================================");


            op = errorhandle();
            switch (op) {
                case 1:
                    sendall(managernmae);
                    break;
                case 2:
                    sendindividual(managernmae);
                    break;

                case 3:
                    System.out.println("Returning to main menu...");
                    exit = false;
                    break;
                default:
                    System.out.println("Invalid option.");


            }
        }

    }
//4.
    private void sendindividual(String managername) {
        System.out.println("Enter emp registration id to send massage");
        String regno = sc.nextLine();
        for (NewEmploye emp : employees){
            if(Objects.equals(regno,emp.regNo)) {
                System.out.println("Type your message : ");
                String message = sc.nextLine();
                String sender = managername;
                String sendertype = "Manager";
                emp.empchatbox(message ,sender,sendertype);
            }
        }

    }
//5.
    private void sendall(String managername) {
        System.out.println("Type your massage");
        String message = sc.nextLine();
        String sender = managername;
        String sendertype = "Manager";
        for (NewEmploye emp : employees){

            emp.empchatbox(message,sender,sendertype);
        }

    }

//6.
    private int controlsalary(){
        System.out.println("enter new salary of employees");
        int newsalary = errorhandle();
        System.out.println("salary setted to : "+newsalary);
        return newsalary;


    }
//7.
    private void viewemployee(String namoeofbank) {
        if(employees.isEmpty()){
            System.out.println("No employees in bank ");
        }
        else{

            for (int i = 0; i <employees.size(); i++) {  // Iterate using index
                NewEmploye emp = employees.get(i);
                int m = i+1;
                System.out.println(m + "." + "username : " + emp.name);
                emp.printEmpInfo(namoeofbank);

            }
        }
    }





//#2.
    private void checkuseraccount(String managername) {
        int op;
        boolean exit = true;

        while (exit) {
            // Welcome message
            System.out.println("\n===============================================");
            System.out.println("Good Morning, manager " + managername + " sir !");
            System.out.println("This is users account control section -->");
            System.out.println("===============================================");


            System.out.println("1. View all user account ");
            System.out.println("2. send message to users ");
            System.out.println("note ->> the next plan  is to handle user account from employee and employee from manager and manager from bank owner ");
            System.out.println("3. View user account responses");
            System.out.println("4. check loan details of user account");

            System.out.println("5. Return to manager mani main menue");
            System.out.println("===============================================");

            // Get user input for option selection
            op = errorhandle();
            switch (op) {
                case 1:
                    viewallaccounts();
                    break;
                case 2:
                   sendmessagetoacc(managername);
                    break;
                case 3:
                    viewuserresponses();
                    break;
                case 4:
                    checkloandetails();
                    break;


                case 5:
                    System.out.println("Returning to main menu...");
                    exit = false;
                    break;
                default:
                    System.out.println("Invalid option.");


            }
        }

    }
//1.
    private void checkloandetails() {
        System.out.println("coming soon...");
    }

    private void viewuserresponses() {
        System.out.println("Messages from users");
        for (Manager manager : managers){
            String managername = manager.name;
            manager.printManmessages(managername);

        }
    }
//2.
    private void sendmessagetoacc(String managername) {

        int op;
        boolean exit = true;
        String nameofbank = "Notsetyet";
        while (exit) {


            System.out.println("1. Send mesage to all users ");
            System.out.println("2. Send mesage to individual user");

            System.out.println("3. Return to manager mani main menue");
            System.out.println("===============================================");


            op = errorhandle();
            switch (op) {
                case 1:
                    sendallacc(managername);
                    break;
                case 2:
                    sendindividualacc(managername);
                    break;

                case 3:
                    System.out.println("Returning to main menu...");
                    exit = false;
                    break;
                default:
                    System.out.println("Invalid option.");


            }
        }

    }
    //3.
    private void sendindividualacc(String managernae) {
        System.out.println("Enter user accno to send massage");
        int accno = errorhandle();
        for (Account acc : userAccounts){
            if(Objects.equals(accno,acc.getAccNo())) {
                System.out.println("Type your message : ");
                String message = sc.nextLine();
                String sender = managernae;
                String sendertype = "Manager";
                acc.userchatbox(message,sender,sendertype);
            }
        }

    }
    //4.
    private void sendallacc(String managername) {
        System.out.println("Type your massage");
        String message = sc.nextLine();
        String sender = managername;
        String sendertype = "Manager";
        for (Account acc : userAccounts){

            acc.userchatbox(message ,sender,sendertype);
        }

    }

//5.
    private void viewallaccounts() {

        if(userAccounts.isEmpty()){
            System.out.println("No user accounts in bank ");
        }
        else{

            for (int i = 0; i <userAccounts.size(); i++) {  // Iterate using index
                Account acc = userAccounts.get(i);
                int m = i+1;
                System.out.println(m + "." + "username : " + acc.name);
                acc.printInfo();

            }
        }

    }
//x.
    public void deleteuseracc(){

        System.out.println("enter accno of user to delete acc");
        int accno = sc.nextInt();
        calldeletion(accno);

    }
//x..
    private void calldeletion(int accno) {
        for (Account acc : userAccounts){
            if (Objects.equals(accno,acc.getAccNo())){
                userAccounts.remove(acc);
            }else{
                System.out.println("account no not found");
            }
        }
    }


    private void removeemp() {
        System.out.println("ENTER EMP REGINO TO remove emp:");
        String reginno  = sc.nextLine();

            boolean bankFound = false;
            for (int i = 0; i <employees.size(); i++) {  // Iterate using index
                NewEmploye employe = employees.get(i);
                if (Objects.equals(reginno,employe.regNo)) {
                    employees.remove(i);  // Safe removal by index in ArrayList
                    employe.updateemployeFromDB();
                    bankFound = true;
                    System.out.println("Employe removed successfully.");
                    break;  // Exit loop after removal
                }
            }

            if (!bankFound) {
                System.out.println("Manager ID not found.");
            }
    }



// IDEA IDEA IDEA EK KAM KARO JO CHIG TTUN USER SE BARR BARR LE RAHE HO TAKI TUM CHECK KAR PAO KI ARRLIST ME VO HE KI NI
    // USKE BADLE EK BAR MANUE KE STARTING ME HI LE LO AND GLOBALLY STORE KARVA DO US FN OR METHODE ME ISSE
    // ISSE BARR BAR USER SE PUCHNA NI PADHE GAA KI TUMHARA NAME DALE VFERA VAGERA
private void displayBankManagerOptions(String ownerName, String managerName) {
    int op;
    boolean exit = true;

    while (exit) {
        // Welcome message
        System.out.println("\n===============================================");
        System.out.println("Good Morning, " + ownerName + "!");
        System.out.println("This is " + managerName + ", your bank manager.");
        System.out.println("===============================================");

        // Display options for the bank manager
        System.out.println("Please select an option to view information or set bank settings:");
        System.out.println("1. Check Current Bank Funds");
        System.out.println("2. View Total Employees");
        System.out.println("3. View Account Details");
        System.out.println("4. View Loan Details");
        System.out.println("5. Track Bank Profits and Losses");
        System.out.println("6. Set Bank Manager");
        System.out.println("7. Set Bank Employees");
        System.out.println("8. Set Bank Loan Interest Rate");
        System.out.println("9. View Investor Details");
        System.out.println("10. Return to Control Section");
        System.out.println("===============================================");

        // Get user input for option selection
        op = errorhandle();
        switch (op) {
            case 1:
                fundsinbank(ownerName);
                break;
            case 2:
                employesinfo(managerName);
                break;

            case 10:
                System.out.println("Returning to main menu...");
                exit = false;
                break;
            default:
                System.out.println("Invalid option.");


        }
    }
}


    private void employesinfo(String managername) {
        int totalemp = employees.size();
        System.out.println("Total employees in bank: " + totalemp);

        // Define the width for the boundary
        int boundaryWidth = 50;

        for (NewEmploye emp : employees) {
            // Print top boundary
            printBoundary(boundaryWidth);

            // Print employee information within boundaries
            System.out.println("| Employee Name:     " + emp.getName());
            System.out.println("| Employee ID:       " + emp.getemailid());
            System.out.println("| Employee Salary:   " + emp.getSalary());
            System.out.println("| Employee RegNo:    " + emp.getRegNo());

            // Print bottom boundary
            printBoundary(boundaryWidth);

            System.out.println(); // Add space between employees
        }
    }

    // Helper method to print a boundary of a given width
    private void printBoundary(int width) {
        for (int i = 0; i < width; i++) {
            System.out.print("*");
        }
        System.out.println();
    }


    private void fundsinbank(String ownername) {
        for (Banks bank : banks) {
            if (Objects.equals(ownername, bank.nameofowner)) {

                System.out.println(" funds in bank is : " + bank.funds);

            }
        }

    }

    public void bankststus(String nameofbank, String ownername) {
        int op = 0;
        String managername = "";
        boolean exit = true;

        while (exit) {
            // Stylish Bank Control Section Header
            System.out.println("╔══════════════════════════════════════════════════════════╗");
            System.out.println("║         ✨ Welcome to " + nameofbank + " Bank ✨         ║");
            System.out.println("╚══════════════════════════════════════════════════════════╝");

            // Menu Options
            System.out.println("      ╔════════════════════════════════════════════╗");
            System.out.println("      ║                 Bank Control               ║");
            System.out.println("      ║════════════════════════════════════════════║");
            System.out.println("      ║ 1. 🏦 Check Bank Details                   ║");
            System.out.println("      ║ 2. 👩‍💼 Add Manager                          ║");
            System.out.println("      ║ 3. 🚫 Remove Manager                       ║");
            System.out.println("      ║ 4. ⚙  Set Bank Settings                     ║");
            System.out.println("      ║ 5. 💰 Add Funds in Bank                    ║");
            System.out.println("      ║ 6. 👨‍💼 Manager Info                         ║");
            System.out.println("      ║════════════════════════════════════════════║");
            System.out.println("      ║ ⚠️ Note: Accounts and employee info        ║");
            System.out.println("      ║          are under manager control.        ║");
            System.out.println("      ║════════════════════════════════════════════║");
            System.out.println("      ║ 7. 🏦 Take a Look at Your Bank Mr. " + ownername);
            System.out.println("      ║ 8. 🚪 Close this Bank                      ║");
            System.out.println("      ║════════════════════════════════════════════║");
            System.out.println("      ║ 💡 Note: To delete this bank, go to the    ║");
            System.out.println("      ║    main menu and select 'Delete Bank'.     ║");
            System.out.println("      ╚════════════════════════════════════════════╝");

            // Get user's choice
            op = errorhandle();

            // Handle menu options
            switch (op) {
                case 1:
                    for (Banks b : banks) {
                        if (Objects.equals(b.nameofbank, nameofbank)) {
                            b.bankinfo();  // Show bank details
                        }
                    }
                    break;
                case 2:
                    managername = crateManger(nameofbank);  // Add manager
                    break;
                case 3:
                    Removeman(nameofbank);  // Remove manager
                    break;
                case 4:
                    displayBankManagerOptions(ownername, managername);  // Bank settings
                    break;
                case 5:
                    addfund(nameofbank);  // Add funds
                    break;
                case 6:
                    System.out.println("Enter manager ID to see manager details:");
                    String manno = sc.nextLine();
                    managerinfo(manno);  // Manager info
                    break;
                case 7:
                    displayMainMenu(nameofbank, ownername,managername);  // Display main menu
                    break;
                case 8:
                    System.out.println("Returning to main menu...");
                    exit = false;
                    break;
                default:
                    System.out.println("❌ Invalid option, please try again.");
            }
        }
    }



    //'fund adding fn'\\
    public void addfund(String nofbank) {

        for (Banks bank : banks) {
            if (Objects.equals(nofbank, bank.nameofbank)) {
                System.out.println("Enter ammount to add :");
                int amm = sc.nextInt();
                bank.funds += amm;
                System.out.println("Now funds in bank is : " + bank.funds);

            }
        }
    }


//    public void skip(){
//
//    }

    // inside main manue
    public String profilemanue(long mobileno) {
        System.out.println("╔═════════════════════════════════════════════════════╗");
        System.out.println("║               ✨ Profile Creation Section ✨        ║");
        System.out.println("╚═════════════════════════════════════════════════════╝");

        System.out.println("1. Enter Name: ");
        String name = sc.nextLine();

        System.out.println("2. Enter Email: ");
        String Email = sc.nextLine();

        System.out.println("3. Enter Nickname: ");
        String nickname = sc.nextLine();

        System.out.println("4. Add Links: ");
        String links = sc.nextLine();

        // Create and add the profile
        Profile profile = new Profile(name, Email, nickname, links,mobileno);
        profiles.add(profile);

        System.out.println("╔═════════════════════════════════════════════════════╗");
        System.out.println("║            ✅ Profile Created Successfully!         ║");
        System.out.println("║         You can check or reset your profile later.  ║");
        System.out.println("╚═════════════════════════════════════════════════════╝");

        return name; // Return the name to be used elsewhere if needed
    }
    private String setprofile(long mobileNo) {
        System.out.println("╔═════════════════════════════════════════════════════╗");
        System.out.println("║               ✨ Set Profile Section ✨             ║");
        System.out.println("╚═════════════════════════════════════════════════════╝");

        // Search for the profile using the mobile number
        boolean found = false;
        String updatedName = null;

        for (Profile profile : profiles) {
            if (profileMatchesMobileNo(profile, mobileNo)) {
                found = true;

                // Ask for new values to update
                System.out.print("1. Enter new Name (Press Enter to skip): ");
                String newName = sc.nextLine();
                if (!newName.isEmpty()) {
                    profile.name = newName; // Update name if provided
                    updatedName = newName; // Store updated name
                }

                System.out.print("2. Enter new Email (Press Enter to skip): ");
                String newEmail = sc.nextLine();
                if (!newEmail.isEmpty()) {
                    profile.Email = newEmail; // Update email if provided
                }

                System.out.print("3. Enter new Nickname (Press Enter to skip): ");
                String newNickname = sc.nextLine();
                if (!newNickname.isEmpty()) {
                    profile.nickname = newNickname; // Update nickname if provided
                }

                System.out.print("4. Enter new Links (Press Enter to skip): ");
                String newLinks = sc.nextLine();
                if (!newLinks.isEmpty()) {
                    profile.links = newLinks; // Update links if provided
                }

                System.out.println("╔═════════════════════════════════════════════════════╗");
                System.out.println("║           ✅ Profile Updated Successfully!           ║");
                System.out.println("╚═════════════════════════════════════════════════════╝");

                // Update the database with new values
                profile.updatprofileFromDB();
                break;
            }
        }

        if (!found) {
            System.out.println("╔═════════════════════════════════════════════════════╗");
            System.out.println("║         ❌ No profile found for this mobile number.   ║");
            System.out.println("╚═════════════════════════════════════════════════════╝");
        }

        return updatedName; // Return the updated name if any
    }

    // Helper method to check if the profile belongs to the user with the given mobile number
    private boolean profileMatchesMobileNo(Profile profile, long mobileNo) {
        for (Register reg : registers) {
            if (reg.phono == mobileNo) {
                return true; // Match found
            }
        }
        return false;
    }



    // Flag to ensure the welcome message is shown only once
    private boolean welcomeDisplayed = false;

    public void displayapp(String type) {
        int option = 0;
        boolean exit = true;
        // Display the welcome message only once after the first connection
        if (!welcomeDisplayed) {
            System.out.println("╔═════════════════════════════════════════════════════╗");
            System.out.println("║               ✨ Welcome to E-Bank ✨                ║");
            System.out.println("╚═════════════════════════════════════════════════════╝");
            System.out.println("Connected to the database successfully!");
            welcomeDisplayed = true;  // Set the flag to true so it doesn't display again
        }

        while (exit){
            System.out.println("\n╔═════════════════════════════════════════════════════╗");
            System.out.println("║                   E-Bank Main Menu                  ║");
            System.out.println("╚═════════════════════════════════════════════════════╝");
            System.out.println("1. New Registration with Mobile Number");
            System.out.println("2. Login to an Existing Account");
            System.out.println("3. Help");
            System.out.println("4. About Us");
            System.out.println("5. Close App");

            // Error handling for user input
            option = errorhandle();

            // Menu options
            switch (option) {
                case 1:
                    NewAppacount(type);
                    break;
                case 2:
                    Existingacc(type);
                    break;
                case 3:
                    gethelp();
                    break;
                case 4:
                    Aboutus();
                    break;
                case 5:
                    System.out.println("╔═════════════════════════════════════════════════════╗");
                    System.out.println("║              Thank You for Using E-Bank!            ║");
                    System.out.println("╚═════════════════════════════════════════════════════╝");
                    exit = false;
                    break;

                default:
                    System.out.println("❌ Invalid option. Please try again.");
            }
        }  // Loop until the user chooses to exit
    }


    public void gethelp() {

        System.out.println("ask your query ");
        System.out.print("->");

    }

    public void Aboutus() {

        System.out.println("As you know that the world's first 'E-Bank' was invented by JLSS co-orporation");
        System.out.println("you can check more about us on our website the link is given below -> ");
        System.out.println("link");

    }

    private void Existingacc(String type) {
        System.out.println("Enter moblie no to varify");
        long mobno = errorhandle2();
        FindRegisteredmobno(mobno ,type);

    }

    public void FindRegisteredmobno(long mobno ,String type) {
        for (Register mob : registers) {
            if (mobno == mob.phono) {
                for (Profile pro : profiles){
                    if (Objects.equals(pro.registerdmobileno,mob.phono)){
                        System.out.println("you loged in to your Existing acc ");
                        System.out.println("Name of account is ");

                        if (Objects.equals(pro.registerdmobileno,mobno)){
                            if (type == "banker"){
                                mainmanue(mobno, pro.name);
                            }
                            else if (type == "emp"){
                                empapp(mobno,pro.name);
                            }
                            else if (type == "user"){
                                userapp(mobno,pro.name);
                            }



                        }

                    }
                }



                // now show main manue which contais following options

            } else {
                System.out.println("Moblie no not found in 'Database' ");
            }
        }

    }
    private void NewAppacount(String type) {
        // Ask user for mobile number
        System.out.println("Enter mobile no: ");
        long phono = errorhandle2();  // Ensure errorhandle2 handles input correctly

        // Optional: Check if the mobile number already exists in registers (uncomment if needed)

    for (Register reg : registers) {
        if (phono == reg.phono) {
            System.out.println("You already have an existing account.");
            System.out.println("To login to that account, enter 'yes'. Otherwise, enter 'no': ");
            String ch = sc.nextLine();

            if (Objects.equals(ch, "yes")) {
                for(Profile pro : profiles){
                    if (Objects.equals(pro.registerdmobileno,phono)){
                        if (type == "banker"){
                            mainmanue(phono, pro.name);
                        }
                        else if (type == "emp"){
                            empapp(phono,pro.name);
                        }
                        else if (type == "user"){
                            userapp(phono,pro.name);
                        }



                    }
                }
              // Redirect to the main menu
                return;       // Exit the method after redirecting
            } else if (Objects.equals(ch, "no")) {
                System.out.println("You selected 'no', so you want to add a new mobile number.");
                System.out.println("Enter new mobile no: ");
                phono = errorhandle2();  // Prompt again for a new mobile number
            }
        }
    }


        // Generate OTP
        int otp = otpmake();
        System.out.println("OTP generated: " + otp);
        System.out.println("Enter OTP: ");
        int otps = errorhandle();  // Ensure errorhandle handles the OTP input correctly

        if (otps == otp) {
            System.out.println("You have successfully registered to E-Bank!");
            System.out.println("Now kindly create your profile.");

            // Create a new Register object and add to the 'registers' list

           String proname =  profilemanue(phono);

            Register Newreg = new Register(phono, otps);
            registers.add(Newreg);  // Add to the 'registers' list
            if (Objects.equals(type, "banker")){
                mainmanue(phono,proname);
            }
            else if(Objects.equals(type, "user")){
                userapp(phono,proname);
            }
            else if(Objects.equals(type, "emp")){
                empapp(phono,proname);
            }
        } else {
            System.out.println("OTP is incorrect. Try again or resend.");
        }
    }

    private void empapp(long phono,String proname) {

            int op;
            boolean exit = true;

            while (exit) {
                System.out.println("**************************************************");
                System.out.println("*          Welcome to E-Bank Employee App         *");
                System.out.println("**************************************************");
                System.out.println("Hello, " + proname + "! We're glad to have you on board.");
                System.out.println();
                System.out.println("What would you like to do today? Choose an option below:");
                System.out.println("--------------------------------------------------");
                System.out.println("1. 🔍 Search banks by name");
                System.out.println("2. 💰 View banks by capital");
                System.out.println("3. 🌍 Search banks by location");
                System.out.println("4. 👤 View your profile");
                System.out.println("5. ✏️ Update your profile");
                System.out.println("6. 🏢 View the banks you're working with");
                System.out.println("7. 💼 Check salary and bank balance details");
                System.out.println("8. 🚪 Leave a bank");
                System.out.println("9. ❌ Exit");
                System.out.println("--------------------------------------------------");
                System.out.println("Type the number corresponding to your choice.");


                op = errorhandle(); // Input handling

                switch (op) {
                    case 1:
                        System.out.println("1. Search banks by name");
                        searchbank("emp");
                        break;

                    case 2:
                        System.out.println("2. see banks by bank capital ");
                        break;

                        case 3:
                            System.out.println("3. search bank by location");
                        break;

                        case 4:
                            System.out.println("5. set your profile ");
                            for(Profile pro : profiles){
                                if(pro.registerdmobileno == phono){
                                    pro.printpro();
                                }
                            }
                        break;


                    case 5:
                        for(Profile pro : profiles){
                            if(pro.registerdmobileno == phono){
                                setprofile(phono);
                            }
                        }
                        break;
                    case 6:
                        System.out.println("6. see banks in which you are working ");
                        break;
                    case 7:
                        System.out.println("7. check you salary and bank balance details  ");
                        break;
                    case 8:
                        System.out.println("7. left from bank  ");
                        break;



                    case 9:
                        System.out.println("closing app");
                        exit = false;

                        break;
                    default:
                        System.out.println("Invalid option.");
                }

            }


        }

    private void searchbank(String type) {
        System.out.println("enter name of bank");
        String bname = sc.nextLine();
        for (Banks b : banks){
            if (Objects.equals(b.nameofbank, bname)){
                for (Manager manager : managers){
                    if (Objects.equals(manager.bankname, bname)){
                        if (Objects.equals(type, "emp")){
                            employeeMenu(bname,manager.name);
                        }
                        else if (Objects.equals(type, "user")){
                            userMenu(bname,manager.name);
                        }

                    }
                    else{

                        System.out.println("manager bank name not found");

                    }
                }



            }
        }
    }


    // the user name and profile detail should be stored in that app locally so the app runs without internet
    private void userapp(long phono, String proname) {



        int op;
        boolean exit = true;

        while (exit) {
            System.out.println("🌟✨ Welcome to E-Bank User App, " + proname + "! ✨🌟");
            System.out.println("-------------------------------------------------");
            System.out.println("🌐 Your gateway to a seamless banking experience 🌐");
            System.out.println();
            System.out.println("Let's get started! What would you like to do today?");
            System.out.println("-------------------------------------------------");
            System.out.println("1. 🔍 Explore banks by name");
            System.out.println("2. 💰 Discover banks by capital");
            System.out.println("3. 🌍 Find banks by location");
            System.out.println("4. 👤 View your profile");
            System.out.println("5. ✏️ Update your profile");
            System.out.println("6. 🏦 View your account banks");
            System.out.println("7. 💼 Check overall balance details");
            System.out.println("8. ❌ Remove an account from a bank");
            System.out.println("9. 🚪 Exit");
            System.out.println("-------------------------------------------------");
            System.out.println("👉 Simply enter the number of your choice and proceed!");


            op = errorhandle(); // Input handling

            switch (op) {
                case 1:
                    System.out.println("1. Search banks by name");
                    searchbank("user");
                    break;

                case 2:
                    System.out.println("2. see banks by bank capital ");
                    break;

                case 3:
                    System.out.println("3. search bank by location");
                    break;

                case 4:
                    System.out.println("5. set your profile ");
                    for(Profile pro : profiles){
                        if(pro.registerdmobileno == phono){
                            pro.printpro();
                        }
                    }
                    break;


                case 5:
                    for(Profile pro : profiles){
                        if(pro.registerdmobileno == phono){
                            setprofile(phono);
                        }
                    }
                    break;
                case 6:

                    break;
                case 7:
                    System.out.println("7. check you salary and bank balance details  ");
                    break;

                case 8:
                    System.out.println("comming soon");
                    break;


                case 9:
                    System.out.println("closing app");
                    exit = false;

                    break;
                default:
                    System.out.println("Invalid option.");
            }

        }

    }

    public int otpmake() {
        // Generate a random OTP (ensuring it's a 4-digit number)
        int otp = (int) (Math.random() * 10000);
        return otp;
    }


    // Display the main menu after creating the bank
    public void displayMainMenu(String nameofbank, String nameofowner,String managername) {
        int option = 0;

        // Stylish and User-Friendly Menu
        do {
            System.out.println("╔═══════════════════════════════════════════════════════════════╗");
            System.out.println("║            🌟 Welcome to " + nameofbank.toUpperCase() + " Bank 🌟            ║");
            System.out.println("║                Managed by Mr. " + nameofowner + "                ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════╝");

            // Friendly instructions
            System.out.println("\nPlease select an option to continue:");

            // User options
            System.out.println("1. 👤 User Menu");
            System.out.println("2. 🏢 Employee Menu");
            System.out.println("3. 👨‍💼 Manager");
            System.out.println("4. 💼 Investor");

            // Exit option
            System.out.println("5. 🔙 Return to Main Menu");
            System.out.println("---------------------------------------------------");

            // Capture user choice
            option = errorhandle();

            // Process user choice
            switch (option) {
                case 1:
                    userMenu(nameofbank,managername);
                    break;
                case 2:
                    employeeMenu(nameofbank,managername);
                    break;
                case 3:
                    displaymanager(managername);
                    break;
                case 4:
                    investerMenu();
                    break;
                case 5:
                    System.out.println("Thank you for managing your bank with E-Bank.");
                    break;
                default:
                    System.out.println("⚠️  Invalid option. Please try again.");
            }
        } while (option != 5);
    }


    int loan = 0;

    private void managerMenu() {
        DateandTime();
        int moneyinbank = calculatetotalmoney() + ammtoinvest;

        System.out.println("money in bank is : " + moneyinbank);


    }

    int ammtoinvest = 0;

    private void investerMenu() {
        DateandTime();
        int existingmoney = calculatetotalmoney();
        System.out.println("entert money to invest");
        ammtoinvest = errorhandle();
        existingmoney += ammtoinvest;
        System.out.println("you invested in JLSS E-bank of reupee : " + ammtoinvest);
        System.out.println("you toatal money in  JLSS E-bank of reupee : " + existingmoney);

    }
    int loans = 0;
    public void loan() {
        System.out.println("Enter associated Account No:");
        int accNo = errorhandle();
        Account account = findAccountByNumber(accNo);
        System.out.println("youor acc no is : " + account.getAccNo());
        System.out.println("enter loan ammount : ");
        loan = errorhandle();

        account.credit(loan);
        loans += loan;
        System.out.println("your acc is credited by loan of " + loan);
        System.out.println("Now balance is  " + account.getBalance());

    }

    private void DateandTime() {
        LocalDateTime currentDateTime = LocalDateTime.now(); // Get the current local date and time
        int currentHour = currentDateTime.getHour(); // Extract the hour part of the time
        int currentDate = currentDateTime.getDayOfMonth(); // Extract the current day of the month

        if (currentHour < 12) {
            System.out.println("Good morning sir :)");
        } else {
            System.out.println("Good evening sir :)");
        }
        System.out.println("Today's date is: " + currentDate);
    }

    // User menu options
    private void userMenu(String nameofbank,String managername) {
        int option = 0;


        do {
            System.out.println("════════════════════════════════════════════════════════════");
            System.out.println("          WELCOME TO " + nameofbank.toUpperCase());
            System.out.println("          Managed by: " + managername);
            System.out.println("════════════════════════════════════════════════════════════");
            System.out.println("             USER MENU OPTIONS");
            System.out.println("");
            System.out.println("1.  Create a new account");
            System.out.println("2.  View account details");
            System.out.println("3.  Search account by name");
            System.out.println("4.  Search account by age");
            System.out.println("5.  Credit money");
            System.out.println("6.  Debit money");
            System.out.println("7.  View transaction history");
            System.out.println("8.  Transfer money");
            System.out.println("9.  Apply for loan");
            System.out.println("10. Our Special Feature: 'Chat with Your Manager'");
            System.out.println();
            System.out.println("11. Back to main menu");
            System.out.println("════════════════════════════════════════════════════════════");
            System.out.print("Please select an option: ");

            // Here you will capture the user's input, e.g.:
            // option = sc.nextInt();
            option = errorhandle();
            switch (option) {
                case 1:
                nameofuser =  createUserAccount(nameofbank);
                    break;
                case 2:
                    System.out.println("Enter Account No:");
                    int accNo = errorhandle();
                    Account acc = findAccountByNumber(accNo);
                    if (acc != null) {
                        acc.printInfo();
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;
                case 3:
                    System.out.println("Enter name to search:");
                    String name = sc.nextLine();
                    for (Account account : userAccounts) {
                        if (account.name.equalsIgnoreCase(name)) {
                            account.printInfo();
                        }
                    }
                    break;
                case 4:
                    System.out.println("Enter age to search:");
                    int age = errorhandle();
                    for (Account account : userAccounts) {
                        if (account.age == age) {
                            account.printInfo();
                        }
                    }
                    break;
                case 5:
                    System.out.println("Enter Account No:");
                    accNo = errorhandle();
                    acc = findAccountByNumber(accNo);
                    if (acc != null) {
                        System.out.println("Enter amount to credit:");
                        int amount = errorhandle();
                        acc.credit(amount);
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;
                case 6:
                    System.out.println("Enter Account No:");
                    accNo = errorhandle();
                    acc = findAccountByNumber(accNo);
                    if (acc != null) {
                        System.out.println("Enter amount to debit:");
                        int amount = errorhandle();
                        acc.debit(amount);
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;
                case 7:
                    System.out.println("Enter Account No:");
                    accNo = errorhandle();
                    acc = findAccountByNumber(accNo);
                    if (acc != null) {
                        acc.printTransactions();
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;
                case 8:
                    System.out.println("Enter your Account No:");
                    int fromAccNo = errorhandle();
                    System.out.println("Enter recipient Account No:");
                    int toAccNo = errorhandle();
                    System.out.println("Enter amount to transfer:");
                    int amount = errorhandle();
                    transferMoney(fromAccNo, toAccNo, amount);
                    break;
                case 9:
                    loan();
                    break;
                case 10:
                    System.out.println("hooooreey Fianlly you are gonig to real chat with manager");
                    chatingfeature(nameofuser,managername);
                    break;
                case 11:
                    System.out.println("Returning to main menu...");

                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (option != 11);
    }

    private void chatingfeature(String nameofuser, String managername) {
        int op;
        boolean exit = true;
        String nameofbank = "Notsetyet"; // You might want to set the actual bank name here

        for (Account acc : userAccounts) {
            if (Objects.equals(nameofuser, acc.name)) {
                while (exit) {
                    System.out.println("════════════════════════════════════════════════════════════");
                    System.out.println("     Welcome to Your Chat, " + nameofuser);
//                    System.out.println("   Chatting with Manager: " + managername);
                    System.out.println();
                    System.out.println("1. 💬 View Messages");
                    System.out.println("2. ✉️ Send a Message to Manager");
//              System.out.println("3. ✉️ Send Message to a Friend or Another Account Holder");
                    System.out.println("3. 🔙 Return to Main Menu");
                    System.out.println("════════════════════════════════════════════════════════════");
                    System.out.print("Select an option: ");

                    op = errorhandle(); // Input handling

                    switch (op) {
                        case 1:
                            System.out.println();
                            System.out.println("📥 Checking messages for " + nameofuser);
                            acc.printmessages(nameofuser); // Display messages
                            System.out.println("════════════════════════════════════════");
                            break;

                        case 2:
                            for (Manager manager : managers) {
                                if (Objects.equals(managername, manager.name)) {

                                    System.out.println("📤 Type your message to Manager " + managername + ": ");
                                    String mes = sc.nextLine();  // Input message
                                    String sender = nameofuser;
                                    String sendertype = "user";

                                    // Send message to manager
                                    manager.managerchatbox(mes, sender, sendertype);
                                    System.out.println("✅ Message sent successfully to " + managername);
                                    System.out.println("════════════════════════════════════════");

                                } else {
                                    System.out.println("⚠️ Manager not found.");
                                }

                            }
                            break;
                        case 3:
                            System.out.println("Returning to main menu...");
                           exit = false;

                            break;
                        default:
                            System.out.println("Invalid option.");
                    }

                    }
                }
            }
        }





                            // Employee menu options
    private void employeeMenu(String nameofbank,String manangername) {
        int option = 0;

        do {
            System.out.println("\n==================== Employee Menu ====================");
            System.out.println("💼 Welcome to the " + nameofbank + " Bank Employee Portal");
            System.out.println("-------------------------------------------------------");
            System.out.println("1️⃣  Register for a Job");
            System.out.println("2️⃣  Apply for an Interview");
            System.out.println("3️⃣  View Employee Details");
            System.out.println("4️⃣  Check Salary Information");
            System.out.println("5️⃣  💬 Special Feature: Chat with Manager or Colleagues");
            System.out.println("6️⃣  🔙 Return to Main Menu");
            System.out.println("=======================================================");
            System.out.print("Please select an option (1-6): ");
            option = errorhandle();
            String regNo;
            switch (option) {
                case 1:
                 nameofemp =  createEmployee(nameofbank);
                    break;
                case 2:
                    System.out.println("Enter your Registration ID:");
                    regNo = sc.nextLine();
                    NewEmploye emp = findEmployeeByRegNo(regNo);
                    if (emp != null) {
                        // Simulate interview and getting the job
                        emp.startSalaryIncrement();
                        System.out.println("Interview successful. You have been hired.");
                    } else {
                        System.out.println("Registration ID not found.");
                    }
                    break;
                case 3:
                    System.out.println("Enter your Registration ID:");
                    regNo = sc.nextLine();
                    emp = findEmployeeByRegNo(regNo);
                    if (emp != null) {
                        String bankname = "E-bank";

                        emp.printEmpInfo(bankname);
                    } else {
                        System.out.println("Registration ID not found.");
                    }
                    break;
                case 4:
                    System.out.println("Enter your Registration ID:");
                    regNo = sc.nextLine();
                    emp = findEmployeeByRegNo(regNo);
                    if (emp != null) {
                        System.out.println("1. Check salary details");
                        System.out.println("2. Check salary transactions");
                        System.out.println("3. Return to previous menu");
                        int salaryOption = errorhandle();
                        switch (salaryOption) {
                            case 1:
                                emp.printSalaryDetails();
                                break;
                            case 2:
                                emp.printSalaryTransactions();
                                break;
                            case 3:
                                System.out.println("Returning to previous menu...");
                                break;
                            default:
                                System.out.println("Invalid option.");
                        }
                    } else {
                        System.out.println("Registration ID not found.");
                    }
                    break;
                case 5:
                    System.out.println("hooooreey Fianlly you are gonig to real chat with manager");
                    chatingfeatureemp(nameofemp,manangername);
                    break;
                case 6:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (option != 5);

    }

    private void chatingfeatureemp(String nameofemp, String manangername) {
        int op;
        boolean exit = true;
        String nameofbank = "Notsetyet";
        for (NewEmploye emp : employees){
            if(Objects.equals(nameofemp,emp.name)){
                while (exit) {

                    System.out.println("hello "+nameofemp);
                    System.out.println("1. Check messages");
                    System.out.println("2. send message to manager ");
//                    System.out.println("3. send message to friend or diff acc holder in this bank ");

                    System.out.println("3. Return to manager mani main menue");
                    System.out.println("===============================================");


                    op = errorhandle();
                    switch (op) {
                        case 1:
                            emp.printEmpmessages(nameofemp);
                            break;
                        case 2:
                            for(Manager manager: managers){
                                if (Objects.equals(manager.name, manangername)){
                                    System.out.println("Type your message");
                                    String mes = sc.nextLine();
                                    String sender = nameofemp;
                                    String sendertype = "employee";
                                    manager.managerchatbox(mes,sender,sendertype);
                                }
                            }
                            break;

                        case 3:
                            System.out.println("Returning to main menu...");
                            exit = false;
                            break;
                        default:
                            System.out.println("Invalid option.");


                    }
                }
            }
        }

    }


    // manue after registration


    public void mainmanue(long registerd_mobileno, String proname) {
        boolean exit = true;
        long mobileno = registerd_mobileno;
        int op = 0;
        String managername = null;
        String tostorebankname = null;
// the profile name should be displayed if present
        while (exit) {
            // Stylish welcome message
            System.out.println("\n===============================================");
            System.out.println("    Welcome to Your E-Bank Main Menu");
            System.out.println("  Here, you can use the whole app :)");
            System.out.println("═════════════════════════════════════════════════");


            System.out.println("1. Set your profile");
            System.out.println("2. Create New Bank");
            System.out.println("3. Add Managers to Bank");
            System.out.println("4. Open an Existing Bank");
            System.out.println("5. See All Banks");
            System.out.println("6. Delete an Existing Bank");
            System.out.println("7. Log Out from this Account of E-Bank");
            System.out.println("8. Subscribe to Premium Features of E-Bank");
            System.out.println("9. Ask to JE AI 'Chat Bot'");
            System.out.println("10. See Profile");
            System.out.println("11. Exit");
            System.out.println("══════════════════════════════════════════════\n");

            // Get user input for option selection
            op = errorhandle();

            // Process the selected option
            switch (op) {
                case 1:
                 setprofile(mobileno);
                    break;

                case 2:
                    tostorebankname = Createnewbank();
                    break;

                case 3:
                      managername =   crateManger(tostorebankname);
                    break;

                case 4:
                    openexistingbank();
                    break;

                case 5:
                    seeAllBanks();
                    break;

                case 6:
                    deletebank();
                    break;

                case 7:
                    AppStart();
                    break;

                case 8:
                    Primiume();
                    break;

                case 9:
                    Chatbot();
                    break;

                case 10:
                    Searching(proname);
                    break;

                case 11:
                    System.out.println("Returning to main menu...");
                    exit = false; // Exit the loop
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }



    public void Primiume() {
        System.out.println("══════════════════════════════════════════════════════════════════════════");
        System.out.println("                     🚀 Discover Our Premium Features! 🚀                 ");
        System.out.println("══════════════════════════════════════════════════════════════════════════");
        System.out.println("We are thrilled to offer you the ultimate features of E-Bank, designed to elevate your banking experience!");
        System.out.println();

        System.out.println("✨ Premium Features:");
        System.out.println("1. **Auto Manage Bank**: Say goodbye to traditional management! Our system operates seamlessly without the need for managers.");
        System.out.println("2. **Ad-Free Experience**: Enjoy a clean and focused banking environment with no annoying ads to disrupt your experience.");
        System.out.println("3. **Unlimited Bank Creation**: The sky's the limit! Create as many banks as you desire to suit your needs.");
        System.out.println("4. **AI Assistant 'JE'**: Leverage our state-of-the-art AI model, 'JE', to manage your banking effortlessly and efficiently.");
        System.out.println();

        System.out.println("🔍 For more details on any of these exciting features, simply select the option number (e.g., '1' for Auto Manage Bank) to learn more!");
        System.out.println("══════════════════════════════════════════════════════════════════════════");
    }


    public void Searching(String tostorename) {
        for (Profile pro : profiles) {
            if (Objects.equals(tostorename, pro.name)) {
                System.out.println("your profile Details ->");
                pro.printpro();

            } else {
                System.out.println("EMAIL IS WRONG ");
            }
        }
    }

    public void seeAllBanks() {
        System.out.println("══════════════════════════════════════════════════════════════════");
        System.out.println("                  VIEW ALL BANKS                                 ");
        System.out.println("══════════════════════════════════════════════════════════════════");
        System.out.print("Enter your profile name for verification: ");
        String profileName = sc.nextLine();

        boolean profileFound = false; // Track if the profile is found

        // Iterate through profiles to find the matching profile name
        for (Profile profile : profiles) {
            if (Objects.equals(profileName, profile.name)) {
                profileFound = true; // Set flag if profile is found
                System.out.println("Profile verified successfully!\n");

                // Display bank information in a formatted style
                for (Banks bank : banks) {
                    System.out.println("════════════════════════════════════════════════════════════════");
                    bank.bankinfo(); // Display bank information
                    System.out.println("════════════════════════════════════════════════════════════════");
                }
                break; // Exit loop after displaying banks
            }
        }

        // If the profile was not found, print an error message
        if (!profileFound) {
            System.out.println("❌ Error: Profile name not found. Please try again.");
        }

        System.out.println("══════════════════════════════════════════════════════════════════");
    }

    public void Chatbot(){
        System.out.println("Askyour quee to JE ->");
        String quee = sc.nextLine();

        System.out.println("AI is under work");
    }

    public void AppStart(){


        int op;
        boolean exit = true;

                while (exit) {
                    System.out.println("Welcome to Ebank - Your Virtual Banking Partner!");
                    System.out.println("-----------------------------------------------");
                    System.out.println("Please introduce yourself and choose from the options below:");
                    System.out.println("1. Banker");
                    System.out.println("2. User");
                    System.out.println("3. Employee");
                    System.out.println("4. Exit the app");
                    System.out.println("-----------------------------------------------");


                    op = errorhandle(); // Input handling

                    switch (op) {
                        case 1:
                            displayapp("banker");
                            break;
                        case 2:
                           displayapp("user");
                        break;
                        case 3:
                            displayapp("emp");
                            break;


                        case 4:
                            System.out.println("closing app");
                            exit = false;

                            break;
                        default:
                            System.out.println("Invalid option.");
                    }

                }

    }


}


