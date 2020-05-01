import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AdviceTest {

    private DDADAO fakeDB;
    private  ArrayList<String[]> acct;
    ArrayList<String[]> advice;
    ArrayList<String[]> cust;
    ArrayList<String[]> trans;

    @BeforeEach
    void setUp() {
        acct = new ArrayList<>();
        String[] basic = {"1","1","basic","100"};
        String[] standard = {"2", "2", "standard","500"};
        String[] deluxe = {"3", "3", "deluxe", "2000"};
        String[] premier = {"4","4","premier","10000"};
        acct.add(basic);
        acct.add(standard);
        acct.add(deluxe);
        acct.add(premier);

        advice = new ArrayList<>();

        cust = new ArrayList<>();
        String[] accountOneCustomer = {"1","Teaz","Michell","008 Rutledge Crossing"};
        String[] accountTwoCustomer = {"2","Mount,Chloette","254 Dwight Alley"};
        String[] accountThreeCustomer = {"3","Farress","Fianna","6 Hauk Road"};
        String[] accountFourCustomer = {"4","Omar","Daile","85951 Melvin Way"};
        cust.add(accountOneCustomer);
        cust.add(accountTwoCustomer);
        cust.add(accountThreeCustomer);
        cust.add(accountFourCustomer);

        trans = new ArrayList<>();

        fakeDB = new DDADAO(acct, advice, cust, trans);
    }

    @Test
    void overdraftFee() {
        Advice test = new Advice(1, fakeDB);
        assertEquals(100, fakeDB.getBalance(1));
        test.overdraftFee();
        assertEquals(85, fakeDB.getBalance(1));
    }

    @Test
    void monthlyBalanceBasic() {
        Advice testBasic = new Advice(1, fakeDB);
        testBasic.monthlyBalance();
        assertEquals(0, advice.size());
    }

    @Test
    void monthlyBalanceStandard() {
        Advice testStandard = new Advice(2, fakeDB);
        testStandard.monthlyBalance();
        assertEquals(0, advice.size());

        Transaction forStandard = new Transaction(2, fakeDB);
        forStandard.addTransaction(-50, "withdrawal", "bank");
        testStandard.monthlyBalance();
        assertEquals(2, advice.size()); // the expected is 2 because every transaction also has a fee
    }

    @Test
    void monthlyBalanceDeluxe() {
        Advice testDeluxe = new Advice(3, fakeDB);
        testDeluxe.monthlyBalance();
        assertEquals(0, advice.size());
        Transaction forDeluxe = new Transaction(3, fakeDB);
        forDeluxe.addTransaction(-50, "withdrawal", "bank");
        testDeluxe.monthlyBalance();
        assertEquals(1, advice.size());
    }
    @Test
    void monthlyBalancePremier() {
        Advice testPremier = new Advice (4, fakeDB);
        testPremier.monthlyBalance();
        assertEquals(0, advice.size());
        Transaction forPremier = new Transaction(4, fakeDB);
        forPremier.addTransaction(-50, "withdrawal", "bank");
        testPremier.monthlyBalance();
        assertEquals(1, advice.size());
    }

    @Test
    void numChecksBasic() {
        Advice testBasic = new Advice(1, fakeDB);
        testBasic.numChecks();
        assertEquals(0, advice.size());
        Transaction checksForBasic = new Transaction(1, fakeDB);
        for (int i = 0; i <= 11; i++) {
            checksForBasic.addTransaction(-1, "check", "store");
        }
        testBasic.numChecks();
        assertEquals(1, advice.size());
    }

    @Test
    void numChecksDeluxe() {
        Advice testDeluxe = new Advice (3, fakeDB);
        testDeluxe.numChecks();
        assertEquals(0, advice.size());
        Transaction checksForDeluxe = new Transaction(3, fakeDB);
        for (int i = 0; i <= 30; i++) {
            checksForDeluxe.addTransaction(-1, "check", "store");
        }
        testDeluxe.numChecks();
        assertEquals(1, advice.size());
    }

    @Test
    void transactionFeeBasic() {
        Advice testBasic = new Advice(1, fakeDB);
        assertEquals(0, advice.size());
        Transaction addToBasicAdvices = new Transaction(1, fakeDB);
        addToBasicAdvices.addTransaction(-15, "debit", "ATM");
        assertEquals(0, advice.size());
    }

    @Test
    void transactionFeeStandard() {
        Advice testStandard = new Advice(2, fakeDB);
        assertEquals(0, advice.size());
        Transaction addToStandardAdvices = new Transaction(2, fakeDB);
        addToStandardAdvices.addTransaction(-15, "debit", "ATM");
        assertEquals(1, advice.size());
    }

    @Test
    void transactionFeeDeluxe() {
        Advice testDeluxe = new Advice(3, fakeDB);
        assertEquals(0, advice.size());
        Transaction addToDeluxeAdvices = new Transaction(3, fakeDB);
        addToDeluxeAdvices.addTransaction(-15, "debit", "ATM");
        assertEquals(0, advice.size());
    }

    @Test
    void transactionFeePremier() {
        Advice testPremier = new Advice(4, fakeDB);
        assertEquals(0, advice.size());
        Transaction addToPremierAdvices = new Transaction(4, fakeDB);
        addToPremierAdvices.addTransaction(-15, "debit", "ATM");
        assertEquals(0, advice.size());
    }

    @Test
    void interest() {
        Advice test = new Advice(1, fakeDB);
        assertEquals(100, fakeDB.getBalance(1));
        test.interest();
        assertEquals(100.30, fakeDB.getBalance(1));
    }
}