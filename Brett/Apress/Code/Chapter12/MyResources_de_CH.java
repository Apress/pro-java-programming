import java.util.ListResourceBundle;
import javax.swing.ImageIcon;

public class MyResources_de_CH extends ListResourceBundle {

  protected static Object[][] resources = {
    {"FlagIcon", new ImageIcon("flags/switzerland.gif")}
  };

  public Object[][] getContents() {
    return resources;
  }
}