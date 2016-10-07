import java.util.*;

public class Hardcoded {

  public static void main(String[] args) {
    ResourceBundle myBundle = ResourceBundle.getBundle(
        "MyResources");
//    System.out.println("The number of arguments entered is " +
//                         args.length);
    String msg = myBundle.getString("MsgText");
    System.out.println(msg + args.length);
  }
}
