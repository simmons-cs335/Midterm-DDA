import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Katie Wind; fakeDB elements for testing added by Caelum Noonan
 */

public class DDADAO {

    private Connection connection;
    private ArrayList<String[]> fakeDBACCOUNTS;
    private ArrayList<String[]> fakeDBADVICE;
    private ArrayList<String[]> fakeDBCUSTOMERS;
    private ArrayList<String[]> fakeDBTRANSACTIONS;
    private boolean useRealDB = true;

    // Constructor initializes database connection.
    DDADAO(String user, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://dany.simmons.edu:3306/33501sp20_noonanc?useUnicode=yes&characterEncoding=UTF-8",
                    user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //alternate constructor for tests
    DDADAO(ArrayList<String[]> fakeDBACCOUNTS, ArrayList<String[]> fakeDBADVICE, ArrayList<String[]> fakeDBCUSTOMERS, ArrayList<String[]> fakeDBTRANSACTIONS) {
        this.fakeDBACCOUNTS = fakeDBACCOUNTS;
        this.fakeDBADVICE = fakeDBADVICE;
        this.fakeDBCUSTOMERS = fakeDBCUSTOMERS;
        this.fakeDBTRANSACTIONS = fakeDBTRANSACTIONS;
        useRealDB = false;
    }

    // get current balance for individual account
    public Double getBalance(int accountno) {
        if (useRealDB == true) {
            double amount = 0;
            try {
                Statement checkBalance = connection.createStatement();
                ResultSet rs = checkBalance.executeQuery(
                        "SELECT Balance FROM ACCOUNTS WHERE AccountNo = " + accountno + ";");
                rs.next();
                amount = rs.getInt("Balance");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return amount;
        } else {
           return Double.parseDouble(fakeDBACCOUNTS.get(accountno-1)[3]);
        }
    }

    //change balance for individual account with withdrawal for deposit
    public void updateBalance(int accountno, double amount) {
        if (useRealDB == true) {
            try {
                Statement updateBalance = connection.createStatement();
                updateBalance.execute(
                        "UPDATE ACCOUNTS SET Balance = " + amount + "WHERE AccountNo = " + accountno + ";");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            fakeDBACCOUNTS.get(accountno-1)[3] = Double.toString(amount);
        }
    }

    //return transaction type for individual transaction
    public String getTransactionType(int transactionno) {
        String type = "null";
        if (useRealDB == true) {
            try {
                Statement getTransactiontype = connection.createStatement();
                ResultSet rs = getTransactiontype.executeQuery(
                        "SELECT TransactionType FROM TRANSACTIONS WHERE TransactionNo = " + transactionno + ";");
                while (rs.next()) type = rs.getString("TransactionType");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return type;
        } else {
            return fakeDBTRANSACTIONS.get(transactionno)[3];
        }
    }

    //return transaction amount for individual transaction
    public int getTransactionAmount(int transactionno) {
        int transactionamount = 0;
        if (useRealDB == true) {
            try {
                Statement getTransactionamount = connection.createStatement();
                ResultSet rs = getTransactionamount.executeQuery(
                        "SELECT Amount FROM TRANSACTIONS WHERE TransactionNo = " + transactionno + ";");
                while (rs.next()) transactionamount = rs.getInt("Amount");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return transactionamount;
        } else {
            return Integer.parseInt(fakeDBTRANSACTIONS.get(transactionno)[2]);
        }
    }

    //return transaction date for individual transaction
    public String getTransactionDate(int transactionno) {
        String transactiondate = "null";
        if (useRealDB == true) {
            try {
                Statement getTransactiondate = connection.createStatement();
                ResultSet rs = getTransactiondate.executeQuery(
                        "SELECT DateofTransaction FROM TRANSACTIONS WHERE TransactionNo = " + transactionno + ";");
                while (rs.next()) {
                    transactiondate = rs.getString("DateOfTransaction");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return transactiondate;
        } else {
            return fakeDBTRANSACTIONS.get(transactionno)[4];
        }
    }

    //return transaction location for individual transaction
    public String getTransactionLocataion(int transactionno) {
        String transactionlocation = "null";
        if (useRealDB == true) {
            try {
                Statement getTransactionloc = connection.createStatement();
                ResultSet rs = getTransactionloc.executeQuery(
                        "SELECT Location FROM TRANSACTIONS WHERE TransactionNo = " + transactionno + ";");
                while (rs.next()) {
                    transactionlocation = rs.getString("Location");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return transactionlocation;
        } else {
            return fakeDBTRANSACTIONS.get(transactionno)[5];
        }
    }

    //return account number accessed from customer ID
    public int getAccountNo(int customerID) {
        int accountNum = 0;
        if (useRealDB == true) {
            try {
                Statement getAccountNum = connection.createStatement();
                ResultSet rs = getAccountNum.executeQuery(
                        "SELECT AccountNo FROM ACCOUNTS WHERE AccountNo = " + customerID + ";");
                rs.next();
                accountNum = rs.getInt("AccountNo");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return accountNum;
        } else {
            return Integer.parseInt(fakeDBACCOUNTS.get(customerID-1)[0]);
        }
    }

    //returns account type for an individual account
    public String getAccountType(int accountno) {
        String type = "null";
        if (useRealDB == true) {
            try {
                Statement getType = connection.createStatement();
                ResultSet rs = getType.executeQuery(
                        "SELECT AccountType FROM ACCOUNTS WHERE AccountNo = " + accountno + ";");
                rs.next();
                type = rs.getString("AccountType");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return type;
        } else {
            return fakeDBACCOUNTS.get(accountno-1)[2];
        }
    }

    //returns customer first name for an individual customer
    public String getCustomerFirst(int customerID) {
        String first = "null";
        if (useRealDB == true) {
            try {
                Statement getName = connection.createStatement();
                ResultSet rs = getName.executeQuery(
                        "SELECT FirstName FROM CUSTOMERS WHERE CustomerID = " + customerID + ";");
                rs.next();
                first = rs.getString("FirstName");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return first;
        } else {
            return fakeDBCUSTOMERS.get(customerID)[2];
        }
    }

    //returns customer last name for indivdual customer
    public String getCustomerLast(int customerID) {
        String last = "null";
        if (useRealDB == true) {
            try {
                Statement getName = connection.createStatement();
                ResultSet rs = getName.executeQuery(
                        "SELECT LastName FROM CUSTOMERS WHERE CustomerID = " + customerID + ";");
                rs.next();
                last = rs.getString("LastName");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return last;
        } else {
            return fakeDBCUSTOMERS.get(customerID)[1];
        }
    }

    //returns customer address for individual customer
    public String getCustomerAddress(int customerID) {
        String address = "null";
        if (useRealDB == true) {
            try {
                Statement getaddress = connection.createStatement();
                ResultSet rs = getaddress.executeQuery(
                        "SELECT Address FROM CUSTOMERS WHERE CustomerID = " + customerID + ";");
                rs.next();
                address = rs.getString("Address");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return address;
        } else {
            return fakeDBCUSTOMERS.get(customerID)[3];
        }
    }

    //adds transaction (with account num, amount, transactiontype, date, and location) to database
    public void addTransaction(int accountNo, double amount, String transactiontype, String dateofTran, String location ) {
        if (useRealDB == true) {
            try {
                Statement addTransaction = connection.createStatement();
                addTransaction.execute(
                        "INSERT into TRANSACTIONS " +
                                "(AccountNo, Amount, TransactionType, DateOfTransaction, Location)" +
                                " values (" + accountNo + ", " + amount + ", '" + transactiontype +
                                "', '" + dateofTran + "', '" + location + "')");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            int nextTransNum = fakeDBTRANSACTIONS.size()+1;
            String[] data = {Integer.toString(nextTransNum), Integer.toString(accountNo), Double.toString(amount), transactiontype, dateofTran, location};
            fakeDBTRANSACTIONS.add(data);
        }
    }

    //adds advice with  to account
    public void addAdvice(int accountNo, double amount, String adviceType, String dateofAdvice) {
        if (useRealDB == true) {
            try {
                Statement addAdvice = connection.createStatement();
                addAdvice.execute(
                        "INSERT into ADVICE " +
                                "(AccountNo, Amount, TypeOfAdvice, DateOfAdvice)" +
                                " values (" + accountNo + ", " + amount + ", '" + adviceType +
                                "', '" + dateofAdvice + "');");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            int nextAdviceNum = fakeDBADVICE.size()+1;
            String[] data = {Integer.toString(nextAdviceNum), Integer.toString(accountNo), Double.toString(amount), adviceType, dateofAdvice};
            fakeDBADVICE.add(data);
        }
    }
    //return list of transactions within specific time range for an individual account
    public ArrayList<String> getTransactionsStatement(int accountNo,  String start, String end) {
        ArrayList<String> transactions = new ArrayList<String>();
        if (useRealDB == true) {
            try {
                Statement getTransactions = connection.createStatement();
                ResultSet rs = getTransactions.executeQuery(
                        "SELECT Amount, TransactionType, DateOfTransaction, Location FROM TRANSACTIONS WHERE accountNo = " + accountNo + " and DateOfTransaction between '"
                                + start + "' and '" + end + "';");
                while (rs.next()) {
                    transactions.add(rs.getString("DateOfTransaction"));
                    transactions.add(rs.getString("Amount"));
                    transactions.add(rs.getString("TransactionType"));
                    transactions.add(rs.getString("Location"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            for (int i = 0; i < fakeDBTRANSACTIONS.size(); i++) {
                if ( fakeDBTRANSACTIONS.get(i)[1].contentEquals(Integer.toString(accountNo)) && fakeDBTRANSACTIONS.get(i)[4].compareTo(start) < 0 && fakeDBTRANSACTIONS.get(i)[4].compareTo(end) > 0 ) {
                    transactions.add(fakeDBTRANSACTIONS.get(i)[4]);
                    transactions.add(fakeDBTRANSACTIONS.get(i)[2]);
                    transactions.add(fakeDBTRANSACTIONS.get(i)[3]);
                    transactions.add(fakeDBTRANSACTIONS.get(i)[5]);
                }
            }
        }
        return transactions;
    }

    //returns list of advices within a specific time range for an individual account
    public ArrayList<String> getAdviceStatements(int accountNo,  String start, String end) {
        ArrayList<String> advices = new ArrayList<String>();
        if (useRealDB == true) {
            try {
                Statement getAdvices = connection.createStatement();
                ResultSet rs = getAdvices.executeQuery(
                        "SELECT AccountNo, Amount, TypeOfAdvice, DateOfAdvice FROM ADVICE WHERE accountNo = " + accountNo + " and DateOfAdvice between '"
                                + start + "' and '" + end + "';");
                while (rs.next()) {
                    advices.add(rs.getString("Amount"));
                    advices.add(rs.getString("TypeOfAdvice"));
                    advices.add(rs.getString("DateOfAdvice"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            for (int i = 0; i < fakeDBADVICE.size(); i++) {
                if ( fakeDBADVICE.get(i)[1].contentEquals(Integer.toString(accountNo)) && fakeDBADVICE.get(i)[4].compareTo(start) < 0 && fakeDBADVICE.get(i)[4].compareTo(end) > 0 ) {
                    advices.add(fakeDBTRANSACTIONS.get(i)[2]);
                    advices.add(fakeDBTRANSACTIONS.get(i)[3]);
                    advices.add(fakeDBTRANSACTIONS.get(i)[4]);
                }
            }
        }
        return advices;
    }

    //returns the number of checks used within a specific time range for an individual account
    public int countChecks(int accountno, String start, String end) {
        int checkCount = 0;
        if (useRealDB == true) {
            try {
                Statement checkCountNum = connection.createStatement();
                ResultSet rs = checkCountNum.executeQuery(
                        "SELECT count(TransactionNo) FROM TRANSACTIONS WHERE accountNo = " + accountno + " and TransactionType = 'check'" + " and DateOfTransaction between '"
                                + start + "' and '" + end + "';");
                rs.next();
                checkCount = rs.getInt("count(TransactionNo)");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            for(int i = 0; i < fakeDBTRANSACTIONS.size(); i++) {
                if (fakeDBTRANSACTIONS.get(i)[3].contains("check")) {
                    checkCount++;
                }
            }
        }
        return checkCount;
    }

}