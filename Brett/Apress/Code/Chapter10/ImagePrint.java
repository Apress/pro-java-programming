import java.awt.*;
import java.awt.print.*;
import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;
import javax.swing.ImageIcon;

public class ImagePrint {

  protected ImageIcon printImage;

  public static void main(String[] args) throws Exception {
    ImagePrint ip = new ImagePrint(args[0]);
    ip.performPrint();
    System.exit(0);
  }

  public ImagePrint(String fileName) {
    printImage = new javax.swing.ImageIcon(fileName);
  }

  private void performPrint() throws Exception {
    PrintService service = PrintServiceLookup.lookupDefaultPrintService();
    DocPrintJob job = service.createPrintJob();
    DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
    SimpleDoc doc = new SimpleDoc(new MyPrintable(), flavor, null);
    job.print(doc, null);
  }

  class MyPrintable implements Printable {

    public int print(Graphics g, PageFormat pf, int pageIndex) {
    Graphics2D g2d = (Graphics2D)g;
    g.translate((int)(pf.getImageableX()),
        (int)(pf.getImageableY()));
    if (pageIndex == 0) {
        double pageWidth = pf.getImageableWidth();
        double pageHeight = pf.getImageableHeight();
        double imageWidth = printImage.getIconWidth();
        double imageHeight = printImage.getIconHeight();
        // Find out what scale factor should be applied
        // to make the image's width small enough to
        // fit on the page
        double scaleX = pageWidth / imageWidth;
        // Now do the same for the height
        double scaleY = pageHeight / imageHeight;
        // Pick the smaller of the two values so that
        // the image is as large as possible while
        // not exceeding either the page's width or
        // its height
        double scaleFactor = Math.min(scaleX, scaleY);
        // Now set the scale factor
        g2d.scale(scaleFactor, scaleFactor);
        g.drawImage(printImage.getImage(), 0, 0, null);
        return Printable.PAGE_EXISTS;
      }
      return Printable.NO_SUCH_PAGE;
    }

  }

}
