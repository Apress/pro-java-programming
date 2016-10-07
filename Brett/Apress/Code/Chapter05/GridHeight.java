import java.awt.*;
import javax.swing.*;

public class GridHeight {

  public static void main(String[] args) {
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Container pane = f.getContentPane();
    pane.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    pane.add(new JButton("First row, first column"), gbc);
    pane.add(new JButton("First row, second column"), gbc);
    gbc.gridheight = GridBagConstraints.REMAINDER;
    gbc.fill = GridBagConstraints.VERTICAL;
    pane.add(new JButton("First row, third column"), gbc);
    gbc.gridx = 0;
    gbc.gridheight = 1;
    gbc.fill = GridBagConstraints.NONE;
    pane.add(new JButton("Second row"), gbc);
    pane.add(new JButton("Third row"), gbc);
    f.setSize(600, 300);
    f.setVisible(true);
  }

}