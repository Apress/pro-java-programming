import java.awt.*;
import java.util.StringTokenizer;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

public class MultiLineHeaderRenderer extends JPanel implements TableCellRenderer {

  public Component getTableCellRendererComponent(JTable table, 
          Object value, boolean isSelected, boolean hasFocus, 
          int row, int column) {
    JLabel label;
    removeAll();
    StringTokenizer strtok = new StringTokenizer((String)value, "\r\n");
    setLayout(new GridLayout(strtok.countTokens(), 1));
    while (strtok.hasMoreElements()) {
      label = new JLabel((String)strtok.nextElement(), JLabel.CENTER);
      LookAndFeel.installColorsAndFont(label, 
                                       "TableHeader.background", 
                                       "TableHeader.foreground", 
                                       "TableHeader.font");
      add(label);
    } 
    LookAndFeel.installBorder(this, "TableHeader.cellBorder");
    return this;
  } 

}
