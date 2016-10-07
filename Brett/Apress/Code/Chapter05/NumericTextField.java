import javax.swing.*;
import javax.swing.text.*;

public class NumericTextField extends JTextField {

  protected boolean allowDecimal;

  public NumericTextField(int size, boolean decimal) {
    super(size);
    setDocument(new NumericDocument());
    allowDecimal = decimal;
  }

  class NumericDocument extends PlainDocument {

    public void insertString(int offset, String text, AttributeSet as)
        throws BadLocationException {
      if (isInsertAllowed(offset, text)) {
        super.insertString(offset, text, as);
      }
    }

    protected boolean isInsertAllowed(int offset,
        String text) throws BadLocationException {
      boolean allowed = true;
      int length = getLength();
      String current = getText(0, length);
      String before = current.substring(0, offset);
      String after = current.substring(offset);
      String newText = before + text + after;
      try {
        if (allowDecimal) {
          Double d = Double.valueOf(newText);
        }
        else {
          Integer.parseInt(newText);
        }
      } catch (NumberFormatException nfe) {
        allowed = false;
      }
      return allowed;
    }

  }

}