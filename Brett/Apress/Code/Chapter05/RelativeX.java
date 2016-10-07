import java.awt.*;
import javax.swing.*;

public class RelativeX {

  public static void main(String[] args) {
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Container pane = f.getContentPane();
    pane.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridy = 0;
    pane.add(new JButton("First row"), gbc);
    gbc.gridx = GridBagConstraints.RELATIVE;
    gbc.gridy = 1;
    pane.add(new JButton("Second row, first column"), gbc);
    pane.add(new JButton("Second row, second column"), gbc);
    pane.add(new JButton("Second row, third column"), gbc);
    gbc.gridy = 2;
    pane.add(new JButton("Third row"), gbc);
    f.setSize(600, 300);
    f.setVisible(true);
  }

}
