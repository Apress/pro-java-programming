import java.io.PrintWriter;
import java.sql.*;
import javax.sql.*;

public class OracleDataSource implements DataSource {

  static {
    new oracle.jdbc.driver.OracleDriver();
  }

  protected boolean usingThinDriver;

  protected String description = "Oracle Data Source";
  protected String serverName;
  protected int portNumber;
  protected String databaseName;

  public OracleDataSource(String host, int port, String sid) {
    setServerName(host);
    setPortNumber(port);
    setDatabaseName(sid);
    usingThinDriver = true;
  }

  public OracleDataSource(String sid) {
    setDatabaseName(sid);
    usingThinDriver = false;
  }

  public boolean isUsingThinDriver() {
    return usingThinDriver;
  }

  public void setUsingThinDriver(boolean thin) {
    usingThinDriver = thin;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String desc) {
    description = desc;
  }

  public String getServerName() {
    return serverName;
  }

  public void setServerName(String name) {
    serverName = name;
  }

  public int getPortNumber() {
    return portNumber;
  }

  public void setPortNumber(int port) {
    portNumber = port;
  }

  public String getDatabaseName() {
    return databaseName;
  }

  public void setDatabaseName(String name) {
    databaseName = name;
  }

  public Connection getConnection() throws SQLException{
    return getConnection(null, null);
  }

  public Connection getConnection(String userid, String password)
      throws SQLException {
    String url = "jdbc:oracle:" + getSubname();
    return DriverManager.getConnection(url, userid, password);
  }

  protected String getSubname() {
    return (isUsingThinDriver() 
            ? "thin:@" + getServerName() + ":" + getPortNumber() + ":" +
              getDatabaseName() 
            : "oci8:@" + getDatabaseName());
  }

  public int getLoginTimeout() throws SQLException {
    return DriverManager.getLoginTimeout();
  }

  public PrintWriter getLogWriter() throws SQLException {
    return DriverManager.getLogWriter();
  }

  public void setLoginTimeout(int timeout) throws SQLException {
    DriverManager.setLoginTimeout(timeout);
  }

  public void setLogWriter(PrintWriter writer) throws SQLException {
    DriverManager.setLogWriter(writer);
  }

}
