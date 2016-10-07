import java.awt.*;
import javax.swing.*;

public class RelativeY {

  public static void main(String[] args) {
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Container pane = f.getContentPane();
    pane.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    pane.add(new JButton("First column"), gbc);
    gbc.gridx = 1;
    gbc.gridy = GridBagConstraints.RELATIVE;
    pane.add(new JButton("Second column, first row"), gbc);
    pane.add(new JButton("Second column, second row"), gbc);
    pane.add(new JButton("Second column, third row"), gbc);
    gbc.gridx = 2;
    pane.add(new JButton("Third column"), gbc);
    f.setSize(500, 300);
    f.setVisible(true);
  }

}