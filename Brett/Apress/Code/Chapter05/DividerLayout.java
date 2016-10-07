import java.awt.*;

public class DividerLayout implements LayoutManager2 {

  public final static String WEST = "WEST";
  public final static String EAST = "EAST";
  public final static String CENTER = "CENTER";

  protected Component westComponent;
  protected Component centerComponent;
  protected Component eastComponent;

  public void addLayoutComponent(Component comp, Object constraints) {
    if (WEST.equalsIgnoreCase((String)constraints)) {
      westComponent = comp;
    }
    else if (CENTER.equalsIgnoreCase((String)constraints)) {
      centerComponent = comp;
    }
    else if (EAST.equalsIgnoreCase((String)constraints)) {
      eastComponent = comp;
    }
  }

  public Dimension maximumLayoutSize(Container target) {
    Dimension size;
    int width = 0;
    int height = 0;
    if ((westComponent != null) && (westComponent.isVisible())) {
      size = westComponent.getMaximumSize();
      width = Math.max(width, size.width);
      height = Math.max(height, size.height);
    }
    if ((eastComponent != null) && (eastComponent.isVisible())) {
      size = eastComponent.getMaximumSize();
      width = Math.max(width, size.width);
      height = Math.max(height, size.height);
    }
    width *= 2;
    if ((centerComponent != null) && (centerComponent.isVisible())) {
      size = centerComponent.getPreferredSize();
      width += size.width;
      height = Math.max(height, size.height);
    }
    return new Dimension(width, height);
  }

  public float getLayoutAlignmentX(Container target) {
    return 0.0f;
  }

  public float getLayoutAlignmentY(Container target) {
    return 0.0f;
  }

  public void invalidateLayout(Container target) {
  }

  public void addLayoutComponent(String name, Component comp) {
    addLayoutComponent(comp, name);
  }

  public void removeLayoutComponent(Component comp) {
    if (comp == westComponent) {
      westComponent = null;
    }
    else if (comp == centerComponent) {
      centerComponent = null;
    }
    else if (comp == eastComponent) {
      centerComponent = null;
    }
  }

  public Dimension preferredLayoutSize(Container parent) {
    Dimension size;
    int width = 0;
    int height = 0;
    if ((westComponent != null) && (westComponent.isVisible())) {
      size = westComponent.getPreferredSize();
      width = Math.max(width, size.width);
      height = Math.max(height, size.height);
    }
    if ((eastComponent != null) && (eastComponent.isVisible())) {
      size = eastComponent.getPreferredSize();
      width = Math.max(width, size.width);
      height = Math.max(height, size.height);
    }
    width *= 2;
    if ((centerComponent != null) && (centerComponent.isVisible())) {
      size = centerComponent.getPreferredSize();
      width += size.width;
      height = Math.max(height, size.height);
    }
    return new Dimension(width, height);
  }

  public Dimension minimumLayoutSize(Container parent) {
    Dimension size;
    int width = 0;
    int height = 0;
    if ((westComponent != null) && (westComponent.isVisible())) {
      size = westComponent.getMinimumSize();
      width = Math.max(width, size.width);
      height = Math.max(height, size.height);
    }
    if ((eastComponent != null) && (eastComponent.isVisible())) {
      size = eastComponent.getMinimumSize();
      width = Math.max(width, size.width);
      height = Math.max(height, size.height);
    }
    width *= 2;
    if ((centerComponent != null) && (centerComponent.isVisible())) {
      size = centerComponent.getPreferredSize();
      width += size.width;
      height += Math.max(height, size.height);
    }
    return new Dimension(width, height);
  }

  public void layoutContainer(Container container) {
    Insets insets = container.getInsets();
    Dimension westSize = new Dimension(0, 0);
    Dimension centerSize = new Dimension(0, 0);
    Dimension eastSize = new Dimension(0, 0);
    Rectangle centerBounds = new Rectangle(0, 0, 0, 0);
    Dimension containerSize = container.getSize();
    int centerX = containerSize.width / 2;
    int centerY = containerSize.height / 2;
    if ((centerComponent != null) &&
        (centerComponent.isVisible())) {
      centerSize = centerComponent.getPreferredSize();
      centerSize.width = Math.min(centerSize.width,
          containerSize.width - insets.left -
          insets.right);
      centerSize.height = Math.min(centerSize.height,
          containerSize.height - insets.top -
          insets.bottom);
      centerComponent.setBounds(centerX -
          (centerSize.width / 2),
          centerY - (centerSize.height / 2),
          centerSize.width, centerSize.height);
      centerBounds = centerComponent.getBounds();
    }
    if ((westComponent != null) && (westComponent.isVisible())) {
      westSize = westComponent.getPreferredSize();
    }
    if ((eastComponent != null) && (eastComponent.isVisible())) {
      eastSize = eastComponent.getPreferredSize();
    }
    int maxWidth = Math.min(westSize.width, eastSize.width);
    maxWidth = Math.max(maxWidth, (containerSize.width -
        centerBounds.width - insets.left -
        insets.right) / 2);
    int maxHeight = Math.min(westSize.height, eastSize.height);
    maxHeight = Math.min(maxHeight, containerSize.height -
        insets.top - insets.bottom);
    if (westComponent != null) {
      westComponent.setBounds(centerBounds.x - maxWidth,
          centerY - (maxHeight / 2),
          maxWidth, maxHeight);
    }
    if (eastComponent != null) {
      eastComponent.setBounds(centerBounds.x +
          centerBounds.width,
          centerY - (maxHeight / 2),
          maxWidth, maxHeight);
    }
  }

}
