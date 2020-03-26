import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Isabelle Molander
 */

public class Transaction {
    private int accountNo;
    private DDADAO dao;


    public Transaction(int accNo, DDADAO db){
        dao = db;
        accountNo = accNo;
    }

    /**
     * This method takes in information about a transaction and adds it to the database, updates the
     * account's balance, then adds an overdraft fee if necessary.
     * @author Isabelle Molander
     * @param amount a positive or negative integer representing the amount deposited or withdrawn respectively
     * @param transactionType a string description of what the transaction was for
     * @param location a string of the place of transaction
     */
    public void addTransaction(int amount, String transactionType, String location){
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        dao.addTransaction(accountNo, amount, transactionType, date, location);
        updateBalance(amount);
        checkOverdraft();
    }

    /**
     * This helper method takes an integer, adds it to the current account's balance,
     * and saves the new amount in the database
     * @author Isabelle Molander
     * @param amount a positive or negative integer representing the amount of the transaction to be added to the current balance
     */
    protected void updateBalance(int amount){
        dao.updateBalance(accountNo, dao.getBalance(accountNo) + amount);
    }

    /**
     *This helper method adds an advice to an account if the balance is negative
     * @author Isabelle Molander
     */
    protected void checkOverdraft(){
        if(dao.getBalance(accountNo) < 0){
            Advice ad = new Advice(accountNo, dao);
            ad.overdraftFee();
        }
    }

}