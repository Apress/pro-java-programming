import java.awt.*;
import javax.swing.*;

public class ColumnSpan {

  public static void main(String[] args) {
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Container pane = f.getContentPane();
    pane.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 1;
    gbc.gridy = GridBagConstraints.RELATIVE;
    pane.add(new JButton("First row, first column"), gbc);
    pane.add(new JButton("Second row"), gbc);
    gbc.gridwidth = 2;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    pane.add(new JButton("Third row, spans two columns"), gbc);
    gbc.gridwidth = 1;
    gbc.fill = GridBagConstraints.NONE;
    gbc.gridx = GridBagConstraints.RELATIVE;
    pane.add(new JButton("First row, second column"), gbc);
    f.setSize(400, 300);
    f.setVisible(true);
  }

}
