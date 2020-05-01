import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountTest {

    private Account acc;
    private DDADAO db;
    private ArrayList<String[]> accounts;
    private ArrayList<String[]> advice;
    private ArrayList<String[]> customers;
    private ArrayList<String[]> transactions;

    @BeforeEach
    void setUp() {
        accounts = new ArrayList<String[]>();
        String[] accRow = {"1","1","standard","300"};
        accounts.add(0,accRow);

        advice = new ArrayList<String[]>();
        String[] adRow = {"1","1","5.50","fee","2020-03-13"};
        String[] adRow2 = {"2","1","4","fee","2020-03-12"};
        advice.add(0,adRow);
        advice.add(1,adRow2);

        customers = new ArrayList<String[]>();
        String[] custRow = {"1", "Teaz","Michell","008 Rutledge Crossing"};
        customers.add(0,custRow);

        transactions = new ArrayList<String[]>();
        String[] transRow = {"1","1","300.50","debit card","2020-03-11", "bank"};
        String[] transRow2 = {"2","1","150","check","2020-03-01","gas station"};
        transactions.add(0,transRow);
        transactions.add(1,transRow2);

        db = new DDADAO(accounts, advice, customers, transactions);
        acc = new Account(1,db);
    }

    @Test
    public void produceStatement() {
        String statement = "Transactions:\n300.50 >> debit card >> 2020-03-11 >> bank >> \n150 >> check >> 2020-03-01 >> gas station >> " +
                "\nAdvices:\n5.50 >> fee >> 2020-03-13 >> \n4 >> fee >> 2020-03-12 >> " ;
        assertEquals(statement,acc.produceStatement());
    }

    @Test
    public void formatAdviceStatement(){
        ArrayList<String> unformattedAdvices = new ArrayList<String>();
        assertEquals("",acc.formatAdviceStatement(unformattedAdvices));

        unformattedAdvices.add(0,"5.50");
        unformattedAdvices.add(1,"fee");
        unformattedAdvices.add(2,"2020-03-13");

        assertEquals("\n5.50 >> fee >> 2020-03-13 >> ", acc.formatAdviceStatement(unformattedAdvices));
    }

    @Test
    public void formatTransactionStatement(){
        ArrayList<String> unformattedTrans = new ArrayList<String>();
        assertEquals("",acc.formatTransactionStatement(unformattedTrans));

        unformattedTrans.add(0,"300");
        unformattedTrans.add(1,"debit card");
        unformattedTrans.add(2,"2020-03-11");
        unformattedTrans.add(3,"bank");

        assertEquals("\n300 >> debit card >> 2020-03-11 >> bank >> ", acc.formatTransactionStatement(unformattedTrans));
    }

    @Test
    public void formatStartDate(){
       assertEquals("2020-06-01", acc.formatStartDate(6, 2020));
       assertEquals("2020-11-01", acc.formatStartDate(11,2020));
    }

    @Test
    public void formatEndDate(){
        assertEquals("2020-05-31", acc.formatEndDate(5,2020,"31"));
        assertEquals("2020-11-30", acc.formatEndDate(11, 2020, "30"));
    }

    @Test
    public void lastDayOfMonth(){
        assertEquals("30", acc.lastDayOfMonth(5));
    }

    @Test
    void getAccountNumber() {
        assertEquals(1,acc.getAccountNumber());
    }

    @Test
    void getAccountType() {
        assertEquals("standard", acc.getAccountType());
    }

    @Test
    void getBalance() {
        assertEquals(300, acc.getBalance());
    }

    @Test
    void getFirstName() {
        assertEquals("Michell",acc.getFirstName());
    }

    @Test
    void getLastName() {
        assertEquals("Teaz",acc.getLastName());
    }

    @Test
    void getAddress() {
        assertEquals("008 Rutledge Crossing", acc.getAddress());
    }
}