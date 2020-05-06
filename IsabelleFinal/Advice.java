import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Caelum Noonan and Isabelle Molander
 */

public class Advice {
    Account account;
    DDADAO db;

    public Advice(int customerID, DDADAO db){
        account = new Account(customerID, db);
        this.db = db;
    }

    public void overdraftFee(){
        db.addAdvice(account.getAccountNumber(), -15, "overdraft", getDate());
        updateBalance(-15);
    }

    private void updateBalance(double amount) {
        db.updateBalance(account.getAccountNumber(), account.getBalance() + amount);
    }

    private String getDate(){
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return(date);
    }

    private String getFirstOfMonth(){
        String date = new SimpleDateFormat("yyyy-mm").format(new Date());
        return (date+"-01");
    }

    public void monthlyBalance() {
        if (account.getBalance() < 500 && account.getAccountType() == "standard") {
            db.addAdvice(account.getAccountNumber(), -15, "monthly balance", getDate());
        } else if (account.getBalance() < 2000 && account.getAccountType() == "deluxe") {
            db.addAdvice(account.getAccountNumber(), -15, "monthly balance", getDate());
        } else if (account.getBalance() < 10000 && account.getAccountType() == "premier") {
            db.addAdvice(account.getAccountNumber(), -15, "monthly balance", getDate());
        }
    }

    public void numChecks() {
        int count = db.countChecks(account.getAccountNumber(), getFirstOfMonth(), getDate());
        if ((account.getAccountType() == "basic" && count > 10) || (account.getAccountType() == "deluxe" && count > 30)) {
            db.addAdvice(account.getAccountNumber(), -2, "check fee", getDate());
            updateBalance(-2);
        }
    }

    public void transactionFee() {
        if (account.getAccountType() == "standard") {
            db.addAdvice(account.getAccountNumber(), -1, "transaction fee", getDate());
            updateBalance(-1);
        }
    }

    public void interest() {
        double interest = account.getBalance() * 0.003;
        double roundedInterest = Math.round(interest*100.0)/100.0;
        updateBalance(roundedInterest);
    }
}
