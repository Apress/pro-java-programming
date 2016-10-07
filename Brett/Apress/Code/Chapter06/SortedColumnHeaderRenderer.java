import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.plaf.basic.BasicArrowButton;

public class SortedColumnHeaderRenderer implements TableCellRenderer {

  protected TableCellRenderer textRenderer;
  protected SortedTableModel sortedModel;
  protected int sortColumn = -1;
  protected boolean sortAscending = true;

  public SortedColumnHeaderRenderer(SortedTableModel model,
      TableCellRenderer renderer) {
    sortedModel = model;
    textRenderer = renderer;
  }

  public SortedColumnHeaderRenderer(SortedTableModel model) {
    this(model, null);
  }

  public Component getTableCellRendererComponent(JTable table, Object value,
      boolean isSelected, boolean hasFocus, int row, int column) {
    Component text;
    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());

    if (textRenderer != null) {
      text = textRenderer.getTableCellRendererComponent(table,
          value, isSelected, hasFocus, row, column);
    }
    else {
      text = new JLabel((String)value, JLabel.CENTER);
      LookAndFeel.installColorsAndFont((JComponent)text,
          "TableHeader.background",
          "TableHeader.foreground",
          "TableHeader.font");
    }
    panel.add(text, BorderLayout.CENTER);

    if (column == sortColumn) {
      BasicArrowButton bab = new BasicArrowButton((sortAscending ?
          SwingConstants.NORTH : SwingConstants.SOUTH));
      panel.add(bab, BorderLayout.WEST);
    }
    LookAndFeel.installBorder(panel, "TableHeader.cellBorder");
    return panel;
  }

  public void columnSelected(int column) {
    if (column != sortColumn) {
      sortColumn = column;
      sortAscending = true;
    }
    else {
      sortAscending = !sortAscending;
      if (sortAscending) sortColumn = -1;
    }
    if (sortColumn != -1) {
      sortedModel.sortRows(sortColumn, sortAscending);
    }
    else {
      sortedModel.clearSort();
    }
  }

}
