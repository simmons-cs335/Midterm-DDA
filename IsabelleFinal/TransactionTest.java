import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {
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

        String[] accountString = {"1", "1", "basic", "0"};
        fakeDBACCOUNTS.add(accountString);

        fakedb = new DDADAO(fakeDBACCOUNTS, fakeDBADVICE, fakeDBCUSTOMERS, fakeDBTRANSACTIONS);
        transaction = new Transaction(1, fakedb);
    }

    @Test
    void addTransaction() {
        //Test before adding transaction
        assertEquals(0, fakedb.getBalance(1));
        assertEquals(0, fakeDBTRANSACTIONS.size());

        //Add transaction
        transaction.addTransaction(10, "deposit", "bank");

        //Test after adding transaction
        assertEquals(10, fakedb.getBalance(1));
        assertEquals(1, fakeDBTRANSACTIONS.size());

    }

    @Test
    void updateBalance() {
        //Check before
        assertEquals(0, fakedb.getBalance(1));

        transaction.updateBalance(15);

        //Check after
        assertEquals(15, fakedb.getBalance(1));
    }

    @Test
    void checkOverdraft() {
        //check for no overdraft
        transaction.checkOverdraft();
        assertEquals(0, fakeDBADVICE.size());

        //check it adds an advice after an overdraft transaction
        transaction.addTransaction(-20, "check", "grocery store");
        assertEquals(1, fakeDBADVICE.size());
        assertEquals(-35, fakedb.getBalance(1)); //-20 + (-15) transaction fee
    }
}