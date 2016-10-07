import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class FontPropertiesPanel extends JPanel {

  protected JList nameList;
  protected JComboBox sizeBox;
  protected JCheckBox boldBox;
  protected JCheckBox italicBox;

//  protected SampleTextFrame frame;
  protected FontListener listener;

  public final static int[] fontSizes = {10, 12, 14, 18, 24, 32, 48, 64};

  public FontPropertiesPanel() {
    super();
    createComponents();
    buildLayout();
  }

  protected void buildLayout() {
    JLabel label;
    GridBagConstraints gbc = new GridBagConstraints();
    GridBagLayout gbl = new GridBagLayout();
    setLayout(gbl);

    gbc.anchor = GridBagConstraints.WEST;
    gbc.insets = new Insets(5, 10, 5, 10);

    gbc.gridx = 0;
    label = new JLabel("Name:", JLabel.LEFT);
    gbl.setConstraints(label, gbc);
    add(label);
    label = new JLabel("Size:", JLabel.LEFT);
    gbl.setConstraints(label, gbc);
    add(label);
    gbl.setConstraints(boldBox, gbc);
    add(boldBox);

    gbc.gridx++;
    nameList.setVisibleRowCount(3);
    JScrollPane jsp = new JScrollPane(nameList);
    gbl.setConstraints(jsp, gbc);
    add(jsp);
    gbl.setConstraints(sizeBox, gbc);
    add(sizeBox);
    gbl.setConstraints(italicBox, gbc);
    add(italicBox);
  }

  protected void createComponents() {
    GraphicsEnvironment ge =
        GraphicsEnvironment.getLocalGraphicsEnvironment();
    String[] names = ge.getAvailableFontFamilyNames();
    nameList = new JList(names);
    nameList.setSelectedIndex(0);
    nameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    nameList.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent event) {
        handleFontPropertyChange();
      }
    }
    );
    Integer sizes[] = new Integer[fontSizes.length];
    for (int i = 0; i < sizes.length; i++) {
      sizes[i] = new Integer(fontSizes[i]);
    }
    sizeBox = new JComboBox(sizes);
    sizeBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        handleFontPropertyChange();
      }
    }
    );
    boldBox = new JCheckBox("Bold");
    boldBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        handleFontPropertyChange();
      }
    }
    );
    italicBox = new JCheckBox("Italic");
    italicBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        handleFontPropertyChange();
      }
    }
    );
  }

  public void setFontListener(FontListener fl) {
    listener = fl;
  }

  protected void handleFontPropertyChange() {
    listener.fontChanged(getSelectedFont());
  }

  public Font getSelectedFont() {
    String name = (String)(nameList.getSelectedValue());
    int style = 0;
    style += (boldBox.isSelected() ? Font.BOLD : 0);
    style += (italicBox.isSelected() ? Font.ITALIC : 0);
    int size = ((Integer)(sizeBox.getSelectedItem())).intValue();
    return new Font(name, style, size);
  }

//  public String getSelectedFontName() {
//    return (String)(nameList.getSelectedValue());
//  }

//  public int getSelectedFontSize() {
//    return ((Integer)(sizeBox.getSelectedItem())).intValue();
//  }

//  public boolean isBoldSelected() {
//    return boldBox.isSelected();
//  }

//  public boolean isItalicSelected() {
//    return italicBox.isSelected();
//  }

}