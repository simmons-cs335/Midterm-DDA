import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Isabelle Molander
 */

public class DDAMain {



    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        DDADAO dao = new DDADAO("noonanc", "1997520");

        Scanner sc = new Scanner(System.in);
        int choice = 0;

        while (choice != 4) {
            System.out.println("Welcome to the DDA. What would you like to do?");
            System.out.println("1. Customer portal \n2. Process Interest \n3. Process Monthly Balance \n4. Exit");
            choice = sc.nextInt();

            switch(choice) {
                case 1:
                    customerPortal(dao);
                    break;
                case 2:
                    interest(dao);
                    System.out.println("Interest successfully processed for all accounts");
                    break;
                case 3:
                    monthlyBalance(dao);
                    System.out.println("Monthly balance successfully processed for all accounts");
                    break;
                case 4:
                    choice = 4;
                    break;
            }
        }
        System.out.println("Goodbye");
    }

    /**
     * This helper method goes through every account in the database and adds an advice for interest to the account
     * @author Isabelle Molander
     * @param dao This is the database connection that is sent to the Advice class in order ot access the accounts
     */
    public static void interest(DDADAO dao){
        for (int i =1; i<101; i++){
            Advice advice = new Advice(i, dao);
            advice.interest();
        }
    }

    /**
     * This method adds an advice for every account if it went below the required monthly balance
     * @author Isabelle Molander
     * @param dao This is the database connection that is sent to the Advice class in order ot access the accounts
     */
    public static void monthlyBalance(DDADAO dao){
        for (int i =1; i<101; i++){
            Advice advice = new Advice(i, dao);
            advice.monthlyBalance();
        }
    }

    /**
     * This method provides the options for a customer to interact with their account
     * @author Isabelle Molander
     * @param dao This is the database connection that is sent to the Advice class in order ot access the accounts
     */
    public static void customerPortal(DDADAO dao){
        int customerChoice =1;
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the customer ID");
        int customerID = sc.nextInt();
        Account ac = new Account(customerID, dao);
        Transaction transac = new Transaction(ac.getAccountNumber(), dao);


        while (customerChoice != 5) {
            System.out.println("Welcome, " + ac.getFirstName());
            System.out.println("What would you like to do?");
            System.out.println("1. Check balance \n2. Get statement \n3. Add Transaction \n4. Get account type \n5. Back ");
            customerChoice = sc.nextInt();

            switch (customerChoice) {
                case 1:
                    System.out.println(ac.getBalance());
                    break;
                case 2:
                    System.out.println(ac.produceStatement());
                    break;
                case 3:
                    System.out.println("Enter the amount");
                    int amount = sc.nextInt();
                    System.out.println("Enter the transaction type");
                    String type = sc.next();
                    System.out.println("Enter the transaction location");
                    String location = sc.next();
                    transac.addTransaction(amount, type, location);
                    break;
                case 4:
                    System.out.println("Account type: " + ac.getAccountType());
                    break;
                case 5:
                    customerChoice = 5;
                    break;
            }///switch
        }//while
    }
}