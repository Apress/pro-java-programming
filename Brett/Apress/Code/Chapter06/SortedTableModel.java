import javax.swing.table.*;
import java.util.*;

public class SortedTableModel extends AbstractTableModel {

  protected TableModel sourceModel;
  protected int[] indexValues;

  public SortedTableModel(TableModel model) {
    super();
    sourceModel = model;
  }

  public int getRowCount() {
    return sourceModel.getRowCount();
  } 

  public int getColumnCount() {
    return sourceModel.getColumnCount();
  } 

  public Object getValueAt(int row, int column) {
    if (indexValues != null) {
      row = getSourceIndex(row);
    } 
    return sourceModel.getValueAt(row, column);
  } 

  public void setValueAt(Object value, int row, int column) {
    if (indexValues != null) {
      row = getSourceIndex(row);
    } 
    sourceModel.setValueAt(value, row, column);
  } 

  public boolean isCellEditable(int row, int column) {
    return sourceModel.isCellEditable(row, column);
  } 

  public String getColumnName(int column) {
    return sourceModel.getColumnName(column);
  } 

  public Class getColumnClass(int column) {
    return sourceModel.getColumnClass(column);
  } 

  public int getSourceIndex(int index) {
    if (indexValues != null) {
      return indexValues[index];
    } 
    return -1;
  } 

  public void sortRows(int column, boolean ascending) {
    SortedItemHolder holder;
    TreeSet sortedList = new TreeSet();
    int count = getRowCount();
    for (int i = 0; i < count; i++) {
      holder = new SortedItemHolder(sourceModel.getValueAt(i, column), i);
      sortedList.add(holder);
    } 
    indexValues = new int[count];
    Iterator iterator = sortedList.iterator();
    int index = (ascending ? 0 : count - 1);
    while (iterator.hasNext()) {
      holder = (SortedItemHolder)(iterator.next());
      indexValues[index] = holder.position;
      index += (ascending ? 1 : -1);
    } 
    refreshViews();
  } 

  public void clearSort() {
    indexValues = null;
    refreshViews();
  } 

  public void refreshViews() {
    fireTableDataChanged();
  } 

  class SortedItemHolder implements Comparable {

    public final Object value;
    public final int position;

    public SortedItemHolder(Object value, int position) {
      this.value = value;
      this.position = position;
    }

    public int compareTo(Object parm) {
      SortedItemHolder holder = (SortedItemHolder)parm;
      Comparable comp = (Comparable)value;
      int result = comp.compareTo(holder.value);
      if (result == 0) {
        result = (position < holder.position) ? -1 : 1;
      } 
      return result;
    } 

    public int hashCode() {
      return position;
    } 

    public boolean equals(Object comp) {
      if (comp instanceof SortedItemHolder) {
        SortedItemHolder other = (SortedItemHolder)comp;
        if ((position == other.position) && (value == other.value)) {
          return true;
        } 
      } 
      return false;
    } 

  }

}