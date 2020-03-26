import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class Account {
    int customerID;
    int accountNumber;
    String accountType;
    DDADAO db;

    /**
     * This is a constructor that creates an account object for a particular user.
     *
     * @author Abigail PinterParsons
     * @param cID The customer's ID that is entered in the customer portal. Represents unique customer & account
     * @param database Database object that has access to the database.
     */
    public Account(int cID, DDADAO database){
        try {
            db = database;
            customerID = cID;
            accountNumber = db.getAccountNo(cID);
            accountType = db.getAccountType(accountNumber);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This method gives all the transactions & advices of a particular account for the previous month.
     *
     * @author Abigail PinterParsons
     * @return A String of all advices & transactions for an account in the previous month
     */
    public String produceStatement(){
        String startDate;
        String endDate;

        int month = Calendar.getInstance().get(Calendar.MONTH);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String finalDay = lastDayOfMonth(month);

        if(month == 0){
            month = 12;
            year = year-1;
        }

        startDate = formatStartDate(month, year);
        endDate = formatEndDate(month, year, finalDay);

        ArrayList<String> transactions = new ArrayList<String>(db.getTransactionsStatement(accountNumber, startDate, endDate));
        ArrayList<String> advices = new ArrayList<String>(db.getAdviceStatements(accountNumber, startDate, endDate));

        String transStatement = "Transactions:" + formatTransactionStatement(transactions);
        String adviceStatement = "\nAdvices:" + formatAdviceStatement(advices);

        String statement = transStatement + adviceStatement;

        return statement;
    }

    /**
     * This method formats the transactions into a String that is more legible for the user.
     * Each transaction is printed on a separate line.
     *
     * @author Abigail PinterParsons
     * @param transaction An ArrayList<String> of all the transactions a particular account accrued in the previous month
     * @return A String of properly formatted transactions
     */
    private String formatTransactionStatement(ArrayList<String> transaction){
        String statement = "";
        for(int i=0; i<transaction.size(); i++){
            if(i%4==0){
                statement += "\n";
            }
            statement += transaction.get(i) + " >> ";
        }
        return statement;
    }

    /**
     * This method formats the advices into a String that is more legible for the user.
     * Each advice is printed on a separate line.
     *
     * @author Abigail PinterParsons
     * @param advice ArrayList<String> of all the advices a particular account accrued in the previous month
     * @return A String of properly formatted advices
     */
    private String formatAdviceStatement(ArrayList<String> advice){
        String statement = "";
        for(int i=0; i<advice.size(); i++){
            if(i%3==0){
                statement += "\n";
            }
            statement += advice.get(i) + " >> ";
        }
        return statement;
    }

    /**
     * This method uses the previous month & year to create a correctly formatted date that can be sent into the database.
     * It is for the first day of the previous month.
     *
     * @author Abigail PinterParsons
     * @param month An integer representing the numerical value of the previous month
     * @param year An integer representing the year of the previous month
     * @return String of parameters combined into "YYYY-MM-DD" format
     */
    String formatStartDate(int month, int year){
        String date;
        if(month<10){
            date = Integer.toString(year) + "-0" + Integer.toString(month) + "-01";
        } else{
            date = Integer.toString(year) + "-" + Integer.toString(month) + "-01";
        }
        return date;
    }

    /**
     * This method combines the month, day, and year into a correctly formatted date that can be sent into the database.
     * It is for the last day of the previous month.
     *
     * @author Abigail PinterParsons
     * @param month An integer representing the numerical value of the previous month
     * @param year An integer representing the year of the previous month
     * @param finalDay A String of the numerical value of the last day of the previous month
     * @return String of all parameters combined into "YYYY-MM-DD" format
     */
    String formatEndDate(int month, int year, String finalDay){
        String date;
        if(month<10){
            date = Integer.toString(year) + "-0" + Integer.toString(month) + "-" + finalDay;
        } else{
            date = Integer.toString(year) + "-" + Integer.toString(month) + "-" + finalDay;
        }
        return date;
    }

    /**
     * This method takes in a numerical value representing a month of the year and returns the
     * value of the final day of that month
     *
     * @author Abigail PinterParsons
     * @param currentMonth The numerical value of the previous month
     * @return String representing the last day of a particular month
     */
    String lastDayOfMonth(int currentMonth){
        String finalDay = "";
        List<String> lastDay = Arrays.asList("31","28","31","30","31","30","31","31","30","31","30","31");

        try{
            finalDay = lastDay.get(currentMonth);
        } catch(Exception e){
            e.printStackTrace();
        }
        return finalDay;
    }

    /**
     * This method returns the customer's account number.
     *
     * @author Abigail PinterParsons
     * @return Customer's unique account number
     */
    public int getAccountNumber(){
        return accountNumber;
    }

    /**
     * This method returns the customer's account type.
     *
     * @author Abigail PinterParsons
     * @return The type of account a customer holds (standard, basic, deluxe, premier)
     */
    public String getAccountType(){
        return accountType;
    }

    /**
     * This method returns the current balance of a customer's account.
     *
     * @author Abigail PinterParsons
     * @return The current balance of an account
     */
    public int getBalance() {
        return db.getBalance(accountNumber);
    }

    /**
     * This method returns the customer's first name.
     *
     * @author Caelum Noonan
     * @return The customer's first name
     */
    public String getFirstName(){
        return db.getCustomerFirst(customerID);
    }

    /**
     * This method returns the customer's last name.
     *
     * @author Caelum Noonan
     * @return The customer's last name.
     */
    public String getLastName(){
        return db.getCustomerLast(customerID);
    }

    /**
     * This method returns the customer's address.
     *
     * @author Caelum Noonan
     * @return The customer's current address
     */
    public String getAddress(){
        return db.getCustomerAddress(customerID);
    }

}