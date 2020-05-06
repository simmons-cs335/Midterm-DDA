import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * @author Abigail Pinterparsons
 */

public class Account {
    int customerID;
    int accountNumber;
    String accountType;
    DDADAO db;

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

    public String produceStatement(){
        String startDate;
        String endDate;
        String statement = "";

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

        String transStatement = "Transactions:" + formatTransactionStatement(transactions, statement);
        String adviceStatement = "\nAdvices:" + formatAdviceStatement(advices, statement);

        statement = transStatement + adviceStatement;

        return statement;
    }

    private String formatTransactionStatement(ArrayList<String> transaction, String statement){
        for(int i=0; i<transaction.size(); i++){
            if(i%4==0){
                statement += "\n";
            }
            statement += transaction.get(i) + " >> ";
        }
        return statement;
    }

    private String formatAdviceStatement(ArrayList<String> advice, String statement){
        for(int i=0; i<advice.size(); i++){
            if(i%3==0){
                statement += "\n";
            }
            statement += advice.get(i) + " >> ";
        }
        return statement;
    }

    String formatStartDate(int month, int year){
        String date;
        if(month<10){
            date = Integer.toString(year) + "-0" + Integer.toString(month) + "-01";
        } else{
            date = Integer.toString(year) + "-" + Integer.toString(month) + "-01";
        }
        return date;
    }

    String formatEndDate(int month, int year, String finalDay){
        String date;
        if(month<10){
            date = Integer.toString(year) + "-0" + Integer.toString(month) + "-" + finalDay;
        } else{
            date = Integer.toString(year) + "-" + Integer.toString(month) + "-" + finalDay;
        }
        return date;
    }

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

    public int getAccountNumber(){
        return accountNumber;
    }

    public String getAccountType(){
        return accountType;
    }

    public double getBalance() {
        return db.getBalance(accountNumber);
    }

    public String getFirstName(){
        return db.getCustomerFirst(customerID);
    }

    public String getLastName(){
        return db.getCustomerLast(customerID);
    }

    public String getAddress(){
        return db.getCustomerAddress(customerID);
    }

}