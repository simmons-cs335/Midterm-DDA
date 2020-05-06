import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AdviceTest {
    DDADAO fakedb;
    ArrayList<String[]> fakeDBTRANSACTIONS;
    Transaction transaction;
    ArrayList<String[]> fakeDBADVICE;

    @BeforeEach
    void setUp() {
        ArrayList<String[]> fakeDBACCOUNTS = new ArrayList<String[]>();
        fakeDBADVICE = new ArrayList<String[]>();
        ArrayList<String[]> fakeDBCUSTOMERS = new ArrayList<String[]>();
        fakeDBTRANSACTIONS = new ArrayList<String[]>();

        String[] accountString1 = {"1", "1", "basic", "0"};
        fakeDBACCOUNTS.add(accountString1);
        String[] accountString2 = {"2", "2", "standard", "0"};
        fakeDBACCOUNTS.add(accountString2);

        fakedb = new DDADAO(fakeDBACCOUNTS, fakeDBADVICE, fakeDBCUSTOMERS, fakeDBTRANSACTIONS);
    }

    @Test
    void overdraftFee() {
        //check to make sure there is no advice or fee beforehand
        assertEquals(0, fakedb.getBalance(1));
        assertEquals(0, fakeDBADVICE.size());

        transaction = new Transaction(1,fakedb);
        transaction.addTransaction(-5, "check", "rent");

        //check to make sure there's an advice afterwards
        assertEquals(-20, fakedb.getBalance(1)); //-5 (transaction) + -15 overdraft fee
        assertEquals(1, fakeDBADVICE.size());
    }

    @Test
    void transactionFee() {
        //check before
        assertEquals(0, fakedb.getBalance(2));
        assertEquals(0, fakeDBADVICE.size());

        transaction = new Transaction(2,fakedb);
        transaction.addTransaction(20, "deposit", "ATM");

        //check after
        assertEquals(19, fakedb.getBalance(2));
        assertEquals(1, fakeDBADVICE.size());
    }
}