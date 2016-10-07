import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

public class SelectorPanel extends JPanel {

  protected SelectorListModel unselectedModel;
  protected SelectorListModel selectedModel;
  protected JList unselectedList;
  protected JList selectedList;

  protected JButton addSelections;
  protected JButton addAll;
  protected JButton removeSelections;
  protected JButton removeAll;

  public SelectorPanel(Object[] values, int[] selections) {
    super();
    unselectedModel = new SelectorListModel(values);
    selectedModel = new SelectorListModel(null);
    if (selections != null) {
      selectedModel.takeEntriesFrom(unselectedModel, selections);
    }
    buildLayout();
  }

  public SelectorPanel(Object[] values) {
    this(values, null);
  }

  protected void buildLayout() {
    SelectionListener listener = new SelectionListener();
    setLayout(new DividerLayout());
    unselectedList = new JList(unselectedModel);
    unselectedList.addListSelectionListener(listener);
    add(new JScrollPane(unselectedList), DividerLayout.WEST);
    add(getButtonPanel(), DividerLayout.CENTER);
    selectedList = new JList(selectedModel);
    selectedList.addListSelectionListener(listener);
    add(new JScrollPane(selectedList), DividerLayout.EAST);

    addButtonListeners();
    enableButtons();
  }

  protected Component getButtonPanel() {
    JPanel innerPanel = new JPanel();
    innerPanel.setLayout(new GridLayout(4, 1, 0, 5));
    addSelections = new JButton(">");
    innerPanel.add(addSelections);
    addAll = new JButton(">>");
    innerPanel.add(addAll);
    removeSelections = new JButton("<");
    innerPanel.add(removeSelections);
    removeAll = new JButton("<<");
    innerPanel.add(removeAll);

    JPanel outerPanel = new JPanel();
    outerPanel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(0, 10, 0, 10);
    outerPanel.add(innerPanel, gbc);

    return outerPanel;
  }

  protected void addButtonListeners() {
    addSelections.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        addSelectedItems();
      }
    });
    addAll.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        addAllItems();
      }
    });
    removeSelections.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        removeSelectedItems();
      }
    });
    removeAll.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        removeAllItems();
      }
    });
  }

  protected void addSelectedItems() {
    int[] selections = unselectedList.getSelectedIndices();
    selectedModel.takeEntriesFrom(unselectedModel, selections);
    enableButtons();
  }

  protected void addAllItems() {
    selectedModel.takeAllEntriesFrom(unselectedModel);
    enableButtons();
  }

  protected void removeSelectedItems() {
    int[] selections = selectedList.getSelectedIndices();
    unselectedModel.takeEntriesFrom(selectedModel, selections);
    enableButtons();
  }

  protected void removeAllItems() {
    unselectedModel.takeAllEntriesFrom(selectedModel);
    enableButtons();
  }

  protected void enableButtons() {
    ListModel model;
    int[] selections;

    selections = unselectedList.getSelectedIndices();
    addSelections.setEnabled(selections.length > 0);
    model = unselectedList.getModel();
    addAll.setEnabled(model.getSize() > 0);

    selections = selectedList.getSelectedIndices();
    removeSelections.setEnabled(selections.length > 0);
    model = selectedList.getModel();
    removeAll.setEnabled(model.getSize() > 0);
  }

  public Object[] getSelectedValues() {
    return selectedModel.getValues();
  }

  public Object[] getUnselectedValues() {
    return unselectedModel.getValues();
  }

  class SelectorListModel extends AbstractListModel {

    protected TreeMap map;

    public SelectorListModel(Object[] values) {
      map = new TreeMap();
      if (values != null) {
        for (int i = 0; i < values.length; i++) {
          map.put(new Integer(i), values[i]);
        }
      }
    }

    public Object getElementAt(int index) {
      Set keys = map.keySet();
      return map.get(keys.toArray()[index]);
    }

    public int getSize() {
      return map.size();
    }

    public void takeEntriesFrom(SelectorListModel source, int[] rows) {
      Object key;
      Object[] keys = source.map.keySet().toArray();
      for (int i = 0; i < rows.length; i++) {
        key = keys[rows[i]];
        map.put(key, source.map.remove(key));
      }
      source.fireIntervalRemoved(source, 0, keys.length - 1);
      fireIntervalAdded(this, 0, getSize() - 1);
    }

    public void takeAllEntriesFrom(SelectorListModel source) {
      map.putAll(source.map);
      int count = source.getSize();
      source.map.clear();
      source.fireIntervalRemoved(source, 0, count - 1);
      fireIntervalAdded(this, 0, getSize() - 1);
    }

    public Object[] getValues() {
      return map.values().toArray();
    }

  }

  class SelectionListener implements ListSelectionListener {

    public void valueChanged(ListSelectionEvent event) {
      JList list = (JList)(event.getSource());
      int[] selections = list.getSelectedIndices();
      if (selections.length > 0) {
        int[] indices = {};
        list = (list == selectedList ? unselectedList :
            selectedList);
        list.setSelectedIndices(indices);
      }
      enableButtons ();
    }

  }

}