import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.*;

public class DatabaseBrowser extends JFrame {

  protected Connection connection;
  protected JComboBox catalogBox;
  protected JComboBox schemaBox;
  protected JComboBox tableBox;
  protected JTable table;

  public static void main(String[] args) throws Exception {
    new sun.jdbc.odbc.JdbcOdbcDriver();
    DatabaseBrowser db = new DatabaseBrowser();
  }

  public DatabaseBrowser() throws Exception {
    super("Database Browser");
    ConnectionDialog cd = new ConnectionDialog(this);
    connection = cd.getConnection();
    buildFrameLayout();
    setSize(600, 450);
    setVisible(true);
  }

  protected void buildFrameLayout() {
    Container pane = getContentPane();
    pane.add(getSelectionPanel(), BorderLayout.NORTH);
    table = new JTable();
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    refreshTable();
    pane.add(new JScrollPane(table), BorderLayout.CENTER);
    pane.add(getFrameButtonPanel(), BorderLayout.SOUTH);
  }

  protected JPanel getSelectionPanel() {
    JLabel label;
    JPanel panel = new JPanel();
    panel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridy = 0;
    gbc.insets = new Insets(5, 10, 5, 10);
    label = new JLabel("Catalog", JLabel.RIGHT);
    panel.add(label, gbc);
    label = new JLabel("Schema", JLabel.RIGHT);
    panel.add(label, gbc);
    label = new JLabel("Table", JLabel.RIGHT);
    panel.add(label, gbc);

    gbc.gridy = 1;
    catalogBox = new JComboBox();
    populateCatalogBox();
    panel.add(catalogBox, gbc);
    schemaBox = new JComboBox();
    populateSchemaBox();
    panel.add(schemaBox, gbc);
    tableBox = new JComboBox();
    populateTableBox();
    panel.add(tableBox, gbc);

    catalogBox.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent event) {
        String newCatalog = (String)(
            catalogBox.getSelectedItem());
        try {
          connection.setCatalog(newCatalog);
        } catch (Exception e) {};
        populateSchemaBox();
        populateTableBox();
        refreshTable();
      }
    });

    schemaBox.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent event) {
        populateTableBox();
        refreshTable();
      }
    });

    tableBox.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent event) {
        refreshTable();
      }
    });
    return panel;
  }

  protected void populateCatalogBox() {
    try {
      DatabaseMetaData dmd = connection.getMetaData();
      ResultSet rset = dmd.getCatalogs();
      Vector values = new Vector();
      while (rset.next()) {
        values.addElement(rset.getString(1));
      }
      rset.close();
      catalogBox.setModel(new DefaultComboBoxModel(values));
      catalogBox.setSelectedItem(connection.getCatalog());
      catalogBox.setEnabled(values.size() > 0);
    } catch (Exception e) {
      catalogBox.setEnabled(false);
    }
  }

  protected void populateSchemaBox() {
    try {
      DatabaseMetaData dmd = connection.getMetaData();
      ResultSet rset = dmd.getSchemas();
      Vector values = new Vector();
      while (rset.next()) {
        values.addElement(rset.getString(1));
      }
      rset.close();
      schemaBox.setModel(new DefaultComboBoxModel(values));
      schemaBox.setEnabled(values.size() > 0);
    } catch (Exception e) {
      schemaBox.setEnabled(false);
    }
  }

  protected void populateTableBox() {
    try {
      String[] types = {"TABLE"};
      String catalog = connection.getCatalog();
      String schema = (String)(schemaBox.getSelectedItem());
      DatabaseMetaData dmd = connection.getMetaData();
      ResultSet rset = dmd.getTables(catalog, schema, null,
          types);
      Vector values = new Vector();
      while (rset.next()) {
        values.addElement(rset.getString(3));
      }
      rset.close();
      tableBox.setModel(new DefaultComboBoxModel(values));
      tableBox.setEnabled(values.size() > 0);
    } catch (Exception e) {
      tableBox.setEnabled(false);
    }
  }

  protected JPanel getFrameButtonPanel() {
    JPanel panel = new JPanel();
    JButton button = new JButton("Exit");
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        System.exit(0);
      }
    });
    panel.add(button);
    return panel;
  }

  protected void refreshTable() {
    String catalog = (catalogBox.isEnabled() ?
        catalogBox.getSelectedItem().toString() :
        null);
    String schema = (schemaBox.isEnabled() ?
        schemaBox.getSelectedItem().toString() :
        null);
    String tableName = (String)tableBox.getSelectedItem();
    if (tableName == null) {
      table.setModel(new DefaultTableModel());
      return;
    }
    String selectTable = (schema == null ? "" : schema + ".") +
        tableName;
    if (selectTable.indexOf(' ') > 0) {
      selectTable = "\"" + selectTable + "\"";
    }
    try {
      Statement stmt = connection.createStatement();
      ResultSet rset = stmt.executeQuery("SELECT * FROM " +
          selectTable);
      table.setModel(new ResultSetTableModel(rset));
    } catch (Exception e) {};
  }

  class ConnectionDialog extends JDialog {

    protected JTextField useridField;
    protected JTextField passwordField;
    protected JTextField urlField;

    protected boolean canceled;
    protected Connection connect;

    public ConnectionDialog(JFrame f) {
      super(f, "Connect To Database", true);
      buildDialogLayout();
      setSize(300, 200);
    }

    public Connection getConnection() {
      setVisible(true);
      return connect;
    }

    protected void buildDialogLayout() {
      JLabel label;

      Container pane = getContentPane();
      pane.setLayout(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.anchor = GridBagConstraints.WEST;
      gbc.insets = new Insets(5, 10, 5, 10);

      gbc.gridx = 0;
      gbc.gridy = 0;
      label = new JLabel("Userid:", JLabel.LEFT);
      pane.add(label, gbc);

      gbc.gridy++;
      label = new JLabel("Password:", JLabel.LEFT);
      pane.add(label, gbc);

      gbc.gridy++;
      label = new JLabel("URL:", JLabel.LEFT);
      pane.add(label, gbc);

      gbc.gridx = 1;
      gbc.gridy = 0;

      useridField = new JTextField(10);
      pane.add(useridField, gbc);

      gbc.gridy++;
      passwordField = new JTextField(10);
      pane.add(passwordField, gbc);

      gbc.gridy++;
      urlField = new JTextField(15);
      pane.add(urlField, gbc);

      gbc.gridx = 0;
      gbc.gridy = 3;
      gbc.gridwidth = GridBagConstraints.REMAINDER;
      gbc.anchor = GridBagConstraints.CENTER;
      pane.add(getButtonPanel(), gbc);
    }

    protected JPanel getButtonPanel() {
      JPanel panel = new JPanel();
      JButton btn = new JButton("Ok");
      btn.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent event) {
          onDialogOk();
        }
      });
      panel.add(btn);
      btn = new JButton("Cancel");
      btn.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent event) {
          onDialogCancel();
        }
      });
      panel.add(btn);
      return panel;
    }

    protected void onDialogOk() {
      if (attemptConnection()) {
        setVisible(false);
      }
    }

    protected void onDialogCancel() {
      System.exit(0);
    }

    protected boolean attemptConnection() {
      try {
        connect = DriverManager.getConnection(
            urlField.getText(),
            useridField.getText(),
            passwordField.getText());
        return true;
      } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
            "Error connecting to " +
            "database: " + e.getMessage());
      }
      return false;
    }

  }

  class ResultSetTableModel extends AbstractTableModel {

    protected Vector columnHeaders;
    protected Vector tableData;

    public ResultSetTableModel(ResultSet rset)
        throws SQLException {
      Vector rowData;
      ResultSetMetaData rsmd = rset.getMetaData();
      int count = rsmd.getColumnCount();
      columnHeaders = new Vector(count);
      tableData = new Vector();
      for (int i = 1; i <= count; i++) {
        columnHeaders.addElement(rsmd.getColumnName(i));
      }
      while (rset.next()) {
        rowData = new Vector(count);
        for (int i = 1; i <= count; i++) {
          rowData.addElement(rset.getObject(i));
        }
        tableData.addElement(rowData);
      }
    }

    public int getColumnCount() {
      return columnHeaders.size();
    }

    public int getRowCount() {
      return tableData.size();
    }

    public Object getValueAt(int row, int column) {
      Vector rowData = (Vector)(tableData.elementAt(row));
      return rowData.elementAt(column);
    }

    public boolean isCellEditable(int row, int column) {
      return false;
    }

    public String getColumnName(int column) {
      return (String)(columnHeaders.elementAt(column));
    }

  }

}
