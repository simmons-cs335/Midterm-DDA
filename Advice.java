import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Caelum Noonan and Isabelle Molander
 */

public class Advice {
    Account account;
    DDADAO db;

    /**
     * constructor which takes a customerID and database object and creates a new Advice with an associated account
     * @author Caelum Noonan and Isabelle Molander
     * @param customerID customer's ID number
     * @param db database object allowing connection via jdbc
     */
    public Advice(int customerID, DDADAO db){
        account = new Account(customerID, db);
        this.db = db;
    }

    /**
     * adds an advice labeled "overdraft fee" to the database and updates the customer's balance to reflect this fee
     * @author Caelum Noonan and Isabelle Molander
     */
    public void overdraftFee(){
        db.addAdvice(account.getAccountNumber(), -15, "overdraft", getDate());
        updateBalance(-15);
    }

    /**
     * helper method which accesses the database and updates the balance of the account with the given amount
     * @author Caelum Noonan and Isabelle Molander
     * @param amount the amount of money to add to the account (a negative number to subtract)
     */
    private void updateBalance(double amount) {
        db.updateBalance(account.getAccountNumber(), account.getBalance() + amount);
    }

    /**
     * a helper method which returns the current date
     * @author Caelum Noonan and Isabelle Molander
     * @return the current date as a String
     */
    private String getDate(){
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return(date);
    }

    /**
     * a helper method which returns the first day of the month
     * @author Caelum Noonan and Isabelle Molander
     * @return the first day of the current month as a String
     */
    private String getFirstOfMonth(){
        String date = new SimpleDateFormat("yyyy-mm").format(new Date());
        return (date+"-01");
    }

    /**
     * gets the current balance and assesses a fee if it is below the allowed minimum for the month
     * @author Caelum Noonan and Isabelle Molander
     */
    public void monthlyBalance() {
        if (account.getBalance() < 500 && account.getAccountType() == "standard") {
            db.addAdvice(account.getAccountNumber(), -15, "monthly balance", getDate());
        } else if (account.getBalance() < 2000 && account.getAccountType() == "deluxe") {
            db.addAdvice(account.getAccountNumber(), -15, "monthly balance", getDate());
        } else if (account.getBalance() < 10000 && account.getAccountType() == "premier") {
            db.addAdvice(account.getAccountNumber(), -15, "monthly balance", getDate());
        }
    }

    /**
     * counts the number of checks used in the past month and assesses a fee if it is above the monthly allowance
     * @author Caelum Noonan and Isabelle Molander
     */
    public void numChecks() {
        int count = db.countChecks(account.getAccountNumber(), getFirstOfMonth(), getDate());
        if ((account.getAccountType() == "basic" && count > 10) || (account.getAccountType() == "deluxe" && count > 30)) {
            db.addAdvice(account.getAccountNumber(), -2, "check fee", getDate());
            updateBalance(-2);
        }
    }

    /**
     * assesses a fee for each transaction if the type of account requires a transaction fee
     * @author Caelum Noonan and Isabelle Molander
     */
    public void transactionFee() {
        if (account.getAccountType() == "standard") {
            db.addAdvice(account.getAccountNumber(), -1, "transaction fee", getDate());
            updateBalance(-1);
        }
    }

    /**
     * calculates interest for the account's balance and updates the balance to reflect this
     * @author Caelum Noonan and Isabelle Molander
     */
    public void interest() {
        double interest = account.getBalance() * 0.003;
        double roundedInterest = Math.round(interest*100.0)/100.0;
        updateBalance(roundedInterest);
    }
}
