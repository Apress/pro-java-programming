import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class SimpleTableTest extends JFrame {

  protected JTable table;
  protected SortedColumnHeaderRenderer renderer;

  public static void main(String[] args) {
    SimpleTableTest stt = new SimpleTableTest();
    stt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    stt.setSize(400, 200);
    stt.setVisible(true);
  } 

  public SimpleTableTest() {
    Container pane = getContentPane();
    pane.setLayout(new BorderLayout());
    TableValues tv = new TableValues();
    SortedTableModel stm = new SortedTableModel(tv);
//    stm.sortRows(TableValues.ACCOUNT_BALANCE, true);
    table = new JTable(stm);
    table.setRowSelectionAllowed(false);
    table.setColumnSelectionAllowed(true);
    TableColumnModel tcm = table.getColumnModel();
    TableColumn tc = tcm.getColumn(TableValues.GENDER);
    tc.setCellRenderer(new GenderRenderer());
    tc.setCellEditor(new GenderEditor());
    MultiLineHeaderRenderer mlhr = new MultiLineHeaderRenderer();
//    tc = tcm.getColumn(TableValues.ACCOUNT_BALANCE);
//    tc.setHeaderRenderer(mlhr);
    renderer = new SortedColumnHeaderRenderer(stm, mlhr);
    int count = tcm.getColumnCount();
    for (int i = 0; i < count; i++) {
      tc = tcm.getColumn(i);
      tc.setHeaderRenderer(renderer);
    }
    JTableHeaderToolTips jthtt =
        new JTableHeaderToolTips(table.getColumnModel());
    jthtt.setToolTips(new String[] {"Customer's First Name",
        "Customer's Last Name", "Customer's Date of Birth",
        "Customer's Account Balance", "Customer's Gender"});
    table.setTableHeader(jthtt);
    table.setDefaultRenderer(Float.class, new CurrencyRenderer());
    JScrollPane jsp = new JScrollPane(table);
    pane.add(jsp, BorderLayout.CENTER);
    addHeaderListener();
  }

  public void addHeaderListener() {
    table.getTableHeader().addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent event) {
        JTableHeader header = (JTableHeader)(event.getSource());
        int index = header.columnAtPoint(event.getPoint());
        Class dataType = table.getModel().getColumnClass(index);
        Class[] interfaces = dataType.getInterfaces();
        for (int i = 0; i < interfaces.length; i++) {
          if (interfaces[i].equals(java.lang.Comparable.class)) {
            renderer.columnSelected(index);
            break;
          }
        }
        table.setColumnSelectionInterval(index, index);
      }
    });
  }

}
