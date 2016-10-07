import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

public class NumberViewer extends JPanel {

  protected JTextField valueField;
  protected JRadioButton numberButton;
  protected JRadioButton currencyButton;
  protected JRadioButton percentButton;

  protected AbstractTableModel tableModel;

  protected double currentValue = 1234567.89d;

  protected final static Locale[] availableLocales;

  static {
    availableLocales = Locale.getAvailableLocales();
  }

  public final static int LOCALE_COLUMN = 0;
  public final static int NUMBER_COLUMN = 1;
  public final static int CURRENCY_COLUMN = 2;
  public final static int PERCENT_COLUMN = 3;

  public final static String[] columnHeaders = 
        {"Locale", "Numeric", "Currency", "Percent"};

  // Create a window for the number viewer
  // and make sure that later components will fit
  public static void main(String[] args) {
    JFrame f = new JFrame("Number Viewer");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.getContentPane().add(new NumberViewer());
    f.pack();
    f.setVisible(true);
  }

  // Make the window contain a panel
  // and a table, which are defined next
  public NumberViewer() {
    setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.insets = new Insets(5, 10, 5, 10);

    JPanel panel = getSelectionPanel();
    add(panel, gbc);

    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.fill = GridBagConstraints.BOTH;
    tableModel = new LocaleTableModel();
    JTable table = new JTable(tableModel);
    add(new JScrollPane(table), gbc);

    refreshTable();
  }

  // Create a panel with buttons to select number format
  protected JPanel getSelectionPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 10, 5, 10);
    gbc.gridy = 0;
    JLabel label = new JLabel("Current value:", JLabel.LEFT);
    panel.add(label, gbc);
    valueField = new JTextField(12);
    valueField.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        refreshTable();
      }
    });
    valueField.setMinimumSize(valueField.getPreferredSize());
    panel.add(valueField, gbc);
    JButton btn = new JButton("Refresh");
    btn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        refreshTable();
      }
    });
    panel.add(btn, gbc);

    // Add the format buttons to the panel
    gbc.gridy++;
    JPanel innerPanel = new JPanel();
    innerPanel.setLayout(new GridLayout(1, 3, 10, 0));

    numberButton = new JRadioButton("Numeric", true);
    innerPanel.add(numberButton);

    currencyButton = new JRadioButton("Currency");
    innerPanel.add(currencyButton);

    percentButton = new JRadioButton("Percent");
    innerPanel.add(percentButton);

    ButtonGroup bg = new ButtonGroup();
    bg.add(numberButton);
    bg.add(currencyButton);
    bg.add(percentButton);

    gbc.gridwidth = GridBagConstraints.REMAINDER;
    panel.add(innerPanel, gbc);

    BevelBorder bb = new BevelBorder(BevelBorder.RAISED);
    Locale locale = Locale.getDefault();
    TitledBorder tb = new TitledBorder(bb, locale.getDisplayName());
    panel.setBorder(tb);

    return panel;
  }

  protected void refreshTable() {
    NumberFormat parser = NumberFormat.getInstance();

    // Work out which button is selected
    if (numberButton.isSelected()) {
      parser = NumberFormat.getNumberInstance();
    }
    else if (currencyButton.isSelected()) {
      parser = NumberFormat.getCurrencyInstance();
    }
    else if (percentButton.isSelected()) {
      parser = NumberFormat.getPercentInstance();
    }
    // and format the number in the value field
    try {
      currentValue = parser.parse(valueField.getText()).doubleValue();
      tableModel.fireTableDataChanged();
    }
    catch (ParseException nfe) {
      valueField.setText(parser.format(currentValue));
    }
  }

  // Create a table of international number formats
  class LocaleTableModel extends AbstractTableModel {
    public int getRowCount() {
      return availableLocales.length;
    }

    public int getColumnCount() {
      return columnHeaders.length;
    }

    public Object getValueAt(int row, int column) {
      Locale locale = availableLocales[row];
      NumberFormat formatter = NumberFormat.getNumberInstance();
      // For each column in the table, get the number in the right format
      switch (column) {
        case LOCALE_COLUMN:
          return locale.getDisplayName();
        case NUMBER_COLUMN:
          formatter = NumberFormat.getNumberInstance(locale);
          break;
        case CURRENCY_COLUMN:
          formatter = NumberFormat.getCurrencyInstance(locale);
          break;
        case PERCENT_COLUMN:
          formatter = NumberFormat.getPercentInstance(locale);
          break;
      }
      return formatter.format(currentValue);
    }

    public String getColumnName(int column) {
      return columnHeaders[column];
    }
  }
}