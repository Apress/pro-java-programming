import java.awt.*;
import javax.swing.*;

public class RelativeXY {

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
    pane.add(new JButton("Third row"), gbc);
    gbc.gridx = GridBagConstraints.RELATIVE;
    pane.add(new JButton("First row, second column"), gbc);
    f.setSize(500, 300);
    f.setVisible(true);
  }

}