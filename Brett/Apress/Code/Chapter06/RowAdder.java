import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.util.Vector;

public class RowAdder extends JFrame {

  protected SimpleModel tableData;
  protected JTable table;
  protected JTextField textField;

  public static void main(String[] args) {
    RowAdder ra = new RowAdder();
    ra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    ra.setSize(400, 300);
    ra.setVisible(true);
  }

  public RowAdder() {
    Container pane = getContentPane();
    pane.setLayout(new BorderLayout());
    tableData = new SimpleModel();
    table = new JTable(tableData);
    table.getColumnModel().getColumn(0).setPreferredWidth(300);
    table.addComponentListener(new TableScroller());
    JScrollPane jsp = new JScrollPane(table);
    pane.add(jsp, BorderLayout.CENTER);
    textField = new JTextField();
    textField.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        addLineToTable();
      }
    });
    pane.add(textField, BorderLayout.SOUTH);
  }

  protected void addLineToTable() {
    tableData.addText(textField.getText());
    textField.setText("");
  }

  class SimpleModel extends AbstractTableModel {

    protected Vector textData = new Vector();

    public void addText(String text) {
      textData.addElement(text);
      fireTableDataChanged();
    }

    public int getRowCount() {
      return textData.size();
    }


    public int getColumnCount() {
      return 1;
    }

    public Object getValueAt(int row, int column) {
      return textData.elementAt(row);
    }

  }

  class TableScroller extends ComponentAdapter {

    public void componentResized(ComponentEvent event) {
      int lastRow = tableData.getRowCount() - 1;
      int cellTop = table.getCellRect(lastRow, 0, true).y;
      JScrollPane jsp = (JScrollPane)SwingUtilities.getAncestorOfClass(
          JScrollPane.class, table);
      JViewport jvp = jsp.getViewport();
      int portHeight = jvp.getSize().height;
      int position = cellTop - (portHeight - table.getRowHeight() -
          table.getRowMargin());
      if (position >= 0) {
        jvp.setViewPosition(new Point(0, position));
      }
    }
  }
}