import java.awt.Component;

public class InputValidationException extends Exception {

  protected Component errorSource;

  public InputValidationException(String message, Component source) {
    super(message);
    errorSource = source;
  }

}
