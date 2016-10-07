import java.awt.*;
import javax.swing.*;

public class Remainder {

  public static void main(String[] args) {
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Container pane = f.getContentPane();
    pane.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    pane.add(new JButton("First row, first column"), gbc);
    pane.add(new JButton("First row, second column"), gbc);
    pane.add(new JButton("First row, third column"), gbc);
    gbc.gridx = 0;
    pane.add(new JButton("Second row"), gbc);
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    pane.add(new JButton(
      "Third row, gridwidth set to REMAINDER"), gbc);
    f.setSize(600, 300);
    f.setVisible(true);
  }

}
