import java.awt.*;
import javax.swing.*;

public class Fill {

  public static void main(String[] args) {
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Container pane = f.getContentPane();
    pane.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = GridBagConstraints.RELATIVE;
    pane.add(new JButton("This button's preferred width " +
        "is large because its text is long"),
        gbc);
    pane.add(new JButton("Small centered button"), gbc);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    pane.add(new JButton("Expands to fill column width"), gbc);
    f.setSize(400, 300);
    f.setVisible(true);
  }

}
