import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

public class DateViewer extends JPanel {
  protected JTextField valueField;
  protected JRadioButton shortButton;
  protected JRadioButton mediumButton;
  protected JRadioButton longButton;
  protected JRadioButton fullButton;

  protected AbstractTableModel tableModel;

  protected Date selectedDate = new Date();

  protected final static Locale[] availableLocales;

  static {
    availableLocales = Locale.getAvailableLocales();
  }

  public final static int LOCALE_COLUMN = 0;
  public final static int SHORT_COLUMN = 1;
  public final static int MEDIUM_COLUMN = 2;
  public final static int LONG_COLUMN = 3;
  public final static int FULL_COLUMN = 4;

  public final static String[] columnHeaders = 
               {"Locale", "Short", "Medium", "Long", "Full"};

  // Create the window for the Date viewer,
  // and make it fit the later components
  public static void main(String[] args) {
    JFrame f = new JFrame("Date Viewer");
    f.getContentPane().add(new DateViewer());
    f.pack();
    f.setVisible(true);
  }

  // Make the window contain a panel
  // and a table, which are defined next
  public DateViewer() {
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

  // Create a panel with buttons to select date format
  protected JPanel getSelectionPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 10, 5, 10);
    gbc.gridy = 0;
    JLabel label = new JLabel("Selected date:", JLabel.LEFT);
    panel.add(label, gbc);
    valueField = new JTextField(20);
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
    innerPanel.setLayout(new GridLayout(1, 4, 10, 0));

    shortButton = new JRadioButton("Short", true);
    innerPanel.add(shortButton);

    mediumButton = new JRadioButton("Medium");
    innerPanel.add(mediumButton);

    longButton = new JRadioButton("Long");
    innerPanel.add(longButton);

    fullButton = new JRadioButton("Full");
    innerPanel.add(fullButton);

    ButtonGroup bg = new ButtonGroup();
    bg.add(shortButton);
    bg.add(mediumButton);
    bg.add(longButton);
    bg.add(fullButton);

    gbc.gridwidth = GridBagConstraints.REMAINDER;
    panel.add(innerPanel, gbc);

    BevelBorder bb = new BevelBorder(BevelBorder.RAISED);
    Locale locale = Locale.getDefault();
    TitledBorder tb = new TitledBorder(bb, locale.getDisplayName());
    panel.setBorder(tb);

    return panel;
  }

  protected void refreshTable() {
    int style = DateFormat.SHORT;

    // Work out which button is selected
    if (shortButton.isSelected()) {
      style = DateFormat.SHORT;
    }
    else if (mediumButton.isSelected()) {
      style = DateFormat.MEDIUM;
    }
    else if (longButton.isSelected()) {
      style = DateFormat.LONG;
    }
    else if (fullButton.isSelected()) {
      style = DateFormat.FULL;
    }
    // and format the date in the value field
    DateFormat parser = DateFormat.getDateInstance(style);
    try {
      selectedDate = parser.parse(valueField.getText());
      tableModel.fireTableDataChanged();
    }
    catch (ParseException nfe) {
      valueField.setText(parser.format(selectedDate));
    }
  }

  // Create a table of international date formats
  class LocaleTableModel extends AbstractTableModel {
    public int getRowCount() {
      return availableLocales.length;
    }

    public int getColumnCount() {
      return columnHeaders.length;
    }

    public Object getValueAt(int row, int column) {
      Locale locale = availableLocales[row];
      DateFormat formatter = DateFormat.getInstance();

      //For each column in the table, get the date in the right format
      switch (column) {
        case LOCALE_COLUMN:
          return locale.getDisplayName();
        case SHORT_COLUMN:
          formatter = DateFormat.getDateInstance(DateFormat.SHORT, locale);
          break;
        case MEDIUM_COLUMN:
          formatter = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
          break;
        case LONG_COLUMN:
          formatter = DateFormat.getDateInstance(DateFormat.LONG, locale);
          break;
        case FULL_COLUMN:
          formatter = DateFormat.getDateInstance(DateFormat.FULL, locale);
      }
      return formatter.format(selectedDate);
    }

    public String getColumnName(int column) {
      return columnHeaders[column];
    }
  }
}