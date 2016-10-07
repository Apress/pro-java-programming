import javax.swing.*;

public class SimpleTreeTest extends JFrame {

  public static void main(String[] args) {
    SimpleTreeTest stt = new SimpleTreeTest();
    stt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    stt.setSize(250, 250);
    stt.setVisible(true);
  }

  public SimpleTreeTest() {
    Object[] genealogy = {"Jeff", "Joseph", "Pearl", "Owen", "Sarah",
        "John"};
    java.util.Vector v = new java.util.Vector() {
      public String toString() {
        return "Jeff";
      }
    };
    v.addElement("Jerry");
    v.addElement("Selma");
    v.addElement("Joe");
    v.addElement("Evelyn");
    genealogy[0] = v;
    JTree tree = new JTree(genealogy);
    tree.setRootVisible(true);
    JScrollPane jsp = new JScrollPane(tree);
    getContentPane().add(jsp);
  }

}
