import java.sql.*;

public class TransTest {

  protected String url = "jdbc:odbc:banktest";
  protected String userid = "bspell";
  protected String password = "brett";

  public void transferFunds(float transferAmount, int accountNumber,
      String fromTable, String toTable) throws SQLException,
      InvalidTransferException {
    Statement stmt = null;
    ResultSet rset = null;
    Connection conn = DriverManager.getConnection(url, userid,
        password);
    conn.setAutoCommit(false);
    try {
      stmt = conn.createStatement();
      rset = stmt.executeQuery("SELECT BALANCE FROM " + fromTable +
          " WHERE ACCOUNTID = " + accountNumber);
      rset.next();
      float fromBalance = rset.getFloat(1);
      if (fromBalance < transferAmount) {
        throw new InvalidTransferException("Insufficient funds available");
      }
      rset.close();
      rset = stmt.executeQuery("SELECT BALANCE FROM " + toTable +
          " WHERE ACCOUNTID = " + accountNumber);
      rset.next();
      float toBalance = rset.getFloat(1);
      fromBalance -= transferAmount;
      toBalance += transferAmount;
      stmt.executeUpdate("UPDATE " + fromTable + " SET BALANCE = " +
          fromBalance + " WHERE ACCOUNTID = " + accountNumber);
      stmt.executeUpdate("UPDATE " + toTable + " SET BALANCE = " +
          toBalance + " WHERE ACCOUNTID = " + accountNumber);
      conn.commit();
    } catch (SQLException sqle) {
      conn.rollback();
      throw sqle;
    } finally {
      if (rset != null) rset.close();
      if (stmt != null) stmt.close();
      conn.close();
    }
  }

  class InvalidTransferException extends Exception {

    public InvalidTransferException(String message) {
      super(message);
    }

  }

}
