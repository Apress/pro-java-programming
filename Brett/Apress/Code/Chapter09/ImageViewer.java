import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.io.*;
import javax.swing.*;

public class ImageViewer extends JPanel {

  public final static DataFlavor LABEL_FLAVOR =
      new DataFlavor(JLabel.class, "Label Instances");

  public final static DataFlavor LOCAL_LABEL_FLAVOR = new DataFlavor(
      DataFlavor.javaJVMLocalObjectMimeType +
      "; class=javax.swing.JLabel", "Local Label");

  protected DragSourceListener sourceListener;
  protected JLabel draggedComponent;

  public static void main(String[] args) {
    JFrame f = new JFrame("ImageViewer");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setContentPane(new ImageViewer());
    f.setSize(400, 300);
    f.setVisible(true);
  }

  public ImageViewer() {
    super();
    setLayout(null);
    DropTarget dt = new DropTarget(this,
        DnDConstants.ACTION_COPY_OR_MOVE,
        new MyDropListener());
    sourceListener = new MySourceListener();
  }

  protected JLabel getLabelFromFile(File f) {
    ImageIcon icon = new ImageIcon(f.getAbsolutePath());
    JLabel label = new JLabel(icon);
    label.setText(f.getName());
    label.setHorizontalTextPosition(JLabel.CENTER);
    label.setVerticalTextPosition(JLabel.BOTTOM);
    return label;
  }

  protected void addNewComponent(Component comp, Point location) {
    DragSource source = DragSource.getDefaultDragSource();
    source.createDefaultDragGestureRecognizer(comp,
        DnDConstants.ACTION_COPY_OR_MOVE,
        new MyGestureListener());
    comp.setLocation(location);
    comp.setSize(comp.getPreferredSize());
    add(comp);
    repaint();
  }


  class LabelSelection implements Transferable {

    private DataFlavor[] flavors = {LABEL_FLAVOR, LOCAL_LABEL_FLAVOR};

    protected JLabel label;

    public LabelSelection(JLabel lbl) {
      label = lbl;
    }

    public DataFlavor[] getTransferDataFlavors() {
      return flavors;
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
      for (int i = 0; i < flavors.length; i++) {
        if (flavors[i].equals(flavor)) return true;
      }
      return false;
    }

    public Object getTransferData(DataFlavor flavor) throws
        UnsupportedFlavorException, IOException {
      if (flavor.equals(LABEL_FLAVOR)) {
        return label;
      }
      else if (flavor.equals(LOCAL_LABEL_FLAVOR)) {
        return label;
      }
      throw new UnsupportedFlavorException(flavor);
    }

  }

  class MyGestureListener implements DragGestureListener {

    public void dragGestureRecognized(DragGestureEvent event) {
      Cursor cursor = null;
      draggedComponent = (JLabel)(event.getComponent());
      switch (event.getDragAction()) {
        case DnDConstants.ACTION_MOVE:
          cursor = DragSource.DefaultMoveNoDrop;
          break;
        case DnDConstants.ACTION_COPY:
          cursor = DragSource.DefaultCopyNoDrop;
          break;
        case DnDConstants.ACTION_LINK:
          cursor = DragSource.DefaultLinkNoDrop;
          break;
      }
      event.startDrag(cursor,
          new LabelSelection(draggedComponent),
          sourceListener);
    }
  }

  class MySourceListener implements DragSourceListener {

    public void dragEnter(DragSourceDragEvent event) {};
    public void dragExit(DragSourceEvent event) {};
    public void dragOver(DragSourceDragEvent event) {};
    public void dropActionChanged(DragSourceDragEvent event) {};

    public void dragDropEnd(DragSourceDropEvent event) {
      if ((event.getDropSuccess()) 
              && (event.getDropAction() == DnDConstants.ACTION_MOVE)) {
        remove(draggedComponent);
        repaint();
      } 
      draggedComponent = null;
    } 
  }

  class MyDropListener implements DropTargetListener {

    public void dragEnter(DropTargetDragEvent event) {
      if (event.isDataFlavorSupported(
          DataFlavor.javaFileListFlavor)) {
        return;
      }
      event.rejectDrag();
    }

    public void dragExit(DropTargetEvent event) {
    }

    public void dragOver(DropTargetDragEvent event) {
    }

    public void dropActionChanged(DropTargetDragEvent event) {
    }

    public void drop(DropTargetDropEvent event) {
      if (event.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
        try {
          event.acceptDrop(DnDConstants.ACTION_COPY);
          Transferable t = event.getTransferable();
          java.util.List list = (java.util.List) 
                  (t.getTransferData(DataFlavor.javaFileListFlavor));
          java.util.Iterator i = list.iterator();
          while (i.hasNext()) {
            JLabel label = getLabelFromFile((File)(i.next()));
            addNewComponent(label, event.getLocation());
          } 
          event.dropComplete(true);
        } catch (Exception e) {
          event.dropComplete(false);
        } 
      } else if (event.isDataFlavorSupported(LABEL_FLAVOR)) {
        try {
          int operation = (event.isLocalTransfer() 
                           ? DnDConstants.ACTION_REFERENCE 
                           : DnDConstants.ACTION_MOVE);
          event.acceptDrop(operation);
          Transferable t = event.getTransferable();
          boolean doLocal = (event.isLocalTransfer() &&  
              (t.isDataFlavorSupported(LOCAL_LABEL_FLAVOR)));
          DataFlavor flavor = (doLocal ? LOCAL_LABEL_FLAVOR : LABEL_FLAVOR);
          JLabel label = (JLabel)(t.getTransferData(flavor));
          addNewComponent(label, event.getLocation());
          event.dropComplete(true);
        } catch (Exception e) {
          event.dropComplete(false);
        }
      }
    }

  }
}