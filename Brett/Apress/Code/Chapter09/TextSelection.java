import java.awt.datatransfer.*;
import java.io.*;

public class TextSelection implements Transferable {

  protected String text;

  public final static DataFlavor UNICODE_FLAVOR = new DataFlavor(
      "text/plain; charset=unicode; " +
      "class=java.io.InputStream", "Unicode Text");
  public final static DataFlavor LATIN1_FLAVOR = new DataFlavor(
      "text/plain; charset=iso-8859-1; " +
      "class=java.io.InputStream", "Latin-1 Text");
  public final static DataFlavor ASCII_FLAVOR = new DataFlavor(
      "text/plain; charset=ascii; " +
      "class=java.io.InputStream", "ASCII Text");


  public static DataFlavor[] SUPPORTED_FLAVORS = {DataFlavor.stringFlavor,
      UNICODE_FLAVOR, LATIN1_FLAVOR, ASCII_FLAVOR};

  public TextSelection(String selection) {
    text = selection;
  }

  public DataFlavor[] getTransferDataFlavors() {
    return SUPPORTED_FLAVORS;
  }

  public boolean isDataFlavorSupported(DataFlavor flavor) {
    for (int i = 0; i < SUPPORTED_FLAVORS.length; i++) {
      if (SUPPORTED_FLAVORS[i].equals(flavor)) return true;
    }
    return false;
  }

  public Object getTransferData(DataFlavor flavor) throws
      IOException, UnsupportedFlavorException {
    if (flavor.equals(DataFlavor.stringFlavor)) {
      return text;
    }
    else if ((flavor.isMimeTypeEqual("text/plain")) &&
        (flavor.getRepresentationClass().equals(
        java.io.InputStream.class))) try {
      String encoding = flavor.getParameter("charset");
      if ((encoding != null) && (encoding.length() > 0)) {
        return new ByteArrayInputStream(
          text.getBytes(encoding));
      }
      return new ByteArrayInputStream(text.getBytes());
    } catch (Exception e) {};
    throw new UnsupportedFlavorException(flavor);
  }

}
