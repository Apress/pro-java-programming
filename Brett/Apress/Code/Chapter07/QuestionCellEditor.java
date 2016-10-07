import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;

public class QuestionCellEditor extends DefaultCellEditor {

  protected TrueFalseQuestion question;

  public QuestionCellEditor() {
    super(new JCheckBox());
  }

  public Component getTreeCellEditorComponent(JTree tree, Object value,
      boolean selected, boolean expanded, boolean leaf,
      int row) {
    JCheckBox editor = null;
    question = getQuestionFromValue(value);
    if (question != null) {
      editor = (JCheckBox)(super.getComponent());
      editor.setText(question.getQuestion());
      editor.setSelected(question.getAnswer());
    }
    return editor;
  }

  public static TrueFalseQuestion getQuestionFromValue(
      Object value) {
    if (value instanceof DefaultMutableTreeNode) {
      DefaultMutableTreeNode node =
          (DefaultMutableTreeNode)value;
      Object userObject = node.getUserObject();
      if (userObject instanceof TrueFalseQuestion) {
        return (TrueFalseQuestion)userObject;
      }
    }
    return null;
  }

  public Object getCellEditorValue() {
    JCheckBox editor = (JCheckBox)(super.getComponent());
    question.setAnswer(editor.isSelected());
    return question;
  }

}